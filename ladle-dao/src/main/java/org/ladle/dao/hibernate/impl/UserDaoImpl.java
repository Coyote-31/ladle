package org.ladle.dao.hibernate.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;
import org.ladle.beans.User;
import org.ladle.dao.UserDao;
import org.ladle.dao.hibernate.object.Utilisateur;

public class UserDaoImpl implements UserDao {

	private static SessionFactory factory;
	private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);
	byte[] salt = {1};
	byte role = 0;


	public UserDaoImpl() {

		/** Initialisation du factory **/
		try {

			factory = new Configuration().configure().buildSessionFactory();

		} catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
	}

	@Override
	public void addUser(User user) {

		Session session = factory.openSession();
		Transaction tx = null;
		Integer utilisateurID = null;

		try {
			tx = session.beginTransaction();
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

			utilisateurID = (Integer) session.save(utilisateur);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
		LOG.info("utilisateurID : " + utilisateurID);

	}

	private int getVilleId(String ville) {
		// todo dao string -> Id
		int villeId = 666;
		return villeId;
	}

	@Override
	public boolean containsPseudo(String pseudo) {

		Session session = factory.openSession();
		Transaction tx = null;
		//EntityManager entityManager = getEntityManager();

		try {
			tx = session.beginTransaction();
			
			String hql = "SELECT U.pseudo FROM utilisateur U WHERE U.pseudo = :pseudo";
			Query query = entityManager.createQuery(hql);
			query.setParameter("pseudo", pseudo);
			
			List<Utilisateur> results = query.getResultList();
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return false;
	}


}
