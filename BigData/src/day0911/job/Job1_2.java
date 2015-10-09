package day0911.job;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import com.likang.util.MyUtils;

public class Job1_2 {
	public static void main(String[] args) {
		Job job = null;
		String uriin = MyUtils.HOMEPATH + "seqfile/1w.seq";
		String uriout = MyUtils.RESPATH + "1w_job1_2";
		Class[] clazz = new Class[] { Job1_2.class, Text.class, NullWritable.class, NullWritable.class, Text.class,
				MyMap.class, MyReduce.class, SequenceFileInputFormat.class, SequenceFileOutputFormat.class };
		try {
			job = new Job(new Configuration());
			MyUtils.setJob(job, uriin, uriout, clazz);
			job.waitForCompletion(true);
		} catch (Exception e) {
			System.exit(-1);
		}
	}
//it is for Mapper
	public static class MyMap extends Mapper<Object, Text, Text, NullWritable> {
		public static final Text tn = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			// System.out.println(line);
			String word[] = line.split("\t");
			if (word.length == 6) {
				String uid = word[1];
				tn.set(uid);
				context.write(tn, NullWritable.get());
			}
		}
	}
//it is for Reducer
	public static class MyReduce extends Reducer<Text, NullWritable, NullWritable, Text> {
		// public static final Text tx = new Text();

		@Override
		protected void reduce(Text arg0, Iterable<NullWritable> arg1,
				Reducer<Text, NullWritable, NullWritable, Text>.Context arg2) throws IOException, InterruptedException {
			// byte[] buf = arg0.getBytes();
			// tx.set(buf, 0, buf.length);
			arg2.write(NullWritable.get(), arg0);

		}
	}
}
