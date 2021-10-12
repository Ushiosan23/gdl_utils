package org.godot.utilities.core.ini;

import org.godot.utilities.core.ini.convertion.DataConvert;
import org.godot.utilities.core.error.InvalidTypeValueException;
import org.godot.utilities.core.error.PropertyNotFoundException;
import org.godot.utilities.utils.MapUtils;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class SectionImpl implements ISection {

	/* ------------------------------------------------------------------
	 *
	 * Static properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Section attributes regex
	 */
	@RegExp
	public static final String sectionAttributesRegex = "([\\w]+)=(\"(.*?)\"|\\d+)";

	/**
	 * Regular expression to check if line is a valid property
	 */
	@RegExp
	public static final String propertySectionRegex = "^([A-Za-z][\\w/]+)\\s?=\\s?(.*+)$";

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Default section
	 */
	private ISection defaultSection;

	/**
	 * Section name
	 */
	private String sectionName;

	/**
	 * Section properties
	 */
	final Map<String, String> properties = new HashMap<>();

	/**
	 * Section attributes
	 */
	final Map<String, String> attributes = new HashMap<>();

	/* ------------------------------------------------------------------
	 *
	 * Constructors
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Section default constructor
	 *
	 * @param n  Section name
	 * @param bs Default section
	 */
	public SectionImpl(@NotNull String n, @Nullable ISection bs) {
		sectionName = n;
		defaultSection = bs;
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Get current section name
	 *
	 * @return Section name
	 */
	@Override
	public @NotNull String getSectionName() {
		return sectionName;
	}

	/**
	 * Get default section
	 *
	 * @return Return default section
	 */
	@Override
	public @Nullable ISection getDefaultSection() {
		return defaultSection;
	}

	/**
	 * Get section attributes
	 *
	 * @return Section attributes map
	 */
	@Override
	public @NotNull Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Get all property names
	 *
	 * @return Return all property names
	 */
	@Contract(pure = true)
	@Override
	public @NotNull Set<String> getPropertyNames() {
		return properties.keySet();
	}

	/**
	 * Change section name.
	 *
	 * @param newName Target new name
	 */
	@Override
	public void changeName(String newName) {
		sectionName = newName;
	}

	/**
	 * Change default section.
	 *
	 * @param section Target default section
	 */
	@Override
	public void setDefaultSection(ISection section) {
		defaultSection = section;
	}

	/**
	 * Get section property value.
	 *
	 * @param name Target property name
	 * @return Section property value
	 * @throws PropertyNotFoundException If property not exists
	 */
	@Override
	public String getProperty(@NotNull String name) throws PropertyNotFoundException {
		if (!properties.containsKey(name))
			throw new PropertyNotFoundException(String.format("Property \"%s\" not exists.", name));
		return properties.get(name);
	}

	/**
	 * Get section property value or default if not exists.
	 *
	 * @param name   Target property name
	 * @param defVal Default value if property not exists
	 * @return Section property value or {@code defVal} value if not exists
	 */
	@Override
	public @Nullable String getProperty(@NotNull String name, @Nullable String defVal) {
		try {
			return getProperty(name);
		} catch (PropertyNotFoundException err) {
			return defVal;
		}
	}

	/**
	 * Get boolean property value.
	 *
	 * @param name Target property name
	 * @return Return property boolean value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	@Override
	public boolean getBooleanProperty(@NotNull String name) throws InvalidTypeValueException {
		try {
			return DataConvert.parseBoolean(getProperty(name));
		} catch (PropertyNotFoundException err) {
			throw new InvalidTypeValueException(err);
		}
	}

	/**
	 * Get boolean property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property boolean value or {@code defValue} if not exists
	 */
	@Override
	public boolean getBooleanPropertyOrDefault(@NotNull String name, boolean defValue) {
		try {
			return getBooleanProperty(name);
		} catch (InvalidTypeValueException ignored) {
			return defValue;
		}
	}

	/**
	 * Get byte property value.
	 *
	 * @param name Target property name
	 * @return Return property byte value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	@Override
	public byte getByteProperty(@NotNull String name) throws InvalidTypeValueException {
		try {
			return DataConvert.parseByte(getProperty(name));
		} catch (PropertyNotFoundException err) {
			throw new InvalidTypeValueException(err);
		}
	}

	/**
	 * Get byte property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property byte value or {@code defValue} if not exists
	 */
	@Override
	public byte getBytePropertyOrDefault(@NotNull String name, byte defValue) {
		try {
			return getByteProperty(name);
		} catch (InvalidTypeValueException ignored) {
			return defValue;
		}
	}

	/**
	 * Get short property value.
	 *
	 * @param name Target property name
	 * @return Return property short value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	@Override
	public short getShortProperty(@NotNull String name) throws InvalidTypeValueException {
		try {
			return DataConvert.parseShort(getProperty(name));
		} catch (PropertyNotFoundException err) {
			throw new InvalidTypeValueException(err);
		}
	}

	/**
	 * Get short property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property short value or {@code defValue} if not exists
	 */
	@Override
	public short getShortPropertyOrDefault(@NotNull String name, short defValue) {
		try {
			return getShortProperty(name);
		} catch (InvalidTypeValueException ignored) {
			return defValue;
		}
	}

	/**
	 * Get integer property value.
	 *
	 * @param name Target property name
	 * @return Return property integer value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	@Override
	public int getIntegerProperty(@NotNull String name) throws InvalidTypeValueException {
		try {
			return DataConvert.parseInt(getProperty(name));
		} catch (PropertyNotFoundException err) {
			throw new InvalidTypeValueException(err);
		}
	}

	/**
	 * Get integer property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property integer value or {@code defValue} if not exists
	 */
	@Override
	public int getIntegerPropertyOrDefault(@NotNull String name, int defValue) {
		try {
			return getIntegerProperty(name);
		} catch (InvalidTypeValueException ignored) {
			return defValue;
		}
	}

	/**
	 * Get float property value.
	 *
	 * @param name Target property name
	 * @return Return property float value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	@Override
	public float getFloatProperty(@NotNull String name) throws InvalidTypeValueException {
		try {
			return DataConvert.parseFloat(getProperty(name));
		} catch (PropertyNotFoundException err) {
			throw new InvalidTypeValueException(err);
		}
	}

	/**
	 * Get float property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property float value or {@code defValue} if not exists
	 */
	@Override
	public float getFloatPropertyOrDefault(@NotNull String name, float defValue) {
		try {
			return getFloatProperty(name);
		} catch (InvalidTypeValueException ignored) {
			return defValue;
		}
	}

	/**
	 * Get double property value.
	 *
	 * @param name Target property name
	 * @return Return property double value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	@Override
	public double getDoubleProperty(@NotNull String name) throws InvalidTypeValueException {
		try {
			return DataConvert.parseDouble(getProperty(name));
		} catch (PropertyNotFoundException err) {
			throw new InvalidTypeValueException(err);
		}
	}

	/**
	 * Get double property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property double value or {@code defValue} if not exists
	 */
	@Override
	public double getDoublePropertyOrDefault(@NotNull String name, double defValue) {
		try {
			return getDoubleProperty(name);
		} catch (InvalidTypeValueException ignored) {
			return defValue;
		}
	}

	/**
	 * Get long property value.
	 *
	 * @param name Target property name
	 * @return Return property long value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	@Override
	public long getLongProperty(@NotNull String name) throws InvalidTypeValueException {
		try {
			return DataConvert.parseLong(getProperty(name));
		} catch (PropertyNotFoundException err) {
			throw new InvalidTypeValueException(err);
		}
	}

	/**
	 * Get long property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property long value or {@code defValue} if not exists
	 */
	@Override
	public long getLongPropertyOrDefault(@NotNull String name, long defValue) {
		try {
			return getLongProperty(name);
		} catch (InvalidTypeValueException ignored) {
			return defValue;
		}
	}

	/**
	 * Get list property value.
	 *
	 * @param name      Property name
	 * @param separator List separator. If separator is {@code null} by default the value is a single coma "{@code ,}"
	 * @return Return a list with all elements
	 */
	@Contract(pure = true)
	@Override
	public @NotNull @UnmodifiableView List<String> getPropertyList(@NotNull String name, @Nullable String separator)
		throws PropertyNotFoundException {
		// Storage
		separator = separator == null ? DataConvert.ValueSeparators.get("listSeparator") : separator;
		String value = getProperty(name);
		List<String> result = new ArrayList<>();
		// Add all elements
		Collections.addAll(result, value.split(separator));
		// Get result
		return Collections.unmodifiableList(result);
	}

	/**
	 * Get list property value.
	 *
	 * @param name Property name
	 * @return Return a list with all elements
	 */
	@Contract(pure = true)
	@Override
	public @NotNull @UnmodifiableView List<String> getPropertyList(@NotNull String name) throws PropertyNotFoundException {
		return getPropertyList(name, null);
	}

	/**
	 * Get list property value.
	 *
	 * @param name      Property name
	 * @param separator List separator. If separator is {@code null} by default the value is a single coma "{@code ,}"
	 * @return Return a list with all elements
	 */
	@Override
	public @Nullable @UnmodifiableView List<String> getPropertyListOrNull(@NotNull String name, @Nullable String separator) {
		try {
			return getPropertyList(name, separator);
		} catch (PropertyNotFoundException ignored) {
			return null;
		}
	}

	/**
	 * Get list property value.
	 *
	 * @param name Property name
	 * @return Return a list with all elements
	 */
	@Override
	public @Nullable @UnmodifiableView List<String> getPropertyListOrNull(@NotNull String name) {
		return getPropertyListOrNull(name, null);
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return "Section{" +
			"sectionName='" + sectionName + '\'' +
			", properties=" + properties +
			", attributes=" + attributes +
			'}';
	}

	/* ------------------------------------------------------------------
	 *
	 * Utilities
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Detect if current line is a comment.
	 *
	 * @param line Current line to check
	 * @return Return {@code true} if line is a comment or {@code false} otherwise
	 */
	public static boolean isLineComment(@NotNull String line) {
		return line.trim().startsWith(";") || line.trim().length() == 0;
	}

	/**
	 * Check if current line is a valid section
	 *
	 * @param line Current line to check
	 * @return Return {@code true} if line is a valid section or {@code false} otherwise
	 */
	public static boolean isValidSection(@NotNull String line) {
		// Storage result
		boolean result = true;
		String sectionData = line;
		// Check if starts with [
		if (!line.startsWith("[")) {
			result = false;
		} else {
			sectionData = sectionData.substring(1);
		}
		if (!line.endsWith("]")) {
			result = false;
		} else {
			sectionData = sectionData.substring(0, sectionData.length() - 1);
		}
		// Check if section data is valid
		if (sectionData.trim().length() == 0) {
			result = false;
		}
		return result;
	}

	/**
	 * Check if current line is a valid property
	 *
	 * @param line Current line to check
	 * @return Return {@code true} if line is a valid property or {@code false} otherwise
	 */
	public static boolean isValidProperty(@NotNull String line) {
		line = line.trim();
		// Create pattern
		Pattern pattern = Pattern.compile(propertySectionRegex);
		Matcher matcher = pattern.matcher(line);
		// Check if matcher has any result
		return matcher.find();
	}

	/**
	 * Get property entry value
	 *
	 * @param line Current line to check
	 * @return Return an entry with property name and value or {@code null} if property is not valid
	 */
	public static @Nullable Map.Entry<String, String> getPropertyEntry(@NotNull String line) {
		line = line.trim();
		// Check if is a valid property
		if (!isValidProperty(line)) return null;
		// Get a pattern
		Pattern pattern = Pattern.compile(propertySectionRegex);
		Matcher matcher = pattern.matcher(line);
		// Get the unique group
		if (matcher.find()) {
			return MapUtils.entry(
				DataConvert.cleanString(matcher.group(1).trim()),
				DataConvert.cleanString(matcher.group(2).trim())
			);
		}
		return null;
	}

	/**
	 * Get section name without attributes
	 *
	 * @param line Current line to check
	 * @return Return a section name or {@code null} if section is not valid
	 */
	public static @Nullable String getSectionName(@NotNull String line) {
		// Check if is valid
		if (!isValidSection(line)) return null;
		// Storage partial result
		line = line.trim();
		String data = line.substring(1, line.length() - 1);
		// Discard attributes
		List<String> nameElements = Arrays.stream(data.split(sectionAttributesRegex))
			.map(String::trim)
			.filter(trim -> trim.length() != 0)
			.collect(Collectors.toList());
		String sectionName = null;
		// Get the first one element
		if (nameElements.size() != 0) {
			sectionName = nameElements.get(0);
		}
		return sectionName;
	}

	/**
	 * Get all section attributes. This kind of attributes are not conventional in
	 * normal ini files, but you can use it in another similar formats like "Godot scenes" or "File configurations"
	 *
	 * @param line Current line to check
	 * @return Returns all section attributes
	 */
	public static @Nullable Map<String, String> getSectionAttributes(@NotNull String line) {
		// Check if section is valid
		if (!isValidSection(line)) return null;
		// Storage partial result
		line = line.trim();
		String data = line.substring(1, line.length() - 1);
		// Discard attributes
		Pattern pattern = Pattern.compile(sectionAttributesRegex);
		Matcher matcher = pattern.matcher(data);
		Map<String, String> attributes = new HashMap<>();
		// Find all attributes
		while (matcher.find()) {
			String group = matcher.group();
			String[] assignGroup = group.split(DataConvert.ValueSeparators.get("assignSeparator"));
			// Check if group are two members
			if (assignGroup.length == 2) {
				attributes.put(
					DataConvert.cleanString(assignGroup[0]),
					DataConvert.cleanString(assignGroup[1])
				);
			}
		}
		return attributes;
	}

}
