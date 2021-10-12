package org.godot.utilities.project.resources;

import org.godot.utilities.Chooser;
import org.godot.utilities.core.Version;
import org.junit.Test;

import javax.swing.*;
import java.nio.file.Path;

public class ProjectResourceTest {

	private final JFileChooser chooser = new JFileChooser();

	@Test
	public void runTest() throws Exception {
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// Display chooser
		Chooser.displayChooser(chooser);
		// File
		Path f = chooser.getSelectedFile().toPath();
		ProjectResource resource = new ProjectResource(f, Version.getRecommended());
		// Show info
		System.out.println(resource);
	}


}
