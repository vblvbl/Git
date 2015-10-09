package day0909;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;

import day0909.MR_Test2.MyMap;
import day0909.MR_Test2.MyReduce;

public class MR_Test2 {
	public static void main(String[] args) {
		try {
			Configuration configuration = new Configuration();
			Job job = new Job(configuration, "uuid");
			Class[] clazz = new Class[] { MR_Test2.class, Text.class, IntWritable.class, Text.class, NullWritable.class,
					MyMap.class, MyReduce.class };
			MyUtils.setJob(job, "hdfs://localhost:9000/1w", "hdfs://localhost:9000/1w_uuid", clazz);
			System.exit(job.waitForCompletion(true) ? 0 : 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Configuration configuration = new Configuration();
		// try {
		// Job job = new Job(configuration, "uuid");
		// job.setJarByClass(MR_Test2.class);
		//
		// job.setInputFormatClass(TextInputFormat.class);
		// job.setOutputFormatClass(TextOutputFormat.class);
		//
		// job.setMapOutputKeyClass(Text.class);
		// job.setMapOutputValueClass(IntWritable.class);
		//
		// job.setOutputKeyClass(Text.class);
		// job.setOutputValueClass(NullWritable.class);
		//
		// job.setMapperClass(MyMap.class);
		// job.setReducerClass(MyReduce.class);
		// // job.setNumReduceTasks(0);
		// Path in = new Path("hdfs://localhost:9000/1w");
		// Path out = new Path("hdfs://localhost:9000/1w_ues");
		// FileInputFormat.addInputPath(job, in);
		// FileOutputFormat.setOutputPath(job, out);
		// System.exit(job.waitForCompletion(true) ? 0 : 1);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public static class MyMap extends Mapper<Object, Text, Text, IntWritable> {
		public static IntWritable iw = new IntWritable(1);
		public static final Text tx = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			tx.set(value.toString().split("\t")[1]);
			context.write(tx, iw);
			// super.map(key, value, context);
			// 千万别把super方法写在后面！！！
		}

	}

	public static class MyReduce extends Reducer<Text, IntWritable, Text, NullWritable> {

		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Reducer<Text, IntWritable, Text, NullWritable>.Context arg2) throws IOException, InterruptedException {
			arg2.write(arg0, NullWritable.get());
			// super.reduce(arg0, arg1, arg2);
		}

	}
}
