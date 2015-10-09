package day0907;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class SogouWritable_stand implements WritableComparable<SogouWritable_stand> {

	private Text time;
	private Text uid;
	private Text keyword;
	private IntWritable rank;
	private IntWritable order;
	private Text url;

	public SogouWritable_stand() {
		this.time = new Text();
		this.uid = new Text();
		this.keyword = new Text();
		this.rank = new IntWritable();
		this.order = new IntWritable();
		this.url = new Text();
	}

	public SogouWritable_stand(Text time, Text uid, Text keyword, IntWritable rank,
			IntWritable order, Text url) {
		super();
		this.time = time;
		this.uid = uid;
		this.keyword = keyword;
		this.rank = rank;
		this.order = order;
		this.url = url;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		this.time.write(out);
		this.uid.write(out);
		this.keyword.write(out);
		this.rank.write(out);
		this.order.write(out);
		this.url.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.time.readFields(in);
		this.uid.readFields(in);
		this.keyword.readFields(in);
		this.rank.readFields(in);
		this.order.readFields(in);
		this.url.readFields(in);
	}

	@Override
	public int compareTo(SogouWritable_stand o) {
		int cmp = this.time.compareTo(o.time);
		if (cmp != 0) {
			return cmp;
		}
		cmp = this.uid.compareTo(o.uid);
		if (cmp != 0) {
			return cmp;
		}
		cmp = this.keyword.compareTo(o.keyword);
		if (cmp != 0) {
			return cmp;
		}
		cmp = this.rank.compareTo(o.rank);
		if (cmp != 0) {
			return cmp;
		}
		cmp = this.order.compareTo(o.order);
		if (cmp != 0) {
			return cmp;
		}

		return this.url.compareTo(o.url);
	}

	public Text getTime() {
		return time;
	}

	public void setTime(Text time) {
		this.time = time;
	}

	public Text getUid() {
		return uid;
	}

	public void setUid(Text uid) {
		this.uid = uid;
	}

	public Text getKeyword() {
		return keyword;
	}

	public void setKeyword(Text keyword) {
		this.keyword = keyword;
	}

	public IntWritable getRank() {
		return rank;
	}

	public void setRank(IntWritable rank) {
		this.rank = rank;
	}

	public IntWritable getOrder() {
		return order;
	}

	public void setOrder(IntWritable order) {
		this.order = order;
	}

	public Text getUrl() {
		return url;
	}

	public void setUrl(Text url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "SogouWritable [time=" + time + ", uid=" + uid + ", keyword="
				+ keyword + ", rank=" + rank + ", order=" + order + ", url="
				+ url + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SogouWritable_stand other = (SogouWritable_stand) obj;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
