package day0917;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class MyMessage implements WritableComparable<MyMessage> {

	private Text tag;
	private Text child;
	private Text par;

	public MyMessage() {
//		tag = new Text();
//		child = new Text();
//		par = new Text();
	}

	public MyMessage(Text tag, Text child, Text par) {
		set(tag, child, par);
	}

	public void set(Text tag2, Text child2, Text par2) {
		tag = tag2;
		child = child2;
		par = par2;
	}

	public Text getTag() {
		return tag;
	}

	public void setTag(Text tag) {
		this.tag = tag;
	}

	public Text getChild() {
		return child;
	}

	public void setChild(Text child) {
		this.child = child;
	}

	public Text getPar() {
		return par;
	}

	public void setPar(Text par) {
		this.par = par;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		tag.write(out);
		child.write(out);
		par.write(out);

	}

	@Override
	public void readFields(DataInput in) throws IOException {
		tag.readFields(in);
		child.readFields(in);
		par.readFields(in);
	}

	@Override
	public int compareTo(MyMessage o) {
		int com = tag.toString().compareTo(o.getTag().toString());
		if (com == 0) {
			int com1 = child.toString().compareTo(o.getChild().toString());
			if (com1 == 0) {
				return par.toString().compareTo(o.getPar().toString());
			} else {
				return com1;
			}
		} else {
			return com;
		}
	}

}
