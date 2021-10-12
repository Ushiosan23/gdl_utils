package org.godot.utilities.core;

import org.jetbrains.annotations.Nullable;

public enum AudioDriver {

	/**
	 * Audio driver {@code "RtAudio-DirectSound"} used in {@link Version#V1} and {@link Version#V2} of Godot
	 *
	 * @see <a href="https://github.com/thestk/rtaudio">RtAudio-DirectSound (Github)</a>
	 */
	RtAudio("RtAudio-DirectSound"),

	/**
	 * Audio driver {@code "WASAPI"} used only in {@link Version#V3}
	 *
	 * @see <a href="https://docs.microsoft.com/en-us/windows/win32/coreaudio/wasapi">WASAPI (Microsoft docs)</a>
	 */
	WASAPI("WASAPI"),

	/**
	 * Audio driver {@code "Dummy"} used only in {@link Version#V3}
	 */
	DUMMY("Dummy");

	/**
	 * Driver name
	 */
	public final String driverName;

	/**
	 * Driver name element
	 *
	 * @param name Target driver name
	 */
	AudioDriver(String name) {
		driverName = name;
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
	public static @Nullable AudioDriver getFromName(String name) {
		for (AudioDriver driver : values()) {
			if (name.equalsIgnoreCase(driver.driverName)) return driver;
		}
		return null;
	}

}
