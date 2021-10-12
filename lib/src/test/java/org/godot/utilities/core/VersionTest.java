package org.godot.utilities.core;

import org.junit.Assert;
import org.junit.Test;

public class VersionTest {

	@Test
	public void getRecommendedTest() {
		Version recommended = Version.getRecommended();

		System.out.println(recommended);
		Assert.assertNotEquals(Version.UNKNOWN, recommended);
	}

}
