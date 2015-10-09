package day0921;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TimeRank {
	public static String path1 = "hdfs://localhost:9000/ddddd2/part-r-00000";
	public static String path2 = "hdfs://localhost:9000/500w";
	// 都是500w数据
	public static String path3 = "hdfs://localhost:9000/ddddd1";

	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		Job job = new Job(new Configuration(), "Join");
		job.setJarByClass(TimeRank.class);
		MultipleInputs.addInputPath(job, new Path(path1), TextInputFormat.class, MyMapA.class);
		MultipleInputs.addInputPath(job, new Path(path2), TextInputFormat.class, MyMapB.class);
		job.setReducerClass(MyReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(path3));
		job.waitForCompletion(true);
	}

	public static class MyMapA extends Mapper<Object, Text, Text, Text> {
		public static final Text mykey = new Text();
		public static final Text myvalue = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			mykey.set(words[0]);
			myvalue.set(words[1]);
			context.write(mykey, myvalue);
		}

	}

	public static class MyMapB extends Mapper<Object, Text, Text, Text> {
		public static final Text mykey = new Text();
		public static final Text myvalue = new Text();

		// 在20111230的08:00-10:00 中，rank在[1,3]，order=1的情况下，
		//点击的url的里面是包含baidu.com的用户，都搜索了什么关键词
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			if (words.length == 6) {
				String url = words[5];
				String uid = words[1];
				String keyword = words[2];
				if (url.indexOf("baidu.com") >= 0) {
					mykey.set(uid);
					String tml = "B_" + keyword;
					myvalue.set(tml);
					context.write(mykey, myvalue);
				}
			}
		}

	}

	public static class MyReduce extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			Set<String> ls = new HashSet<String>();
			String uid = null;
			Iterator<Text> it = arg1.iterator();
			while (it.hasNext()) {
				Text tx = it.next();
				String line = tx.toString();
				if (line.startsWith("A_")) {
					uid = line.substring(2);
				} else if (line.startsWith("B_")) {
					String mline = line.substring(2);
					ls.add(mline);
				}

			}
			if (null != uid && ls.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (String kw : ls) {
					sb.append(kw + "\t");
				}
				arg2.write(new Text(uid), new Text(sb.toString()));
			}
		}

	}
}
