package org.ladle.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;
import org.ladle.dao.UserDao;

@Stateful
public class UserHandler {
	
	private static final Logger LOG = LogManager.getLogger(UserHandler.class);
	
	//@Inject @Named("UserDaoImpl")O
	@EJB(name = "UserDaoImpl")
	private UserDao userDao;
	
	//private UserDao userDao = new UserDaoImpl();
	
	/**
	 * Lance les tests et ajoute l'utilisateur à la BDD.
	 * 
	 * @return La map de validation du formulaire :</br>
	 * 			keys = form/pseudo/genre/prenom/nom/email/ville/mdp/mdp2</br>
	 * 			value = 0 : erreur / 1 : valide
	 */
	public Map<String, Integer> addUser(User user) {
		
		/* Création de la map de validation pour la jsp */
		Map<String, Integer> validationList = new HashMap<>();
		
		/* Tests des données et ajout des codes dans la liste de validation */
		validationList.put("pseudo", testPseudo(user));
		
		if (!validationList.containsValue(0))  {
			
			/* Si tout est valide on ajoute dans la bdd */
			userDao.addUser(user);
			LOG.info("%s ajouté(e) à la bdd.", user.getPseudo());
		}

		return validationList;
	}

	private Integer testPseudo(User user) {
		
		if(userDao.containsPseudo(user.getPseudo())) {
			return 0;
		}
		
		return 1;
	}
}
