package job1.one;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//1.1、通过统计乘用车辆和商用车辆的数量和销售额分布
public class Car_Publish {
	public static List<String> C_use = new ArrayList<String>();
	public static List<String> S_use = new ArrayList<String>();

	static {
		C_use.add("小型普通客车");
		S_use.add("大型普通客车");
		S_use.add("大型专用校车");
	}

	public class myM extends Mapper<Object, Text, Text, Text> {

	}
}
