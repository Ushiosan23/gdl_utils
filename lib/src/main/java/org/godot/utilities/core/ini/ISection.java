package org.godot.utilities.core.ini;

import org.godot.utilities.core.error.InvalidTypeValueException;
import org.godot.utilities.core.error.PropertyNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISection {

	/**
	 * Change section name.
	 *
	 * @param newName Target new name
	 */
	void changeName(String newName);

	/**
	 * Change default section.
	 *
	 * @param section Target default section
	 */
	void setDefaultSection(ISection section);

	/**
	 * Get current section name
	 *
	 * @return Section name
	 */
	@NotNull String getSectionName();

	/**
	 * Get default section
	 *
	 * @return Return default section
	 */
	@Nullable ISection getDefaultSection();

	/* ------------------------------------------------------------------
	 *
	 * Section attributes methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Get section attributes
	 *
	 * @return Section attributes map
	 */
	@NotNull
	Map<String, String> getAttributes();

	/**
	 * Get all property names
	 *
	 * @return Return all property names
	 */
	@NotNull
	Set<String> getPropertyNames();

	/* ------------------------------------------------------------------
	 *
	 * Section properties methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Get section property value.
	 *
	 * @param name Target property name
	 * @return Section property value
	 * @throws PropertyNotFoundException If property not exists
	 */
	String getProperty(@NotNull String name) throws PropertyNotFoundException;

	/**
	 * Get section property value or default if not exists.
	 *
	 * @param name   Target property name
	 * @param defVal Default value if property not exists
	 * @return Section property value or {@code defVal} value if not exists
	 */
	@Nullable String getProperty(@NotNull String name, @Nullable String defVal);

	/**
	 * Get boolean property value.
	 *
	 * @param name Target property name
	 * @return Return property boolean value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	boolean getBooleanProperty(@NotNull String name) throws InvalidTypeValueException;

	/**
	 * Get boolean property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property boolean value or {@code defValue} if not exists
	 */
	boolean getBooleanPropertyOrDefault(@NotNull String name, boolean defValue);

	/**
	 * Get byte property value.
	 *
	 * @param name Target property name
	 * @return Return property byte value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	byte getByteProperty(@NotNull String name) throws InvalidTypeValueException;

	/**
	 * Get byte property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property byte value or {@code defValue} if not exists
	 */
	byte getBytePropertyOrDefault(@NotNull String name, byte defValue);

	/**
	 * Get short property value.
	 *
	 * @param name Target property name
	 * @return Return property short value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	short getShortProperty(@NotNull String name) throws InvalidTypeValueException;

	/**
	 * Get short property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property short value or {@code defValue} if not exists
	 */
	short getShortPropertyOrDefault(@NotNull String name, short defValue);

	/**
	 * Get integer property value.
	 *
	 * @param name Target property name
	 * @return Return property integer value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	int getIntegerProperty(@NotNull String name) throws InvalidTypeValueException;

	/**
	 * Get integer property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property integer value or {@code defValue} if not exists
	 */
	int getIntegerPropertyOrDefault(@NotNull String name, int defValue);

	/**
	 * Get float property value.
	 *
	 * @param name Target property name
	 * @return Return property float value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	float getFloatProperty(@NotNull String name) throws InvalidTypeValueException;

	/**
	 * Get float property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property float value or {@code defValue} if not exists
	 */
	float getFloatPropertyOrDefault(@NotNull String name, float defValue);

	/**
	 * Get double property value.
	 *
	 * @param name Target property name
	 * @return Return property double value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	double getDoubleProperty(@NotNull String name) throws InvalidTypeValueException;

	/**
	 * Get double property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property double value or {@code defValue} if not exists
	 */
	double getDoublePropertyOrDefault(@NotNull String name, double defValue);

	/**
	 * Get long property value.
	 *
	 * @param name Target property name
	 * @return Return property long value
	 * @throws InvalidTypeValueException Error if casting value is not possible
	 */
	long getLongProperty(@NotNull String name) throws InvalidTypeValueException;

	/**
	 * Get long property value.
	 *
	 * @param name     Target property name
	 * @param defValue Default value if property not exists
	 * @return Return property long value or {@code defValue} if not exists
	 */
	long getLongPropertyOrDefault(@NotNull String name, long defValue);

	/**
	 * Get list property value.
	 *
	 * @param name      Property name
	 * @param separator List separator. If separator is {@code null} by default the value is a single coma "{@code ,}"
	 * @return Return a list with all elements
	 * @throws PropertyNotFoundException Error if property not found
	 */
	@NotNull @UnmodifiableView List<String> getPropertyList(@NotNull String name, @Nullable String separator)
		throws PropertyNotFoundException;

	/**
	 * Get list property value.
	 *
	 * @param name Property name
	 * @return Return a list with all elements
	 * @throws PropertyNotFoundException Error if property not exists
	 */
	@NotNull @UnmodifiableView List<String> getPropertyList(@NotNull String name)
		throws PropertyNotFoundException;

	/**
	 * Get list property value.
	 *
	 * @param name      Property name
	 * @param separator List separator. If separator is {@code null} by default the value is a single coma "{@code ,}"
	 * @return Return a list with all elements
	 */
	@Nullable @UnmodifiableView List<String> getPropertyListOrNull(@NotNull String name, @Nullable String separator);

	/**
	 * Get list property value.
	 *
	 * @param name Property name
	 * @return Return a list with all elements
	 */
	@Nullable @UnmodifiableView List<String> getPropertyListOrNull(@NotNull String name);

}
