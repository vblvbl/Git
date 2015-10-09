package day0915;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.likang.util.MyUtils;

public class SumFiled {
	public static void main(String[] args) {
		String from = MyUtils.BASEPATH + "demo.txt";
		String to = MyUtils.BASEPATH + "result/";
		try {
			Configuration cf = new Configuration();
			Job job = new Job(cf, "it is easy");
			Class[] clazz = new Class[] { SumFiled.class, Text.class, MyList.class, Text.class, Text.class, MyMap.class,
					MyReduce.class, TextInputFormat.class, TextOutputFormat.class };
			MyUtils.setJob(job, from, to, clazz);
			job.waitForCompletion(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class MyMap extends Mapper<Object, Text, Text, MyList> {
		public static final Text tkey = new Text();
		public static final MyList lvalue = new MyList();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, MyList>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] datas = line.split("\t");
			tkey.set(datas[0]);
			lvalue.set(new Text(datas[1]), new Text(datas[2]), new Text(datas[3]));
			context.write(tkey, lvalue);
		}
	}

	public static class MyReduce extends Reducer<Text, MyList, Text, Text> {
		@Override
		protected void setup(Reducer<Text, MyList, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String mykey = "手机号";
			String myvalue = "上网总流量" + "\t" + "通话总时间" + "\t" + "短信总量";
			context.write(new Text(mykey), new Text(myvalue));
		}

		public static final Text myvalue = new Text();

		@Override
		protected void reduce(Text arg0, Iterable<MyList> arg1, Reducer<Text, MyList, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			Iterator<MyList> it = arg1.iterator();
			int sum1 = 0;
			int sum2 = 0;
			int sum3 = 0;
			while (it.hasNext()) {
				MyList ms = it.next();
				sum1 += Integer.parseInt(ms.getNetTraffic().toString());
				sum2 += Integer.parseInt(ms.getCallTime().toString());
				sum3 += Integer.parseInt(ms.getSmsCount().toString());
			}
			String need = sum1 + "\t" + sum2 + "\t" + sum3;
			myvalue.set(need);
			arg2.write(arg0, myvalue);
		}

	}
}
