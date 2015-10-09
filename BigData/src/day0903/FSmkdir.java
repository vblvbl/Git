package day0903;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FSmkdir {
	public static void main(String[] args) {
		try {
			String uri = "";
			FileSystem fileSystem = FileSystem.get(URI.create(uri), new Configuration());
			if(fileSystem.mkdirs(new Path("uri"))){
				System.out.println("sucessful");
			}else{
				System.out.println("wrong");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
