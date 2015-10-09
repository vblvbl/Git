package login.likang.web.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.tools.internal.ws.processor.model.Request;

public class RegistOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs=request.getSession();
		if(hs!=null){
			hs.removeAttribute("user");
		}
		request.setAttribute("message","用户已经成功注销,3秒后自动跳转 <meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/index.jsp'>");
	request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
