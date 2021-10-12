package org.godot.utilities.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ArrTest {

	@Test
	public void ofTest() {
		double[] arr = Arr.doubleOf(0.1, 0, 21, 43);
		Assert.assertNotNull(arr);
		System.out.println(Arrays.toString(arr));
	}

	@After
	public void emptyTest() {
		String[] emptyArr = Arr.stringEmpty();
		Assert.assertNotNull(emptyArr);
		System.out.println(Arrays.toString(emptyArr));
	}

	@After
	public void indexOfText() {
		// Primitive array
		int[] array = Arr.intOf(2, 4, 6, 8, 10, 4);
		int search = 4;
		int index = Arr.indexOf(array, search);

		System.out.printf("[%d] -> %s = %d\n", search, Arrays.toString(array), index);
		Assert.assertEquals("Invalid value", 1, index);
	}

	@After
	public void lastIndexOfTest() {
		// Primitive array
		int[] array = Arr.intOf(2, 4, 6, 8, 10, 4);
		int search = 4;
		int index = Arr.lastIndexOf(array, search);

		System.out.printf("-[%d] -> %s = %d\n", search, Arrays.toString(array), index);
		Assert.assertEquals("Invalid value", 5, index);
	}

}
