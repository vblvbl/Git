package com.likang.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.likang.view.main.CLibrary;

public class MethUtil {
	public static Object[][] getForm(String needData, String content, String need2) {
		String[] datas = needData.split("#");
		List<String> ls = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < datas.length; i++) {
			if (!ls.contains(datas[i])) {
				ls.add(datas[i]);
			}
		}
		String[] datass = new String[ls.size()];
		ls.toArray(datass);
		Object[][] obj = new Object[datass.length][3];
		for (int i = 0; i < datass.length; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 0) {
					obj[i][j] = datass[i];
				} else if (j == 1) {
					if (need2.contains(datass[i])) {
						obj[i][j] = "new";
					} else {
						obj[i][j] = "key";
					}
				} else if (j == 2) {
					obj[i][j] = weight(datass[i], content) + "/" + datas.length;
				}
			}
		}
		return obj;

	}

	private static int weight(String string, String content) {

		return content.split(string).length;
	}

	public static int wordCount(String input) {
		String[] words = input.split(" ");
		return words.length;
	}

	public static String sortFenci(String input) {
		StringBuilder sball = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		sb1.append("人名:\r\n");
		StringBuilder sb2 = new StringBuilder();
		sb2.append("地点:\r\n");
		StringBuilder sb3 = new StringBuilder();
		sb3.append("名词:\r\n");
		StringBuilder sb4 = new StringBuilder();
		sb4.append("数词:\r\n");
		StringBuilder sb5 = new StringBuilder();
		sb5.append("时间词:\r\n");
		StringBuilder sb6 = new StringBuilder();
		sb6.append("其他:\r\n");
		String[] words = input.split(" ");
		for (String word : words) {
			if (word.contains("/nrf")) {
				sb1.append(word + ",");
			} else if (word.contains("/ns")) {
				sb2.append(word + ",");
			} else if (word.contains("/n")) {
				sb3.append(word + ",");
			} else if (word.contains("/m")) {
				sb4.append(word + ",");
			} else if (word.contains("/t")) {
				sb5.append(word + ",");
			} else {
				sb6.append(word + ",");
			}
		}
		if (!(sb1.length() > 5)) {
			sb1.append("无");
		}
		if (!(sb2.length() > 5)) {
			sb2.append("无");
		}
		if (!(sb3.length() > 5)) {
			sb3.append("无");
		}
		if (!(sb4.length() > 5)) {
			sb4.append("无");
		}
		if (!(sb5.length() > 6)) {
			sb5.append("无");
		}
		if (!(sb6.length() > 5)) {
			sb6.append("无");
		}

		sball.append(sb1.toString() + "\r\n");
		sball.append(sb2.toString() + "\r\n");
		sball.append(sb3.toString() + "\r\n");
		sball.append(sb4.toString() + "\r\n");
		sball.append(sb5.toString() + "\r\n");
		sball.append(sb6.toString() + "\r\n");
		return sball.toString();
	}

	public static ArrayList<Integer> locationWord(String content, String[] words) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (int i = 0; i < words.length; i++) {
			arrayList.add(content.indexOf(words[i]) - 2 * i);
		}
		return arrayList;

	}

	public static void writeAllToFile1(File file, String charset) throws Exception {
		File myfile = new File(file.getAbsolutePath(), "resultAll");
		if (!myfile.exists()) {
			myfile.mkdir();
		}
		File[] listFile = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".txt");
			}
		});

		ArrayList<File> arrayList = new ArrayList<File>();
		for (File tmpfile : listFile) {
			arrayList.add(tmpfile);
		}
		Collections.sort(arrayList, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				String name1 = o1.getName();
				String name2 = o2.getName();
				if (name1.length() == name2.length()) {
					return name1.compareTo(name2);
				} else {
					return name1.length() > name2.length() ? 1 : -1;
				}
			}
		});

		StringBuilder sb = new StringBuilder();

		for (File tmpfile : arrayList) {
			String name = "=============" + tmpfile.getParent() + "---分词1---" + ":" + tmpfile.getName()
					+ "=============";
			sb.append(name);
			sb.append("\r\n");
			String content = fileToString(tmpfile, charset);
			String fenci = splitMethod(content);
			sb.append(fenci);
			sb.append("\r\n" + "\r\n" + "\r\n");
		}

		File mytxtFile = new File(myfile.getAbsolutePath(), "resultAll1.txt");
		FileWriter output = new FileWriter(mytxtFile);
		output.write(sb.toString());
		output.close();

	}

	public static void writeAllKeyToFile(File file, String charset) throws Exception {
		File myfile = new File(file.getAbsolutePath(), "keyresultAll");
		if (!myfile.exists()) {
			myfile.mkdir();
		}
		File[] listFile = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".txt");
			}
		});

		ArrayList<File> arrayList = new ArrayList<File>();
		for (File tmpfile : listFile) {
			arrayList.add(tmpfile);
		}
		Collections.sort(arrayList, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				String name1 = o1.getName();
				String name2 = o2.getName();
				if (name1.length() == name2.length()) {
					return name1.compareTo(name2);
				} else {
					return name1.length() > name2.length() ? 1 : -1;
				}
			}
		});

		StringBuilder sb = new StringBuilder();

		for (File tmpfile : arrayList) {
			String name = "=============" + tmpfile.getParent() + "---关键词---" + ":" + tmpfile.getName()
					+ "=============";
			sb.append(name);
			sb.append("\r\n");
			String content = fileToString(tmpfile, charset);
			sb.append("--->关键词:" + "\r\n");
			String keyword = keywordMethod(content);
			sb.append(keyword + "\r\n");
			sb.append("--->新词:" + "\r\n");
			String newword = newwordMethod(content);
			sb.append(newword);
			sb.append("\r\n" + "\r\n" + "\r\n");
		}

		File mytxtFile = new File(myfile.getAbsolutePath(), "keyresultAll.txt");
		FileWriter output = new FileWriter(mytxtFile);
		output.write(sb.toString());
		output.close();

	}

	public static void writeAllToFile2(File file, String charset) throws Exception {
		File myfile = new File(file.getAbsolutePath(), "resultAll");
		if (!myfile.exists()) {
			myfile.mkdir();
		}
		File[] listFile = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".txt");
			}
		});

		ArrayList<File> arrayList = new ArrayList<File>();
		for (File tmpfile : listFile) {
			arrayList.add(tmpfile);
		}
		Collections.sort(arrayList, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				String name1 = o1.getName();
				String name2 = o2.getName();
				if (name1.length() == name2.length()) {
					return name1.compareTo(name2);
				} else {
					return name1.length() > name2.length() ? 1 : -1;
				}
			}
		});

		StringBuilder sb = new StringBuilder();

		for (File tmpfile : arrayList) {
			String name = "=============" + tmpfile.getParent() + "---分词2---" + ":" + tmpfile.getName()
					+ "=============";
			sb.append(name);
			sb.append("\r\n");
			String content = fileToString(tmpfile, charset);
			String fenci = splitMethod(content);
			String sort = sortFenci(fenci);
			sb.append(sort);
			sb.append("\r\n" + "\r\n" + "\r\n");
		}

		File mytxtFile = new File(myfile.getAbsolutePath(), "resultAll2.txt");
		FileWriter output = new FileWriter(mytxtFile);
		output.write(sb.toString());
		output.close();

	}

	public static void writeToFile1(File file, String need, int count) throws IOException {
		File myfile = new File(file.getAbsolutePath(), "result1");
		if (!myfile.exists()) {
			myfile.mkdir();
		}
		File mytxtFile = new File(myfile.getAbsolutePath(), "result1 of " + count + ".txt");
		FileWriter output = new FileWriter(mytxtFile);
		output.write(need);
		output.close();

	}

	public static void writeToFileKey(File file, String need, int count) throws IOException {
		File myfile = new File(file.getAbsolutePath(), "keyresult");
		if (!myfile.exists()) {
			myfile.mkdir();
		}
		File mytxtFile = new File(myfile.getAbsolutePath(), "keyresult of " + count + ".txt");
		FileWriter output = new FileWriter(mytxtFile);
		output.write(need);
		output.close();

	}

	public static void writeToFile2(File file, String need, int count) throws IOException {
		File myfile = new File(file.getAbsolutePath(), "result2");
		if (!myfile.exists()) {
			myfile.mkdir();
		}
		File mytxtFile = new File(myfile.getAbsolutePath(), "result2 of " + count + ".txt");
		FileWriter output = new FileWriter(mytxtFile);
		output.write(need);
		output.close();
	}

	public static ArrayList<File> fileList(File file) {
		File[] files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".txt");
			}
		});
		ArrayList<File> arrayList = new ArrayList<File>();
		for (File fi : files) {
			arrayList.add(fi);
		}
		Collections.sort(arrayList, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				String name1 = o1.getName();
				String name2 = o2.getName();
				if (name1.length() == name2.length()) {
					return name1.compareTo(name2);
				} else {
					return name1.length() > name2.length() ? 1 : -1;
				}
			}
		});
		return arrayList;

	}

	public static String fileToString(File file, String charset) {
		try {
			StringBuilder sb = new StringBuilder();
			String readtmp = null;
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(file), charset));
			while ((readtmp = bufferedReader.readLine()) != null) {
				sb.append(readtmp);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String keywordMethod(String Input) throws Exception {
		String argu = "";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "UTF-8";
		int charset_type = 1;
		// int charset_type = 0;
		// 调用printf打印信息
		if (!CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset), charset_type, "0".getBytes(system_charset))) {
			System.err.println("初始化失败！");
		}

		String sInput = Input;

		String nativeByte = null;
		try {
			int nCountKey = 0;
			nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(sInput, 10, false);

			CLibrary.Instance.NLPIR_Exit();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return nativeByte;
	}

	public static String newwordMethod(String Input) throws Exception {
		String argu = "";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "UTF-8";
		int charset_type = 1;
		// int charset_type = 0;
		// 调用printf打印信息
		if (!CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset), charset_type, "0".getBytes(system_charset))) {
			System.err.println("初始化失败！");
		}
		String sInput = Input;
		String nativeByte = null;
		try {
			int nCountKey = 0;
			nativeByte = CLibrary.Instance.NLPIR_GetNewWords(sInput, 10, false);
			CLibrary.Instance.NLPIR_Exit();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return nativeByte;
	}

	public static String splitMethod(String Input) throws Exception {
		String argu = "";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "UTF-8";
		int charset_type = 1;
		// int charset_type = 0;
		// 调用printf打印信息
		if (!CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset), charset_type, "0".getBytes(system_charset))) {
			System.err.println("初始化失败！");
		}

		String sInput = Input;

		String nativeBytes = null;
		try {
			nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(sInput, 3);
			// String nativeStr = new String(nativeBytes, 0,
			// nativeBytes.length,"utf-8");
			// System.out.println("分词结果为： " + nativeBytes);
			// System.out.println("分词结果为： "
			// + transString(nativeBytes, system_charset, "UTF-8"));
			//
			// System.out.println("分词结果为： "
			// + transString(nativeBytes, "gb2312", "utf-8"));
			// System.out.print("关键词提取结果是：" + nativeByte);

			// int nativeElementSize = 4 * 6 +8;//size of result_t in native
			// code
			// int nElement = nativeByte.length / nativeElementSize;
			// ByteArrayInputStream(nativeByte));
			//
			// nativeByte = new byte[nativeByte.length];
			// nCountKey = testNLPIR30.NLPIR_KeyWord(nativeByte, nElement);
			//
			// Result[] resultArr = new Result[nCountKey];
			// DataInputStream dis = new DataInputStream(new
			// ByteArrayInputStream(nativeByte));
			// for (int i = 0; i < nCountKey; i++)
			// {
			// resultArr[i] = new Result();
			// resultArr[i].start = Integer.reverseBytes(dis.readInt());
			// resultArr[i].length = Integer.reverseBytes(dis.readInt());
			// dis.skipBytes(8);
			// resultArr[i].posId = Integer.reverseBytes(dis.readInt());
			// resultArr[i].wordId = Integer.reverseBytes(dis.readInt());
			// resultArr[i].word_type = Integer.reverseBytes(dis.readInt());
			// resultArr[i].weight = Integer.reverseBytes(dis.readInt());
			// }
			// dis.close();
			//
			// for (int i = 0; i < resultArr.length; i++)
			// {
			// System.out.println("start=" + resultArr[i].start + ",length=" +
			// resultArr[i].length + "pos=" + resultArr[i].posId + "word=" +
			// resultArr[i].wordId + " weight=" + resultArr[i].weight);
			// }

			CLibrary.Instance.NLPIR_Exit();

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return nativeBytes;

	}
}
