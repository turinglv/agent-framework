/*
 * Copyright (C) 2006 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.util.concurrent.Futures.getDone;
import static com.google.common.util.concurrent.MoreExecutors.rejectionPropagatingExecutor;

/**
 * Implementations of {@code Futures.catching*}.
 */
abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends
    AbstractFuture.TrustedFuture<V> implements Runnable {

  /*
   * In certain circumstances, this field might theoretically not be visible to an afterDone() call
   * triggered by cancel(). For details, see the comments on the fields of TimeoutFuture.
   */
  @Nullable ListenableFuture<? extends V> inputFuture;
  @Nullable Class<X> exceptionType;
  @Nullable F fallback;

  AbstractCatchingFuture(ListenableFuture<? extends V> inputFuture, Class<X> exceptionType,
      F fallback) {
    this.inputFuture = checkNotNull(inputFuture);
    this.exceptionType = checkNotNull(exceptionType);
    this.fallback = checkNotNull(fallback);
  }

  static <V, X extends Throwable> ListenableFuture<V> create(ListenableFuture<? extends V> input,
      Class<X> exceptionType, Function<? super X, ? extends V> fallback, Executor executor) {
    CatchingFuture<V, X> future = new CatchingFuture<>(input, exceptionType, fallback);
    input.addListener(future, rejectionPropagatingExecutor(executor, future));
    return future;
  }

  @Override
  public final void run() {
    ListenableFuture<? extends V> localInputFuture = inputFuture;
    Class<X> localExceptionType = exceptionType;
    F localFallback = fallback;
    if (localInputFuture == null | localExceptionType == null | localFallback == null
        | isCancelled()) {
      return;
    }
    inputFuture = null;

    // For an explanation of the cases here, see the comments on AbstractTransformFuture.run.
    V sourceResult = null;
    Throwable throwable = null;
    try {
      sourceResult = getDone(localInputFuture);
    } catch (ExecutionException e) {
      throwable = checkNotNull(e.getCause());
    } catch (Throwable e) { // this includes cancellation exception
      throwable = e;
    }

    if (throwable == null) {
      set(sourceResult);
      return;
    }

    X castThrowable = (X) throwable;
    T fallbackResult;
    try {
      fallbackResult = doFallback(localFallback, castThrowable);
    } catch (Throwable t) {
      setException(t);
      return;
    } finally {
      exceptionType = null;
      fallback = null;
    }

    setResult(fallbackResult);
  }

  @Override
  protected String pendingToString() {
    ListenableFuture<? extends V> localInputFuture = inputFuture;
    Class<X> localExceptionType = exceptionType;
    F localFallback = fallback;
    String superString = super.pendingToString();
    String resultString = "";
    if (localInputFuture != null) {
      resultString = "inputFuture=[" + localInputFuture + "], ";
    }
    if (localExceptionType != null && localFallback != null) {
      return resultString + "exceptionType=[" + localExceptionType + "], fallback=[" + localFallback
          + "]";
    } else if (superString != null) {
      return resultString + superString;
    }
    return null;
  }

  /**
   * Template method for subtypes to actually run the fallback.
   */

  abstract @Nullable T doFallback(F fallback, X throwable) throws Exception;

  /**
   * Template method for subtypes to actually set the result.
   */

  abstract void setResult(@Nullable T result);

  @Override
  protected final void afterDone() {
    maybePropagateCancellationTo(inputFuture);
    this.inputFuture = null;
    this.exceptionType = null;
    this.fallback = null;
  }

  /**
   * An {@link AbstractCatchingFuture} that delegates to a {@link Function} and {@link
   * #set(Object)}.
   */
  private static final class CatchingFuture<V, X extends Throwable> extends
      AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {

    CatchingFuture(ListenableFuture<? extends V> input, Class<X> exceptionType,
        Function<? super X, ? extends V> fallback) {
      super(input, exceptionType, fallback);
    }

    @Override
    V doFallback(Function<? super X, ? extends V> fallback, X cause) throws Exception {
      return fallback.apply(cause);
    }

    @Override
    void setResult(V result) {
      set(result);
    }
  }
}
