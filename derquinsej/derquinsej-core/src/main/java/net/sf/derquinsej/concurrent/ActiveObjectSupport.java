/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.derquinsej.concurrent;

import java.util.ConcurrentModificationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.base.Preconditions;

/**
 * Support class for implementing active objects. This class provides basic
 * lifecycle semantics and additional support methods so that different
 * semantics may be implemented upon this class.
 * @author Andres Rodriguez
 */
public final class ActiveObjectSupport {
	/** Singleton OFF state. */
	private static final Off OFF = new Off();
	/** Singleton ON state with zero in-flight requests. */
	private static final On ON = new On();

	/** Action to perform when the object is started. */
	private final Runnable onStart;
	/** Action to perform when the object is stopped. */
	private final Runnable onStop;
	/** Action to perform when an exception is thrown during startup. */
	private final Runnable onAbort;

	/** Executor for asynchronous transitions. */
	private final Executor executor;

	/** Current state. */
	private final AtomicReference<State> currentState = new AtomicReference<State>(OFF);

	/**
	 * Constructs a new object.
	 * @param onStart Action to perform when the object is started.
	 * @param onAbort Action to perform when the object is stopped.
	 * @param onStop Action to perform when an exception is thrown during
	 *            startup.
	 * @param executor Executor for asynchronous transitions. Set to {@code
	 *            null} if you don't want support for asynchronous transitions.
	 */
	public ActiveObjectSupport(final Runnable onStart, final Runnable onAbort, final Runnable onStop,
			final Executor executor) {
		this.onStart = onStart;
		this.onStop = onStop;
		this.onAbort = onAbort;
		this.executor = executor;
	}

	/**
	 * Constructs a new object with support for asynchronous transitions using a
	 * default executor.
	 * @param onStart Action to perform when the object is started.
	 * @param onAbort Action to perform when the object is stopped.
	 * @param onStop Action to perform when an exception is thrown during
	 *            startup.
	 */
	public ActiveObjectSupport(final Runnable onStart, final Runnable onAbort, final Runnable onStop) {
		this(onStart, onAbort, onStop, Executors.newSingleThreadExecutor());
	}

	/**
	 * Returns the current status of the object.
	 */
	public ActiveObjectStatus getStatus() {
		return currentState.get().getStatus();
	}

	/**
	 * Check if the object is currently in a transient status.
	 * @param state Current state.
	 * @throws ConcurrentModificationException if the object is currently in a
	 *             transient status.
	 */
	private void checkTransient(final State state) {
		final ActiveObjectStatus status = state.getStatus();
		if (status == ActiveObjectStatus.STARTING || status == ActiveObjectStatus.STOPPING) {
			throw new ConcurrentModificationException();
		}
	}

	/**
	 * Ensures that the object supports asynchronous transitions.
	 * @throws IllegalStateException if it does not.
	 */
	private void supportsAsync() {
		Preconditions.checkState(executor != null, "This object does not support asynchronous transitions");
	}

	/**
	 * Starts the object.
	 * <ul>
	 * <li>If the object is already ON nothing happens.</li>
	 * <li>If the object is in a transient state {@code
	 * ConcurrentModificationException} is thrown.
	 * <li>If the object is OFF:</li>
	 * <ul>
	 * <li>The object is set to status STARTING.</li>
	 * <li>The {@code onStart} block (if provided) is executed.</li>
	 * <li>If the block completes normally, the object is set to status ON.</li>
	 * <li>If the block throws an exception:</li>
	 * <ul>
	 * <li>The {@code onAbort} block (if provided) is executed.</li>
	 * <li>The object is set to status OFF.</li>
	 * <li>The exception thrown by the {@code onStart} block is propagated to
	 * the caller of this method.</li>
	 * </ul>
	 * </ul></ul>
	 */
	public void start() {
		while (!start(currentState.get(), true)) {
			// try again
		}
	}

	/**
	 * Starts the object asynchronously. Same operation that the synchronous
	 * version. The checks are performed in the calling thread and the
	 * transition in another thread.
	 */
	public void startAsynchronously() {
		supportsAsync();
		while (!start(currentState.get(), false)) {
			// try again
		}
	}

	/**
	 * Tries to start the object from an given state.
	 * @param state Initial state.
	 * @param sync If the transition is to be made synchronously.
	 * @return false if the call must be retried.
	 */
	private boolean start(State state, boolean sync) {
		if (state instanceof On) {
			return true;
		}
		checkTransient(state);
		final Starting starting = new Starting();
		if (!currentState.compareAndSet(state, starting)) {
			return false;
		}
		if (sync) {
			doStart(starting);
		} else {
			final Runnable transition = new Runnable() {
				public void run() {
					doStart(starting);
				}
			};
			executor.execute(transition);
		}
		return true;
	}

	/**
	 * Performs the actual start.
	 * @param starting Starting state.
	 */
	private void doStart(Starting starting) {
		boolean ok = false;
		try {
			if (onStart != null) {
				onStart.run();
			}
			currentState.compareAndSet(starting, ON);
			ok = true;
		} finally {
			if (!ok) {
				abort();
			}
			starting.done();
		}
	}

	/**
	 * Performs the actual abort.
	 */
	private void abort() {
		try {
			if (onAbort != null) {
				onAbort.run();
			}
		} catch (Throwable t) {
			// do nothing;
		} finally {
			currentState.set(OFF);
		}
	}

	public final void stop() {
		while (!stop(currentState.get(), true)) {
			// try again
		}
	}

	/**
	 * Stops the object asynchronously. Same operation that the synchronous
	 * version. The checks are performed in the calling thread and the
	 * transition in another thread.
	 */
	public void stopAsynchronously() {
		supportsAsync();
		while (!stop(currentState.get(), false)) {
			// try again
		}
	}

	public final void stopNow() {
		while (!stopNow(currentState.get())) {
			// try again
		}
	}

	private boolean isStopped(final State state) {
		if (state instanceof Off) {
			return true;
		}
		checkTransient(state);
		return false;
	}

	private boolean stopNow(final State state) {
		if (isStopped(state)) {
			return true;
		}
		final Stopping stopping = new Stopping();
		if (!currentState.compareAndSet(state, stopping)) {
			return false;
		}
		stop(stopping);
		return true;
	}

	private boolean stop(final State state, boolean sync) {
		if (isStopped(state)) {
			return true;
		}
		if (state instanceof Waiting) {
			throw new ConcurrentModificationException();
		}
		// At this point, state is On
		final On on = (On) state;
		final Waiting waiting = new Waiting(on);
		if (!currentState.compareAndSet(state, waiting)) {
			return false;
		}
		final Runnable transition = new Runnable() {
			public void run() {
				try {
					waiting.awaitRequests();
				} catch (InterruptedException e) {
					// do nothing
				}
				stop(waiting);
			}
		};
		if (sync) {
			transition.run();
		} else {
			executor.execute(transition);
		}
		return true;
	}

	private void stop(Awaitable stopping) {
		try {
			if (onStop != null) {
				onStop.run();
			}
		} finally {
			currentState.set(OFF);
			stopping.done();
		}
	}

	public void begin() {
		boolean done = false;
		do {
			final State state = currentState.get();
			Preconditions.checkState(state instanceof On, "The object does not accept new requests");
			done = currentState.compareAndSet(state, new On((On) state, 1));
		} while (!done);
	}

	public void end() {
		boolean done = false;
		do {
			final State state = currentState.get();
			if (state instanceof Waiting) {
				((Waiting) state).end();
				done = true;
			} else {
				Preconditions.checkState(state instanceof On, "The object does not have any running requests");
				done = currentState.compareAndSet(state, new On((On) state, -1));
			}
		} while (!done);
	}

	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		final State state = currentState.get();
		if (state instanceof Awaitable) {
			return ((Awaitable) state).await(timeout, unit);
		}
		return true;
	}

	private static abstract class State {
		State() {
		}

		abstract ActiveObjectStatus getStatus();
	}

	private static final class Off extends State {
		Off() {
		}

		public ActiveObjectStatus getStatus() {
			return ActiveObjectStatus.OFF;
		}
	}

	private static abstract class Awaitable extends State {
		private final CountDownLatch latch = new CountDownLatch(1);

		Awaitable() {
		}

		final boolean await(long timeout, TimeUnit unit) throws InterruptedException {
			return latch.await(timeout, unit);
		}

		final void done() {
			latch.countDown();
		}
	}

	private static final class Starting extends Awaitable {
		Starting() {
		}

		@Override
		ActiveObjectStatus getStatus() {
			return ActiveObjectStatus.STARTING;
		}
	}

	private static final class On extends State {
		final int count;

		On() {
			this.count = 0;
		}

		On(On on, int delta) {
			this.count = Math.max(0, on.count + delta);
		}

		@Override
		ActiveObjectStatus getStatus() {
			return ActiveObjectStatus.ON;
		}
	}

	private static final class Stopping extends Awaitable {
		Stopping() {
		}

		@Override
		ActiveObjectStatus getStatus() {
			return ActiveObjectStatus.STOPPING;
		}
	}

	private static final class Waiting extends Awaitable {
		private final CountDownLatch latch;

		Waiting(On on) {
			this.latch = new CountDownLatch(on.count);
		}

		void end() {
			this.latch.countDown();
		}

		void awaitRequests() throws InterruptedException {
			latch.await();
		}

		@Override
		ActiveObjectStatus getStatus() {
			return ActiveObjectStatus.STOPPING;
		}
	}
}
