package day0904.job;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MapReduce500W {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		@SuppressWarnings("deprecation")
		Configuration cg = new Configuration();
		Job job = new Job(cg, "mr500w");
		job.setJarByClass(MapReduce500W.class);
		job.setMapperClass(MyMap.class);
		job.setCombinerClass(MyReduce.class);
		job.setReducerClass(MyReduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		Path in = new Path(new String("hdfs://localhost:9000/demo"));
		Path out = new Path(new String("hdfs://localhost:9000/demo_space"));
		FileInputFormat.addInputPath(job, in);
		FileOutputFormat.setOutputPath(job, out);
		// 这一段是开启job的核心代码
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

	public static class MyMap extends Mapper<Writable, Text, Text, Text> {

		@Override
		protected void map(Writable wb, Text value, Mapper<Writable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String str = value.toString();
			// StringTokenizer st = new StringTokenizer(str, "\n");
			// while (st.hasMoreTokens()) {
			// String line = st.nextToken();
			String[] word = str.split("\t");
			// if (!(word[1].equals("") || word[2].equals(""))) {
			// context.write(new Text(str), new Text(""));
			// }
			if (word.length == 6) {
				boolean flag = true;
				for (String ss : word) {
					if (ss.equals(" ") || ss.equals("")) {
						flag = false;
					}
				}
				if (flag) {
					context.write(new Text(str), new Text(""));
				}
				// context.write(new Text(str), new Text(""));
			}
		}

	}

	public static class MyReduce extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			arg2.write(arg0, new Text(""));
		}

	}

}
