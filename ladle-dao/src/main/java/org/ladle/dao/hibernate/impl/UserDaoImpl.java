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

@Stateful
//@Named("UserDaoImpl")
public class UserDaoImpl implements UserDao {

	private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);
	
	@PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	byte[] salt = {1}; //TODO
	Integer role = 0; //TODO


	@Override
	public void addUser(User user) {

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
		System.out.println(user.getPrenom());
		try {
			em.persist(utilisateur);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOG.info(utilisateur.getPseudo() + " : Saved");
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
			LOG.info("Pseudo = " + pseudo +" libre");
			return false;
		}

			LOG.info("Pseudo = " + pseudo +" déjà utilisé");
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
			LOG.info("Email = " + email +" libre");
			return false;
		}

			LOG.info("Email = " + email +" déjà utilisé");
			return true;		

	}

}
