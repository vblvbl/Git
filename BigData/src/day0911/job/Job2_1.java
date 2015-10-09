package day0911.job;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;

public class Job2_1 {
	public static void main(String[] args) {
		Job job = null;
		String uriin = MyUtils.SG500W;
		String uriout = MyUtils.RESPATH + "500w_job2_1";
		Class[] clazz = new Class[] { Job2_1.class, Text.class, IntWritable.class, Text.class, NullWritable.class,
				MyMap.class, MyReduce.class, TextInputFormat.class, TextOutputFormat.class };
		try {
			job = new Job(new Configuration());
			MyUtils.setJob(job, uriin, uriout, clazz);
			job.waitForCompletion(true);
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	public static class MyMap extends Mapper<Object, Text, Text, IntWritable> {
		public static final IntWritable order = new IntWritable();
		public static final Text uid = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] word = line.split("\t");
			String keyword = word[2];
			int time = 0;
			try {
				time = Integer.parseInt(word[4]);
			} catch (Exception e) {
				return;
			}
			if (keyword.length() > 256) {
				order.set(time);
				uid.set(word[1]);
				context.write(uid, order);
			}
		}

	}

	public static class MyReduce extends Reducer<Text, IntWritable, Text, NullWritable> {

		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Reducer<Text, IntWritable, Text, NullWritable>.Context arg2) throws IOException, InterruptedException {
			Iterator<IntWritable> it = arg1.iterator();
			while (it.hasNext()) {
				int time = it.next().get();
				if (time < 3) {
					arg2.write(arg0, NullWritable.get());
				}
			}
		}

	}
}
