package day0917;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.likang.util.MyUtils;

public class ChildParent2 {

	public static class Map extends Mapper<Object, Text, Text, MyMessage> {
		public static final MyMessage mm1 = new MyMessage();
		public static final MyMessage mm2 = new MyMessage();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String[] sp = value.toString().split("\t");
//			mm1.setTag(new Text("left"));
//			mm1.setChild(new Text(sp[0]));
//			mm1.setPar(new Text(sp[1]));
			mm1.set(new Text("left"), new Text(sp[0]), new Text(sp[1]));
			context.write(new Text(sp[1]), mm1);
//			mm2.setTag(new Text("right"));
//			mm2.setChild(new Text(sp[0]));
//			mm2.setPar(new Text(sp[1]));
			mm2.set(new Text("right"), new Text(sp[0]),new Text(sp[1]));
			context.write(new Text(sp[0]), mm2);
		}
	}

	public static class Reduce extends Reducer<Text, MyMessage, Text, Text> {

		@Override
		protected void setup(Reducer<Text, MyMessage, Text, Text>.Context context)
				throws IOException, InterruptedException {
			context.write(new Text("grandchild"), new Text("grandparent"));
		}

		public void reduce(Text key, Iterable<MyMessage> values, Context context)
				throws IOException, InterruptedException {
			List<String> grandchild = new ArrayList<String>();
			List<String> grandparent = new ArrayList<String>();
			Iterator<MyMessage> it = values.iterator();
			while (it.hasNext()) {
				MyMessage record = it.next();
				String relationtype = record.getTag().toString();
				String childname = record.getChild().toString();
				String parentname = record.getPar().toString();
				if ("left".equals(relationtype)) {
					grandchild.add(childname);
				} else if ("right".equals(relationtype)) {
					grandparent.add(parentname);
				}
			}
			for (int m = 0; m < grandchild.size(); m++) {
				for (int n = 0; n < grandparent.size(); n++) {
					// 输出结果
					context.write(new Text(grandchild.get(m)), new Text(grandparent.get(n)));
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String from = MyUtils.BASEPATH + "a.txt";
		String to = MyUtils.BASEPATH + "result2/";
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Single Table Join");
		job.setJarByClass(ChildParent2.class);
		// 设置Map和Reduce处理类
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		// 设置输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MyMessage.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		// 设置输入和输出目录
		FileInputFormat.addInputPath(job, new Path(from));
		FileOutputFormat.setOutputPath(job, new Path(to));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
