package org.godot.utilities.engine;

import org.godot.utilities.Chooser;
import org.godot.utilities.core.VideoDriver;
import org.godot.utilities.project.Project;
import org.junit.Test;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class EngineThreadTest {

	JFileChooser chooser = new JFileChooser();

	@Test
	public void runTest() throws Exception {
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
		// Show another dialog
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		Chooser.displayChooser(chooser);
		Path projectDir = chooser.getSelectedFile().toPath();

		Engine engine = new Engine(selected);
		Project project = new Project(projectDir, engine.engineVersion);
		// Generate thread
		EngineArgs.Builder args = new EngineArgs.Builder()
			.setSize(new Dimension(1000, 600))
			.setPosition(new Point(0, 0))
			.setProject(project)
			.setScene(project.getMainScene())
			.openEditor()
			.setVideoDriver(VideoDriver.GLES3);
		EngineThread thread = new EngineThread(engine);
		// Set execution events
		thread.setOnExecutionDataListener((data, isError) -> {
			String info = new String(data, StandardCharsets.UTF_8);
			System.err.println(info);
			if (info.contains("0.0")) thread.closeAndKill();
		});
		thread.setOnEngineStateChangeListener((oldState, newState) -> {
			System.out.println(oldState);
			System.out.println(newState);
		});
		thread.setOnEngineClosingListener(System.err::println);
		// Execute
		thread.start(args);
		thread.join();
	}


}
