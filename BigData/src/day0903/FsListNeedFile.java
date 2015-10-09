package day0903;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

public class FsListNeedFile {
	public static void main(String[] args) {
		String[] uris = {};
		try {
			FileSystem fileSystem = FileSystem.get(URI.create(uris[0]), new Configuration());
			Path[] hadpath = new Path[uris.length];
			int i = 0;
			for (String ss : uris) {
				hadpath[i] = new Path(ss);
				i++;
			}
			FileStatus[] fileStatus = fileSystem.listStatus(hadpath, new PathFilter() {

				@Override
				public boolean accept(org.apache.hadoop.fs.Path path) {
					// TODO Auto-generated method stub
					return !path.getName().matches("*d");
				}

			});
			Path[] myneed = FileUtil.stat2Paths(fileStatus);
			for(Path pp:myneed){
				System.out.println(pp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
