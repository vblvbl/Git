package login.likang.service;

import login.likang.domain.User;
import login.likang.exception.UserExitsException;

public interface LoginService {

	void regist(User us) throws UserExitsException;

	User login(String username, String passwd);

}