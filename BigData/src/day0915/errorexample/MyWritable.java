package day0915.errorexample;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class MyWritable implements Writable {
	private int internet;
	private int callTime;
	private int mailcount;

	public MyWritable() {
	}

	public int getInternet() {
		return internet;
	}

	public void setInternet(int internet) {
		this.internet = internet;
	}

	public int getCallTime() {
		return callTime;
	}

	public void setCallTime(int callTime) {
		this.callTime = callTime;
	}

	public int getMailcount() {
		return mailcount;
	}

	public void setMailcount(int mailcount) {
		this.mailcount = mailcount;
	}

	public MyWritable(int internet, int callTime, int mailcount) {
		set(internet, callTime, mailcount);
	}

	public void set(int internet2, int callTime2, int mailcount2) {
		this.internet = internet2;
		this.callTime = callTime2;
		this.mailcount = mailcount2;

	}

	public String toString() {
		return " \t" + internet + "\t" + callTime + "\t" + mailcount;
	}

	@Override
	public void readFields(DataInput in) throws IOException {

		this.internet = in.readInt();
		this.callTime = in.readInt();
		this.mailcount = in.readInt();

	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(internet);
		out.writeInt(callTime);
		out.writeInt(mailcount);
	}

}
