package org.godot.utilities.utils;

import org.junit.Test;

import java.util.Map;

public class MapUtilsTest {

	@Test
	public void runTest() {
		Map<String, String[]> extensions = MapUtils.of(
			MapUtils.entry("Scenes", Arr.of("tscn", "xml", "ini", "gd")),
			MapUtils.entry("Shaders", Arr.of(""))
		);

		System.out.println(extensions);
	}


}
