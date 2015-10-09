package day0917.job;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Table.jar /0911-Table/1.txt /0911-Table1/2.txt /output0911-Table
 */
public class MultipleInputText {
	// public static final String path1 = "hdfs://master:9000/1.txt";
	// public static final String path2 = "hdfs://master:9000/2.txt";
	// public static final String path3 = "hdfs://master:9000/res";
	public static final String path1 = "hdfs://localhost:9000/1.txt";
	public static final String path2 = "hdfs://localhost:9000/2.txt";
	public static final String path3 = "hdfs://localhost:9000/resaaa";

	/**
	 * 多个输入
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		Job job = new Job(new Configuration(), MultipleInputText.class.getSimpleName());
		job.setJarByClass(MultipleInputText.class);

		job.setReducerClass(MyReduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		// input
		MultipleInputs.addInputPath(job, new Path(path1), TextInputFormat.class, MyMapperA.class);
		MultipleInputs.addInputPath(job, new Path(path2), TextInputFormat.class, MyMapperB.class);
		// output
		FileOutputFormat.setOutputPath(job, new Path(path3));

		job.waitForCompletion(true);
	}

	public static class MyMapperA extends Mapper<LongWritable, Text, Text, Text> {
		public static final Text mykey = new Text();
		public static final Text myValue = new Text();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String words[] = value.toString().split("\t");
			String uid = words[0];
			String flag = "A";
			String score = words[3];
			String myString = flag + "\t" + score;
			mykey.set(uid);
			myValue.set(myString);
			context.write(mykey, myValue);
		}

	}

	public static class MyMapperB extends Mapper<LongWritable, Text, Text, Text> {
		public static final Text mykey = new Text();
		public static final Text myValue = new Text();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			String words[] = line.split("\t");
			String uid = words[0];
			String flag = "B";
			String score = words[1];
			String myString = flag + "\t" + score;
			mykey.set(uid);
			myValue.set(myString);
			context.write(mykey, myValue);
		}
	}

	public static class MyReduce extends Reducer<Text, Text, Text, Text> {
		// public static final Text mykey = new Text();
		public static final Text myValue = new Text();
		// MultipleOutputs<Text, Text> mu=null;

		@Override
		protected void setup(org.apache.hadoop.mapreduce.Reducer.Context context)
				throws IOException, InterruptedException {
			// mu=new MultipleOutputs(context);
		}

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			Iterator<Text> it = values.iterator();
			String bef = null;
			String now = null;
			while (it.hasNext()) {
				Text tx = it.next();
				String getString = tx.toString();
				String[] word = getString.split("\t");
				String flag = word[0];
				if (flag.equals("A")) {
					// now = Integer.parseInt(word[1]);
					now = word[1];
				} else if (flag.equals("B")) {
					bef = word[1];
				}
			}
			/*
			 * String putString = null; if (bef < now) { putString = "进步了"; }
			 * else { putString = "退步了"; }
			 */
			// String uidString = key.toString();
			// mykey.set(uidString);
			String put = now + "-" + bef;
			myValue.set(put);
			context.write(key, myValue);
			// mu.write(key, value, baseOutputPath)
		}

		@Override
		protected void cleanup(org.apache.hadoop.mapreduce.Reducer.Context context)
				throws IOException, InterruptedException {
			// mu.close();
		}

	}
}
