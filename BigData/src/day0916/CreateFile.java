package day0916;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.BR;

public class CreateFile {
	public static void main(String[] args) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(new File("/Users/Kang/Desktop/demo.txt")));
			bw = new BufferedWriter(new FileWriter(new File("/Users/Kang/Desktop/demo1.txt"), true));
			String line = null;
			while ((line = br.readLine()) != null) {
				String words[] = line.split(" ");
				String newWord = words[0] + "\t" + new Random().nextInt(100);
				bw.write(newWord);
				bw.newLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
