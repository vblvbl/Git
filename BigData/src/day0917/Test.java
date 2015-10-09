package day0917;

public class Test {
	public static void main(String[] args) {
		Demo d = new Demo();
		d.print(new Person("李康", 20));
	}

}

class Demo {
	Person p;
	public void print(Person p) {
		this.p = p;
		System.out.println(this.p);
	}
}

class Person {
	public Person() {

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name + "...." + this.age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int age = 0;
	private String name = null;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
}