package org.godot.utilities.project.resources;

import org.godot.utilities.core.Version;
import org.godot.utilities.core.ini.Ini;
import org.godot.utilities.core.io.FileResource;
import org.godot.utilities.project.Project;
import org.godot.utilities.utils.Arr;
import org.godot.utilities.utils.ContentGD;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class ProjectResource extends FileResource {

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Project version
	 */
	public final Version version;

	/**
	 * Check if file is readable (only files with .ini syntax)
	 */
	public final boolean isReadable;

	/**
	 * Check if file is displayable (only pictures with specific formats)
	 */
	public final boolean isDisplayable;

	/**
	 * Current resource type
	 */
	public final ResourceType resourceType;

	/**
	 * Content file
	 */
	protected Ini contentFile;

	/* ------------------------------------------------------------------
	 *
	 * Constructors
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * ProjectResource constructor.
	 *
	 * @param l Target resource location
	 * @param v Target resource version
	 */
	public ProjectResource(@NotNull Path l, @NotNull Version v) {
		super(l);
		version = v;
		resourceType = detectResourceType();
		isReadable = checkReadability();
		isDisplayable = checkDisplayable();
		// Check if is readable
		if (isReadable) contentFile = new Ini();
	}

	/**
	 * ProjectResource constructor.
	 *
	 * @param l Target resource location
	 * @param p Target project container
	 */
	public ProjectResource(@NotNull Path l, @NotNull Project p) {
		this(l, p.projectVersion);
	}

	/**
	 * Get file content only if current file is readable.
	 *
	 * @return Return an ini file with all content in INI format.
	 * @throws UnsupportedOperationException Error if operation is not supported
	 * @throws IOException                   Error if file not exists or is not a valid file
	 */
	public Ini getFileContent() throws IOException {
		updateContent();
		return contentFile;
	}

	/**
	 * The same behavior of {@link #getFileContent()} but this method return nothing.
	 *
	 * @throws UnsupportedOperationException Error if operation is not supported
	 * @throws IOException                   Error if file not exists or is not a valid file
	 */
	public void updateContent() throws IOException {
		if (!isReadable)
			throw new UnsupportedOperationException(
				String.format("This operation is not available for \"%s\" class.", this.getClass().getName())
			);
		// Load information
		if (!contentFile.isInitialized())
			contentFile.loadFromPath(location);
	}

	/* ------------------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Determine what kind of resource is current resource
	 *
	 * @return Return a resource type
	 * @see ResourceType
	 */
	private ResourceType detectResourceType() {
		// Iterate all valid entries
		for (Map.Entry<String, ContentGD.IResourceStruct> entry : ContentGD.allValidResourceTypes.entrySet()) {
			// Save struct
			ContentGD.IResourceStruct struct = entry.getValue();
			// Check type
			if (Arr.indexOf(struct.getExtensions(), getExtension()) != -1) {
				if (struct.getResourceType() == null) return ResourceType.UNKNOWN;
				return struct.getResourceType();
			}
		}
		return ResourceType.UNKNOWN;
	}

	/**
	 * Check if current resource is readable.
	 *
	 * @return Returns {@code true} if file is readable or {@code false} otherwise
	 */
	private boolean checkReadability() {
		return Arr.indexOf(ContentGD.allValidReadableExtensions, getExtension()) != Arr.NOT_FOUND;
	}

	/**
	 * Check if current resource can be displayed.
	 *
	 * @return Return {@code true} if current resource can be displayed or {@code false} otherwise
	 */
	private boolean checkDisplayable() {
		// Only pictures can be displayed
		if (resourceType != ResourceType.PICTURE) return false;
		// Check all valid pictures extensions
		return Arr.indexOf(ContentGD.allPossiblePicturePreviewExtensions, getExtension()) != Arr.NOT_FOUND;
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Override
	public @NotNull String toString() {
		return "ProjectResource{" +
			"location=" + location +
			", resourceType=" + resourceType +
			", isReadable=" + isReadable +
			", isDisplayable=" + isDisplayable +
			'}';
	}

}
