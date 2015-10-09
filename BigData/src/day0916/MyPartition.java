package day0916;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartition extends Partitioner<IntWritable, IntWritable> {

	@Override
	public int getPartition(IntWritable key, IntWritable value, int numPartitions) {
		int num = key.get();
		if (num < 25) {
			return 0;
		} else if (num >= 25 && num < 50) {
			return 1;
		} else if (num >= 5 && num < 70) {
			return 2;
		} else {
			return 3;
		}
	}

}
