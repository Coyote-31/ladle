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
import org.ladle.beans.jpa.Departement;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Ville;
import org.ladle.dao.RechercheSiteSecteurDao;

@Stateless
public class RechercheSiteSecteurDaoImpl implements RechercheSiteSecteurDao {

  private static final Logger LOG = LogManager.getLogger(RechercheSiteSecteurDaoImpl.class);

  @PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public RechercheSiteSecteurDaoImpl() {
    super();
  }

  @Override
  public List<Region> getAllRegions() {

    List<Region> regions = new ArrayList<>();

    try {
      regions = em.createQuery("from Region as r order by r.nom", Region.class).getResultList();

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getResultList() : failed", e);
    }

    return regions;
  }

  @Override
  public List<Departement> getAllDepartements() {

    List<Departement> departements = new ArrayList<>();

    try {
      departements = em.createQuery("from Departement as d order by d.departementCode", Departement.class)
          .getResultList();

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getResultList() : failed", e);
    }

    return departements;
  }

  @Override
  public List<Departement> getDepartementsByRegionCode(String regionCode) {

    List<Departement> departements = new ArrayList<>();

    try {
      departements = em
          .createQuery("FROM Departement as D WHERE D.region.regionCode = :regionCode ORDER BY D.departementCode",
              Departement.class)
          .setParameter("regionCode", regionCode)
          .getResultList();

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getResultList() : failed", e);
    }

    return departements;
  }

  @Override
  public List<Ville> getVillesByCP(String codePostal) {

    List<Ville> villes = new ArrayList<>();

    try {
      villes = em
          .createQuery("FROM Ville as V WHERE V.cp = :cp ORDER BY V.nom", Ville.class)
          .setParameter("cp", codePostal)
          .getResultList();

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getVillesByCP() : failed", e);
    }

    return villes;
  }

}
