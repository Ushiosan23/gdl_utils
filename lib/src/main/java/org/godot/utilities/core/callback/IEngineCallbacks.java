package org.godot.utilities.core.callback;

import org.godot.utilities.engine.EngineThread;

import java.util.EventListener;

public interface IEngineCallbacks {

	interface IEngineStateChangedCallback extends EventListener {

		/**
		 * Detect when engine change it's state
		 *
		 * @param oldState Old engine state
		 * @param newState New engine state
		 */
		void onStateChanged(EngineThread.EngineState oldState, EngineThread.EngineState newState);

	}

	interface IEngineExecutionCallback extends EventListener {

		/**
		 * Called every time when engine send information to console
		 *
		 * @param data    Buffer data
		 * @param isError Determine if data is an error
		 */
		void onExecutionData(byte[] data, boolean isError);

	}

	interface IEngineClosingCallback extends EventListener {

		/**
		 * Called when engine execution ends
		 *
		 * @param exitCode Engine exit code
		 */
		void onClosed(int exitCode);

	}

}
