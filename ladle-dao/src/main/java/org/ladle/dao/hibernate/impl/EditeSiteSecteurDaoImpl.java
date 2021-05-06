package org.ladle.dao.hibernate.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TransactionRequiredException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;
import org.ladle.dao.EditeSiteSecteurDao;

@Stateless
public class EditeSiteSecteurDaoImpl implements EditeSiteSecteurDao {

  private static final Logger LOG = LogManager.getLogger(EditeSiteSecteurDaoImpl.class);

  @PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public EditeSiteSecteurDaoImpl() {
    super();
  }

  @Override
  public void update(Secteur secteurUpdated) {

    try {
      em.merge(secteurUpdated);
    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("Update secteur failed", e);
    }

  }

  @Override
  public void update(Site siteUpdated) {

    try {
      em.merge(siteUpdated);
    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("Update site failed", e);
    }

  }

  @Override
  public Integer persist(Secteur secteur) {
    try {
      em.persist(secteur);
      em.flush();
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("Persist secteur failed", e);
    }
    return secteur.getSecteurID();
  }

}
