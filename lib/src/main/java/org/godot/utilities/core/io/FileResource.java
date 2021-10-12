package org.godot.utilities.core.io;

import org.godot.utilities.utils.IOUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileResource implements IResource {

	/**
	 * Base location
	 */
	protected final Path location;

	/**
	 * Primary constructor
	 *
	 * @param l Base location
	 * @throws IllegalArgumentException Error if location is not a regular file
	 */
	public FileResource(@NotNull Path l) {
		// Check if location is valid
		if (Files.isDirectory(l))
			throw new IllegalArgumentException(
				String.format("Target location \"%s\" is not a valid file. Directory given.", l)
			);
		// Initialize
		location = l;
	}

	/**
	 * Get base resource path
	 *
	 * @return Return current resource path
	 */
	@Override
	public @NotNull Path getResourcePath() {
		return location;
	}

	/**
	 * Get file extension
	 *
	 * @return Return file extension
	 */
	public @NotNull String getExtension() {
		return Objects.requireNonNull(IOUtils.getExtension(location));
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return "FileResource{" +
			"location=" + location +
			'}';
	}

}
