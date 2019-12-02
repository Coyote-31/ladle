package org.ladle.dao;

import javax.ejb.Local;

import org.ladle.beans.User;

@Local
public interface UserDao {
	
	public void addUser(User user);
	public boolean containsPseudo(String pseudo);

}
