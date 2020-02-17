package org.ladle.dao;

import javax.ejb.Local;

import org.ladle.beans.User;

@Local
public interface UserDao {

  void addUser(User user);

  boolean containsPseudo(String pseudo);

  boolean containsEmail(String email);

  boolean emailSHAExist(String emailSHA);

  void emailSHADelete(String emailSHA);

  /**
   * Vérifie dans la bdd si le couple [(Pseudo ou Email) + mdp] existe.
   * 
   * @param login : Pseudo ou Email
   * @param pwd   : Password
   * @return true : connexion valide <br>
   *         false : connexion invalide
   */
  boolean isLoginValid(String login, String pwd);

  /**
   * Récupère le sel à partir d'un pseudo
   * 
   * @param pseudo
   * @return Le sel
   */
  Byte[] getSaltByPseudo(String pseudo);

  /**
   * Récupère le sel à partir d'un email
   * 
   * @param email
   * @return Le sel
   */
  Byte[] getSaltByEmail(String email);

}
