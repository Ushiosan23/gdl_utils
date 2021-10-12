package org.godot.utilities.utils;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class IOUtils {

	/**
	 * Cannot instantiate
	 */
	private IOUtils() {
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Walk directory with filters
	 *
	 * @param location  Target location
	 * @param filters   Custom search filter
	 * @param recursive Determine if walk is recursive or not
	 * @return Return a lis with all filter elements
	 * @throws IOException if an I/O error is thrown when accessing the starting file.
	 */
	@SafeVarargs
	public static @Nullable List<Path> walkDirectory(
		Path location,
		boolean recursive,
		Predicate<Path>... filters
	) throws IOException {
		// Check if is valid directory
		if (!Files.isDirectory(location)) return null;
		// Get walker
		Stream<Path> walker = recursive ? Files.walk(location) : Files.walk(location, 1);
		for (Predicate<Path> item : filters) {
			walker = walker.filter(item);
		}
		return walker.collect(Collectors.toList());
	}

	/**
	 * Walk directory with filters
	 *
	 * @param location Target location
	 * @param filters  Custom search filter
	 * @return Return a lis with all filter elements
	 * @throws IOException if an I/O error is thrown when accessing the starting file.
	 */
	@SafeVarargs
	public static @Nullable List<Path> walkDirectory(
		Path location,
		Predicate<Path>... filters
	) throws IOException {
		return walkDirectory(location, true, filters);
	}

	/**
	 * Get file extensions from path
	 *
	 * @param location Target location
	 * @return Return an extension file without dot or {@code null} if is not a valid file.
	 */
	public static @Nullable String getExtension(Path location) {
		// Check if is a directory
		if (Files.isDirectory(location)) return null;
		// Split file name
		String fileName = location.getFileName().toString();
		int extIndex = fileName.lastIndexOf('.');
		// Return extension result
		return extIndex == -1 ? "" : fileName.substring(extIndex + 1);
	}

}
