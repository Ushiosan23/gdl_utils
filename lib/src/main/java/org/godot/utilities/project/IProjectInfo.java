package org.godot.utilities.project;

import org.godot.utilities.core.VideoDriver;
import org.godot.utilities.project.resources.ProjectResource;
import org.godot.utilities.project.scene.Scene;
import org.jetbrains.annotations.Nullable;

public interface IProjectInfo {

	/**
	 * Get project title name.
	 * <p>
	 * This name is defined in "project.godot" or "engine.cfg", it depends on the version
	 *
	 * @return Project title name or {@code null} if property is not defined
	 */
	@Nullable
	String getProjectName();

	/**
	 * Get project description.
	 *
	 * @return Project description or {@code null} if property is not defined
	 */
	@Nullable
	String getProjectDescription();

	/**
	 * Get current project video driver.
	 * <p>
	 * This is useful if you want to change before to launch.
	 *
	 * @return Return a project driver or {@code null} if property is not defined
	 */
	@Nullable
	VideoDriver getProjectVideoDriver();

	/**
	 * Get project main scene. This is useful when you can run the project.
	 *
	 * @return Returns a special godot location or {@code null} if property is not defined.
	 */
	@Nullable
	Scene getMainScene();

	/**
	 * Get project icon.
	 *
	 * @return Return a special godot location or {@code null} if property is not defined.
	 */
	@Nullable
	ProjectResource getProjectIcon();

}
