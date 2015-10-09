package day0908;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import com.likang.util.MyUtils;

public class SequenceFileDemo {
	public static final String[] Data = {};

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		writeSeqFile();
		// readSeqFile();
	}

	private static void readSeqFile() {
		String mp3 = "/Users/Kang/Desktop/500w";
		String uri = MyUtils.HOMEPATH + "seqfile/500w.seq";
		SequenceFile.Reader read = null;
		BufferedOutputStream bf = null;
		try {
			bf = new BufferedOutputStream(new FileOutputStream(new File(mp3)));
			Configuration configuration = new Configuration();
			Path path = new Path(uri);
			FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
			read = new SequenceFile.Reader(fileSystem, path, configuration);
			IntWritable key = new IntWritable();
			BytesWritable value = new BytesWritable();
			while (read.next(key, value)) {
				byte[] buf1 = value.copyBytes();
				bf.write(buf1, 0, buf1.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void writeSeqFile() {
		String mp3 = "/Users/Kang/Desktop/1w";
		String uri = MyUtils.HOMEPATH + "seqfile/1w.seq";
		BufferedReader bi = null;
		SequenceFile.Writer wt = null;
		try {
			bi = new BufferedReader(new FileReader(new File(mp3)));
			Configuration configuration = new Configuration();
			FileSystem fs = FileSystem.get(URI.create(uri), configuration);
			Path path = new Path(uri);
			int sum = 0;
			IntWritable key = new IntWritable(sum);
			Text value = new Text();
			wt = SequenceFile.createWriter(fs, configuration, path, key.getClass(), value.getClass());
			String need = null;
			while ((need = bi.readLine()) != null) {
				value.set(need);
				wt.append(key, value);
				sum++;
				key.set(sum);
			}
			// for (int i = 0; i < 100; i++) {
			// key.set(i);
			// value.set(Data[i % Data.length]);
			// wt.append(key, value);
			// System.out.printf(" [%s ] \t%s \t%s \n ", wt.getLength(), key,
			// value);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(wt);
		}
	}
}
