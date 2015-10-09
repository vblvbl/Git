package day0910.job;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyOutPutFormat extends FileOutputFormat {
	String splite = null;

	MyOutPutFormat(String splite) {
		this.splite = splite;
	}

	@Override
	public RecordWriter getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Path file = getDefaultWorkFile(job, "");
		FileSystem fileSystem = file.getFileSystem(job.getConfiguration());
		FSDataOutputStream create = fileSystem.create(file);
		return new MyRecordWriter(create, splite);
	}

	/**
	 * =====================================================================
	 * =================
	 */
	public static class MyRecordWriter extends RecordWriter {
		public static String SEPERATOR = "mapreduce.output.textoutputformat.separator";
		private static final String utf8 = "UTF-8";
		private static final byte[] newline;
		private DataOutputStream out;
		private final byte[] keyValueSeparator;

		static {
			try {
				newline = "\n".getBytes(utf8);
			} catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException("can't find " + utf8 + " encoding");
			}
		}

		public MyRecordWriter(DataOutputStream out, String keyValueSeparator) {
			this.out = out;
			try {
				this.keyValueSeparator = keyValueSeparator.getBytes(utf8);
			} catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException("can't find " + utf8 + " encoding");
			}
		}

		public MyRecordWriter(DataOutputStream out) {
			this(out, "\t");
		}

		/**
		 * =====================================================================
		 * =================
		 */
		@Override
		public void write(Object key, Object value) throws IOException, InterruptedException {
			if (key != null) {
				writeObject(key);
			}
			// if (!(nullKey || nullValue)) {
			// out.write(keyValueSeparator);
			// }
			if (value != null) {
				writeObject(value);
			}
			out.write(newline);
		}

		// private void writeObjectValue(Object value) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// private void writeObjectKey(Object key) {
		// IntWritable o = (IntWritable) key;
		//
		// }

		private void writeObject(Object obj) throws IOException {
			Text o = (Text) obj;
			out.write(o.toString().getBytes());
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub

		}

	}
}
