package day0917.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import com.likang.util.MyUtils;

public class MyJob {

	public static class Map extends Mapper<Object, Text, Text, IntWritable> {
		public static final Text myKey = new Text();
		public static final IntWritable myValue = new IntWritable(1);

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String words[] = value.toString().split("\t");
			String uid = words[1];
			myKey.set(uid);
			context.write(myKey, myValue);
		}
	}

	public static class Reduce extends Reducer<Text, IntWritable, Text, Text> {

		public final static List<MyBean> ls = new ArrayList<MyBean>();

		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Reducer<Text, IntWritable, Text, Text>.Context arg2) throws IOException, InterruptedException {
			int sum = 0;
			Iterator<IntWritable> it = arg1.iterator();
			while (it.hasNext()) {
				sum += it.next().get();
			}
			MyBean mb = new MyBean(sum, arg0.toString());
			if (ls.size() < 20) {
				ls.add(mb);
			} else {
				MyBean wbmin = Collections.min(ls);
				if (mb.getTime() >= wbmin.getTime()) {
					ls.remove(wbmin);
					ls.add(mb);
				}
			}
		}

		@Override
		protected void cleanup(Reducer<Text, IntWritable, Text, Text>.Context context)
				throws IOException, InterruptedException {
			ls.sort(new Comparator<MyBean>() {
				@Override
				public int compare(MyBean o1, MyBean o2) {
					int x = o1.getTime();
					int y = o2.getTime();
					return (x > y) ? -1 : ((x == y) ? 0 : 1);
				}
			});
			for (MyBean f : ls) {
				context.write(new Text(f.getUid()), new Text(f.getTime() + ""));
			}
		}

	}

	public static void main(String[] args) throws Exception {
		String from = MyUtils.BASEPATH + "500w";
		String to = MyUtils.BASEPATH + "/res";
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Single Table Join");
		job.setJarByClass(MyJob.class);
		// 设置Map和Reduce处理类
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		// 设置输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		// 设置输入和输出目录
		FileInputFormat.addInputPath(job, new Path(from));
		FileOutputFormat.setOutputPath(job, new Path(to));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
