package com.wml.service;

import com.wml.dao.StudentDao;
import com.wml.domain.StudentBean;

public class UpdateService {
	public static void updateStudent(StudentBean studentBean) {
		StudentDao studentDao = new StudentDao();
		studentDao.updateStudent(studentBean);
	}
}
