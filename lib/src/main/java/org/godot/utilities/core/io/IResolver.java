package org.godot.utilities.core.io;

import org.godot.utilities.utils.ContentGD;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public interface IResolver extends IResource {

	/**
	 * Resolve relative path to os path
	 *
	 * @param relativePath Target resource path
	 * @return Return the os path in base of resource path
	 */
	default Path resolvePath(@NotNull String relativePath) {
		// Check if relative path starts with engine protocol
		if (relativePath.startsWith(ContentGD.resourcesProtocol))
			relativePath = relativePath.substring(ContentGD.resourcesProtocol.length());
		return getResourcePath().resolve(relativePath);
	}

	/**
	 * Resolve resource to valid engine resource string
	 *
	 * @param resource Target resource
	 * @return Returns a string with valid engine resource location
	 */
	default String resolveResource(@NotNull IResource resource) {
		Path resolve = getResourcePath().relativize(resource.getResourcePath());
		String relativePath = resolve.toString();

		// Clean directory separators
		relativePath = relativePath
			.replace(File.separatorChar, '/')
			.replace('\\', '/');
		// Check if relative path starts with '/'
		if (relativePath.startsWith("/"))
			relativePath = relativePath.substring(1);

		// Concatenate protocol with resource path
		return ContentGD.resourcesProtocol + relativePath;
	}

}
