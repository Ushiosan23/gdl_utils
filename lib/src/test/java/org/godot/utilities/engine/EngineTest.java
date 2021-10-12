package org.godot.utilities.engine;

import org.godot.utilities.Chooser;
import org.junit.Test;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Path;

public class EngineTest {

	JFileChooser chooser = new JFileChooser();

	@Test
	public void EngineInstanceTest() throws IOException {
		// Home
		Path home = Path.of(System.getProperty("user.home"));
		// Configure chooser
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setCurrentDirectory(home.toFile());
		chooser.setDialogTitle("Select a valid Godot Executable");
		chooser.setFileFilter(
			new FileNameExtensionFilter("Windows Executable", "exe")
		);
		// Show dialog
		Chooser.displayChooser(chooser);

		Path selected = chooser.getSelectedFile().toPath();
		Engine engine = new Engine(selected);

		System.out.println(engine);
	}

}
