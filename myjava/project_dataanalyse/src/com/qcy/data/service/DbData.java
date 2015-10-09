package com.qcy.data.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.likang.data.domain.Student;
import com.qcy.data.config.Config;
import com.qcy.data.dao.Db;
import com.qcy.data.dao.KmeansDao;
import com.qcy.data.utils.ListParseString;

public class DbData {

	public static int countRow = 0;

	public static String[] getAllTableInfo() throws Exception {
		return ListParseString.LToStr(getAllTableNames(Db.getConn()));
	}

	public static void main(String[] args) throws Exception {
		new Db(0, "demo", "root", "admin");
		getAllTableInfo();
	}

	public static Object[][] getAllRowsNameAndType(String tablename)
			throws SQLException, Exception {

		String sql = "select * from " + tablename;
		ResultSet rs = Db.getRs(sql);

		ResultSetMetaData data = rs.getMetaData();
		Object[][] obj = new Object[data.getColumnCount()][2];

		for (int i = 1; i <= data.getColumnCount(); i++) {
			// 获得指定列的列名
			String columnName = data.getColumnName(i);

			// 获得指定列的数据类型名
			String columnTypeName = data.getColumnTypeName(i);
			obj[i - 1][0] = columnName;
			obj[i - 1][1] = columnTypeName;

			/*
			 * // 获得指定列的列值 String columnValue = rs.getString(i); // 获得指定列的数据类型
			 * int columnType = data.getColumnType(i); // 所在的Catalog名字 String
			 * catalogName = data.getCatalogName(i); // 对应数据类型的类 String
			 * columnClassName = data.getColumnClassName(i); // 在数据库中类型的最大字符个数
			 * int columnDisplaySize = data.getColumnDisplaySize(i); // 默认的列的标题
			 * String columnLabel = data.getColumnLabel(i); // 获得列的模式 String
			 * schemaName = data.getSchemaName(i); // 某列类型的精确度(类型的长度) int
			 * precision = data.getPrecision(i); // 小数点后的位数 int scale =
			 * data.getScale(i); // 获取某列对应的表名 String tableName =
			 * data.getTableName(i); // 是否自动递增 boolean isAutoInctement =
			 * data.isAutoIncrement(i); // 在数据库中是否为货币型 boolean isCurrency =
			 * data.isCurrency(i); // 是否为空 int isNullable = data.isNullable(i);
			 * // 是否为只读 boolean isReadOnly = data.isReadOnly(i); // 能否出现在where中
			 * boolean isSearchable = data.isSearchable(i);
			 * System.out.println(columnCount); System.out.println("获得列" + i +
			 * "的字段名称:" + columnName); System.out.println("获得列" + i + "的字段值:" +
			 * columnValue); System.out.println("获得列" + i + "的类型,返回SqlType中的编号:"
			 * + columnType); System.out.println("获得列" + i + "的数据类型名:" +
			 * columnTypeName); System.out.println("获得列" + i + "所在的Catalog名字:" +
			 * catalogName); System.out.println("获得列" + i + "对应数据类型的类:" +
			 * columnClassName); System.out.println("获得列" + i +
			 * "在数据库中类型的最大字符个数:" + columnDisplaySize); System.out.println("获得列"
			 * + i + "的默认的列的标题:" + columnLabel); System.out.println("获得列" + i +
			 * "的模式:" + schemaName); System.out.println("获得列" + i +
			 * "类型的精确度(类型的长度):" + precision); System.out.println("获得列" + i +
			 * "小数点后的位数:" + scale); System.out.println("获得列" + i + "对应的表名:" +
			 * tableName); System.out.println("获得列" + i + "是否自动递增:" +
			 * isAutoInctement); System.out.println("获得列" + i + "在数据库中是否为货币型:" +
			 * isCurrency); System.out.println("获得列" + i + "是否为空:" +
			 * isNullable); System.out.println("获得列" + i + "是否为只读:" +
			 * isReadOnly); System.out.println("获得列" + i + "能否出现在where中:" +
			 * isSearchable);
			 */
		}

		return obj;
	}

	public static List<String> getAllFiledInTableReturnList(String tableName,
			Boolean isFilter) throws Exception {

		List<String> strList = new ArrayList<String>();

		String[] str = getAllFiledInTable(tableName, isFilter);

		for (String string : str) {
			strList.add(string);
		}

		return strList;
	}

	/**
	 * 根据表名获取所有的字段名称
	 * 
	 * @param tableName
	 * @param isFilter
	 * @return
	 * @throws Exception
	 */
	public static String[] getAllFiledInTable(String tableName, Boolean isFilter)
			throws Exception {

		String[] fields = null;
		List<String> fieldsList = new ArrayList<String>();

		String sql = "select * from " + tableName;

		ResultSet rs = Db.getRs(sql);
		ResultSetMetaData meta = rs.getMetaData();

		if (!isFilter) {
			fields = new String[meta.getColumnCount()];

			for (int i = 0; i < meta.getColumnCount(); i++) {
				fields[i] = meta.getColumnName(i + 1);// 获取字段名
			}
		} else {
			int[] countColumn = new int[meta.getColumnCount()];
			double count = 0;
			while (rs.next()) {
				for (int i = 0; i < countColumn.length; i++) {
					try {
						rs.getObject(i + 1).toString().equals("");
					} catch (NullPointerException ex) {
						countColumn[i]++;
					}
				}
				count++;
			}

			for (int i = 0; i < meta.getColumnCount(); i++) {
				if (countColumn[i] / count < Config.columnNull / 100) {
					fieldsList.add(meta.getColumnName(i + 1));
				}
			}
			fields = new String[fieldsList.size()];
			fields = fieldsList.toArray(fields);
		}

		return fields;
	}

	/**
	 * 根据表名获取所有的字段数据
	 * 
	 * @param tableName
	 * @param isFilter
	 * @return
	 * @throws Exception
	 */
	public static Object[][] getAllFiledDataInTable(String tableName,
			Boolean isFilter) throws Exception {

		String sql = "select * from " + tableName;
		List<Object[]> rowList = new ArrayList<Object[]>();

		ResultSet rs = Db.getRs(sql);
		rs.last();
		int row = rs.getRow();

		int column = rs.getMetaData().getColumnCount();

		Object[][] obj = null;
		rs.first();
		if (!isFilter) {

			countRow = row;
			obj = new Object[row][column];

			int i = 0;

			do {
				for (int j = 0; j < column; j++) {
					obj[i][j] = rs.getObject(j + 1);
				}
				i++;
			} while (rs.next());
		} else {

			double count = 0;
			Object[] o = null;
			do {
				count = 0;

				o = new Object[column];

				for (int j = 0; j < column; j++) {
					try {
						o[j] = rs.getObject(j + 1);
						rs.getObject(j + 1).toString().equals("");
					} catch (NullPointerException ex) {
						count++;
					}
				}
				if (count / column < Config.rowNull / 100) {
					rowList.add(o);
				}

			} while (rs.next());

			countRow = rowList.size();
			obj = new Object[rowList.size()][column];

			for (int p = 0; p < rowList.size(); p++) {
				for (int z = 0; z < column; z++) {
					obj[p][z] = rowList.get(p)[z];
				}
			}

		}
		return obj;
	}

	/**
	 * 筛选数据 isFilter等于1则表示筛选重复数据 否则则表示去除不规范数据
	 * 
	 * @param tableName
	 * @param isFilter
	 * @return
	 * @throws Exception
	 */
	public static Object[][] getAllFiledDataInTable(String tableName,
			int isFilter, String fieldName, int up, int down) throws Exception {

		Object[][] obj = null;
		if (isFilter == 1) {// 筛选重复数据
			String sql = "select DISTINCT * from " + tableName;

			ResultSet rs = Db.getRs(sql);
			rs.last();
			int row = rs.getRow();

			int column = rs.getMetaData().getColumnCount();

			rs.first();
			countRow = row;
			obj = new Object[row][column];

			int i = 0;

			do {
				for (int j = 0; j < column; j++) {
					obj[i][j] = rs.getObject(j + 1);
				}
				i++;
			} while (rs.next());
		} else {// 去除不规范数据
			String sql = "select * from " + tableName + " where  " + fieldName
					+ " between " + up + " and " + down;
			ResultSet rs = Db.getRs(sql);
			rs.last();
			int row = rs.getRow();

			int column = rs.getMetaData().getColumnCount();

			rs.first();
			countRow = row;
			obj = new Object[row][column];

			int i = 0;

			do {
				for (int j = 0; j < column; j++) {
					obj[i][j] = rs.getObject(j + 1);
				}
				i++;
			} while (rs.next());
		}
		return obj;
	}

	/**
	 * 获取不重复查询字段
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getQueryField(String table) throws Exception {
		String[] all = getAllFiledInTable(table, false);
		List<String> list = getParField(table);
		String ss = "";
		for (int i = 0; i < all.length; i++) {
			if (!list.contains(all[i])) {
				ss += all[i] + ",";
			}
		}
		return ss.substring(0, ss.length() - 1);
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	private static List<String> getParField(String table) throws SQLException {
		List<String> strlist = new ArrayList<String>();
		ResultSet rs = Db.getConn().getMetaData().getPrimaryKeys("", "", table);

		while (rs.next()) {
			strlist.add(rs.getObject(4).toString());
		}

		return strlist;
	}

	/**
	 * 获取指定数据库和用户的所有表名
	 * 
	 * @param conn
	 *            连接数据库对象
	 * @param user
	 *            用户
	 * @param database
	 *            数据库名
	 * @return
	 */
	private static List<String> getAllTableNames(Connection conn) {
		List<String> tableNames = new ArrayList<String>();
		if (conn != null) {
			try {
				DatabaseMetaData meta = conn.getMetaData();
				ResultSet rs = meta.getTables(null, null, null,
						new String[] { "TABLE" });
				while (rs.next()) {
					tableNames.add(rs.getString(3));
					/*
					 * System.out.println("表所属用户名：" + rs.getString(2));
					 */
				}

			} catch (Exception e) {
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		}

		return tableNames;
	}

	/**
	 * 生成新表
	 * 
	 * @param list
	 * @param where
	 */
	public static boolean saveTable(List<Object[]> list, String where,
			String tableName) {
		String sql = "create table " + tableName + "(";

		for (Object[] objects : list) {
			sql += objects[0] + " " + objects[1] + ",";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";

		System.out.println(sql);
		if (Db.createTable(sql, tableName) >= 0) {
			return true;
		}
		return false;
	}

	public static boolean newTable(List<Object[]> list, String where,
			String tableName) {
		String sql = "create table " + tableName + "(";
		List<Object[]> arrlist = new ArrayList<Object[]>();
		for (Object[] objects : list) {
			sql += objects[0] + " " + objects[1] + ",";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		if (Db.createTable(sql, tableName) >= 0) {
			return true;
		}
		return false;
	}

	public static List<Student> getStudentList() {
		KmeansDao kd = new KmeansDao();
		return kd.findAll();
	}
}
