package org.ladle.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.beans.jpa.Ville;
import org.ladle.dao.UserDao;

/**
 * Classe de gestion des données utilisateur et de leur persistence.
 *
 * @author Coyote
 * @see org.ladle.beans.User
 * @see org.ladle.dao.hibernate.impl.UserDaoImpl
 */
@Stateless
public class UserHandler {

  private static final Logger LOG = LogManager.getLogger(UserHandler.class);

  @EJB(name = "UserDaoImpl")
  private UserDao userDao;

  /* Création de la map de validation pour la jsp */
  private Map<String, Integer> validationList = new HashMap<>();

  private static final Integer ROLE_UTILISATEUR = 0;

  public UserHandler() {
    super();
  }

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
  public Map<String, Integer> addUser(User user) throws SQLDataException {

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
    /* cp */
    validationList.put("cpEmpty", testCpEmpty(user));
    validationList.put("cpValid", testCpValid(user));
    validationList.put("cp", testCp());
    /* ville id */
    validationList.put("villeIdEmpty", testVilleIdEmpty(user));
    validationList.put("villeIdValid", testVilleIdValid(user));
    validationList.put("villeId", testVilleId());
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
      Timestamp currentDate = new Timestamp(System.currentTimeMillis());
      user.setDateCompte(currentDate);

      // Ajout du SHA de validation du mail
      user.setEmailSHA(getEmailSHA());

      // Ajout de la date du mail de confirmation SHA
      user.setDateEmail(currentDate);

      // Affiche dans le log la date de création du compte et du mail
      SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy 'à' hh:mm:ss");
      String message = formatter.format(user.getDateCompte());
      LOG.debug("Date du compte : {}", message);
      message = formatter.format(user.getDateEmail());
      LOG.debug("Date du mail : {}", message);

      // Transfert du bean User vers le bean Utilisateur
      Utilisateur utilisateur = new Utilisateur(
          user.getVilleID(),
          user.getPseudo(),
          user.getGenre(),
          user.getNom(),
          user.getPrenom(),
          user.getEmail(),
          user.getMdpSecured(),
          user.getSalt(),
          ROLE_UTILISATEUR,
          user.getEmailSHA(),
          user.getDateEmail(),
          user.getDateCompte());

      // On ajoute l'utilisateur dans la bdd
      if (userDao.addUser(utilisateur)) {
        // Envoit le mail de validation du compte mail
        MailHandler.sendValidationMail(user);
      }
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
  private static Integer testPseudoEmpty(User user) {

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
  private static Integer testPseudoLength(User user) {

    /* Taille maximum du pseudo */
    final int MAX_LENGTH = 30;

    if (user.getPseudo().length() > MAX_LENGTH) {
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

    if (validationList.get("pseudoEmpty") == 1 && validationList.get("pseudoExist") == 1
        && validationList.get("pseudoLength") == 1 && validationList.get("pseudoNotEmail") == 1) {
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
  private static Integer testGenreEmpty(User user) {

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
  private static Integer testGenreValid(User user) {

    if ("Madame".equals(user.getGenre()) || "Monsieur".equals(user.getGenre())) {
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

    if (validationList.get("genreEmpty") == 1 && validationList.get("genreValid") == 1) {
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
  private static Integer testPrenomEmpty(User user) {

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
  private static Integer testPrenomLength(User user) {

    /* Taille maximum du prénom */
    final Integer MAX_LENGTH = 40;

    if (user.getPrenom().length() > MAX_LENGTH) {
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
    if (validationList.get("prenomEmpty") == 1 && validationList.get("prenomLength") == 1) {
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
  private static Integer testNomEmpty(User user) {

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
  private static Integer testNomLength(User user) {

    final int MAX_LENGTH = 40;
    if (user.getNom().length() > MAX_LENGTH) {
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
    if (validationList.get("nomEmpty") == 1 && validationList.get("nomLength") == 1) {
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
  private static Integer testEmailValid(User user) {

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

    if (validationList.get("emailExist") == 1 && validationList.get("emailValid") == 1) {
      return 1;
    }
    return 0;
  }

  /* === Fonctions cp === */

  /**
   * Test si le champ code postal est vide
   *
   * @param user
   * @return 0 : error / 1 : valid
   */
  private static Integer testCpEmpty(User user) {

    if (user.getCp().isEmpty()) {
      return 0;
    }
    return 1;
  }

  /**
   * Test si le code postal existe dans la bdd
   *
   * @param user
   * @return 0 : error / 1 : valid
   */
  public Integer testCpValid(User user) {

    if (userDao.isValidCp(user.getCp())) {
      return 1;
    }
    return 0;
  }

  /**
   * Test global du champ code postal
   *
   * @return 0 : error / 1 : valid
   */
  private Integer testCp() {

    if (validationList.get("cpEmpty") == 1 && validationList.get("cpValid") == 1) {
      return 1;
    }
    return 0;
  }

  /* === Fonctions ville === */

  /**
   * Test si le champ de sélection de la ville est vide ou à 0 (unselected)
   *
   * @param user
   * @return 0 : error / 1 : valid
   */
  private static Integer testVilleIdEmpty(User user) {

    if (user.getVilleID() == null || user.getVilleID() == 0) {
      return 0;
    }
    return 1;
  }

  /**
   * Test si la ville existe dans la bdd
   *
   * @param user
   * @return 0 : error / 1 : valid
   */
  private Integer testVilleIdValid(User user) {

    if (userDao.isValidVilleId(user.getVilleID())) {
      return 1;
    }
    return 0;
  }

  /**
   * Test global du champ ville
   *
   * @return 0 : error / 1 : valid
   */
  private Integer testVilleId() {

    if (validationList.get("villeIdEmpty") == 1 && validationList.get("villeIdValid") == 1) {
      return 1;
    }
    return 0;
  }

  public List<Ville> getVillesByCp(String cp) {

    return userDao.getVillesByCp(cp);
  }

  /* === Fonctions mdp === */

  /**
   * Test si le mdp a une taille valide. (8 à 40 caractères)
   *
   * @param user Classe des données utilisateur
   * @return 0 : error / 1 : valid
   * @see org.ladle.beans.User
   */
  private static Integer testMdpLength(User user) {

    final int MIN_LENGTH = 8;
    final int MAX_LENGTH = 40;

    if (user.getMdp().length() >= MIN_LENGTH && user.getMdp().length() <= MAX_LENGTH) {
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

    if (validationList.get("mdpLength") == 1 && validationList.get("mdpEquals") == 1) {
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
  private String getEmailSHA() throws SQLDataException {

    final int RDM_BYTES_SIZE = 32;
    String emailSHA = null;

    final int MAX_LOOP = 50;
    int antiLoop = 0;

    // Loop until emailSHA is unique in DB
    while (emailSHA == null && antiLoop < MAX_LOOP) {

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

      if (userDao.emailSHAExist(emailSHA)) {
        emailSHA = null;
      }

      antiLoop++;
    }

    if (antiLoop >= MAX_LOOP) {
      throw new SQLDataException("Error ! EmailSHA generation exceed 50 try");
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
  public boolean isLoginValidDepreciated(String login, String pwd) {

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
   * Demande à la DAO si le couple [(Pseudo ou Email) + mdp] existe dans la bdd.
   *
   * @param login : Pseudo ou Email
   * @param pwd   : Password
   * @return true : connexion valide <br>
   *         false : connexion invalide
   */
  public boolean isLoginValid(String login, String pwd) {

    Utilisateur utilisateur = getUtilisateurOnLogin(login);

    // Si un utilisateur existe avec ce login
    if (utilisateur != null) {
      byte[] salt = utilisateur.getSalt();
      LOG.debug("salt : {}", salt);

      String pwdEncrypted = PasswordHandler.getSecurePassword(pwd, salt);
      LOG.debug("pwdEncrypted = {}", pwdEncrypted);

      if (utilisateur.getMdp().equals(pwdEncrypted)) {
        return true;
      }

    }

    // Aucune correspondance de pseudo ou email dans la BDD
    return false;
  }

  /**
   * Renvoit un objet Utilisateur lié à un login + password.
   *
   * @param login
   * @param pwd
   * @return Un Utilisateur avec toutes les informations
   */
  public Utilisateur getUtilisateurOnLogin(String login) {

    return userDao.getUtilisateurByLogin(login);
  }

  public void updateUtilisateur(Utilisateur utilisateur) {

    userDao.updateUtilisateur(utilisateur);
  }

  /**
   * Renvoit un token aléatoire pour la connexion par cookie
   *
   * @return
   */
  public String generateTokenLogin() {

    String tokenLogin = null;
    final int BYTE_ARRAY_LENGTH = 32;

    try {
      // Create MessageDigest instance for SHA-256
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      SecureRandom secureRandom = new SecureRandom();
      byte[] randomBytes = new byte[BYTE_ARRAY_LENGTH];
      secureRandom.nextBytes(randomBytes);

      // Get the hash's bytes from secure random
      byte[] bytes = md.digest(randomBytes);

      tokenLogin = Hex.encodeHexString(bytes);

    } catch (NoSuchAlgorithmException e) {
      LOG.error("Error on generating tokenLogin", e);
    }
    return tokenLogin;
  }

  public boolean isValidTokenLogin(String login, String tokenLogin) {

    if (login == null || tokenLogin == null) {
      return false;
    }
    return userDao.isValidTokenLogin(login, tokenLogin);

  }

}
