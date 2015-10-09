package login.likang.service.imp;

import login.likang.dao.imp.UserDaoImp;
import login.likang.domain.User;
import login.likang.exception.UserExitsException;
import login.likang.service.LoginService;
import login.likang.utils.ServiceSafeUtils;

public class LoginServiceImp implements LoginService {
	private UserDaoImp ud;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * login.likang.service.imp.LoginService#regist(login.likang.domain.User)
	 */
	@Override
	public void regist(User us) throws UserExitsException {
		ud = new UserDaoImp();
		if (ud.read(us.getUsername())) {
			throw new UserExitsException();
		} else {
			us.setPassword(ServiceSafeUtils.passwdMd5(us.getPassword()));
			ud.creat(us);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see login.likang.service.imp.LoginService#login(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public User login(String username, String passwd) {
		ud = new UserDaoImp();
		String password = ServiceSafeUtils.passwdMd5(passwd);
		User se = ud.loginAsk(username, password);
		return se;
	}
}
