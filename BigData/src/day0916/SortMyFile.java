package day0916;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;

import day0915.MyList;
import day0915.SumFiled;
import day0915.SumFiled.MyMap;
import day0915.SumFiled.MyReduce;

public class SortMyFile {
	public static void main(String[] args) {
		String from = MyUtils.BASEPATH + "demo1.txt";
		String to = MyUtils.BASEPATH + "result/";
		Configuration cg = new Configuration();
		try {
			Configuration cf = new Configuration();
			Job job = new Job(cf, "it is easy");
			Class[] clazz = new Class[] { SortMyFile.class, IntWritable.class, IntWritable.class, Text.class,
					Text.class, MyMap.class, MyReduce.class, TextInputFormat.class, TextOutputFormat.class };
			job.setPartitionerClass(MyPartition.class);
			job.setNumReduceTasks(4);
			MyUtils.setJob(job, from, to, clazz);
			job.waitForCompletion(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Job job = new Job(cg, "it is my file");

	}

	public static class MyMap extends Mapper<Object, Text, IntWritable, IntWritable> {
		public static final IntWritable mykey = new IntWritable();
		public static final IntWritable myvalue = new IntWritable();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			int age = Integer.parseInt(words[0]);
			int money = Integer.parseInt(words[1]);
			mykey.set(age);
			myvalue.set(money);
			context.write(mykey, myvalue);
		}

	}

	public static class MyReduce extends Reducer<IntWritable, IntWritable, Text, Text> {
		public static final Text mykey = new Text();
		public static final Text myvalue = new Text();

		@Override
		protected void reduce(IntWritable arg0, Iterable<IntWritable> arg1,
				Reducer<IntWritable, IntWritable, Text, Text>.Context arg2) throws IOException, InterruptedException {
			mykey.set(arg0.get() + "");
			double count = 0;
			double sum = 0;
			Iterator<IntWritable> it = arg1.iterator();
			while (it.hasNext()) {
				sum += it.next().get();
				count++;
			}
			double avg = sum / count;
			myvalue.set(avg + "");
			arg2.write(mykey, myvalue);
		}
	}
}
