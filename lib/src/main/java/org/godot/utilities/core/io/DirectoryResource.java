package org.godot.utilities.core.io;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryResource implements IResolver {

	/**
	 * Base location
	 */
	protected final Path location;

	/**
	 * Primary constructor
	 *
	 * @param l Base location
	 */
	public DirectoryResource(Path l) {
		// Check if directory is valid
		if (!Files.isDirectory(l))
			throw new IllegalArgumentException(
				String.format("Target location \"%s\" is not a valid directory. File given.", l)
			);
		// Initialize properties
		location = l;
	}

	/**
	 * Get base resource path
	 *
	 * @return Return current resource path
	 */
	@Override
	public Path getResourcePath() {
		return location;
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return "DirectoryResource{" +
			"location=" + location +
			'}';
	}

}
