package day0910.job;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;

public class RankPoint {
	public static void main(String[] args) throws Exception {
		Job job = new Job(new Configuration());
		Class[] clazz = new Class[] { RankPoint.class, IntWritable.class, IntWritable.class, Text.class, Text.class,
				MyMap.class, MyReduce.class, TextInputFormat.class, TextOutputFormat.class };
		MyUtils.setJob(job, args[0], args[1], clazz);
		job.waitForCompletion(true);
	}

	public static class MyMap extends Mapper<Object, Text, IntWritable, IntWritable> {
		public static final IntWritable rankKey = new IntWritable();
		public static final IntWritable rankValue = new IntWritable(1);

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] tmp = line.split("\t");
			String rankstr = tmp[3];
			if (rankstr != null && !rankstr.equals("")) {
				int rank = Integer.parseInt(rankstr);
				if (rank <= 10) {
					rankKey.set(rank);
				} else {
					return;
				}
			} else {
				return;
			}
			context.write(rankValue, rankKey);
		}

	}

	public static class MyReduce extends Reducer<IntWritable, IntWritable, Text, Text> {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Text tx1 = new Text();
		Text tx2 = new Text();
		int count = 0;
		double allcount = 0.0;
		double onecount = 0.0;

		@Override
		protected void reduce(IntWritable arg0, Iterable<IntWritable> arg1,
				Reducer<IntWritable, IntWritable, Text, Text>.Context arg2) throws IOException, InterruptedException {
			Iterator<IntWritable> it = arg1.iterator();
			while (it.hasNext()) {
				int item = it.next().get();
				if (map.containsKey(item)) {
					int countdemo = map.get(item);
					countdemo++;
					map.put(item, countdemo);
				} else {
					map.put(item, 1);
				}
				count++;
			}
			allcount = count;
			Set<Entry<Integer, Integer>> set = map.entrySet();
			for (Entry<Integer, Integer> ss : set) {
				tx1.set(ss.getKey().toString());
				onecount = ss.getValue();
				tx2.set((onecount / allcount) + "");
				arg2.write(tx1, tx2);
			}
		}

	}
}
