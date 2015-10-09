package day0903;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

public class FsListFile {
	public static void main(String[] args) {
		try {
			String[] uri = { "hdfs://master:9000/", "hdfs://master:9000/user/Kang" };
			FileSystem fileSystem = FileSystem.get(URI.create(uri[0]), new Configuration());
			// fileSystem.listStatus(files)
			Path[] paths = new Path[uri.length];
			int i = 0;
			for (String myuri : uri) {
				paths[i] = new Path(myuri);
				i++;
			}
			FileStatus[] fileStatus = fileSystem.listStatus(paths);
			Path[] path = FileUtil.stat2Paths(fileStatus);
			for (Path pp : path) {
				System.out.println(pp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
