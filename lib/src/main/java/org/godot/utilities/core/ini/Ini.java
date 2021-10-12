package org.godot.utilities.core.ini;

import org.godot.utilities.core.ini.convertion.DataConvert;
import org.godot.utilities.core.io.FileResource;
import org.godot.utilities.utils.ContentGD;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Ini {

	/**
	 * All valid ini extensions.
	 * You can access it to add more extensions
	 */
	public static final List<String> iniValidFileExtensions =
		Arrays.asList(ContentGD.allValidReadableExtensions);

	/**
	 * Check if current instance already load ini content
	 */
	private boolean initialized = false;

	/**
	 * Default ini section
	 */
	private ISection defaultSection = new SectionImpl("Default", null);

	/**
	 * All sections elements
	 */
	private final Set<ISection> sectionSet = new HashSet<>();

	/**
	 * File resource
	 */
	private FileResource resource;

	/**
	 * Special storage used when file is reading.
	 */
	private IniStorage bufferStorage;

	/* ------------------------------------------------------------------
	 *
	 * Constructors
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Default constructor.
	 */
	public Ini() {
		sectionSet.add(defaultSection);
	}

	/**
	 * Constructor with path.
	 *
	 * @param location Target file location.
	 * @throws IOException Error if file not exists or is not valid.
	 */
	public Ini(Path location) throws IOException {
		this();
		loadFromPath(location);
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Load data from file path
	 *
	 * @param location Target location to get data
	 * @throws IOException Error if file not exists or is not valid
	 */
	public void loadFromPath(Path location) throws IOException {
		// Check if file was initialized
		if (initialized)
			throw new IllegalStateException("Current object already initialized.");
		// Load file
		resource = new FileResource(location);
		// Temporal storage configuration
		bufferStorage = new IniStorage();
		bufferStorage.section = (SectionImpl) defaultSection;
		updateData();
		bufferStorage = null;
	}

	/**
	 * Update file content
	 *
	 * @throws IOException Error if file not exists or is not valid
	 */
	public void updateContent() throws IOException {
		if (!initialized)
			throw new IllegalStateException("Current is not initialized.");
		// Set false state
		initialized = false;
		// Clear content
		defaultSection = new SectionImpl("Default", null);
		sectionSet.clear();
		// Initialize buffer
		bufferStorage = new IniStorage();
		bufferStorage.section = (SectionImpl) defaultSection;
		updateData();
		bufferStorage = null;
	}

	/**
	 * Get ini default section.
	 * This section always exists and is used to save all orphan elements
	 *
	 * @return Default section
	 */
	public ISection getDefaultSection() {
		return defaultSection;
	}

	/**
	 * Check if section exists
	 *
	 * @param name Target section name
	 * @return Return {@code true} if section exists or {@code false} otherwise
	 */
	public boolean sectionExists(String name) {
		return sectionSet.stream().anyMatch(el -> el.getSectionName().equals(name));
	}

	/**
	 * Get section from name
	 *
	 * @param name Target section name
	 * @return Returns a section instance with all information
	 */
	public ISection getSection(String name) {
		// Check if exists
		if (!sectionExists(name))
			return null;
		// Get first coincidence
		return sectionSet
			.stream()
			.filter(el -> el.getSectionName().equals(name))
			.collect(Collectors.toList()).get(0);
	}

	/**
	 * Get all sections with the same name
	 *
	 * @param name Target section name
	 * @return Return a list with all sections
	 */
	public List<ISection> getSections(String name) {
		// Check if exists
		if (!sectionExists(name))
			return null;
		// Return all coincidences
		return sectionSet
			.stream()
			.filter(el -> el.getSectionName().equals(name))
			.collect(Collectors.toList());
	}

	/**
	 * Get all sections
	 *
	 * @return Get a set with all sections
	 */
	@UnmodifiableView
	public Set<ISection> getAllSections() {
		return Collections.unmodifiableSet(sectionSet);
	}

	/**
	 * Return initialize state
	 *
	 * @return Return current object status
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Override
	public String toString() {
		return "Ini{" +
			"sections=" + sectionSet +
			'}';
	}

	/* ------------------------------------------------------------------
	 *
	 * IO Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Check if file extension is valid
	 *
	 * @throws IOException Error if file is not valid
	 */
	private void checkExtension() throws IOException {
		if (!iniValidFileExtensions.contains(resource.getExtension())) {
			throw new IOException("Target file is not valid INI file.");
		}
	}

	/**
	 * Update all ini data
	 *
	 * @throws IOException Error if file not exists or file is not valid
	 */
	private synchronized void updateData() throws IOException {
		// Check if exists
		if (!resource.exists())
			throw new FileNotFoundException(String.format("File location \"%s\" not exists", resource));
		// Check extensions
		checkExtension();
		// Update information
		InputStream fStream = Files.newInputStream(resource.getResourcePath());
		BufferedReader reader = new BufferedReader(new InputStreamReader(fStream));
		// Iterate all lines
		while ((bufferStorage.line = reader.readLine()) != null) {
			// Check old buffer
			checkOldBuffer();
			// Ignore empty and comment lines
			if (SectionImpl.isLineComment(bufferStorage.line))
				continue;
			// Check if current line is a section
			if (SectionImpl.isValidSection(bufferStorage.line)) {
				// Create new section
				createNewSection(bufferStorage.line);
				continue;
			}
			// Check if current line is a property
			if (SectionImpl.isValidProperty(bufferStorage.line)) {
				// Create new property
				createNewProperty(bufferStorage.line);
				continue;
			}
			// Add extra data
			if (bufferStorage.buffer == null) {
				bufferStorage.buffer = new StringBuilder(bufferStorage.line);
			}
		}
		// Check if exists any orphan information
		checkOldBuffer();
		// Dispose all resources
		reader.close();
		fStream.close();
		initialized = true;
	}

	/**
	 * Check temporal buffers.
	 */
	private void checkOldBuffer() {
		// Check if exists any buffer and check if property exists
		if (bufferStorage.buffer == null) return;
		if (!bufferStorage.section.properties.containsKey(bufferStorage.lastProperty)) return;

		// Insert last buffer to last known location
		String lastVal = bufferStorage.section.properties.get(bufferStorage.lastProperty).trim();
		String bufferVal = bufferStorage.buffer.toString().trim();
		// Replace information
		bufferStorage.section.properties.put(
			bufferStorage.lastProperty,
			DataConvert.cleanString((lastVal + " " + bufferVal).trim())
		);
		// Clean buffer
		bufferStorage.buffer = null;
	}

	/**
	 * Create new section from line
	 *
	 * @param line Target line
	 */
	private void createNewSection(String line) {
		// Storage config
		String sectionName = SectionImpl.getSectionName(line);
		Map<String, String> attrs = SectionImpl.getSectionAttributes(line);
		// Check section name is valid
		if (sectionName == null) return;
		SectionImpl section = new SectionImpl(sectionName, defaultSection);
		// Configure section
		if (attrs != null)
			section.attributes.putAll(attrs);
		// Insert to list
		sectionSet.add(section);
		bufferStorage.section = section;
	}

	/**
	 * Generate new section.
	 *
	 * @param line Current line
	 */
	private void createNewProperty(String line) {
		// Storage elements
		Map.Entry<String, String> property = SectionImpl.getPropertyEntry(line);
		// Check if property is valid
		if (property == null) return;
		// Clean property
		String key = DataConvert.cleanString(property.getKey());
		String value = DataConvert.cleanString(property.getValue());
		// Insert property
		bufferStorage.section.properties.put(key, value);
		bufferStorage.lastProperty = key;
	}

	/* ------------------------------------------------------------------
	 *
	 * Extra classes
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Internal class used like buffer
	 */
	private static class IniStorage {

		/**
		 * Current file section
		 */
		volatile public SectionImpl section;

		/**
		 * Current file line
		 */
		volatile public String line;

		/**
		 * Buffer with old data
		 */
		volatile public StringBuilder buffer;

		/**
		 * Last section property
		 */
		volatile public String lastProperty;

	}

}
