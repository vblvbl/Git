package com.likang.util;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyUtils {
	public static final String SG500W = "hdfs://localhost:9000/500w";
	public static final String SG1W = "hdfs://localhost:9000/user/Kang/1w/1w";
	public static final String BASEPATH = "hdfs://localhost:9000/";
	public static final String HOMEPATH = "hdfs://localhost:9000/user/Kang/";
	public static final String RESPATH = "hdfs://localhost:9000/user/Kang/result/";

	/**
	 * this is a util for setting job
	 * 
	 * @return void
	 * @param uriin
	 *            执行文件路径
	 * @param uriout
	 *            完成后路径
	 * @param clazz
	 *            [this.class,mpoutK,mpoutV,outK,outV,Mapper,Reduce,InFormat,
	 *            OutFormat]
	 */
	public static void setJob(Job job, String uriin, String uriout, Class[] clazz) {
		try {
			if (clazz.length != 9) {
				throw new RuntimeException("clazz传参有误");
			}
			Class<?> mythis = clazz[0];
			Class<? extends Writable> mapOutputKey = clazz[1];
			Class<? extends Writable> mapOutputValue = clazz[2];
			Class<? extends Writable> reduceOutputKey = clazz[3];
			Class<? extends Writable> reduceOutputValue = clazz[4];
			Class<? extends Mapper> mapclass = clazz[5];
			Class<? extends Reducer> reduceclass = clazz[6];
			Class<? extends InputFormat> inputformatclass = clazz[7];
			Class<? extends OutputFormat> outputformatclass = clazz[8];
			job.setJarByClass(mythis);
			/*
			 * =================================================================
			 * 如果没有这句，下面的那句影响中间和输出两个设置
			 */
			job.setMapOutputKeyClass(mapOutputKey);
			job.setMapOutputValueClass(mapOutputValue);
			/*
			 * 他是会覆盖下面那句原先的全局作用设置
			 * =================================================================
			 */
			job.setOutputKeyClass(reduceOutputKey);
			job.setOutputValueClass(reduceOutputValue);
			/*
			 * =================================================================
			 */
			job.setMapperClass(mapclass);
			job.setReducerClass(reduceclass);
			/*
			 * =================================================================
			 */
			// job.setNumReduceTasks(0);
			/*
			 * =================================================================
			 */
			job.setInputFormatClass(inputformatclass);
			job.setOutputFormatClass(outputformatclass);
			/*
			 * =================================================================
			 */
			Path in = new Path(uriin);
			Path out = new Path(uriout);
			FileInputFormat.addInputPath(job, in);
			FileOutputFormat.setOutputPath(job, out);
			// FileOutputFormat是一个抽象类,是TextOutputFormat的基类.
			/*
			 * =================================================================
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
