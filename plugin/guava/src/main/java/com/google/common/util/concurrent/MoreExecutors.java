/*
 * Copyright (C) 2007 The Guava Authors
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

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory and utility methods for {@link java.util.concurrent.Executor}, {@link ExecutorService},
 * and {@link ThreadFactory}.
 *
 * @author Eric Fellheimer
 * @author Kyle Littlefield
 * @author Justin Mahoney
 * @since 3.0
 */
public final class MoreExecutors {

  private MoreExecutors() {
  }

  /**
   * Returns an {@link Executor} that runs each task in the thread that invokes {@link
   * Executor#execute execute}, as in {@link CallerRunsPolicy}.
   *
   * <p>This instance is equivalent to:
   *
   * <pre>{@code
   * final class DirectExecutor implements Executor {
   *   public void execute(Runnable r) {
   *     r.run();
   *   }
   * }
   * }</pre>
   * <p>
   * {@link ExecutorService} subinterface necessitates significant performance overhead.
   *
   * @since 18.0
   */
  public static Executor directExecutor() {
    return DirectExecutor.INSTANCE;
  }

  static Executor rejectionPropagatingExecutor(final Executor delegate,
      final AbstractFuture<?> future) {
    checkNotNull(delegate);
    checkNotNull(future);
    if (delegate == directExecutor()) {
      // directExecutor() cannot throw RejectedExecutionException
      return delegate;
    }
    return new Executor() {
      boolean thrownFromDelegate = true;

      @Override
      public void execute(final Runnable command) {
        try {
          delegate.execute(new Runnable() {
            @Override
            public void run() {
              thrownFromDelegate = false;
              command.run();
            }
          });
        } catch (RejectedExecutionException e) {
          if (thrownFromDelegate) {
            // wrap exception?
            future.setException(e);
          }
          // otherwise it must have been thrown from a transitive call and the delegate runnable
          // should have handled it.
        }
      }
    };
  }

  /**
   * See {@link #directExecutor} for behavioral notes.
   */
  private enum DirectExecutor implements Executor {
    INSTANCE;

    @Override
    public void execute(Runnable command) {
      command.run();
    }

    @Override
    public String toString() {
      return "MoreExecutors.directExecutor()";
    }
  }

}
