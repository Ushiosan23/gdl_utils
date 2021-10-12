package org.godot.utilities.project.scene;

import org.godot.utilities.core.Version;
import org.godot.utilities.project.Project;
import org.godot.utilities.project.resources.ProjectResource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class Scene extends ProjectResource {

	/**
	 * ProjectResource constructor.
	 *
	 * @param l Target resource location
	 * @param v Target resource version
	 */
	public Scene(@NotNull Path l, @NotNull Version v) {
		super(l, v);
	}

	/**
	 * ProjectResource constructor.
	 *
	 * @param l Target resource location
	 * @param p Target project container
	 */
	public Scene(@NotNull Path l, @NotNull Project p) {
		super(l, p);
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return "Scene{" +
			"location=" + location +
			", isReadable=" + isReadable +
			", resourceType=" + resourceType +
			", sceneContent=" + contentFile +
			'}';
	}

}
