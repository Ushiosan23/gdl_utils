package org.godot.utilities.utils;

import org.godot.utilities.core.callback.IAnyUtilsCallbacks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AnyUtils {

	private AnyUtils() {
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Require not null object
	 *
	 * @param item     Target item to check
	 * @param callback Target action to execute if item is not null
	 * @param <T>      Generic type
	 */
	public static <T> void requireNotNull(@Nullable T item, IAnyUtilsCallbacks.IRequireNotNullCallback<T> callback) {
		if (item != null) callback.invoke(item);
	}

	/**
	 * Try cast object to any type
	 *
	 * @param any      Target value to cast
	 * @param callback Target action to execute
	 * @param <T>      Generic type to cast
	 */
	@SuppressWarnings("unchecked")
	public static <T> void tryCast(@NotNull Object any, IAnyUtilsCallbacks.ITryCastCallback<T> callback) {
		try {
			callback.invoke((T) any);
		} catch (Exception ignored) {
		}
	}

}
