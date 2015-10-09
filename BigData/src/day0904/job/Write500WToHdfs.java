package day0904.job;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Progressable;

public class Write500WToHdfs {
	public static void main(String[] args) {
//		int a=1;
//		double b=1.0;
//		if(a==b){
//			System.out.println("哈哈哈");
//		}
		BufferedInputStream instream = null;
		String uri = "hdfs://localhost:9000/self";
		try {
			FileSystem system = FileSystem.get(URI.create(uri), new Configuration());
			FSDataOutputStream fsout = system.create(new Path(uri), new Progressable() {

				@Override
				public void progress() {
					System.out.println("完成任务～");

				}
			});
			instream = new BufferedInputStream(new FileInputStream(new File("/Users/Kang/Desktop/self.mp3")));
			IOUtils.copyBytes(instream, fsout, 4096, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
