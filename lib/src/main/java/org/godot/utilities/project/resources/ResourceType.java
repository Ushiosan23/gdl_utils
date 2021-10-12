package org.godot.utilities.project.resources;

/**
 * Determine different resources types.
 */
public enum ResourceType {

	/**
	 * Determines if the asset is a project type.
	 * <p>
	 * It is determined by the possible file extensions, in this case {@code [engine.cfg, project.godot]}
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	PROJECT,

	/**
	 * Determines if the asset is a scene type.
	 * <p>
	 * It is determined by the possible file extensions, in this case {@code [*.scn, *.tscn]}
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	SCENE,

	/**
	 * Determines if the asset is a picture type.
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	PICTURE,

	/**
	 * Determines if the asset is a script type.
	 * <p>
	 * It is determined by the possible file extensions, in this case {@code [*.gd, *.cs, *.h, *.c, *.cpp, *.hpp, *.cxx]}
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	SCRIPT,

	/**
	 * Determines if the asset is a font type.
	 * <p>
	 * It is determined by the possible file extensions, in this case {@code [*.otf, *.ttf]}
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	FONT,

	/**
	 * Determines if the asset is a 3d model type.
	 * <p>
	 * It is determined by the possible file extensions, in this case {@code [*.gltf, *.glb, *.dae, *.obj, *.escn, *.fbx]}
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	MODEL3D,

	/**
	 * Determines if the asset is any other type.
	 * <p>
	 * It is determined by the possible file extensions, in this case
	 * {@code [*.material, *.shader, *.tres, *.res, *.atlastex, *.anim]}
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	OTHER,

	/**
	 * Determines if the asset is a native script type.
	 * <p>
	 * It is determined by the possible file extensions, in this case {@code [*.gdns]}
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	NATIVE_SCRIPT,

	/**
	 * Determines if the asset is any other type.
	 *
	 * @see org.godot.utilities.utils.ContentGD#allValidResourceTypes
	 */
	UNKNOWN
}
