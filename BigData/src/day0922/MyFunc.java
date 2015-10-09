package day0922;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class MyFunc extends UDF {

	public MyFunc() {

	}

//	public static void main(String[] args) {
//		String n = "我是天才哦";
//		String m = "天才";
//		System.out.println(n.contains(m));
//	}
	 public String evaluate(Text tx) {
	 // IntWritable
	 List<Character> ls = new ArrayList<Character>();
	 String keywords = tx.toString().trim();// 先祛除两边空格
	 char[] cha_keywords = keywords.toCharArray();
	 for (char item : cha_keywords) {
	 if (item == ' ') {
	 continue;
	 } else {
	 ls.add(item);
	 }
	 }
	 StringBuilder sb = new StringBuilder();
	 for (Character im : ls) {
	 sb.append(im);
	 }
	 return sb.toString();
	 }
}
