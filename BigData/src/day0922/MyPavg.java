package day0922;

import java.util.ArrayList;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.io.LongWritable;

public class MyPavg extends AbstractGenericUDAFResolver {

	@Override
	public GenericUDAFEvaluator getEvaluator(TypeInfo[] info) throws SemanticException {
		return new MyGenUDAF();
	}

	public static class MyGenUDAF extends GenericUDAFEvaluator {
		LongWritable result;
		PrimitiveObjectInspector inputO1;

		@Override
		public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
			super.init(m, parameters);
			result = new LongWritable(0);
			if (parameters.length != 1) {
				throw new RuntimeException("size error");
			}
			inputO1 = (PrimitiveObjectInspector) parameters[0];
			return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
		}

		public static class MyAgg implements AggregationBuffer {
			double sum;
		}

		@Override
		public AggregationBuffer getNewAggregationBuffer() throws HiveException {
			MyAgg ag = new MyAgg();
			reset(ag);
			return ag;
		}

		@Override
		public void reset(AggregationBuffer agg) throws HiveException {
			MyAgg mg = (MyAgg) agg;
			mg.sum = 0;
		}

		// 是map阶段的操作
		@Override
		public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
			if (parameters == null || parameters[0] == null) {
				return;
			}
			double price = PrimitiveObjectInspectorUtils.getDouble(parameters[0], inputO1);
			MyAgg mg = (MyAgg) agg;
			mg.sum += price;
		}

		@Override
		public Object terminatePartial(AggregationBuffer agg) throws HiveException {
			return null;
		}

		@Override
		public void merge(AggregationBuffer agg, Object partial) throws HiveException {

		}

		@Override
		public Object terminate(AggregationBuffer agg) throws HiveException {
			return null;
		}

	}
}
