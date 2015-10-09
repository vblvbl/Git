package day0905;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ShowWritable {
	byte[] by1te = null;

	public static byte[] serialize(Writable wt) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(out);
		try {
			wt.write(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	@Before
	public void writeTest() {
		IntWritable intWritable = new IntWritable(1024);
		by1te = serialize(intWritable);
		assertTrue(by1te.length == 4);
	}

	public static void deserialize(Writable wb, byte[] bt) {
		ByteArrayInputStream aStream = new ByteArrayInputStream(bt);
		DataInputStream stream = new DataInputStream(aStream);
		try {
			wb.readFields(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void readTest() {
		IntWritable intWritable = new IntWritable();
		deserialize(intWritable, by1te);
		assertTrue(intWritable.get() == 1024);
	}
}
