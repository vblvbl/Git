package test5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import test3.one.CountCityW;
import test4.one.ShengWSum.MyMap;
import test4.one.ShengWSum.MyReduce;

//计算北京市的每种农产品的价格波动趋势,即计算每天价格均值,并按照时间先后顺序排列 该值。
//某种农产品的价格均值计算公式:
//PAVG = (PM1+PM2+...+PMn-max(P)-min(P))/(N-2)
//其中,P 表示价格,Mn 表示 market,即农产品市场。PM1 表示 M1 农产品市场的该产品价 格,max(P)表示价格最大值,min(P)价格最小值
public class PriceTo {
	public static class Mp extends Mapper<Object, Text, IntWritable, Text> {
		public static final Text tx = new Text();
		public static IntWritable day1 = new IntWritable(1);
		public static IntWritable day2 = new IntWritable(2);
		public static IntWritable day3 = new IntWritable(3);
		public static IntWritable day4 = new IntWritable(4);
		public static IntWritable day5 = new IntWritable(5);
		public static IntWritable[] ss = { day1, day2, day3, day4, day5 };

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			// System.out.println(words.length);
			if (words.length >= 9) {
				String city = words[7];
				if (city.equals("北京")) {
					for (int i = 0; i < 5; i++) {
						String need = words[i + 1];
						tx.set(need);
						context.write(ss[i], tx);
					}
				}
			}
		}
	}

	public static class Red extends Reducer<IntWritable, Text, Text, NullWritable> {
		public static final Text PAVG = new Text();

		@Override
		protected void reduce(IntWritable arg0, Iterable<Text> arg1,
				Reducer<IntWritable, Text, Text, NullWritable>.Context arg2) throws IOException, InterruptedException {
			Iterator<Text> it = arg1.iterator();
			List<Double> ld = new ArrayList<Double>();
			while (it.hasNext()) {
				String need = it.next().toString();
				Double dd = Double.valueOf(need);
				ld.add(dd);
			}
			Double max = Collections.max(ld);
			Double min = Collections.min(ld);
			double sum = 0;
			for (Double item : ld) {
				sum += item;
			}
			double trueSum = sum - max - min;
			double pavg = trueSum / (ld.size() - 2);
			PAVG.set(pavg + "");
			arg2.write(PAVG, NullWritable.get());
		}

	}

	public static void main(String[] args) throws Exception {
		String from = "hdfs://localhost:9000/input2";
		String to = "hdfs://localhost:9000/reout6";
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Single Tssable Join");
		job.setJarByClass(PriceTo.class);
		// 设置Map和Reduce处理类
		job.setMapperClass(Mp.class);
		job.setReducerClass(Red.class);
		// 设置输出类型
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		FileInputFormat.addInputPath(job, new Path(from));
		FileOutputFormat.setOutputPath(job, new Path(to));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}