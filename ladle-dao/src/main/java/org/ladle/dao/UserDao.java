package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Utilisateur;
import org.ladle.beans.jpa.Ville;

@Local
public interface UserDao {

  boolean addUser(Utilisateur utilisateur);

  boolean containsPseudo(String pseudo);

  boolean containsEmail(String email);

  boolean emailSHAExist(String emailSHA);

  void emailSHADelete(String emailSHA);

  /**
   * Vérifie dans la bdd si le couple <code>pseudo + mdp</code> existe.
   *
   * @param pseudo     : le pseudo à tester
   * @param mdpSecured : le password encrypté en SHA256
   * @return true : connexion valide <br>
   *         false : connexion invalide
   */
  boolean isLoginByPseudoValid(String pseudo, String mdpSecured);

  /**
   * Vérifie dans la bdd si le couple <code>email + mdp</code> existe.
   *
   * @param login      : l'email à tester
   * @param mdpSecured : le password encrypté en SHA256
   * @return true : connexion valide <br>
   *         false : connexion invalide
   */
  boolean isLoginByEmailValid(String email, String mdpSecured);

  /**
   * Test si le code postal existe dans la bdd
   *
   * @param cp
   * @return
   */
  boolean isValidCp(String cp);

  /**
   * Test si l'ID de la ville existe dans la bdd
   *
   * @param villeID
   * @return
   */
  boolean isValidVilleId(Integer villeID);

  /**
   * Renvoit la liste des villes avec ce code postal
   *
   * @param cp
   * @return
   */
  List<Ville> getVillesByCp(String cp);

  /**
   * Récupère le sel à partir d'un pseudo
   *
   * @param pseudo
   * @return Le sel
   */
  byte[] getSaltByPseudo(String pseudo);

  /**
   * Récupère le sel à partir d'un email
   *
   * @param email
   * @return Le sel
   */
  byte[] getSaltByEmail(String email);

  /**
   * Renvoit un utilisateur depuis la bdd à partir d'un login
   *
   * @param login
   * @return L'utilisateur depuis la bdd
   */
  Utilisateur getUtilisateurByLogin(String login);

  /**
   * Met à jour les données d'un utilisateur
   *
   * @param utilisateur
   */
  void updateUtilisateur(Utilisateur utilisateur);

  /**
   * Renvoit vrai si la connexion avec le token est valide
   *
   * @param login
   * @param loginToken
   * @return
   */
  boolean isValidTokenLogin(String login, String tokenLogin);

}
