package day0904.job;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.likang.util.MyUtils;

public class DateWordCount {
	public static void main(String[] args) throws Exception {
		String uriin = "hdfs://localhost:9000/1w";
		String uriout = "hdfs://localhost:9000/1w_date";
		Job job = new Job(new Configuration(), "date job");
		Class[] myclass = new Class[] { DateWordCount.class, Text.class, IntWritable.class, Text.class,
				NullWritable.class, MyMap.class, MyReduce.class };
		MyUtils.setJob(job, uriin, uriout, myclass);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		FileInputFormat.setMinInputSplitSize(job, 1);
		FileInputFormat.setMaxInputSplitSize(job, 10*1024*1024);
	}

	public static class MyMap extends Mapper<Object, Text, Text, IntWritable> {
		public static final Text tx = new Text();
		public static final IntWritable it = new IntWritable(1);

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			// super.map(key, value, context);
			// Text tt=new Text();
			String vi = value.toString();
			String[] str = vi.split("\t");
			StringBuilder newvi = new StringBuilder();
			for (int i = 0; i < str.length; i++) {
				if (i > 0) {
					newvi.append(str[i] + "\t");
				}
			}
			String data = str[0];
			try {
				// 格式化时间工具String-->Date
				// 20111230104334
				SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMDDhhmmss");
				Date trueDate = sFormat.parse(data);
				// 格式化时间工具Date-->String
				SimpleDateFormat cFormat = new SimpleDateFormat("yyyy年MM月DD日hh时mm分ss秒");
				String formatDate = cFormat.format(trueDate);
				newvi.append(formatDate);
				tx.set(newvi.toString());
				context.write(tx, it);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static class MyReduce extends Reducer<Text, IntWritable, Text, NullWritable> {

		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Reducer<Text, IntWritable, Text, NullWritable>.Context arg2) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			// super.reduce(arg0, arg1, arg2);
			arg2.write(arg0, NullWritable.get());
		}

	}
}
