package day0915;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class MyList implements WritableComparable<MyList> {
	public Text getNetTraffic() {
		return netTraffic;
	}

	public void setNetTraffic(Text netTraffic) {
		this.netTraffic = netTraffic;
	}

	public Text getCallTime() {
		return callTime;
	}

	public void setCallTime(Text callTime) {
		this.callTime = callTime;
	}

	public Text getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(Text smsCount) {
		this.smsCount = smsCount;
	}

	@Override
	public int hashCode() {
		int rum = 38;
		return this.netTraffic.toString().hashCode() * rum + this.callTime.toString().hashCode();
	}

	Text netTraffic;
	Text callTime;
	Text smsCount;

	public MyList() {
		set(new Text(), new Text(), new Text());
	}

	public MyList(Text tag, Text value, Text t3) {
		set(tag, value, t3);
	}

	public void set(Text tag2, Text value2, Text t3) {
		this.netTraffic = tag2;
		this.callTime = value2;
		this.smsCount = t3;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		this.netTraffic.write(out);
		this.callTime.write(out);
		this.smsCount.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.netTraffic.readFields(in);
		this.callTime.readFields(in);
		this.smsCount.readFields(in);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof MyList) {
			MyList obj1 = (MyList) obj;
			return this.netTraffic.equals(obj1.callTime) && this.callTime.equals(obj1.callTime);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(MyList o) {
		int one = this.netTraffic.toString().compareTo(o.netTraffic.toString());
		if (one == 0) {
			int two = this.callTime.toString().compareTo(o.callTime.toString());
			if (two == 0) {
				return this.smsCount.toString().compareTo(o.smsCount.toString());
			}
			return two;
		}
		return one;
	}

}
