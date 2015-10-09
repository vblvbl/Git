package day0902;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class FSWrite {
	public static void main(String[] args) {
		FSDataOutputStream fo = null;
		BufferedInputStream bStream = null;
		String javaFile = "/home/Kang/demo.txt";
		String uri = "";
		try {
			bStream = new BufferedInputStream(new FileInputStream(javaFile));
			Configuration configuration = new Configuration();
			FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
			fo = fileSystem.create(new Path(uri), new Progressable() {
//DFSOutputStream.DataStreamer
				@Override
				public void progress() {
					System.out.println("我是回调，执行完成");

				}
			});
			IOUtils.copyBytes(bStream, fo, 4096, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			IOUtils.closeStream(bStream);
			IOUtils.closeStream(fo);
		}
	}
}
