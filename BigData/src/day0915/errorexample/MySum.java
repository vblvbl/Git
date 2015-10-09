package day0915.errorexample;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.likang.util.MyUtils;

public class MySum {
	static String INPUT = MyUtils.BASEPATH + "demo.txt";
	static String OUTPUT = MyUtils.BASEPATH + "result/";

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		// if (fs.exists(new Path(OUTPUT))) {
		// fs.delete(new Path(OUTPUT));
		// }
		Job job = new Job(conf, "MobileText");
		FileInputFormat.setInputPaths(job, new Path(INPUT));
		job.setJarByClass(MySum.class);
		job.setMapperClass(Mymap.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MyWritable.class);
		// job.setInputFormatClass(TextInputFormat.class);
		// job.setOutputFormatClass(TextOutputFormat.class);

		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(MyWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT));

		job.waitForCompletion(true);
	}

	public static class Mymap extends Mapper<LongWritable, Text, Text, MyWritable> {
		public static final MyWritable mobiledemo = new MyWritable();

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] str = value.toString().split("\t");
			String mobileNum = str[0];
			mobiledemo.set(Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[3]));
			System.out.println(mobiledemo.toString()+"====");
			context.write(new Text(mobileNum), mobiledemo);

		};

	}

	public static class MyReducer extends Reducer<Text, MyWritable, Text, MyWritable> {
		// private mobileDemo mobile;

		public void reduce(Text k2, Iterable<MyWritable> v2s, Context context)
				throws IOException, InterruptedException {
			int internet = 0;
			int callTime = 0;
			int mailcount = 0;
			for (MyWritable mobliedemo : v2s) {
				internet += mobliedemo.getInternet();
				callTime += mobliedemo.getCallTime();
				mailcount += mobliedemo.getMailcount();
			}
			MyWritable mobile = new MyWritable(internet, callTime, mailcount);
			System.out.print(mobile.toString());
			context.write(k2, mobile);
		};
	}
}
