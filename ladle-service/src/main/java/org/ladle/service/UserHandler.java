package org.ladle.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.dao.UserDao;

/**
 * Classe de gestion des données utilisateur et de leur persistence.
 *
 * @author Coyote
 * @see org.ladle.beans.User
 * @see org.ladle.dao.hibernate.impl.UserDaoImpl
 */
@Stateful
public class UserHandler {

  private static final Logger LOG = LogManager.getLogger(UserHandler.class);

  @EJB(name = "UserDaoImpl")
  private UserDao userDao;
  /* Création de la map de validation pour la jsp */
  private Map<String, Integer> validationList = new HashMap<>();

  public UserHandler() {
    super();
  }

  private static final Integer ROLE_UTILISATEUR = 0;

  /**
   * Lance les tests et ajoute l'utilisateur à la BDD.
   *
   * @return Une Map [String key, Integer value] de validation du formulaire
   *         :</br>
   *         <b>key = </b> <code>pseudo/pseudoEmpty/pseudoExist/pseudoLength</br>
   * 				   genre/genreEmpty/genreValid</br>
   * 				   prenom/prenomEmpty/prenomLength</br>
   * 				   nom/nomEmpty/nomLength</br>
   * 				   email/emailExist/emailValid</br>
   * 				   ville</br>
   * 				   mdp/mdpLength/mdpEquals</br></code> <b>value = </b>
   *         <code>0 : erreur / 1 : valide</code>
   */
  public Map<String, Integer> addUser(User user) {

    /* Tests des données et ajout des codes dans la liste de validation */
    /* pseudo */
    validationList.put("pseudoEmpty", testPseudoEmpty(user));
    validationList.put("pseudoExist", testPseudoExist(user));
    validationList.put("pseudoLength", testPseudoLength(user));
    validationList.put("pseudoNotEmail", testPseudoNotEmail(user));
    validationList.put("pseudo", testPseudo());
    /* genre */
    validationList.put("genreEmpty", testGenreEmpty(user));
    validationList.put("genreValid", testGenreValid(user));
    validationList.put("genre", testGenre());
    /* prenom */
    validationList.put("prenomEmpty", testPrenomEmpty(user));
    validationList.put("prenomLength", testPrenomLength(user));
    validationList.put("prenom", testPrenom());
    /* nom */
    validationList.put("nomEmpty", testNomEmpty(user));
    validationList.put("nomLength", testNomLength(user));
    validationList.put("nom", testNom());
    /* email */
    validationList.put("emailExist", testEmailExist(user));
    validationList.put("emailValid", testEmailValid(user));
    validationList.put("email", testEmail());
    /* ville */
    /* mdp */
    validationList.put("mdpLength", testMdpLength(user));
    validationList.put("mdpEquals", testMdpEquals(user));
    validationList.put("mdp", testMdp());

    if (!validationList.containsValue(0)) {

      // Si tout est valide:

      // Création du sel
      try {
        user.setSalt(PasswordHandler.getSalt());
        LOG.debug("user.salt : {}", user.getSalt());

      } catch (NoSuchAlgorithmException e) {
        LOG.error("Error generating salt", e);
      }
      // Création du mdp sécurisé (SHA-256)
      user.setMdpSecured(PasswordHandler.getSecurePassword(user));

      // Ajout de la date de création du compte
      ZonedDateTime currentDate = ZonedDateTime.now();
      user.setDateCompte(currentDate);

      // Ajout du SHA de validation du mail
      user.setEmailSHA(getEmailSHA());

      // Ajout de la date du mail de confirmation SHA
      user.setDateEmail(currentDate);

      // Affiche dans le log la date de création du compte et du mail
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'à' hh:mm:ss");
      String message = currentDate.format(formatter);
      LOG.info("Date du compte : {}", message);

      // Transfert du bean User vers le bean Utilisateur

      // TODO VilleDAO
      user.setVilleID(666);

      Utilisateur utilisateur = new Utilisateur(user.getVilleID(), user.getPseudo(), user.getGenre(), user.getNom(),
          user.getPrenom(), user.getEmail(), user.getMdpSecured(), user.getSalt(), ROLE_UTILISATEUR, user.getEmailSHA(),
          user.getDateEmail(), user.getDateCompte());

      // On ajoute l'utilisateur dans la bdd
      userDao.addUser(utilisateur);
      LOG.info("{} ajouté(e) à la bdd.", user.getPseudo());

      // Envoit le mail de validation du compte mail
      MailHandler.sendValidationMail(user);
    }

    return validationList;
  }

  /* === Fonctions pseudo === */

  /**
   * Test si le champ pseudo est vide.
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
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
   * @see org.ladle.beans.User
   */
  private Integer testPseudoExist(User user) {

    if (userDao.containsPseudo(user.getPseudo())) {
      return 0;
    }
    return 1;
  }

  /**
   * Test la longueur du pseudo. Max = 30.
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
   * @see org.ladle.beans.User
   */
  private Integer testPseudoLength(User user) {

    /* Taille maximum du pseudo */
    int maxLength = 30;

    if (user.getPseudo().length() > maxLength) {
      return 0;
    }
    return 1;
  }

  /**
   * Test si le pseudo ressemble à un email.
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
   * @see org.ladle.beans.User
   */
  private static Integer testPseudoNotEmail(User user) {

    // Si le pseudo ne ressemble pas à un email renvoit 1 valid
    if (!EmailValidator.getInstance().isValid(user.getPseudo())) {
      return 1;
    }
    return 0;
  }

  /**
   * Test global du champ pseudo.
   *
   * @return 0 : error / 1 : valid
   */
  private Integer testPseudo() {

    if ((validationList.get("pseudoEmpty") == 1) && (validationList.get("pseudoExist") == 1)
        && (validationList.get("pseudoLength") == 1) && (validationList.get("pseudoNotEmail") == 1)) {
      return 1;
    }
    return 0;
  }

  /* === Fonctions genre === */

  /**
   * Test si le champ genre est vide.
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
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
   * @see org.ladle.beans.User
   */
  private Integer testGenreValid(User user) {

    if (user.getGenre().equals("Madame") || user.getGenre().equals("Monsieur")) {
      return 1;
    }
    return 0;
  }

  /**
   * Test global du champ genre.
   *
   * @return 0 : error / 1 : valid
   */
  private Integer testGenre() {

    if ((validationList.get("genreEmpty") == 1) && (validationList.get("genreValid") == 1)) {
      return 1;
    }
    return 0;
  }

  /* === Fonctions prenom === */

  /**
   * Test si le champ prenom est vide.
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
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
   * Test global du champ prenom.
   *
   * @return 0 : error / 1 : valid
   */
  private Integer testPrenom() {
    if ((validationList.get("prenomEmpty") == 1) && (validationList.get("prenomLength") == 1)) {
      return 1;
    }
    return 0;
  }

  /* === Fonctions nom === */

  /**
   * Test si le champ nom est vide.
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
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
   * @see org.ladle.beans.User
   */
  private Integer testNomLength(User user) {

    if (user.getNom().length() > 40) {
      return 0;
    }
    return 1;
  }

  /**
   * Test global du champ nom.
   *
   * @return 0 : error / 1 : valid
   */
  private Integer testNom() {
    if ((validationList.get("nomEmpty") == 1) && (validationList.get("nomLength") == 1)) {
      return 1;
    }
    return 0;
  }

  /* === Fonctions email === */

  /**
   * Test si le mail existe déjà dans la bdd.
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
   * @see org.ladle.beans.User
   */
  private Integer testEmailExist(User user) {

    if (userDao.containsEmail(user.getEmail())) {
      return 0;
    }
    return 1;
  }

  /**
   * Test si le mail a un format valide.
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
   * @see org.ladle.beans.User
   * @see org.apache.commons.validator.routines.EmailValidator
   */
  private Integer testEmailValid(User user) {

    if (EmailValidator.getInstance().isValid(user.getEmail())) {
      return 1;
    }
    return 0;
  }

  /**
   * Test global du champ email.
   *
   * @return 0 : error / 1 : valid
   */
  private Integer testEmail() {

    if ((validationList.get("emailExist") == 1) && (validationList.get("emailValid") == 1)) {
      return 1;
    }
    return 0;
  }

  /* === Fonctions mdp === */

  /**
   * Test si le mdp a une taille valide. (8 à 40 caractères)
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
   * @see org.ladle.beans.User
   */
  private Integer testMdpLength(User user) {

    if ((user.getMdp().length() >= 8) && (user.getMdp().length() <= 40)) {
      return 1;
    }
    return 0;
  }

  /**
   * Test si les mdp sont identiques (mdp == mdp2)
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
   * @see org.ladle.beans.User
   */
  private static Integer testMdpEquals(User user) {

    if (user.getMdp().equals(user.getMdp2())) {
      return 1;
    }
    return 0;
  }

  /**
   * Test global des champs mdp.
   *
   * @return 0 : error / 1 : valid
   */
  private Integer testMdp() {

    if ((validationList.get("mdpLength") == 1) && (validationList.get("mdpEquals") == 1)) {
      return 1;
    }
    return 0;
  }

  /**
   * Renvoit le code SHA-256 pour la validation du mail.
   *
   * @param user
   * @return String(64) le code SHA de validation du mail
   */
  private static String getEmailSHA() {

    final int RDM_BYTES_SIZE = 32;
    String emailSHA = null;

    try {
      // Create MessageDigest instance for SHA-256
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

      byte[] rdmBytes = new byte[RDM_BYTES_SIZE];
      sr.nextBytes(rdmBytes);

      // Get the hash's bytes
      byte[] bytes = md.digest(rdmBytes);

      emailSHA = Hex.encodeHexString(bytes);

    } catch (NoSuchAlgorithmException e) {
      LOG.error("Erreur de génération du SHA du mail", e);
    }
    return emailSHA;
  }

  /**
   * Test le SHA du mail et le supprime de la bdd.
   *
   * @param mailSHA
   * @return
   */
  public boolean emailValidation(String emailSHA) {

    if (userDao.emailSHAExist(emailSHA)) {
      userDao.emailSHADelete(emailSHA);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Demande à la DAO si le couple [(Pseudo ou Email) + mdp] existe dans la bdd.
   *
   * @param login : Pseudo ou Email
   * @param pwd   : Password
   * @return true : connexion valide <br>
   *         false : connexion invalide
   */
  public boolean isLoginValid(String login, String pwd) {

    // Test le mdp sécurisé par pseudo
    byte[] saltByPseudo = userDao.getSaltByPseudo(login);
    LOG.debug("saltByPseudo : {}", saltByPseudo);

    if (saltByPseudo != null) {
      String pwdEncryptedByPseudo = PasswordHandler.getSecurePassword(pwd, saltByPseudo);
      LOG.debug("pwdEncryptedByPseudo = {}", pwdEncryptedByPseudo);
      return userDao.isLoginByPseudoValid(login, pwdEncryptedByPseudo);
    }

    // Test le mdp sécurisé par email
    byte[] saltByEmail = userDao.getSaltByEmail(login);
    LOG.debug("saltByEmail : {}", saltByEmail);

    if (saltByEmail != null) {
      String pwdEncryptedByEmail = PasswordHandler.getSecurePassword(pwd, saltByEmail);
      LOG.debug("pwdEncryptedByEmail = {}", pwdEncryptedByEmail);
      return userDao.isLoginByEmailValid(login, pwdEncryptedByEmail);
    }

    // Aucune correspondance de pseudo ou email dans la BDD
    return false;
  }

  /**
   * Renvoit un objet User lié à un login + password.
   *
   * @param login
   * @param pwd
   * @return Un User avec toutes les informations
   */
  public User getUserOnLogin(String login, String pwd) {

    User user = new User();
    user.setPseudo("TEST");

    return user;
  }

}
