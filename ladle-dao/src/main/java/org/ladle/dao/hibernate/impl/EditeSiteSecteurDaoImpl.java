package org.ladle.dao.hibernate.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.dao.EditeSiteSecteurDao;

public class EditeSiteSecteurDaoImpl implements EditeSiteSecteurDao {

  @SuppressWarnings("unused")
  private static final Logger LOG = LogManager.getLogger(EditeSiteSecteurDaoImpl.class);

  @PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public EditeSiteSecteurDaoImpl() {
    super();
  }
}
