package com.wml.service;

import com.wml.dao.AdmDao;
import com.wml.dao.StudentDao;
import com.wml.domain.AdmBean;
import com.wml.domain.StudentBean;

public class LoginService {
	public static String getStudentLogin(StudentBean sb) {
		StudentDao sd = new StudentDao();
		return sd.CanLogin(sb)[0];
	}

	public static String giveStudentState(StudentBean sb) {
		StudentDao sd = new StudentDao();
		return sd.CanLogin(sb)[1];
	}

	public static boolean getAdmLogin(AdmBean sb) {
		AdmDao admDao = new AdmDao();
		return admDao.CanLogin(sb);
	}
}
