package org.godot.utilities.core.io;

import java.nio.file.Files;
import java.nio.file.Path;

public interface IResource {

	/**
	 * Get base resource path
	 *
	 * @return Return current resource path
	 */
	Path getResourcePath();

	/**
	 * Determine if current resource exists
	 *
	 * @return Returns {@code true} if resource exists or {@code false} otherwise
	 */
	default boolean exists() {
		return Files.exists(getResourcePath());
	}

}
