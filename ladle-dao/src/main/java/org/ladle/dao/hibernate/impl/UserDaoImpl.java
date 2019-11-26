package org.ladle.dao.hibernate.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.ladle.beans.User;
import org.ladle.dao.UserDao;
import org.ladle.dao.hibernate.object.Utilisateur;

public class UserDaoImpl implements UserDao {

	private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);
	
	@PersistenceContext(unitName = "ladleMySQLPU")
	private EntityManager em;
	
	byte[] salt = {1}; //TODO
	Integer role = 0; //TODO


	@Override
	public void addUser(User user) {


		em.getTransaction().begin();

		Utilisateur utilisateur = 
				new Utilisateur(getVilleId(user.getVille()), 
						user.getPseudo(), 
						user.getGenre(), 
						user.getNom(), 
						user.getPrenom(),	
						user.getEmail(), 
						user.getMdp(), 
						salt, 
						role);

		em.persist(utilisateur);
//		em.getTransaction().commit();
//		em.close();

		LOG.info(utilisateur.getPseudo() + " : Saved");
	}

	private int getVilleId(String ville) {
		// todo dao string -> Id
		int villeId = 666;
		return villeId;
	}

	@Override
	public boolean containsPseudo(String pseudo) {

//		em = JPAUtility.getEntityManager();

		String hql = "FROM Utilisateur U WHERE U.pseudo = :pseudo";
		Query query = em.createQuery(hql);
		query.setParameter("pseudo", pseudo);

		Utilisateur utilisateur = (Utilisateur) query.getSingleResult();

//		em.close();

		if (utilisateur == null) {
			LOG.info("Pseudo = " + pseudo +" libre");
			return false;
		} else {
			LOG.info("Pseudo = " + pseudo +" déjà utilisé");
			return true;
		}
	}

}
