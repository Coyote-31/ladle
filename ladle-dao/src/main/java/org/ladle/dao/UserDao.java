package org.ladle.dao;

import org.ladle.beans.User;

public interface UserDao {
	
	public void addUser(User user);
	public boolean containsPseudo(String pseudo);

}
