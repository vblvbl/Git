package day0905;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class StringAndText {
	@Test
	public void myString() throws UnsupportedEncodingException {
		String s = "\u0041\u00DF\u6771\uD801\uDC00";
		// s.toCharArray()
		assertTrue(s.length() == 5);
		assertTrue(s.getBytes("utf-8").length == 10);
		assertTrue(s.indexOf("\u0041") == 0);
	}

	@Test
	public void myText() {
		Text text = new Text("\u0041\u00DF\u6771\uD801\uDC00");
		System.out.println(text.getLength() + " \t" + Arrays.toString(text.getBytes()));
	}
}
