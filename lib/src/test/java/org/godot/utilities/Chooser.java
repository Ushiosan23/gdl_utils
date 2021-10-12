package org.godot.utilities;

import org.junit.Assert;

import javax.swing.*;

public class Chooser {

	public static void displayChooser(JFileChooser chooser) {
		// Display
		int status = chooser.showOpenDialog(null);
		Assert.assertEquals(JFileChooser.APPROVE_OPTION, status);
		Assert.assertNotNull(chooser.getSelectedFile());
	}

}
