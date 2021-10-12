package org.godot.utilities.core.ini;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class SectionImplTest {

	@Test
	public void getSectionNameTest() {
		String sectionWithoutParams = "[NormalSection]";
		String sectionWithParams = "[Section type=\"VisualShader\" version=2]";

		System.out.println("-------------------- Section Names --------------------");

		System.out.println(SectionImpl.getSectionName(sectionWithoutParams));
		System.out.println(SectionImpl.getSectionName(sectionWithParams));
	}

	@Test
	public void getSectionAttributesTest() {
		String sectionWithoutParams = "[NormalSection]";
		String sectionWithParams = "[ext_resource path=\"res://Scripts/new script.gd\" type=\"Script\" id=3]";

		System.out.println("-------------------- Section Attributes --------------------");

		System.out.println(
			SectionImpl.getSectionName(sectionWithParams)
		);
		System.out.println(
			SectionImpl.getSectionAttributes(sectionWithoutParams)
		);
		System.out.println(
			SectionImpl.getSectionAttributes(sectionWithParams)
		);
	}

	@Test
	public void isValidPropertyTest() {
		String validProperty = "script = ExtResource( 3 )";
		String validProperty2 = "run/main_scene=\"res://Scenes/2dScene.tscn\"";
		String invalidProperty = "1section/app/name=\"xd.xml\"";


		boolean b1, b2, b3;
		b1 = SectionImpl.isValidProperty(validProperty);
		b2 = SectionImpl.isValidProperty(validProperty2);
		b3 = SectionImpl.isValidProperty(invalidProperty);

		String resultVal = "%5s: %s\n";
		String errResolution = "Invalid Regex resolution";

		System.out.println("-------------------- Check Properties --------------------");
		System.out.printf(resultVal, b1, validProperty);
		System.out.printf(resultVal, b2, validProperty2);
		System.out.printf(resultVal, b3, invalidProperty);

		Assert.assertTrue(errResolution, b1);
		Assert.assertTrue(errResolution, b2);
		Assert.assertFalse(errResolution, b3);
	}

	@Test
	public void getPropertyEntryTest() {
		String v1 = "script = ExtResource( 3 )";
		String v2 = "run/main_scene=\"res://Scenes/2dScene.tscn\"";
		String v3 = "1section/app/name=\"xd.xml\"";

		Map.Entry<String, String> r1 = SectionImpl.getPropertyEntry(v1);
		Map.Entry<String, String> r2 = SectionImpl.getPropertyEntry(v2);
		Map.Entry<String, String> r3 = SectionImpl.getPropertyEntry(v3);

		System.out.println("-------------------- Check Property Entry --------------------");
		System.out.println(r1);
		System.out.println(r2);
		System.out.println(r3);

		Assert.assertNotNull(r1);
		Assert.assertNotNull(r2);
		Assert.assertNull(r3);
	}

}
