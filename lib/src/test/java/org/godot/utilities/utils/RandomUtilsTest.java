package org.godot.utilities.utils;

import org.junit.Test;

public class RandomUtilsTest {

	@Test
	public void runTest() {
		System.out.println(ThreadUtils.getEngineThreadGroup());
		System.out.println(ThreadUtils.getEngineThreadGroup());

		System.out.println(RandomUtils.getRandomString());
		System.out.println(RandomUtils.getRandomString(20));
	}

}
