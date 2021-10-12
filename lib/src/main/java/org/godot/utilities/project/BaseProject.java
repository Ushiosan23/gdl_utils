package org.godot.utilities.project;

import org.godot.utilities.core.Version;
import org.godot.utilities.core.io.DirectoryResource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class BaseProject extends DirectoryResource {

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Project version.
	 */
	public final Version projectVersion;

	/**
	 * Project file
	 */
	protected Path projectFile;

	/* ------------------------------------------------------------------
	 *
	 * Constructors
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Base project constructor.
	 *
	 * @param l Project directory path
	 * @param v Project version
	 */
	public BaseProject(@NotNull Path l, @NotNull Version v) {
		super(l);
		projectVersion = v;
	}

	/**
	 * Project without version. Use recommended version.
	 *
	 * @param l Project directory path
	 */
	public BaseProject(@NotNull Path l) {
		this(l, Version.getRecommended());
	}
	
	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return "BaseProject{" +
			"projectFile=" + projectFile +
			", location=" + location +
			", projectVersion=" + projectVersion +
			'}';
	}

}
