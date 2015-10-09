package login.likang.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class ServiceSafeUtils {
	public static String passwdMd5(String passwd) {
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("md5");
			byte[] temp = md.digest(passwd.getBytes());
			BASE64Encoder bs = new BASE64Encoder();
			return bs.encode(temp);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}
}
