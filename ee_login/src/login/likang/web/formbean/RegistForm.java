package login.likang.web.formbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class RegistForm {
	private String username;
	private String password1;
	private String password2;
	private String email;
	private String nickname;
	private String birthday;
	private String yanzhengma;
	public String devideYZM;
	private Map<String, String> errorMap = new HashMap<String, String>();

	public RegistForm() {

	}

	public String getYanzhengma() {
		return yanzhengma;
	}

	public void setYanzhengma(String yanzhengma) {
		this.yanzhengma = yanzhengma;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public boolean validate() {
		boolean flag = true;
		if (this.username == null || this.username.trim().equals("")) {
			flag = false;
			errorMap.put("username", "用户名不能为空");
		} else {
			if (!this.username.matches("[(A-Za-z0-9)]{5,20}")) {
				flag = false;
				errorMap.put("username", "用户名未由7-20数字或者字母组成");
			}
		}

		if (this.password1 == null || this.password1.trim().equals("")) {
			flag = false;
			errorMap.put("password1", "密码不能为空");
		} else {
			if (!this.password1.matches("[(A-Za-z0-9)]{7,20}")) {
				flag = false;
				errorMap.put("password1", "密码未由7-20数字或者字母组成");
			}
		}

		if (this.password2 == null || this.password2.trim().equals("")) {
			flag = false;
			errorMap.put("password2", "重复密码不能为空");
		} else {
			if (!this.password1.equals(this.password2)) {
				flag = false;
				errorMap.put("password2", "两次密码不一致");
			}
		}

		if (this.email == null || this.email.trim().equals("")) {
			flag = false;
			errorMap.put("email", "邮箱不能为空");
		} else {
			if (!this.email
					.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {
				flag = false;
				errorMap.put("email", "邮箱格式有误");
			}
		}
		if (this.nickname == null || this.nickname.trim().equals("")) {
			flag = false;
			errorMap.put("nickname", "昵称不能为空");
		} else {
			if (!this.nickname.matches("[(\\u4e00-\\u9fa5)]{2,10}")) {
				flag = false;
				errorMap.put("nickname", "昵称格式有误");
			}
		}
		if (!(this.birthday == null) && !this.birthday.trim().equals("")) {
			try {
				DateLocaleConverter dc = new DateLocaleConverter();
				dc.convert(this.birthday, "yyyy-MM-dd");
			} catch (Exception e) {
				flag = false;
				errorMap.put("birthday", "出生日期格式不正确");
			}
		}

		if (this.yanzhengma == null || this.yanzhengma.trim().equals("")) {
			flag = false;
			errorMap.put("yanzhengma", "请输入验证码");
		} else {
			if (!this.yanzhengma.equals(devideYZM)) {
				flag = false;
				errorMap.put("yanzhengma", "请正确输入图中验证码");
			}
		}

		return flag;

	}
}
