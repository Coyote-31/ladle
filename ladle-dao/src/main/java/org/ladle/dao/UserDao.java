package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Utilisateur;
import org.ladle.beans.jpa.Ville;

/**
 * Classe d'interface DAO de gestion des utilisateurs.
 *
 * @author Coyote
 */
@Local
public interface UserDao {

  /**
   * Ajoute dans la BDD le nouvel utilisateur.
   *
   * @param utilisateur
   * @return true : Réussite de l'ajout <br>
   *         false : Echec de l'ajout
   */
  boolean addUser(Utilisateur utilisateur);

  /**
   * Vérifie si le pseudo est déjà présent dans la BDD.
   *
   * @param pseudo
   * @return true : Pseudo déjà utilisé <br>
   *         false : Pseudo disponible
   */
  boolean containsPseudo(String pseudo);

  /**
   * Vérifie si l'email est déjà présent dans la BDD.
   *
   * @param email
   * @return true : Email déjà utilisé <br>
   *         false : Email disponible
   */
  boolean containsEmail(String email);

  /**
   * Vérifie l'existence d'un emailSHA.
   *
   * @param emailSHA
   * @return true : emailSHA existe <br>
   *         false : emailSHA n'existe pas
   */
  boolean emailSHAExist(String emailSHA);

  /**
   * Effectue la suppression dans la BDD de l'emailSHA
   *
   * @param emailSHA
   */
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
   * Renvoit l'objet ville depuis son ID
   *
   * @param villeID
   * @return Ville object
   */
  Ville getVilleById(Integer villeID);

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
