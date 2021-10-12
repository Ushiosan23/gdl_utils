package org.godot.utilities.utils;

import org.godot.utilities.Chooser;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class IOUtilsTest {

	private final JFileChooser chooser = new JFileChooser();


	@Test
	public void walkDirectory() throws IOException {
		// Configure chooser
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		Chooser.displayChooser(chooser);
		// Get directory
		Path location = chooser.getSelectedFile().toPath();
		List<Path> allFiles = IOUtils.walkDirectory(
			location,
			true,
			Files::isRegularFile,
			item -> Objects.equals(IOUtils.getExtension(item), "txt")
		);

		Assert.assertNotNull(allFiles);

		System.out.println(allFiles.size());
		System.out.println(allFiles);
	}

	@Test
	public void getExtension() throws IOException {
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		Chooser.displayChooser(chooser);
		// Get extension
		String extension = IOUtils.getExtension(chooser.getSelectedFile().toPath());
		System.out.println(extension);
	}

}
