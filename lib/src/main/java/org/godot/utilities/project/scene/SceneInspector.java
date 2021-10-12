package org.godot.utilities.project.scene;

import org.godot.utilities.core.io.IResource;
import org.godot.utilities.project.Project;
import org.godot.utilities.utils.Arr;
import org.godot.utilities.utils.IOUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SceneInspector implements IResource {

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Scene base location
	 */
	private final Project project;

	/**
	 * All project scenes
	 */
	private Set<Scene> fileTree;

	/* ------------------------------------------------------------------
	 *
	 * Constructors
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Default scene inspector constructor
	 *
	 * @param p Target project
	 * @throws IOException Error if location not exists.
	 */
	public SceneInspector(Project p) throws IOException {
		project = p;
		fileTree = new HashSet<>();
		// Check if is a valid directory
		if (!Files.isDirectory(project.getResourcePath()))
			throw new IllegalArgumentException("Location is not a valid directory.");
		// Inspect directory
		scanLocation();
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Get base resource path
	 *
	 * @return Return current resource path
	 */
	@Override
	public Path getResourcePath() {
		return project.getResourcePath();
	}

	/**
	 * Return all scenes
	 *
	 * @return Return all scenes
	 */
	public Set<Scene> getAllScenes() {
		return fileTree;
	}

	/**
	 * Scan directory
	 *
	 * @throws IOException Error if directory not exists
	 */
	public void scanLocation() throws IOException {
		// Create walker
		fileTree = Files.walk(project.getResourcePath())
			.filter(Files::isRegularFile)
			.filter(el -> Arr.indexOf(
				project.projectVersion.sceneExtensions,
				Objects.requireNonNull(IOUtils.getExtension(el))
			) != -1)
			.map(el -> new Scene(el, project))
			.collect(Collectors.toSet());
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Override
	public String toString() {
		return "SceneInspector{" +
			"fileTree=" + fileTree +
			'}';
	}

}
