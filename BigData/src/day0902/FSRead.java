package day0902;

import java.net.URI;
import java.util.Comparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class FSRead {
	public static void main(String[] args) {
		FSDataInputStream inputStream = null;
		try {
			String uri = "";
			Configuration configuration = new Configuration();
			FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
			inputStream = fileSystem.open(new Path(uri));
			IOUtils.copyBytes(inputStream, System.out, 4096, false);
			System.out.println("===========================");
			inputStream.seek(0);
			IOUtils.copyBytes(inputStream, System.out, 4096, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(inputStream);
		}
	}
}
