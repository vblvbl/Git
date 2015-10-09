package day0907;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class SougouWritable implements WritableComparable {
	public Text date;
	public Text uid;
	public Text keyword;
	public IntWritable range;
	public IntWritable order;
	public Text url;

	public SougouWritable() {
		set(new Text(""), new Text(""), new Text(""), new IntWritable(), new IntWritable(), new Text(""));
	}

	public SougouWritable(String date, String uid, String keyword, int range, int order, String url) {
		set(new Text(date), new Text(uid), new Text(keyword), new IntWritable(range), new IntWritable(order),
				new Text(url));
	}

	public SougouWritable(Text date, Text uid, Text keyword, IntWritable range, IntWritable order, Text url) {
		set(date, uid, keyword, range, order, url);
	}

	private void set(Text date2, Text uid2, Text keyword2, IntWritable range2, IntWritable order2, Text url2) {
		this.date = date2;
		this.uid = uid2;
		this.keyword = keyword2;
		this.range = range2;
		this.order = order2;
		this.url = url2;
	}

	@Override
	public void readFields(DataInput arg0) throws IOException {
		date.readFields(arg0);
		uid.readFields(arg0);
		keyword.readFields(arg0);
		range.readFields(arg0);
		order.readFields(arg0);
		url.readFields(arg0);

	}

	@Override
	public void write(DataOutput write) throws IOException {
		date.write(write);
		uid.write(write);
		keyword.write(write);
		range.write(write);
		order.write(write);
		url.write(write);
	}

	public Text getDate() {
		return date;
	}

	public Text getUid() {
		return uid;
	}

	public Text getKeyword() {
		return keyword;
	}

	public IntWritable getRange() {
		return range;
	}

	public IntWritable getOrder() {
		return order;
	}

	public Text getUrl() {
		return url;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
