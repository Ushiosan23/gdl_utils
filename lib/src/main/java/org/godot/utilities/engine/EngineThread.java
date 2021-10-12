package org.godot.utilities.engine;

import org.godot.utilities.core.callback.IEngineCallbacks;
import org.godot.utilities.utils.Arr;
import org.godot.utilities.utils.ThreadUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.EventListenerList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class EngineThread extends Thread {

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Current engine instance
	 */
	private final Engine engine;

	/**
	 * Engine arguments
	 */
	private String[] engineArgs;

	/**
	 * List with all events
	 */
	private final EventListenerList listenerList = new EventListenerList();

	/**
	 * Current system process
	 */
	private Process currentProcess = null;

	/**
	 * Current thread state
	 */
	private volatile EngineState currentState = EngineState.IDLE;

	/* ------------------------------------------------------------------
	 *
	 * Constructors
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Default constructor
	 *
	 * @param e Target engine
	 */
	public EngineThread(@NotNull Engine e) {
		super(ThreadUtils.getEngineThreadGroup(), e.getResourcePath().toString());
		engine = e;
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Causes this thread to begin execution; the Java Virtual Machine
	 * calls the {@code run} method of this thread.
	 * <p>
	 * The result is that two threads are running concurrently: the
	 * current thread (which returns from the call to the
	 * {@code start} method) and the other thread (which executes its
	 * {@code run} method).
	 * <p>
	 * It is never legal to start a thread more than once.
	 * In particular, a thread may not be restarted once it has completed
	 * execution.
	 *
	 * @throws IllegalThreadStateException if the thread was already started.
	 * @see #run()
	 */
	@Override
	public synchronized void start() {
		start(new EngineArgs.Builder());
	}

	/**
	 * @param args Engine arguments
	 * @see #start()
	 */
	public synchronized void start(@NotNull EngineArgs.Builder args) {
		engineArgs = args.build(engine).generateCommand();
		super.start();
	}

	/**
	 * If this thread was constructed using a separate
	 * {@code Runnable} run object, then that
	 * {@code Runnable} object's {@code run} method is called;
	 * otherwise, this method does nothing and returns.
	 * <p>
	 * Subclasses of {@code Thread} should override this method.
	 *
	 * @see #start()
	 */
	@Override
	public void run() {
		try {
			runException();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * Run with exception
	 *
	 * @throws Exception Execution error
	 */
	private synchronized void runException() throws Exception {
		// Get executors
		IEngineCallbacks.IEngineStateChangedCallback[] stateChangedCallbacks = listenerList
			.getListeners(IEngineCallbacks.IEngineStateChangedCallback.class);
		IEngineCallbacks.IEngineExecutionCallback[] engineExecutionCallbacks = listenerList
			.getListeners(IEngineCallbacks.IEngineExecutionCallback.class);
		IEngineCallbacks.IEngineClosingCallback[] engineClosingCallbacks = listenerList
			.getListeners(IEngineCallbacks.IEngineClosingCallback.class);

		// Execute
		ProcessBuilder processBuilder = new ProcessBuilder(engineArgs);
		currentProcess = processBuilder.start();
		// Send message
		Arr.foreach(stateChangedCallbacks, item -> {
			EngineState oldState = currentState;
			currentState = EngineState.STARTED;
			item.onStateChanged(oldState, currentState);
		});
		// Get stream
		InputStream stream = currentProcess.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;

		while ((line = reader.readLine()) != null) {
			final String fl = line;
			Arr.foreach(
				engineExecutionCallbacks,
				item -> item.onExecutionData(fl.getBytes(StandardCharsets.UTF_8), false)
			);
		}

		final int exitCode = currentProcess.waitFor();
		// Send message
		Arr.foreach(engineClosingCallbacks, item -> item.onClosed(exitCode));
		Arr.foreach(stateChangedCallbacks, item -> {
			EngineState oldState = currentState;
			currentState = EngineState.KILLED;
			item.onStateChanged(oldState, currentState);
		});
	}

	/**
	 * Kill current thread
	 */
	public synchronized void closeAndKill() {
		if (!isAlive()) return;
		if (currentProcess == null) return;
		// Destroy
		currentProcess.destroy();
		// Get executors
		IEngineCallbacks.IEngineStateChangedCallback[] stateChangedCallbacks = listenerList
			.getListeners(IEngineCallbacks.IEngineStateChangedCallback.class);
		IEngineCallbacks.IEngineClosingCallback[] engineClosingCallbacks = listenerList
			.getListeners(IEngineCallbacks.IEngineClosingCallback.class);
		// Send messages
		Arr.foreach(engineClosingCallbacks, item -> item.onClosed(0));
		Arr.foreach(stateChangedCallbacks, item -> {
			EngineState oldState = currentState;
			currentState = EngineState.KILLED;
			item.onStateChanged(oldState, currentState);
		});
	}

	/**
	 * Set state changed listener
	 *
	 * @param callback Target callback
	 */
	public void setOnEngineStateChangeListener(IEngineCallbacks.IEngineStateChangedCallback callback) {
		listenerList.add(IEngineCallbacks.IEngineStateChangedCallback.class, callback);
	}

	/**
	 * Set execution listener.
	 *
	 * @param callback Target callback
	 */
	public void setOnExecutionDataListener(IEngineCallbacks.IEngineExecutionCallback callback) {
		listenerList.add(IEngineCallbacks.IEngineExecutionCallback.class, callback);
	}

	/**
	 * Set engine closing listener.
	 *
	 * @param callback Target callback
	 */
	public void setOnEngineClosingListener(IEngineCallbacks.IEngineClosingCallback callback) {
		listenerList.add(IEngineCallbacks.IEngineClosingCallback.class, callback);
	}

	/* ------------------------------------------------------------------
	 *
	 * Extra types
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Engine thread states
	 */
	public enum EngineState {
		/**
		 * Thread state {@code IDLE}
		 */
		IDLE,
		/**
		 * Thread state {@code STARTED}
		 */
		STARTED,
		/**
		 * Thread state {@code KILLED}
		 */
		KILLED
	}

}
