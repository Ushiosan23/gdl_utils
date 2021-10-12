package org.godot.utilities.core;

import org.godot.utilities.utils.Arr;
import org.godot.utilities.utils.ContentGD;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public enum Version {
	/**
	 * First version of Godot.
	 *
	 * @see <a href="https://godotengine.org/article/godot-1-1-out">Godot 1.1</a>
	 * @deprecated This version is deprecated and is only listed to support older projects
	 */
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	V1(1, Arr.of("engine.cfg"), Arr.of("scn")),

	/**
	 * Second version of Godot.
	 * <ul>
	 *     <li>This version still uses the old files to create projects.</li>
	 *     <li>The new scene format was implemented.</li>
	 * </ul>
	 *
	 * @see <a href="https://godotengine.org/article/godot-engine-reaches-2-0-stable">Godot 2</a>
	 */
	V2(2, V1.projectFiles, Arr.of("tscn", "scn")),

	/**
	 * Third version of Godot.
	 * <ul>
	 *     <li>New project file was added.</li>
	 *     <li>The editor was redesigned.</li>
	 *     <li>Godot "CLI" was completely changed.</li>
	 * </ul>
	 *
	 * @see <a href="https://godotengine.org/article/godot-3-0-released">Godot 3</a>
	 */
	V3(3, Arr.of("project.godot"), V2.sceneExtensions),

	/**
	 * Future version of Godot.
	 * No information at this time.
	 */
	V4(4, V3.projectFiles, V3.sceneExtensions),

	/**
	 * Unknown version.
	 * This option is only used to represent an error.
	 */
	UNKNOWN(-1, Arr.stringEmpty(), Arr.stringEmpty());

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Version code
	 */
	public final int versionCode;

	/**
	 * Project filenames
	 */
	public final String[] projectFiles;

	/**
	 * Scene extensions
	 */
	public final String[] sceneExtensions;

	/* ------------------------------------------------------------------
	 *
	 * Content
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Engine versions.
	 *
	 * @param vc  Version code
	 * @param vpf Version project files
	 * @param vse Version scene extensions
	 */
	Version(int vc, @NotNull String[] vpf, @NotNull String[] vse) {
		versionCode = vc;
		projectFiles = vpf;
		sceneExtensions = vse;
	}

	/**
	 * Get recommended version
	 *
	 * @return Returns a recommended version
	 */
	public static Version getRecommended() {
		for (Version v : values()) {
			if (v.versionCode == ContentGD.currentStableCodeVersion) return v;
		}
		return UNKNOWN;
	}

	/**
	 * Get version from code name
	 *
	 * @param code Target code.
	 * @return Return a valid version or {@link #UNKNOWN} if code is not valid
	 */
	@Contract(pure = true)
	public static @NotNull Version getFromCode(int code) {
		for (Version v : values()) {
			if (v.versionCode == code) return v;
		}
		return UNKNOWN;
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Override
	public @NotNull String toString() {
		return "Version{" +
			"versionCode=" + versionCode +
			", projectFiles=" + Arrays.toString(projectFiles) +
			", sceneExtensions=" + Arrays.toString(sceneExtensions) +
			'}';
	}

}
