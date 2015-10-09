package com.wml.service;

import java.util.List;

import com.wml.dao.StudentDao;
import com.wml.domain.StudentBean;

public class FindService {
	public static Object[][] getStudentInfo(String key, String value) {
		StudentDao studentDao = new StudentDao();
		List<StudentBean> list = studentDao.findAll(key, value);
		Object[][] obj = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < 6; j++) {
				switch (j) {
				case 0:
					obj[i][j] = list.get(i).getNum();
					break;
				case 1:
					obj[i][j] = list.get(i).getPasswd();
					break;
				case 2:
					obj[i][j] = list.get(i).getName();
					break;
				case 3:
					obj[i][j] = list.get(i).getInSchool();
					break;
				case 4:
					obj[i][j] = list.get(i).getAcademy();
					break;
				case 5:
					obj[i][j] = list.get(i).getState();
					break;

				}
			}
		}
		return obj;

	}
	public static Object[][] getStudentInfo() {
		StudentDao studentDao = new StudentDao();
		List<StudentBean> list = studentDao.findAll();
		Object[][] obj = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < 6; j++) {
				switch (j) {
				case 0:
					obj[i][j] = list.get(i).getNum();
					break;
				case 1:
					obj[i][j] = list.get(i).getPasswd();
					break;
				case 2:
					obj[i][j] = list.get(i).getName();
					break;
				case 3:
					obj[i][j] = list.get(i).getInSchool();
					break;
				case 4:
					obj[i][j] = list.get(i).getAcademy();
					break;
				case 5:
					obj[i][j] = list.get(i).getState();
					break;

				}
			}
		}
		return obj;

	}
}
