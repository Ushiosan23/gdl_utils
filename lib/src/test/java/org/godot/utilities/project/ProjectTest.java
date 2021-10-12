package org.godot.utilities.project;

import org.godot.utilities.Chooser;
import org.godot.utilities.core.Version;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;

public class ProjectTest {

	JFileChooser chooser = new JFileChooser();

	@Test
	public void projectTest() throws Exception {
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		Chooser.displayChooser(chooser);

		Project project = new Project(
			chooser.getSelectedFile().toPath(),
			Version.V2
		);

		Assert.assertNotNull(project);

		System.out.println(project.getProjectName());
		System.out.println(project.getProjectIcon());
		System.out.println(project.getMainScene());
		System.out.println(project.getProjectDescription());
		System.out.println(project.getProjectVideoDriver());
	}

}
