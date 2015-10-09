package com.wml.service;

import com.wml.dao.StudentDao;

public class DeleteService {
	public static boolean deleteStudent(String num) {
		StudentDao studentDao = new StudentDao();
		return studentDao.deleteSudent(num);
	}
}
