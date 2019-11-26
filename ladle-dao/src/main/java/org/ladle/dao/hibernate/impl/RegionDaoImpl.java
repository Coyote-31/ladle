package org.ladle.dao.hibernate.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.dao.RegionDao;
import org.ladle.dao.hibernate.object.Region;

@Stateful
public class RegionDaoImpl implements RegionDao {

	private static final Logger LOG = LogManager.getLogger(RegionDaoImpl.class);

	// @PersistenceUnit(unitName = "ladleMySQLPU")

	@PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.EXTENDED)
	private EntityManager em;


	@Override
	public List<Region> getAllRegions() {

		List<Region> regionsToSend;

		System.out.println("getAllRegions Println");
		
		regionsToSend = em.createQuery( "from Region", Region.class ).getResultList();


		for ( Region region : regionsToSend ){
			LOG.debug("ID:" + region.getRegionID());
			LOG.debug(" Nom:" + region.getNom()); 
			LOG.debug(" Soundex:" + region.getSoundex());
		}

		return regionsToSend;

	}

}
