package day0917.job;

public class MyBean implements Comparable<MyBean> {
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public MyBean(int time, String uid) {
		this.time = time;
		this.uid = uid;
	}

	public MyBean() {
	}

	private int time = 0;
	private String uid = null;

	@Override
	public int compareTo(MyBean o) {
		int x = this.time;
		int y = o.getTime();
		int num = (x < y) ? -1 : ((x == y) ? 0 : 1);
		return num;
	}

}
