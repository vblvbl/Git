package day0912;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Createuid {
	public static void main(String[] args) {
		String[] citys = { "安徽", "北京", "重庆", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河北", "黑龙江", "河南", "香港", "湖北", "湖南",
				"江苏", "江西", "吉林", "辽宁", "澳门", "内蒙古", "宁夏", "青海", "山东", "上海", "山西", "陕西", "四川", "台湾", "天津", "新疆", "西藏",
				"云南", "浙江", "海外" };
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(new File("/Users/Kang/Desktop/uid")));
			bw = new BufferedWriter(new FileWriter(new File("/Users/Kang/Desktop/uidneed")));
			String line = null;
			while ((line = br.readLine()) != null) {
				bw.write(line + "\t" + citys[new Random().nextInt(citys.length)] + "\r\n");
			}
			System.out.println("ok!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
