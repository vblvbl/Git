package day0911.job;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

public class Job2_3 {
	public static void main(String[] args) {
		Job job = null;
		String uriin = MyUtils.SG500W;
		String uriout = MyUtils.RESPATH + "500w_job2_3";
		Class[] clazz = new Class[] { Job2_3.class,IntWritable.class,Text.class, IntWritable.class, NullWritable.class,
				MyMap.class, MyReduce.class, TextInputFormat.class, TextOutputFormat.class };
		try {
			job = new Job(new Configuration());
			MyUtils.setJob(job, uriin, uriout, clazz);
			job.waitForCompletion(true);
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	public static class MyMap extends Mapper<Object, Text, IntWritable, Text> {
		public static final IntWritable order = new IntWritable();
		public static final Text uid = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] word = line.split("\t");
			int rank = 0;
			int older = 0;
			try {
				rank = Integer.parseInt(word[3]);
				older = Integer.parseInt(word[4]);
			} catch (Exception e) {
				return;
			}
			if (rank < 3) {
				order.set(older);
				uid.set(word[1]);
				context.write(order, uid);
			}
		}

	}

	public static class MyReduce extends Reducer<IntWritable, Text, IntWritable, NullWritable> {
		public static final IntWritable sumable = new IntWritable();
		public static final Map<Integer, Integer> map = new HashMap<>();

		@Override
		protected void cleanup(Reducer<IntWritable, Text, IntWritable, NullWritable>.Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			Set<Entry<Integer, Integer>> set = map.entrySet();
			for (Entry<Integer, Integer> item : set) {
				sum += item.getValue();
			}
			sumable.set(sum);
			context.write(sumable, NullWritable.get());
		}

		@Override
		protected void reduce(IntWritable arg0, Iterable<Text> arg1,
				Reducer<IntWritable, Text, IntWritable, NullWritable>.Context arg2)
						throws IOException, InterruptedException {
			Iterator<Text> it = arg1.iterator();
			Set<String> seet = new HashSet<String>();
			while (it.hasNext()) {
				String uid = it.next().toString();
				seet.add(uid);
			}
			map.put(arg0.get(), seet.size());
		}

	}
}
