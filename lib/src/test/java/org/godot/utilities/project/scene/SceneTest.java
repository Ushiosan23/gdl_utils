package org.godot.utilities.project.scene;

import org.godot.utilities.Chooser;
import org.godot.utilities.core.Version;
import org.junit.Test;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Path;

public class SceneTest {

	JFileChooser chooser = new JFileChooser();

	@Test
	public void runTest() throws Exception {
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(
			new FileNameExtensionFilter("Godot Scene", "tscn")
		);
		Chooser.displayChooser(chooser);

		Path fileSelected = chooser.getSelectedFile().toPath();
		Scene scene = new Scene(fileSelected, Version.V3);

		scene.updateContent();

		System.out.println(scene);
	}

}
