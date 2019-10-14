package org.ladle.service;

import org.ladle.beans.User;
import org.ladle.dao.UserDao;
import org.ladle.dao.hibernate.impl.UserDaoImpl;

public class UserHandler {
	
	private User user;
	private UserDao userDao;

	/* Constructeur */
	public UserHandler(User user) {
		this.user = user;
		userDao = new UserDaoImpl(user);
	}
	
	/* Ajoute un utilisateur à la BDD */
	public void addUser() {
		
		userDao.addUser();

	}

}
