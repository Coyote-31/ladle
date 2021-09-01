package org.ladle.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Region;
import org.ladle.dao.RegionDao;

/**
 * Implémentation de la DAO pour la gestion des régions.
 *
 * @author Coyote
 */
@Stateless
public class RegionDaoImpl implements RegionDao {

  private static final Logger LOG = LogManager.getLogger(RegionDaoImpl.class);

  @PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public RegionDaoImpl() {
    super();
  }

  @Override
  public List<Region> getAllRegions() {

    List<Region> regionsToSend = new ArrayList<>();

    try {
      regionsToSend = em.createQuery("from Region", Region.class).getResultList();

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getResultList() : failed", e);
    }

    LOG.debug("regionsToSend : creating...");

    for (Region region : regionsToSend) {
      LOG.trace("add ID: {} / Nom: {} / Soundex: {}", region.getRegionID(), region.getNom(), region.getSoundex());
    }

    LOG.debug("regionsToSend : done with {} entrie(s)", regionsToSend.size());

    return regionsToSend;

  }

  @Override
  public Region getRegionByID(Integer id) {

    Region region = null;

    LOG.debug("dao RegionID : {}", id);

    try {
      region = em.find(Region.class, id);

    } catch (IllegalArgumentException e) {
      LOG.error("getRegionByID() : failed", e);
    }

    return region;
  }

}
