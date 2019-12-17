package org.ladle.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;
import org.ladle.dao.UserDao;

/**
 * Classe de gestion des données utilisateur et de leur persistence.
 * 
 * @author Coyote
 * 
 * @see org.ladle.beans.User
 * @see org.ladle.dao.hibernate.impl.UserDaoImpl
 */
@Stateful
public class UserHandler {
	
	private static final Logger LOG = LogManager.getLogger(UserHandler.class);
	
	@EJB(name = "UserDaoImpl")
	private UserDao userDao;
	
	/**
	 * Lance les tests et ajoute l'utilisateur à la BDD.
	 * 
	 * @return La map de validation du formulaire :</br>
	 * 			<b>keys = </b>
	 * 			<code>pseudoEmpty/pseudoExist/pseudoLength</br>
	 * 				   genreEmpty/genreValid</br>
	 * 				   prenomEmpty/prenomLength</br>
	 * 				   nomEmpty/nomLength</br>
	 * 				   emailExist/emailValid</br>
	 * 				   ville/mdp/mdp2</br></code>
	 * 			<b>value = </b> <code>0 : erreur / 1 : valide</code>
	 */
	public Map<String, Integer> addUser(User user) {
		
		/* Création de la map de validation pour la jsp */
		Map<String, Integer> validationList = new HashMap<>();
		
		/* Tests des données et ajout des codes dans la liste de validation */
		/* pseudo */
		validationList.put("pseudoEmpty", testPseudoEmpty(user));
		validationList.put("pseudoExist", testPseudoExist(user));
		validationList.put("pseudoLength", testPseudoLength(user));
		/* genre */
		validationList.put("genreEmpty", testGenreEmpty(user));
		validationList.put("genreValid", testGenreValid(user));
		/* prenom */
		validationList.put("prenomEmpty", testPrenomEmpty(user));
		validationList.put("prenomLength", testPrenomLength(user));
		/* nom */
		validationList.put("nomEmpty", testNomEmpty(user));
		validationList.put("nomLength", testNomLength(user));
		/* email */
		validationList.put("emailExist", testEmailExist(user));
		validationList.put("emailValid", testEmailValid(user));
		/* ville */
		/* mdp */
		
		if (!validationList.containsValue(0))  {
			
			/* Si tout est valide on ajoute dans la bdd */
			userDao.addUser(user);
			LOG.info("%s ajouté(e) à la bdd.", user.getPseudo());
		}

		return validationList;
	}


	/**
	 * Test si le champ pseudo est vide.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testPseudoEmpty(User user) {
		
		if (user.getPseudo().isEmpty()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * Test si le Pseudo existe déjà dans la bdd.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testPseudoExist(User user) {
		
		if(userDao.containsPseudo(user.getPseudo())) {
			return 0;
		}		
		return 1;
	}
	
	/**
	 * Test la longueur du pseudo. Max = 30.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testPseudoLength(User user) {
		
		/* Taille maximum du pseudo	*/
		int maxLength = 30;
		
		if (user.getPseudo().length() > maxLength) {		
			return 0;
		}		
		return 1;
	}
	
	/**
	 * Test si le champ genre est vide.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testGenreEmpty(User user) {

		if (user.getGenre().isEmpty()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * Test si le champ genre est vide.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testGenreValid(User user) {

		if (user.getGenre().equals("Madame") || user.getGenre().equals("Monsieur")) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * Test si le champ prenom est vide.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testPrenomEmpty(User user) {
		
		if (user.getPrenom().isEmpty()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * Test la longeur du prénom. (max = 40)
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testPrenomLength(User user) {
		
		/* Taille maximum du prénom */
		Integer maxLength = 40;
		
		if (user.getPrenom().length() > maxLength) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * Test si le champ nom est vide.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testNomEmpty(User user) {
		
		if (user.getNom().isEmpty()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * Test la longueur du nom. (max = 40)
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testNomLength(User user) {
		
		if (user.getNom().length() > 40) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * Test si le mail existe déjà dans la bdd.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 */
	private Integer testEmailExist(User user) {
	
		if(userDao.containsEmail(user.getEmail())) {			
			return 0;
		}		
		return 1;
	}
	
	/**
	 * Test si le mail a un format valide.
	 * 
	 * @param user Classe des données utilisateur
	 * @return 0 : error / 1 : valid
	 * 
	 * @see org.ladle.beans.User
	 * @see org.apache.commons.validator.routines.EmailValidator
	 */
	private Integer testEmailValid(User user) {
		
		if(EmailValidator.getInstance().isValid(user.getEmail())) {		
			return 1;
		}		
		return 0;
	}
	
}
