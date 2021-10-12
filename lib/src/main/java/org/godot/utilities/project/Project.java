package org.godot.utilities.project;

import org.godot.utilities.core.Version;
import org.godot.utilities.core.VideoDriver;
import org.godot.utilities.core.ini.ISection;
import org.godot.utilities.core.ini.Ini;
import org.godot.utilities.project.resources.ProjectResource;
import org.godot.utilities.project.scene.Scene;
import org.godot.utilities.project.scene.SceneInspector;
import org.godot.utilities.utils.Arr;
import org.godot.utilities.utils.IOUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
public final class Project extends BaseProject implements IProjectInfo {

	/**
	 * Project configuration file
	 */
	private final Ini projectConfig = new Ini();

	/**
	 * Scene inspector
	 */
	private final SceneInspector sceneInspector;

	/**
	 * Project constructor.
	 *
	 * @param l Project location
	 * @param v Project version
	 * @throws IOException Error if location not exists or is not valid directory
	 */
	public Project(@NotNull Path l, @NotNull Version v) throws IOException {
		super(l, v);
		// Check if project file exists
		inspectProjectFile();
		// Load configuration
		projectConfig.loadFromPath(projectFile);
		// Create inspector
		sceneInspector = new SceneInspector(this);
	}

	/**
	 * Project without version. Use recommended version.
	 *
	 * @param l Project location
	 * @throws IOException Error if location not exists or is not valid directory
	 */
	public Project(@NotNull Path l) throws IOException {
		this(l, Version.getRecommended());
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Get current project config
	 *
	 * @return Return all project configuration
	 */
	public Ini getConfig() {
		return projectConfig;
	}

	/**
	 * Get current scene inspector
	 *
	 * @return Return a scene inspector
	 */
	public SceneInspector getSceneInspector() {
		return sceneInspector;
	}

	/**
	 * Get project title name.
	 * <p>
	 * This name is defined in "project.godot" or "engine.cfg", it depends on the version
	 *
	 * @return Project title name
	 */
	@Contract(pure = true)
	@Override
	public @Nullable String getProjectName() {
		// Check if section exists
		if (!projectConfig.sectionExists("application"))
			return null;
		// Storage section
		ISection applicationSection = projectConfig.getSection("application");
		// Check project version
		switch (projectVersion) {
			case V1:
			case V2:
				return applicationSection.getProperty("name", null);
			case V3:
			case V4:
				return applicationSection.getProperty("config/name", null);
			default:
				return null;
		}
	}

	/**
	 * Get project description.
	 *
	 * @return Project description or {@code null} if property is not defined
	 */
	@Contract(pure = true)
	@Override
	public @Nullable String getProjectDescription() {
		// Check if section exists
		if (!projectConfig.sectionExists("application"))
			return null;
		// Storage section
		ISection applicationSection = projectConfig.getSection("application");
		// Check project version
		switch (projectVersion) {
			case V3:
			case V4:
				return applicationSection.getProperty("config/description", null);
			default:
				return null;
		}
	}

	/**
	 * Get project main scene. This is useful when you can run the project.
	 *
	 * @return Returns a special godot location or {@code null} if property is not defined.
	 */
	@Override
	public @Nullable Scene getMainScene() {
		// Check if scene exists
		if (!projectConfig.sectionExists("application"))
			return null;
		// Storage section
		ISection applicationSection = projectConfig.getSection("application");
		String sceneResource;

		// Check project version
		switch (projectVersion) {
			case V1:
			case V2:
				sceneResource = applicationSection.getProperty("main_scene", null);
				break;
			case V3:
			case V4:
				sceneResource = applicationSection.getProperty("run/main_scene", null);
				break;
			default:
				sceneResource = null;
				break;
		}
		// Check if resource exists
		if (sceneResource == null) return null;
		// Resolve resource
		return new Scene(resolvePath(sceneResource), this);
	}

	/**
	 * Get project icon.
	 *
	 * @return Return a special godot location or {@code null} if property is not defined.
	 */
	@Override
	public @Nullable ProjectResource getProjectIcon() {
		// Check if scene exists
		if (!projectConfig.sectionExists("application"))
			return null;
		// Storage section
		ISection applicationSection = projectConfig.getSection("application");
		String resourceString;

		// Check project version
		switch (projectVersion) {
			case V1:
			case V2:
				resourceString = applicationSection.getProperty("icon", null);
				break;
			case V3:
			case V4:
				resourceString = applicationSection.getProperty("config/icon", null);
				break;
			default:
				resourceString = null;
				break;
		}
		// Check if resource exists
		if (resourceString == null) return null;
		// Resolve resource
		return new ProjectResource(resolvePath(resourceString), this);
	}

	/**
	 * Get current project video driver.
	 * <p>
	 * This is useful if you want to change before to launch.
	 *
	 * @return Return a project driver or {@code null} if property is not defined
	 */
	@Override
	public @Nullable VideoDriver getProjectVideoDriver() {
		// Storage section
		ISection renderingSection;
		String driverName;
		// Check project version
		switch (projectVersion) {
			case V1:
			case V2:
				// Check if section exists
				if (!projectConfig.sectionExists("display")) return VideoDriver.GLES2;
				// Set section
				renderingSection = projectConfig.getSection("display");
				driverName = renderingSection.getProperty("driver", "GLES2");
				break;
			case V3:
			case V4:
				// Check if section exists
				if (!projectConfig.sectionExists("rendering")) return VideoDriver.GLES3;
				// Set section
				renderingSection = projectConfig.getSection("rendering");
				driverName = renderingSection.getProperty("quality/driver/driver_name", "GLES3");
				break;
			default:
				return null;
		}
		// Check if driver name is null
		if (driverName == null) return null;
		// Get result
		return VideoDriver.getFromName(driverName);
	}

	/* ------------------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Inspect directory to search project file
	 *
	 * @throws IOException Error if directory no contain project file
	 */
	private void inspectProjectFile() throws IOException {
		List<Path> inspectionResult = IOUtils.walkDirectory(
			getResourcePath(),
			false,
			Files::isRegularFile,
			item -> Arr.indexOf(projectVersion.projectFiles, item.getFileName().toString()) != -1
		);
		// Check if location is valid
		assert inspectionResult != null;
		if (inspectionResult.size() == 0)
			throw new FileNotFoundException(
				String.format("Directory not contain file project \"%s\".", Arrays.toString(projectVersion.projectFiles))
			);
		// Initialize project file root
		projectFile = inspectionResult.get(0);
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return "Project{" +
			"projectFile=" + projectFile +
			", location=" + location +
			", projectVersion=" + projectVersion +
			", projectConfig=" + projectConfig +
			'}';
	}

}
