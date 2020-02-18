package org.ladle.dao.hibernate.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;
import org.ladle.dao.UserDao;
import org.ladle.dao.hibernate.object.Utilisateur;

/**
 * @author Coyote
 */
@Stateful
public class UserDaoImpl implements UserDao {

  private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);

  @PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.TRANSACTION)
  private EntityManager em;

  /*
   * Niveau des comptes :
   * 0 = utilisateur
   * 1 = membre
   * 2 = admin
   */
  private static final Integer ROLE_UTILISATEUR = 0;
  private static final String PSEUDO = "pseudo";

  // Default constructor
  public UserDaoImpl() {
    super();
  }

  @Override
  public void addUser(User user) {

    Utilisateur utilisateur = new Utilisateur(getVilleId(user.getVille()), user.getPseudo(), user.getGenre(),
        user.getNom(), user.getPrenom(), user.getEmail(), user.getMdpSecured(), user.getSalt(), ROLE_UTILISATEUR,
        user.getEmailSHA(), user.getDateEmail(), user.getDateCompte());

    try {
      em.persist(utilisateur);
    } catch (PersistenceException e) {
      LOG.error("Error ! em.persist failed to insert : {}", user.getPseudo(), e);
    }
    LOG.info("Nouvel utilisateur dans la bdd : {}", utilisateur.getPseudo());
  }

  private int getVilleId(String ville) {
    // TODO dao string -> Id
    return 666;
  }

  @Override
  public boolean containsPseudo(String pseudo) {

    String hql = "FROM Utilisateur U WHERE U.pseudo = :" + PSEUDO;
    Query query = em.createQuery(hql);
    query.setParameter(PSEUDO, pseudo);

    try {
      query.getSingleResult();
    } catch (NoResultException e) {
      LOG.debug("Pseudo: {} -> disponible", pseudo);
      LOG.trace(e);
      return false;
    }

    LOG.debug("Pseudo: {} -> déjà utilisé", pseudo);
    return true;
  }

  @Override
  public boolean containsEmail(String email) {

    String hql = "FROM Utilisateur U WHERE U.email = :email";
    Query query = em.createQuery(hql);
    query.setParameter("email", email);

    try {
      query.getSingleResult();
    } catch (NoResultException e) {
      LOG.debug("Email: {} libre", email);
      LOG.trace(e);
      return false;
    }
    LOG.debug("Email: {} déjà utilisé", email);
    return true;
  }

  @Override
  public boolean emailSHAExist(String emailSHA) {

    String hql = "FROM Utilisateur U WHERE U.emailSHA = :emailSHA";
    Query query = em.createQuery(hql);
    query.setParameter("emailSHA", emailSHA);

    try {
      query.getSingleResult();
    } catch (NoResultException e) {
      LOG.debug("emailSHA: {} absent", emailSHA, e);
      LOG.trace(e);
      return false;
    }
    LOG.debug("emailSHA: {} existe", emailSHA);
    return true;
  }

  @Override
  public void emailSHADelete(String emailSHA) {

    String hql = "UPDATE Utilisateur U SET U.emailSHA = NULL WHERE U.emailSHA = :emailSHA";

    Query query = em.createQuery(hql);
    query.setParameter("emailSHA", emailSHA);

    query.executeUpdate();
  }

  @Override
  public boolean isLoginByPseudoValid(String pseudo, String mdpSecured) {

    String hql = "FROM Utilisateur U WHERE U.pseudo = :" + PSEUDO + " AND U.mdp = :pwd";

    Query query = em.createQuery(hql);
    query.setParameter(PSEUDO, pseudo);
    query.setParameter("pwd", mdpSecured);

    try {
      query.getSingleResult();

    } catch (NoResultException e) {
      LOG.debug("login by pseudo fail : {} / {}", pseudo, mdpSecured);
      LOG.trace(e);
      return false;
    }
    LOG.debug("login by pseudo valid : {} / {}", pseudo, mdpSecured);
    return true;
  }

  @Override
  public boolean isLoginByEmailValid(String email, String mdpSecured) {

    String hql = "FROM Utilisateur U WHERE U.email = :email AND U.mdp = :pwd";

    Query query = em.createQuery(hql);
    query.setParameter("email", email);
    query.setParameter("pwd", mdpSecured);

    try {
      query.getSingleResult();

    } catch (NoResultException e) {
      LOG.debug("login by email fail : {} / {}", email, mdpSecured);
      LOG.trace(e);
      return false;
    }
    LOG.debug("login by email valid : {} / {}", email, mdpSecured);
    return true;
  }

  @Override
  public byte[] getSaltByPseudo(String pseudo) {

    String hql = "SELECT U.salt FROM Utilisateur U WHERE U.pseudo = :" + PSEUDO;

    Query query = em.createQuery(hql, byte[].class);
    query.setParameter(PSEUDO, pseudo);

    List<byte[]> results;
    byte[] salt = null;

    try {

      // Conformation en byte[]
      @SuppressWarnings("unchecked")
      List<byte[]> resultsRaw = query.getResultList();
      results = Collections.checkedList(resultsRaw, byte[].class);
      LOG.debug("getSaltByPseudo() -> Number of results = {}", results.size());

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getSaltByPseudo() failed -> return = {}", salt, e);
      return salt;
    }

    // Récupération du sel par le pseudo
    if (results.size() == 1) {
      LOG.debug("getSaltByPseudo() match -> return = {}", results.get(0));
      return results.get(0);

      // Il n'y a pas de correspondance avec le pseudo
    } else if (results.isEmpty()) {
      LOG.debug("getSaltByPseudo() empty -> return = {}", salt);
      return salt;

      // Plusieurs correspondances sont trouvées -> NonUniqueResultException
    } else {
      String message = "Grave ! Le pseudo : " + pseudo + " existe en plusieurs exemplaires dans la BDD.";
      LOG.error(message);
      throw new NonUniqueResultException(message);
    }
  }

  @Override
  public byte[] getSaltByEmail(String email) {

    String hql = "SELECT U.salt FROM Utilisateur U WHERE U.email = :email";

    Query query = em.createQuery(hql, byte[].class);
    query.setParameter("email", email);

    List<byte[]> results;
    byte[] salt = null;

    try {

      // Conformation en byte[]
      @SuppressWarnings("unchecked")
      List<byte[]> resultsRaw = query.getResultList();
      results = Collections.checkedList(resultsRaw, byte[].class);
      LOG.debug("getSaltByEmail() -> Number of results = {}", results.size());

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getSaltByEmail() failed -> return = {}", salt, e);
      return salt;
    }

    // Récupération du sel par l'email
    if (results.size() == 1) {
      LOG.debug("getSaltByEmail() match -> return = {}", results.get(0));
      return results.get(0);

      // Il n'y a pas de correspondance avec l'email
    } else if (results.isEmpty()) {
      LOG.debug("getSaltByEmail() empty -> return = {}", salt);
      return salt;

      // Plusieurs correspondances sont trouvées -> NonUniqueResultException
    } else {
      String message = "Grave ! L'email : " + email + " existe en plusieurs exemplaires dans la BDD.";
      LOG.error(message);
      throw new NonUniqueResultException(message);
    }
  }
}
