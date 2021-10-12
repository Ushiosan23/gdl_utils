package org.godot.utilities.core.ini.convertion;

import org.godot.utilities.core.error.InvalidTypeValueException;
import org.godot.utilities.utils.Arr;
import org.junit.Test;

import java.util.Arrays;

public class DataConvertTest {

	@Test
	public void runTest() throws Exception {
		Boolean[] trueResults = Arr.of(
			DataConvert.parseBoolean("yes"),
			DataConvert.parseBoolean("y"),
			DataConvert.parseBoolean("true"),
			DataConvert.parseBoolean("1")
		);

		Boolean[] falseResults = Arr.of(
			DataConvert.parseBoolean("no"),
			DataConvert.parseBoolean("n"),
			DataConvert.parseBoolean("false"),
			DataConvert.parseBoolean("0")
		);

		System.out.println(Arrays.toString(trueResults));
		System.out.println(Arrays.toString(falseResults));
	}


	@Test(expected = InvalidTypeValueException.class)
	public void runInvalidTest() throws Exception {
		try {
			DataConvert.parseBoolean("yes no");
		} catch (InvalidTypeValueException err) {
			err.printStackTrace();
			throw new InvalidTypeValueException(err);
		}
	}

}
