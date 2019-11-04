package org.ladle.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe utilitaire de gestion de l'Entity Manager Factory.
 * @author Coyote
 */
public class JPAUtility {
	
	private static final EntityManagerFactory emFactory;
	static {
		   emFactory = Persistence.createEntityManagerFactory("ladleMySQLPU");
	}
	
	private JPAUtility() {
		
		throw new IllegalStateException("Utility class static !");
	}

	
	public static EntityManager getEntityManager(){
		
		return emFactory.createEntityManager();
	}
	
	public static void close(){
		emFactory.close();
	}
	
}
