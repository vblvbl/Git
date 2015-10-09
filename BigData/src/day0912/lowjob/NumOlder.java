package day0912.lowjob;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;

public class NumOlder {
	public static void main(String[] args) {
		String from = MyUtils.BASEPATH + "demo.txt";
		String to = MyUtils.BASEPATH + "result/";
		try {
			Configuration cf = new Configuration();
			Job job = new Job(cf, "it is easy");
			Class[] clazz = new Class[] { NumOlder.class, IntWritable.class, Text.class, Text.class, Text.class,
					MyMaper.class, MyReduce.class, TextInputFormat.class, TextOutputFormat.class };
			MyUtils.setJob(job, from, to, clazz);
			job.waitForCompletion(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class MyMaper extends Mapper<Object, Text, IntWritable, Text> {
		public static final IntWritable iw = new IntWritable(1);

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			context.write(iw, value);
		}

	}

	public static class MyReduce extends Reducer<IntWritable, Text, Text, Text> {

		@Override
		protected void reduce(IntWritable arg0, Iterable<Text> arg1,
				Reducer<IntWritable, Text, Text, Text>.Context arg2) throws IOException, InterruptedException {
			Iterator<Text> it = arg1.iterator();
			List<Integer> list = new ArrayList<Integer>();
			while (it.hasNext()) {
				list.add(Integer.parseInt(it.next().toString()));
			}
			Collections.sort(list);
			int sum = 0;
			for (Integer ls : list) {
				arg2.write(new Text(sum + ""), new Text(ls.toString()));
				sum++;
			}
		}

	}
}
