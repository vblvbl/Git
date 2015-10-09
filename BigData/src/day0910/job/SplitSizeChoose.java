package day0910.job;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.likang.util.MyUtils;

public class SplitSizeChoose {

	// @Override
	// public int run(String[] args) throws Exception {
	// if (args.length != 2) {
	// System.err.printf("Usage: %s [generic options] <input> <output>\n");
	// getClass().getSimpleName();
	// ToolRunner.printGenericCommandUsage(System.err);
	// return -1;
	// }
	// Job job = new Job(getConf());
	// job.setJarByClass(SplitSizeChoose.class);
	// job.setMapOutputKeyClass(theClass);
	// job.setMapOutputValueClass(theClass);
	// job.setOutputFormatClass(cls);
	// job.setInputFormatClass(cls);
	// int chooseCode = Integer.parseInt(args[2]);
	// 1表示更改split大小
	// if (chooseCode == 1) {
	// FileInputFormat.setMaxInputSplitSize(job, 50 * 1024 * 1024);
	// }
	// return 0;
	// }
	public static class MyMap extends Mapper<NullWritable, BytesWritable, BytesWritable, NullWritable> {
		BytesWritable bw = new BytesWritable();

		@Override
		protected void map(NullWritable key, BytesWritable value,
				Mapper<NullWritable, BytesWritable, BytesWritable, NullWritable>.Context context)
						throws IOException, InterruptedException {
			bw.set(value);
			context.write(bw, NullWritable.get());
		}

	}

	public static class MyReduce extends Reducer<BytesWritable, NullWritable, BytesWritable, NullWritable> {

		@Override
		protected void reduce(BytesWritable arg0, Iterable<NullWritable> arg1,
				Reducer<BytesWritable, NullWritable, BytesWritable, NullWritable>.Context arg2)
						throws IOException, InterruptedException {
			// byte[] buf = arg0.copyBytes();
			// Text tx = new Text(buf);
			arg2.write(arg0, NullWritable.get());
		}

	}

	public static void main(String[] args) throws Exception {
		// int code = ToolRunner.run(new SplitSizeChoose(), args);
		// System.exit(code);
		Job job = new Job(new Configuration());
		job.setJarByClass(SplitSizeChoose.class);
		Class[] clazz = new Class[] { SplitSizeChoose.class, BytesWritable.class, NullWritable.class, BytesWritable.class,
				NullWritable.class, MyMap.class, MyReduce.class, WholeFileInputFormat.class, TextOutputFormat.class };
		MyUtils.setJob(job, args[0], args[1], clazz);
		job.waitForCompletion(true);
	}
}
