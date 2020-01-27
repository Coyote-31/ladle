package org.ladle.dao;

import javax.ejb.Local;

import org.ladle.beans.User;

@Local
public interface UserDao {
	
	public void addUser(User user);
	public boolean containsPseudo(String pseudo);
	public boolean containsEmail(String email);
	public boolean emailSHAExist(String emailSHA);
	public void emailSHADelete(String emailSHA);

}
