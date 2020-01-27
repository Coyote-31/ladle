package org.ladle.dao.hibernate.impl;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;
import org.ladle.dao.UserDao;
import org.ladle.dao.hibernate.object.Utilisateur;

/**
 * @author Coyote
 *
 */
@Stateful
public class UserDaoImpl implements UserDao {

	private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);
	
	@PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	// Niveau du compte : 0 = utilisateur
	Integer role = 0;


	@Override
	public void addUser(User user) {

		Utilisateur utilisateur = 
				new Utilisateur(getVilleId(user.getVille()), 
						user.getPseudo(), 
						user.getGenre(), 
						user.getNom(), 
						user.getPrenom(),	
						user.getEmail(), 
						user.getMdpSecured(),
						user.getSalt(),
						role,
						user.getEmailSHA(),
						user.getDateEmail(),
						user.getDateCompte());
		
		LOG.info(user.getPrenom());
		
		try {
			em.persist(utilisateur);
		} catch (Exception e) {
			LOG.error("em.persist(utilisateur) Failed",e);
		}
		LOG.info("{} : Saved", utilisateur.getPseudo());
	}

	private int getVilleId(String ville) {
		// todo dao string -> Id
		int villeId = 666;
		return villeId;
	}

	@Override
	public boolean containsPseudo(String pseudo) {

		String hql = "FROM Utilisateur U WHERE U.pseudo = :pseudo";
		Query query = em.createQuery(hql);
		query.setParameter("pseudo", pseudo);


		try {
			query.getSingleResult();			
		} catch (NoResultException e) {
			LOG.info("Pseudo: {} -> disponible", pseudo);
			return false;
		}
		
		LOG.info("Pseudo: {} -> déjà utilisé", pseudo);
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
			LOG.info("Email: {} libre", email);
			return false;
		}
		LOG.info("Email: {} déjà utilisé", email);
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
			LOG.info("emailSHA: {} absent", emailSHA);
			return false;
		}
		LOG.info("emailSHA: {} existe", emailSHA);
		return true;
	}

	@Override
	public void emailSHADelete(String emailSHA) {
		
		String hql = "UPDATE Utilisateur U SET U.emailSHA = NULL WHERE U.emailSHA = :emailSHA";
		Query query = em.createQuery(hql);
		query.setParameter("emailSHA", emailSHA);

		query.executeUpdate();
	}

}
