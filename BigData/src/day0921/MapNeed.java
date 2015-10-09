package day0921;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapNeed extends Mapper<Object, Text, Text, Text> {
	static Date eight = null;
	static Date ten = null;

	static {
		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddhhmmss");
		try {
			eight = sm.parse("20111230075959");
			ten = sm.parse("20111230095959");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final Text mykey1 = new Text();
	public static final Text myvalue1 = new Text();

	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] words = line.split("\t");
		if (words.length == 6) {
			String time = words[0];
			String uid = words[1];
			String keyword = words[2];
			String rank = words[3];
			String order = words[4];
			SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddhhmmss");
			Date md = null;
			try {
				md = sm.parse(time);
			} catch (ParseException e) {
				return;
			}
			if (md.before(eight) || md.after(ten)) {
				return;
			}
			// System.out.println(eight);
			int rk = Integer.parseInt(rank);
			if (rk < 1 || rk > 3) {
				return;
			}
			int od = Integer.parseInt(order);
			if (od != 1) {
				return;
			}
			// System.out.println("走到这了");
			mykey1.set(uid);
			String tml = "A_" + uid;
			myvalue1.set(tml);
			context.write(mykey1, myvalue1);
		}
	}

}
