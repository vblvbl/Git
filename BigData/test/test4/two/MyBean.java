package test4.two;

public class MyBean implements Comparable<MyBean> {

	public MyBean() {

	}

	public MyBean(String city, int num) {
		super();
		this.city = city;
		this.num = num;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	private String city;
	private int num;

	@Override
	public int compareTo(MyBean o) {
		int x = this.getNum();
		int y = o.getNum();
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

}
