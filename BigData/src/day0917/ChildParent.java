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

public class ChildParent {

	/*
	 * map将输出分割child和parent，然后正序输出一次作为右表， 反序输出一次作为左表，需要注意的是在输出的value中必须
	 * 加上左右表的区别标识。
	 */
	public static class Map extends Mapper<Object, Text, Text, Text> {
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String relationtype = new String();// 左右表标识
			// 输入的一行预处理文本
			StringTokenizer itr = new StringTokenizer(value.toString());
			String[] values = new String[2];
			int i = 0;
			while (itr.hasMoreTokens()) {
				values[i] = itr.nextToken();
				i++;
			}
			if (values[0].compareTo("child") != 0) {
				String childname = values[0];
				String parentname = values[1];
				// 输出左表
				context.write(new Text(values[1]), new Text("1" + "+" + childname + "+" + parentname));
				// 输出右表
				context.write(new Text(values[0]), new Text("2" + "+" + childname + "+" + parentname));
			}
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		public static int count = 0;

		@Override
		protected void setup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
			context.write(new Text("grandchild"), new Text("grandparent"));
		}

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			List<String> grandchild = new ArrayList<String>();
			List<String> grandparent = new ArrayList<String>();
			Iterator ite = values.iterator();
			while (ite.hasNext()) {
				String record = ite.next().toString();
				String words[] = record.split("[+]");
				String relationtype = words[0];
				String childname = words[1];
				String parentname = words[2];
				System.out.println(count + "--->" + key.toString() + "--:" + "<" + relationtype + "   " + childname
						+ "   " + parentname + ">");
				if ("1".equals(relationtype)) {
					grandchild.add(childname);
				}
				// 右表，取出parent放入grandparent
				if ("2".equals(relationtype)) {
					grandparent.add(parentname);
				}
			}
			// grandchild和grandparent数组求笛卡尔儿积
			count++;
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
		String to = MyUtils.BASEPATH + "result1/";
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Single Table Join");
		job.setJarByClass(ChildParent.class);
		// 设置Map和Reduce处理类
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		// 设置输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		// 设置输入和输出目录
		FileInputFormat.addInputPath(job, new Path(from));
		FileOutputFormat.setOutputPath(job, new Path(to));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}