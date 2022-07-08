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

import java.util.concurrent.Executor;

/**
 * A {@link ListenableFuture} that supports fluent chains of operations. For example:
 *
 * <pre>{@code
 * ListenableFuture<Boolean> adminIsLoggedIn =
 *     FluentFuture.from(usersDatabase.getAdminUser())
 *         .transform(User::getId, directExecutor())
 *         .transform(ActivityService::isLoggedIn, threadPool)
 *         .catching(RpcException.class, e -> false, directExecutor());
 * }</pre>
 *
 * <h3>Alternatives</h3>
 *
 * <h4>Frameworks</h4>
 *
 * <p>When chaining together a graph of asynchronous operations, you will often find it easier to
 * use a framework. Frameworks automate the process, often adding features like monitoring,
 * debugging, and cancellation. Examples of frameworks include:
 *
 * <ul>
 * <li><a href="http://google.github.io/dagger/producers.html">Dagger Producers</a>
 * </ul>
 *
 * <h4>{@link java.util.concurrent.CompletableFuture} / {@link java.util.concurrent.CompletionStage}
 * </h4>
 *
 * <p>Users of {@code CompletableFuture} will likely want to continue using {@code
 * CompletableFuture}. {@code FluentFuture} is targeted at people who use {@code ListenableFuture},
 * who can't use Java 8, or who want an API more focused than {@code CompletableFuture}. (If you
 * need to adapt between {@code CompletableFuture} and {@code ListenableFuture}, consider <a
 * href="https://github.com/lukas-krecan/future-converter">Future Converter</a>.)
 *
 * <h3>Extension</h3>
 * <p>
 * If you want a class like {@code FluentFuture} but with extra methods, we recommend declaring your
 * own subclass of {@link ListenableFuture}, complete with a method like {@link #from} to adapt an
 * existing {@code ListenableFuture}, implemented atop a {@link ForwardingListenableFuture} that
 * forwards to that future and adds the desired methods.
 *
 * @since 23.0
 */
public abstract class FluentFuture<V> implements ListenableFuture<V> {

  FluentFuture() {
  }

  public final <X extends Throwable> FluentFuture<V> catching(Class<X> exceptionType,
      Function<? super X, ? extends V> fallback, Executor executor) {
    return (FluentFuture<V>) Futures.catching(this, exceptionType, fallback, executor);
  }

}
