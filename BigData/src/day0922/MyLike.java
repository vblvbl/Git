package day0922;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class MyLike extends UDF {
	List<String> ls;

	public MyLike() {
		ls = new ArrayList<String>();
	}

	public void evaluate(Text tx) {
		if (ls.size() == 0) {
			ls.add(tx.toString());
		} else {
			for (String item : ls) {
				if (item.contains(tx.toString())) {
				} else {

				}
			}
		}
	}
}
