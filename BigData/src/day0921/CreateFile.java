package day0921;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import day0921.TimeRank.MyMapA;
import day0921.TimeRank.MyMapB;
import day0921.TimeRank.MyReduce;

public class CreateFile {
	public static String path2 = "hdfs://localhost:9000/ddddd2";
	public static String path1 = "hdfs://localhost:9000/500w";

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = new Job(new Configuration(), "Join");
		job.setJarByClass(CreateFile.class);
		job.setMapperClass(MapNeed.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
//		job.setNumReduceTasks(0);
		FileInputFormat.addInputPath(job, new Path(path1));
		FileOutputFormat.setOutputPath(job, new Path(path2));
		job.waitForCompletion(true);
	}
}
