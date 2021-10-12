package org.godot.utilities;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

public class R {

	/**
	 * Current loader
	 */
	static ClassLoader loader = ClassLoader.getSystemClassLoader();

	public static Path getPath(String resource) throws URISyntaxException {
		return Path.of(Objects.requireNonNull(loader.getResource(resource)).toURI());
	}

	public static InputStream getStream(String resource) {
		return loader.getResourceAsStream(resource);
	}

}
