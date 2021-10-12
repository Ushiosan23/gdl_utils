package org.godot.utilities.core.ini;

import org.godot.utilities.R;
import org.godot.utilities.utils.ContentGD;
import org.junit.Test;

import java.util.Arrays;

public class IniTest {

	@Test
	public void runTest() throws Exception {
		// Add more extensions to read
		Ini.iniValidFileExtensions.addAll(Arrays.asList(ContentGD.allValidReadableExtensions));
		// Create ini
		Ini fileIni = new Ini();
		// Load file
		fileIni.loadFromPath(R.getPath("ini/example.ini"));
		// Get section
		ISection section = fileIni.getSection("input");

		System.out.println(section.getProperty("ui_page_down"));
	}

}
