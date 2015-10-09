package login.likang.web.UI;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.misc.BASE64Encoder;

public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String tid = TokenProcess.getInstance().getTokenId();
		request.getSession().setAttribute("tid", tid);
		request.getRequestDispatcher("/WEB-INF/jsp/regist.jsp").forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

class TokenProcess {
	private static final TokenProcess tp = new TokenProcess();

	private TokenProcess() {
	}

	public static TokenProcess getInstance() {
		return tp;

	}

	public String getTokenId() {
		String tempId = System.currentTimeMillis() + new Random().nextInt()
				+ "";
		byte[] tempbyte = null;
		try {
			MessageDigest ms = MessageDigest.getInstance("md5");
			tempbyte = ms.digest(tempId.getBytes());

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		BASE64Encoder base = new BASE64Encoder();
		String tokenId = base.encode(tempbyte);
		System.out.println(tokenId);
		return tokenId;
	}
}