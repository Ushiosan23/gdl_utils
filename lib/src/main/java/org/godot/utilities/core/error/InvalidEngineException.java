package org.godot.utilities.core.error;

import java.io.IOException;

public class InvalidEngineException extends IOException {

	/**
	 * Constructs an {@code IOException} with {@code null}
	 * as its error detail message.
	 */
	public InvalidEngineException() {
	}

	/**
	 * Constructs an {@code IOException} with the specified detail message.
	 *
	 * @param message The detail message (which is saved for later retrieval
	 *                by the {@link #getMessage()} method)
	 */
	public InvalidEngineException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code IOException} with the specified detail message
	 * and cause.
	 *
	 * <p> Note that the detail message associated with {@code cause} is
	 * <i>not</i> automatically incorporated into this exception's detail
	 * message.
	 *
	 * @param message The detail message (which is saved for later retrieval
	 *                by the {@link #getMessage()} method)
	 * @param cause   The cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method).  (A null value is permitted,
	 *                and indicates that the cause is nonexistent or unknown.)
	 * @since 1.6
	 */
	public InvalidEngineException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an {@code IOException} with the specified cause and a
	 * detail message of {@code (cause==null ? null : cause.toString())}
	 * (which typically contains the class and detail message of {@code cause}).
	 * This constructor is useful for IO exceptions that are little more
	 * than wrappers for other throwables.
	 *
	 * @param cause The cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method).  (A null value is permitted,
	 *              and indicates that the cause is nonexistent or unknown.)
	 * @since 1.6
	 */
	public InvalidEngineException(Throwable cause) {
		super(cause);
	}

}
