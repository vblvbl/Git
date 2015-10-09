package day0909;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MR_Test1 {
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		try {
			Job job = new Job(configuration, "need uid");
			job.setJarByClass(MR_Test1.class);
			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(NullWritable.class);
			job.setMapperClass(MyMap.class);
			job.setReducerClass(MyReduce.class);
			// job.setNumReduceTasks(0);
			Path in = new Path("hdfs://localhost:9000/1w");
			Path out = new Path("hdfs://localhost:9000/1w_res");
			FileInputFormat.addInputPath(job, in);
			FileOutputFormat.setOutputPath(job, out);
//			job.submit();
			System.exit(job.waitForCompletion(true) ? 0 : 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static public class MyMap extends Mapper<Object, Text, Text, NullWritable> {
		public final Text tx = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			// StringTokenizer tokenizer = new StringTokenizer(value.toString(),
			// "\t");
			// while(tokenizer.hasMoreTokens()){
			// tokenizer.nextToken();
			// }

			String line = value.toString();
			String[] str = line.split("\t");
			if (str[1] != null && !str[1].equals("")) {
				tx.set(str[1]);
				context.write(tx, NullWritable.get());
			}
			// super.map(key, value, context);
		}

	}

	static public class MyReduce extends Reducer<Text, NullWritable, Text, NullWritable> {

		@Override
		protected void reduce(Text arg0, Iterable<NullWritable> arg1,
				Reducer<Text, NullWritable, Text, NullWritable>.Context arg2) throws IOException, InterruptedException {
			arg2.write(arg0, NullWritable.get());
			// super.reduce(arg0, arg1, arg2);
		}

	}
}
