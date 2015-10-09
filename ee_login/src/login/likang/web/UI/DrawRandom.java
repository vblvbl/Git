package login.likang.web.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Session;

public class DrawRandom extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 100;
	private final int HEIGHT = 24;
	private static String word = "abcdefghijklmnopqrstuvwxyz123456789";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BufferedImage bf = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics gp = bf.getGraphics();
		setBacground(gp);
		setBorder(gp);
		setLine(gp);
		String yanzheng = setString((Graphics2D) gp);
		request.getSession().setAttribute("yanzhengma", yanzheng);
		response.setContentType("image/jpeg");
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		ImageIO.write(bf, "jpg", response.getOutputStream());
		
	}

	private void setBacground(Graphics gp) {
		gp.setColor(Color.WHITE);
		gp.fillRect(0, 0, WIDTH, HEIGHT);
	}

	private void setBorder(Graphics gp) {
		gp.setColor(Color.BLACK);
		gp.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);

	}

	private void setLine(Graphics gp) {
		gp.setColor(Color.GREEN);
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		for (int i = 0; i < 5; i++) {
			x1 = new Random().nextInt(WIDTH - 1);
			y1 = new Random().nextInt(HEIGHT - 1);
			x2 = new Random().nextInt(WIDTH - 1);
			y2 = new Random().nextInt(HEIGHT - 1);
			gp.drawLine(x1, y1, x2, y2);
		}

	}

	private String setString(Graphics2D gp) {
		gp.setColor(Color.ORANGE);
		gp.setFont(new Font("宋体", Font.BOLD, 19));
		StringBuilder sb = new StringBuilder();
		int x = 10;
		for (int i = 0; i < 4; i++) {
			int dushu = new Random().nextInt() % 30;
			String randomWord = word
					.charAt(new Random().nextInt(word.length())) + "";
			sb.append(randomWord);
			gp.rotate(dushu * Math.PI / 180, x, 19);
			gp.drawString(randomWord, x, 19);
			gp.rotate(-dushu * Math.PI / 180, x, 19);
			x += 20;
		}
		return sb.toString();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
