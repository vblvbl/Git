package day0904;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;
public class CompressCoder {
	public static void main(String[] args) {
		CompressionOutputStream compout = null;
		try {
			String className = "org.apache.hadoop.io.compress.GzipCodec";
			Class<?> clazz = Class.forName(className);
			Configuration configuration = new Configuration();
			CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz, configuration);
			compout = codec.createOutputStream(System.out);
			IOUtils.copyBytes(System.in, compout, 4096, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				compout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
