package day0912.lowjob;

import java.io.IOException;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;
import com.sun.prism.j2d.print.J2DPrinterJob;

import day0912.MultipleReadFile.MapA;
import day0912.MultipleReadFile.MapB;
import day0912.MultipleReadFile.Reduce;

public class DatePass {
	public static void main(String[] args) {
		String pathIn1 = MyUtils.HOMEPATH + "/smallfile/file1.txt";
		String pathIn2 = MyUtils.HOMEPATH + "/smallfile/file2.txt";
		String pathout = MyUtils.RESPATH + "/twofile";
		Configuration cg = new Configuration();
		Job job = null;
		try {
			job = new Job(cg, "it is a small job");
			job.setJarByClass(DatePass.class);
			// job.setMapOutputKeyClass(Text.class);
			// job.setMapOutputValueClass(NullWritable.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(NullWritable.class);
			// 两个种类文件读入
			MultipleInputs.addInputPath(job, new Path(pathIn1), TextInputFormat.class, MapA.class);
			MultipleInputs.addInputPath(job, new Path(pathIn2), TextInputFormat.class, MapB.class);
			// 写出到两个文件中
			job.setReducerClass(Reduce.class);
			job.setOutputFormatClass(TextOutputFormat.class);
			FileOutputFormat.setOutputPath(job, new Path(pathout));
			// job.setOutputFormatClass(TextOutputFormat.class);
			// job.setOutputKeyClass(Text.class);
			// job.setOutputValueClass(Text.class);
			job.waitForCompletion(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class MapA extends Mapper<Object, Text, Text, NullWritable> {

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(value, NullWritable.get());
		}

	}

	public static class MapB extends Mapper<Object, Text, Text, NullWritable> {

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			Counter counter = context.getCounter("map", "num");
			counter.increment(1);
			context.write(value, NullWritable.get());
		}

	}

	public static class Reduce extends Reducer<Text, NullWritable, Text, NullWritable> {

		@Override
		protected void reduce(Text arg0, Iterable<NullWritable> arg1,
				Reducer<Text, NullWritable, Text, NullWritable>.Context arg2) throws IOException, InterruptedException {
			arg2.write(arg0, NullWritable.get());
		}

	}
}
