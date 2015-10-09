package day0912;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;

public class MultipleReadFile {
	public static void main(String[] args) {
		String inPath1 = MyUtils.HOMEPATH + "seqfile/1w.seq";
		String inPath2 = MyUtils.HOMEPATH + "textfile/uidneed";
		String outPath = MyUtils.RESPATH + "/multiple";
		Configuration cg = new Configuration();
		Job job = null;
		try {
			job = new Job(cg, "it is a job");
			job.setJarByClass(MultipleReadFile.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(MyWritable.class);
			// 两个种类文件读入
//			job.setMapperClass(cls);
			MultipleInputs.addInputPath(job, new Path(inPath1), SequenceFileInputFormat.class, MapA.class);
			MultipleInputs.addInputPath(job, new Path(inPath2), TextInputFormat.class, MapB.class);
			// 写出到两个文件中
			job.setReducerClass(Reduce.class);
			// job.setOutputFormatClass(TextOutputFormat.class);
			// job.setOutputKeyClass(Text.class);
			// job.setOutputValueClass(Text.class);
			MultipleOutputs.addNamedOutput(job, "keywordCity", TextOutputFormat.class, Text.class, Text.class);
			MultipleOutputs.addNamedOutput(job, "urlCity", TextOutputFormat.class, Text.class, Text.class);
			FileOutputFormat.setOutputPath(job, new Path(outPath));
			job.waitForCompletion(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// it is used for seq
	public static class MapA extends Mapper<Object, Text, Text, MyWritable> {
		public static final Text txkey = new Text();
		public static final MyWritable txvalue = new MyWritable();
		boolean flag = false;

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, MyWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			for (String word : words) {
				if (word.equals("") || word == null) {
					flag = true;
				}
			}
			if (!flag) {
				String uid = words[1];
				txkey.set(uid);
				txvalue.set(new Text("seq"), new Text(words[2] + "\t" + words[5]));
				context.write(txkey, txvalue);
			} else {
				return;
			}
		}

	}

	// it is used for text
	public static class MapB extends Mapper<Object, Text, Text, MyWritable> {
		public static final Text txkey = new Text();
		public static final MyWritable txvalue = new MyWritable();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, MyWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			String uid = words[0];
			String city = words[1];
			txkey.set(uid);
			txvalue.set(new Text("text"), new Text(city));
			context.write(txkey, txvalue);
		}

	}

	public static class Reduce extends Reducer<Text, MyWritable, Text, Text> {
		MultipleOutputs<Text, Text> mp = null;
		public static final Text key1 = new Text();
		public static final Text key2 = new Text();
		public static final Text value = new Text();

		@Override
		protected void setup(Reducer<Text, MyWritable, Text, Text>.Context context)
				throws IOException, InterruptedException {
			mp = new MultipleOutputs<Text, Text>(context);
		}

		@Override
		protected void reduce(Text key, Iterable<MyWritable> arg1, Reducer<Text, MyWritable, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			Iterator<MyWritable> it = arg1.iterator();
			String keyword = null;
			String url = null;
			while (it.hasNext()) {
				MyWritable mw = it.next();
				String tag = mw.getTag().toString();
				String message = mw.getValue().toString();
				if (tag.equals("seq")) {
					String[] tmp = message.split("\t");
					keyword = tmp[0];// 其中一个key
					key1.set(keyword);
					url = tmp[1];// 其中一个key
					key2.set(url);
				} else if (tag.equals("text")) {
					// 这个里面放的是city的信息
					value.set(message);// 这个是value
				}
			}
			if (keyword != null && !keyword.equals("")) {
				mp.write(key1, value, "keywordCity");
			}
			if (url != null && !url.equals("")) {
				mp.write(key2, value, "urlCity");
			}
		}

		@Override
		protected void cleanup(Reducer<Text, MyWritable, Text, Text>.Context context)
				throws IOException, InterruptedException {
			mp.close();
		}
	}
}
