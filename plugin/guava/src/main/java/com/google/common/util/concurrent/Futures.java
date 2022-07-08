package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.util.concurrent.Uninterruptibles.getUninterruptibly;

public class Futures {

  /**
   * Creates a {@code ListenableFuture} which has its value set immediately upon construction. The
   * getters just return the value. This {@code Future} can't be canceled or timed out and its
   * {@code isDone()} method always returns {@code true}.
   */
  public static <V> ListenableFuture<V> immediateFuture(@Nullable V value) {
    if (value == null) {
      // This cast is safe because null is assignable to V for all V (i.e. it is covariant)
      @SuppressWarnings({"unchecked",
          "rawtypes"}) ListenableFuture<V> typedNull = (ListenableFuture) ImmediateFuture.ImmediateSuccessfulFuture.NULL;
      return typedNull;
    }
    return new ImmediateFuture.ImmediateSuccessfulFuture<V>(value);
  }

  public static <V, X extends Throwable> ListenableFuture<V> catching(
      ListenableFuture<? extends V> input, Class<X> exceptionType,
      Function<? super X, ? extends V> fallback, Executor executor) {
    return AbstractCatchingFuture.create(input, exceptionType, fallback, executor);
  }

  public static <V> V getDone(Future<V> future) throws ExecutionException {
    /*
     * We throw IllegalStateException, since the call could succeed later. Perhaps we "should" throw
     * IllegalArgumentException, since the call could succeed with a different argument. Those
     * exceptions' docs suggest that either is acceptable. Google's Java Practices page recommends
     * IllegalArgumentException here, in part to keep its recommendation simple: Static methods
     * should throw IllegalStateException only when they use static state.
     *
     *
     * Why do we deviate here? The answer: We want for fluentFuture.getDone() to throw the same
     * exception as Futures.getDone(fluentFuture).
     */
    checkState(future.isDone(), "Future was expected to be done: %s", future);
    return getUninterruptibly(future);
  }

  public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
    checkNotNull(throwable);
    return new ImmediateFuture.ImmediateFailedFuture<V>(throwable);
  }

  /**
   * Returns a new {@code Future} whose result is derived from the result of the given {@code
   * Future}. If {@code input} fails, the returned {@code Future} fails with the same exception (and
   * the function is not invoked). Example usage:
   *
   * <pre>{@code
   * ListenableFuture<QueryResult> queryFuture = ...;
   * ListenableFuture<List<Row>> rowsFuture =
   *     transform(queryFuture, QueryResult::getRows, executor);
   * }</pre>
   *
   * <p>When selecting an executor, note that {@code directExecutor} is dangerous in some cases.
   * See the discussion in the {@link ListenableFuture#addListener ListenableFuture.addListener}
   * documentation. All its warnings about heavyweight listeners are also applicable to heavyweight
   * functions passed to this method.
   *
   * <p>The returned {@code Future} attempts to keep its cancellation state in sync with that of
   * the input future. That is, if the returned {@code Future} is cancelled, it will attempt to
   * cancel the input, and if the input is cancelled, the returned {@code Future} will receive a
   * callback in which it will attempt to cancel itself.
   *
   * <p>An example use of this method is to convert a serializable object returned from an RPC into
   * a POJO.
   *
   * @param input    The future to transform
   * @param function A Function to transform the results of the provided future to the results of
   *                 the returned future.
   * @param executor Executor to run the function in.
   * @return A future that holds result of the transformation.
   * @since 9.0 (in 2.0 as {@code compose})
   */
  public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input,
      Function<? super I, ? extends O> function, Executor executor) {
    return AbstractTransformFuture.create(input, function, executor);
  }


}
