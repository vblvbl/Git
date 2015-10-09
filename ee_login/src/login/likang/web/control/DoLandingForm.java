package login.likang.web.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.likang.domain.User;
import login.likang.service.imp.LoginServiceImp;


public class DoLandingForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("TxtUserName");
		String password=request.getParameter("TxtPassword");
		LoginServiceImp ls=new LoginServiceImp();
		User usr=ls.login(username, password);
		if(usr!=null){
			request.getSession().setAttribute("user",usr);
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
		request.setAttribute("loginWrong", "用户名或者密码不正确");
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
