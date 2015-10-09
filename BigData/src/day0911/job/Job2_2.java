package day0911.job;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;

public class Job2_2 {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = new Job(new Configuration());
		String uriin = "hdfs://localhost:9000/500w";
		String uriout = "hdfs://localhost:9000/user/Kang/result/500w_job2_2";
		Class[] clazz = new Class[] { Job2_2.class, Text.class, Text.class, NullWritable.class, Text.class, MyMap.class,
				MyReduce.class, TextInputFormat.class, TextOutputFormat.class };
		MyUtils.setJob(job, uriin, uriout, clazz);
		job.waitForCompletion(true);

	}

	public static class MyReduce extends Reducer<Text, Text, NullWritable, Text> {

		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, NullWritable, Text>.Context arg2)
				throws IOException, InterruptedException {
			Iterator<Text> it = arg1.iterator();
			String ganji = "ganji.com";
			while (it.hasNext()) {
				Text uid = it.next();
				if (uid.toString().contains(ganji)) {
					arg2.write(NullWritable.get(), arg0);
				} else {
					continue;
				}
			}

		}

	}

	public static class MyMap extends Mapper<Object, Text, Text, Text> {
		public static final Text uid = new Text();
		public static final Text url = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] word = line.split("\t");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
			String clickdate = word[0];
			if (clickdate != null && !clickdate.equals("")) {
				Date date = null;
				try {
					date = format.parse(word[0]);
				} catch (ParseException e) {
					return;
				}
				if (date.getHours() > 7 && date.getHours() < 9) {
					if (word[2].contains("赶集网")) {
						uid.set(word[1]);
						url.set(word[5]);
						context.write(uid, url);
					}
				}
			}
		}
	}
}
