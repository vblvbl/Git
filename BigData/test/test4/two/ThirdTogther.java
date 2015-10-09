package test4.two;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class ThirdTogther {
	public static String path1 = "hdfs://localhost:9000/reout3/part-r-00000";
	public static String path2 = "hdfs://localhost:9000/input";
	public static String path3 = "hdfs://localhost:9000/reout5";

	public static void main(String[] args) throws Exception {
		Job job = new Job(new Configuration(), "Joissn");
		job.setJarByClass(ThirdTogther.class);
		MultipleInputs.addInputPath(job, new Path(path1), TextInputFormat.class, MyMapA.class);
		MultipleInputs.addInputPath(job, new Path(path2), TextInputFormat.class, MyMapB.class);
		job.setReducerClass(MyDReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(path3));
		job.waitForCompletion(true);
	}

	public static class MyMapA extends Mapper<Object, Text, Text, Text> {

		public static final Text tk = new Text();
		public static final Text tv = new Text();
		public static final List<MyBean> ls = new ArrayList<MyBean>();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] words = value.toString().split("\t");
			String city = words[0];
			int num = Integer.parseInt(words[1]);
			MyBean mb = new MyBean(city, num);
			ls.add(mb);
		}

		@Override
		protected void cleanup(Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			for (int i = 0; i < 3; i++) {
				MyBean mb = Collections.max(ls);
				String city = mb.getCity();
				tk.set(city);
				String need = "A_" + mb.getNum();
				tv.set(need);
				context.write(tk, tv);
				System.out.println(city + ":" + need);
				ls.remove(mb);
			}
		}
	}

	public static class MyMapB extends Mapper<Object, Text, Text, Text> {
		public static final Text tk = new Text();
		public static final Text tv = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			String cp = words[0];
			String city = words[4];
			tk.set(city);
			String need = "B_" + cp;
			tv.set(need);
			context.write(tk, tv);
			System.out.println(city + ":" + need);
		}

	}

	public static class MyDReduce extends Reducer<Text, Text, Text, Text> {

		public final Map<String, Set<String>> mp = new HashMap<>();
		public static final Text ttk = new Text();

		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			Set<String> sset = new HashSet<String>();
			Iterator<Text> it = arg1.iterator();
			int sum = 0;
			while (it.hasNext()) {
				Text tx = it.next();
				String line = tx.toString();
				// System.out.println(line);
				if (line.startsWith("A_")) {
					String mine = line.substring(2);
					sum = Integer.parseInt(mine);
				} else if (line.startsWith("B_")) {
					String mine = line.substring(2);
					sset.add(mine);
				}
			}
			if (sum != 0 && sset.size() > 0) {
				mp.put(sum + "", sset);
			}
		}

		@Override
		protected void cleanup(Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			Map<String, Integer> mm = new HashMap<String, Integer>();
			Set<Entry<String, Set<String>>> entrySet = mp.entrySet();
			for (Entry<String, Set<String>> item : entrySet) {
				Set<String> sse = item.getValue();
				for (String ss : sse) {
					if (mm.containsKey(ss)) {
						int sum = mm.get(ss);
						sum++;
						mm.put(ss, sum);
					} else {
						mm.put(ss, 1);
					}
				}
			}
			Set<Entry<String, Integer>> entrySet2 = mm.entrySet();
			for (Entry<String, Integer> item : entrySet2) {
				if (item.getValue() >= 3) {
					ttk.set(item.getKey());
					context.write(ttk, new Text(""));
				}
			}
		}

	}

}
