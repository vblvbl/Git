package test3.two;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//统计没有农产品市场的省份有哪些
public class CountCityNo {
	public static class MyMapA extends Mapper<Object, Text, IntWritable, Text> {
		public static final IntWritable tk = new IntWritable(1);
		public static final Text tv = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			if (words.length == 2) {
				String sheng = words[0];
				String need = "A_" + sheng;
				tv.set(need);
				context.write(tk, tv);
			}
		}

	}

	public static class MyMapB extends Mapper<Object, Text, IntWritable, Text> {
		public static final IntWritable tk = new IntWritable(1);
		public static final Text tv = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			if (!line.equals("")) {
				String need = "B_" + line;
				tv.set(need);
				context.write(tk, tv);
			}
		}

	}

	public static class MyReduce extends Reducer<IntWritable, Text, Text, NullWritable> {
		public static final Text tx = new Text();

		@Override
		protected void reduce(IntWritable arg0, Iterable<Text> arg1,
				Reducer<IntWritable, Text, Text, NullWritable>.Context arg2) throws IOException, InterruptedException {
			Iterator<Text> it = arg1.iterator();
			List<String> listA = new ArrayList<String>();
			List<String> listB = new ArrayList<String>();
			while (it.hasNext()) {
				Text tx = it.next();
				String line = tx.toString();
				if (line.startsWith("A_")) {
					String mine = line.substring(2);
					listA.add(mine);
				} else if (line.startsWith("B_")) {
					String mine = line.substring(2);
					listB.add(mine);
				}
			}
			boolean flag = true;
			for (String ls : listB) {
				for (String ll : listA) {
					if (ls.contains(ll)) {
						flag = false;
					}
				}
				if (flag) {
					tx.set(ls);
					arg2.write(tx, NullWritable.get());
				}
				flag = true;
			}
		}
	}

	public static String path1 = "hdfs://localhost:9000/reout/part-r-00000";
	public static String path2 = "hdfs://localhost:9000/city";
	public static String path3 = "hdfs://localhost:9000/reout2";

	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		Job job = new Job(new Configuration(), "Join");
		job.setJarByClass(CountCityNo.class);
		MultipleInputs.addInputPath(job, new Path(path1), TextInputFormat.class, MyMapA.class);
		MultipleInputs.addInputPath(job, new Path(path2), TextInputFormat.class, MyMapB.class);
		job.setReducerClass(MyReduce.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(path3));
		job.waitForCompletion(true);
	}

}
