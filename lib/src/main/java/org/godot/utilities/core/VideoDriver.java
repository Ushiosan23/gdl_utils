package org.godot.utilities.core;

import org.jetbrains.annotations.Nullable;

public enum VideoDriver {
	/**
	 * Video driver {@code "GLES2"} used in {@link Version#V1}, {@link Version#V2} and {@link Version#V3}
	 */
	GLES2("GLES2"),

	/**
	 * Video driver {@code "GLES3"} used in {@link Version#V3}
	 */
	GLES3("GLES3"),

	/**
	 * Video driver {@code "VULKAN"} used only in {@link Version#V4}
	 */
	VULKAN;

	/**
	 * Driver name
	 */
	public final String driverName;

	/**
	 * Driver name element
	 *
	 * @param name Target driver name
	 */
	VideoDriver(String name) {
		driverName = name;
	}

	/**
	 * Driver without name
	 */
	VideoDriver() {
		this("");
	}


	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Get driver from name
	 *
	 * @param name Target names to search
	 * @return Driver type or {@code null} if driver not exists
	 */
	public static @Nullable VideoDriver getFromName(String name) {
		for (VideoDriver driver : values()) {
			if (name.equalsIgnoreCase(driver.driverName)) return driver;
		}
		return null;
	}

}
