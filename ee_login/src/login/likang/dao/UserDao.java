package login.likang.dao;

import login.likang.domain.User;

public interface UserDao {
	public boolean creat(User user);

	public boolean update(User user);

	public boolean delete(User user);

	public boolean read(String username);

	public User loginAsk(String username, String password);
}
