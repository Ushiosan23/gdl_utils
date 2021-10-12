package org.godot.utilities.utils;

public final class ThreadUtils {

	/**
	 * Default engine thread group
	 */
	private static ThreadGroup engineGroup;

	/**
	 * Thread group name
	 */
	private static final String threadGroupName = RandomUtils
		.getRandomString(20);

	/* ------------------------------------------------------------------
	 *
	 * Constructor
	 *
	 * ------------------------------------------------------------------ */

	private ThreadUtils() {
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Get engine thread group instance
	 *
	 * @return Instance of thread group
	 */
	public static ThreadGroup getEngineThreadGroup() {
		if (engineGroup == null)
			engineGroup = new ThreadGroup(threadGroupName);
		return engineGroup;
	}


}
