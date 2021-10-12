package org.godot.utilities.core.callback;

import org.jetbrains.annotations.NotNull;

public interface IAnyUtilsCallbacks {

	/**
	 * Interface used to check if any element is not a {@code null}
	 *
	 * @param <T> Generic type check
	 */
	interface IRequireNotNullCallback<T> {

		/**
		 * Invocation method
		 *
		 * @param item Target not null item
		 */
		void invoke(@NotNull T item);

	}

	/**
	 * Interface used to make a dynamic casting.
	 * It is only invoked if the cast is valid.
	 *
	 * @param <T> Generic casting type
	 */
	interface ITryCastCallback<T> extends IRequireNotNullCallback<T> {
	}

	/**
	 * Interface used to iterate the elements of a collection. Commonly used as a lambda expression.
	 *
	 * @param <T> Generic collection type
	 */
	interface IForeach<T> extends IRequireNotNullCallback<T> {
	}

}
