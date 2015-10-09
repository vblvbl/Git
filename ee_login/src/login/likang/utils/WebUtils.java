package login.likang.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import login.likang.web.formbean.RegistForm;

public class WebUtils {
	public static <T> T form2bean2(HttpServletRequest request, Class<T> clazz) {
		T tt = null;
		try {
			tt = clazz.newInstance();
			Enumeration em = request.getParameterNames();
			while (em.hasMoreElements()) {
				String keyname = (String) em.nextElement();
				String value = request.getParameter(keyname);
				Field fd = ((Class<T>) tt).getField(keyname);
				fd.set(tt, value);
			}
		} catch (Exception e) {
			System.out.println("form2bean2异常");
		}
		return tt;
	}

	public static RegistForm form2bean(HttpServletRequest req) {
		RegistForm rf = new RegistForm();
		Enumeration em = req.getParameterNames();
		while (em.hasMoreElements()) {
			String keyname = (String) em.nextElement();// username //password //
			String value = req.getParameter(keyname);// a122726 //lk123
			if (keyname.equals("username")) {
				rf.setUsername(value);
			} else if (keyname.equals("password1")) {
				rf.setPassword1(value);
			} else if (keyname.equals("password2")) {
				rf.setPassword2(value);
			} else if (keyname.equals("email")) {
				rf.setEmail(value);
			} else if (keyname.equals("nickname")) {
				rf.setNickname(value);
			} else if (keyname.equals("birthday")) {
				rf.setBirthday(value);
			} else if (keyname.equals("yanzhengma")) {
				rf.setYanzhengma(value);
			}
		}
		return rf;
	}

	public static Date stringtoDate(String date) {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		try {
			birthday = sm.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return birthday;
	}
}
