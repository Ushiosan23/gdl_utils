package org.godot.utilities.engine;

import org.godot.utilities.core.Version;
import org.godot.utilities.core.io.FileResource;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class BaseEngine extends FileResource {

	/**
	 * Current engine version
	 */
	protected Version engineVersion;

	/**
	 * Engine raw string version
	 */
	protected String rawVersion;

	/**
	 * Engine type. Check if is a mono version
	 */
	protected boolean isMono;

	/**
	 * Engine type. Check if engine is an early access version.
	 */
	protected boolean isEarlyAccess;

	/**
	 * Engine type. Check if engine is a custom compilation.
	 */
	protected boolean isCustom;

	/**
	 * Regular expression to detect raw engine version
	 */
	@RegExp
	static final String engineVersionRegexDetection = "v(\\d+\\.){1,5}(\\w+\\.?){1,4}";

	/**
	 * Regular expression to detect if engine is a mono version
	 */
	@RegExp
	static final String engineVersionMonoRegexDetection = "\\.mono\\.";

	/**
	 * Regular expression to detect if engine is a pre-release version.
	 * <p>
	 * Detect <b>Alpha</b>, <b>Beta</b> and <b>Release Candidate</b> versions.
	 */
	@RegExp
	static final String engineVersionPreReleaseRegexDetection = "\\.(alpha|beta|rc)(\\d*)\\.";

	/**
	 * Regular expression to detect if engine is a custom build.
	 */
	@RegExp
	static final String engineVersionCustomBuildRegexDetection = "\\.custom_build\\.";

	/* ------------------------------------------------------------------
	 *
	 * Constructor
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * BaseEngine constructor
	 *
	 * @param l Target file location
	 * @throws IllegalArgumentException Error if location is not a regular file
	 */
	public BaseEngine(@NotNull Path l) {
		super(l);
	}

	/**
	 * Get current engine version.
	 * {@link BaseEngine} always return {@code null}.
	 *
	 * @return Target engine version
	 */
	public Version getEngineVersion() {
		return engineVersion;
	}

	/**
	 * Get current engine type.
	 * {@link BaseEngine} always return {@code false}.
	 *
	 * @return Return {@code true} if current engine is a Mono version or {@code false} otherwise.
	 */
	public boolean isMono() {
		return isMono;
	}

	/**
	 * Get current engine type.
	 * {@link BaseEngine} always return {@code false}.
	 *
	 * @return Return {@code true} if current engine is an early access version or {@code false} otherwise.
	 */
	public boolean isEarlyAccess() {
		return isEarlyAccess;
	}

	/**
	 * Get current engine type.
	 * {@link BaseEngine} always return {@code false}.
	 *
	 * @return Return {@code true} if current engine is a custom compilation or {@code false} otherwise.
	 */
	public boolean isCustom() {
		return isCustom;
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Override
	public @NotNull String toString() {
		return "BaseEngine{" +
			"location=" + location +
			'}';
	}

}
