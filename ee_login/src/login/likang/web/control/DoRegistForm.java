package login.likang.web.control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.likang.domain.User;
import login.likang.exception.UserExitsException;
import login.likang.service.imp.LoginServiceImp;
import login.likang.utils.WebUtils;
import login.likang.web.formbean.RegistForm;

public class DoRegistForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 获取session中随机数的值
		String yanzhengma = (String) request.getSession(false).getAttribute(
				"yanzhengma");

		// 把表单提交的数据封装到javabean中并返回该bean对象
		RegistForm rf = WebUtils.form2bean(request);
		// RegistForm rf = WebUtils.form2bean2(request, RegistForm.class);
		// 验证提交的数据是否符合要求
		rf.devideYZM = yanzhengma;
		boolean fl = rf.validate();
		if (!fl) {
			request.getSession().setAttribute("form", rf);
			request.getRequestDispatcher("/WEB-INF/jsp/regist.jsp").forward(
					request, response);
		} else {
			boolean flag = getFlag(request);

			if (flag == false) {
				request.setAttribute("formDouble", "请勿重复提交表单");
				request.getRequestDispatcher("/WEB-INF/jsp/regist.jsp")
						.forward(request, response);
			} else {
				/**
				 * 1.对表单字段合法性校验 2.校验失败跳回表单校验消息 3.校验成功，调用service处理注册请求
				 * 4.service处理不成功： a.全局错误界面 b.跳回到注册页面 显示已存在 5.service处理成功：
				 */

				request.getSession(false).removeAttribute("tid");

				// String的birthday转化成Date类型的birthday

				User us = new User(rf.getUsername(), rf.getPassword1(),
						rf.getEmail(), rf.getNickname(),
						WebUtils.stringtoDate(rf.getBirthday()));

				LoginServiceImp ls = new LoginServiceImp();
				try {
					ls.regist(us);
				} catch (UserExitsException e) {
					request.setAttribute("userHad", "用户已存在");
					request.getRequestDispatcher("/WEB-INF/jsp/regist.jsp")
							.forward(request, response);
				} catch (Exception e) {
					request.setAttribute("error", e.getMessage());
					request.getRequestDispatcher("/WEB-INF/jsp/error.jsp")
							.forward(request, response);
				}
				request.setAttribute("message","注册成功 <a href='"+request.getContextPath()+"/index.jsp'>点我返回</a>");
				request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean getFlag(HttpServletRequest request) {
		String ctosTid = request.getParameter("passtid");
		String stocTid = (String) request.getSession(false).getAttribute("tid");
		if (ctosTid == null) {
			return false;
		}
		if (stocTid == null) {
			return false;
		}
		if (!ctosTid.equals(stocTid)) {
			return false;
		}
		return true;
	}
}
