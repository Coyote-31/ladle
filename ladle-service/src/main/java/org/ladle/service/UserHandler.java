package org.ladle.service;

import java.util.HashMap;
import java.util.Map;

import org.ladle.beans.User;
import org.ladle.dao.UserDao;
import org.ladle.dao.hibernate.impl.UserDaoImpl;

public class UserHandler {
	
	private UserDao userDao;

	/* Constructeur */
	public UserHandler() {
		userDao = new UserDaoImpl();
	}
	
	/**
	 * Lance les tests et ajoute l'utilisateur à la BDD.
	 * 
	 * @return La map de validation du formulaire : </br>
	 * 			keys: pseudo/genre/prenom/nom/email/ville/mdp/mdp2 </br>
	 * 			0 : vide et optionnel / 1 : valide / 2 : erreur		
	 */
	public Map<String, Integer> addUser(User user) {
		
		/* Création de la map de validation pour la jsp */
		Map<String, Integer> validationList = new HashMap<>();
		
		/* Tests des données et ajout des codes dans la liste de validation */
		validationList.put("pseudo", testPseudo());
		
		
		/* Si tout est valide on ajoute dans la bdd */
		userDao.addUser(user);

		return validationList;
	}

	private Integer testPseudo() {
		
		Integer result = 1; // TODO
		
		return result;
	}
}
