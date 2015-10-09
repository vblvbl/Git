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
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.Progressable;
import org.apache.hadoop.util.ReflectionUtils;

public class Write500WToHdfsCoder {
	public static void main(String[] args) {
		String needClass = "org.apache.hadoop.io.compress.GzipCodec";
		String uri = "hdfs://myMaster:9000/500k.gz";
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File("")));
			Class<?> clazz = Class.forName(needClass);
			FileSystem fileout = FileSystem.get(URI.create(uri), new Configuration());
			FSDataOutputStream fsout = fileout.create(new Path(uri), new Progressable() {

				@Override
				public void progress() {
					System.out.println("一个64k数据包发送完成");

				}
			});
			CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz, new Configuration());
			CompressionOutputStream cout = codec.createOutputStream(fsout);
			IOUtils.copyBytes(in, cout, 4096, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
