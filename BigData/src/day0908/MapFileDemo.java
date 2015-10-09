package day0908;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.MapFile;

import javafx.scene.shape.Path;

public class MapFileDemo {
	public static void main(String[] args) {

	}

	private static void readMapFile() {
		String str = "";
		Configuration conf = new Configuration();
		try {
			FileSystem fs = FileSystem.get(URI.create(str), conf);
			MapFile.Reader read = new MapFile.Reader(fs, str, conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void writeMapFile() {

	}
}
