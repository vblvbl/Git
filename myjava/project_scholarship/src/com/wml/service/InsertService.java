package com.wml.service;

import com.wml.dao.StudentDao;
import com.wml.domain.StudentBean;

public class InsertService {
	public static void insertStudent(StudentBean studentBean) {
		StudentDao studentDao = new StudentDao();
		studentDao.insertStudent(studentBean);
	}
}
