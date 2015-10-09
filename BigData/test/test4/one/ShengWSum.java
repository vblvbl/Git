package test4.one;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import test3.one.CountCityW;
import test3.one.CountCityW.MyMap;
import test3.one.CountCityW.MyReduce;

public class ShengWSum {
	public static class MyMap extends Mapper<Object, Text, Text, Text> {
		public static final Text tk = new Text();
		public static final Text tv = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			if (words.length == 6) {
				String sheng = words[4];
				String cp = words[0];
				tk.set(sheng);
				tv.set(cp);
				context.write(tk, tv);
			}
		}

	}

	public static class MyReduce extends Reducer<Text, Text, Text, Text> {
		public static final Text tv = new Text();

		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			Set<String> myset = new HashSet<String>();
			Iterator<Text> it = arg1.iterator();
			while (it.hasNext()) {
				Text tx = it.next();
				String cp = tx.toString();
				myset.add(cp);
			}
			tv.set(myset.size() + "");
			arg2.write(arg0, tv);
		}
	}

	public static void main(String[] args) throws Exception {
		String from = "hdfs://localhost:9000/input";
		String to = "hdfs://localhost:9000/reout3";
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Single Table Join");
		job.setJarByClass(CountCityW.class);
		// 设置Map和Reduce处理类
		job.setMapperClass(MyMap.class);
		job.setReducerClass(MyReduce.class);
		// 设置输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(from));
		FileOutputFormat.setOutputPath(job, new Path(to));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
