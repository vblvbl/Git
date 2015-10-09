package day0912;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class MyWritable implements WritableComparable<MyWritable> {
	@Override
	public int hashCode() {
		int rum = 38;
		return this.tag.toString().hashCode() * rum + this.value.toString().hashCode();
	}

	Text tag;
	Text value;

	public Text getTag() {
		return tag;
	}

	public void setTag(Text tag) {
		this.tag = tag;
	}

	public Text getValue() {
		return value;
	}

	public void setValue(Text value) {
		this.value = value;
	}

	public MyWritable() {
		set(new Text(), new Text());
	}

	public MyWritable(Text tag, Text value) {
		set(tag, value);
	}

	public void set(Text tag2, Text value2) {
		this.tag = tag2;
		this.value = value2;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		this.tag.write(out);
		this.value.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.tag.readFields(in);
		this.value.readFields(in);

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof MyWritable) {
			MyWritable obj1 = (MyWritable) obj;
			return this.tag.equals(obj1.value) && this.value.equals(obj1.value);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(MyWritable o) {
		int one = this.tag.toString().compareTo(o.tag.toString());
		if (one == 0) {
			return this.value.toString().compareTo(o.value.toString());
		}
		return one;
	}
}
