package org.godot.utilities.utils;

import org.godot.utilities.project.resources.ResourceType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class ContentGD {

	/**
	 * Cannot create instances
	 */
	private ContentGD() {
	}

	/* ------------------------------------------------------------------
	 *
	 * Engine properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Current engine stable version code
	 */
	public static final int currentStableCodeVersion = 3;

	/**
	 * This property determines if target executable is a valid Godot executable.
	 */
	public static final Pattern magicExecutableData = Pattern.compile(
		"Godot Engine",
		Pattern.CASE_INSENSITIVE
	);

	/**
	 * Engine executable mime types
	 */
	public static final List<String> allValidEngineExecutableMimeTypes = ListUtils.of(
		"application/octet-stream",
		"application/x-executable",
		"application/x-match-binary", // generic type (Macos)
		"application/x-dosexec" // generic type (windows)
	);

	/**
	 * Engine resource protocol
	 */
	public static final String resourcesProtocol = "res://";

	/* ------------------------------------------------------------------
	 *
	 * Support properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * All valid project files
	 */
	public static final String[] allValidProjectFiles = Arr.of("engine.cfg", "project.godot");

	/**
	 * All valid project resource types
	 */
	public static final Map<String, IResourceStruct> allValidResourceTypes = MapUtils.of(
		MapUtils.entry(
			"project_files",
			new ResourceStructImpl(
				ResourceType.PROJECT,
				Arr.of("cfg", "godot")
			)
		),
		MapUtils.entry(
			"scenes",
			new ResourceStructImpl(
				ResourceType.SCENE,
				Arr.of("scn", "tscn")
			)
		),
		MapUtils.entry(
			"pictures",
			new ResourceStructImpl(
				ResourceType.PICTURE,
				Arr.of("bmp", "dds", "exr", "hdr", "jpg", "jpeg", "png", "tga", "svg", "svgz", "webp")
			)
		),
		MapUtils.entry(
			"scripts",
			new ResourceStructImpl(
				ResourceType.SCRIPT,
				Arr.of("gd", "cs", "c", "cpp", "h", "cxx", "hpp")
			)
		),
		MapUtils.entry(
			"nativeScripts",
			new ResourceStructImpl(
				ResourceType.NATIVE_SCRIPT,
				Arr.of("gdns")
			)
		),
		MapUtils.entry(
			"fonts",
			new ResourceStructImpl(
				ResourceType.FONT,
				Arr.of("ttf", "otf")
			)
		),
		MapUtils.entry(
			"models",
			new ResourceStructImpl(
				ResourceType.MODEL3D,
				Arr.of("gltf", "glb", "dae", "obj", "escn", "fbx")
			)
		),
		MapUtils.entry(
			"others",
			new ResourceStructImpl(
				ResourceType.OTHER,
				Arr.of("material", "shader", "tres", "res", "atlastex", "anim")
			)
		)
	);

	/**
	 * Get all readable extensions
	 */
	public static String[] allValidReadableExtensions = Arr.of(
		"tscn", "tres", "godot", "ini", "cfg"
	);

	/* ------------------------------------------------------------------
	 *
	 * Extras
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * All possible preview picture
	 */
	public static final String[] allPossiblePicturePreviewExtensions = Arr.of(
		"bmp", "jpg", "jpeg", "png"
	);


	/* ------------------------------------------------------------------
	 *
	 * Resource type structure
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Structure for resource structure
	 */
	public interface IResourceStruct {

		/**
		 * Get resource type extensions
		 *
		 * @return All valid extensions
		 */
		String[] getExtensions();

		/**
		 * Get resource type
		 *
		 * @return Resource type
		 */
		@Nullable
		ResourceType getResourceType();

	}

	static class ResourceStructImpl implements IResourceStruct {

		private final ResourceType rType;
		private final String[] extensions;

		public ResourceStructImpl(@Nullable ResourceType t, @NotNull String[] ext) {
			rType = t;
			extensions = ext;
		}

		@Override
		public String[] getExtensions() {
			return extensions;
		}

		@Override
		public @Nullable ResourceType getResourceType() {
			return rType;
		}

	}

}
