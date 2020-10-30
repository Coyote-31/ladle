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
import org.hibernate.Hibernate;
import org.ladle.beans.jpa.Departement;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;
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

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> searchByForm(
      String selectedRegion,
      String selectedDepartement,
      String inputedCodePostal,
      String selectedVille) {

    List<Object[]> searchResults = new ArrayList<>();

    int nbrOfResults = 0;

    // Conversion du all et du null de la variable ville
    Integer selectedVilleInteger;
    if ((selectedVille != null) && !"all".equals(selectedVille)) {
      selectedVilleInteger = Integer.valueOf(selectedVille);
    } else {
      selectedVilleInteger = null;
    }

    String hqlQuery = "SELECT r, d, v, si, sec "
                      + "FROM Region r "
                      + "JOIN r.departements d "
                      + "   WITH d.departementCode = :departementCode OR :departementCode is null "
                      + "JOIN d.villes v "
                      + "   WITH (v.cp = :cp OR :cp is null ) "
                      + "   AND (v.id = :ville OR :ville is null) "
                      + "JOIN v.sites si "
                      + "JOIN si.secteurs sec "
                      + "WHERE r.regionCode = :regionCode OR :regionCode is null ";

    try {
      searchResults = em.createQuery(hqlQuery)
          .setParameter("regionCode", selectedRegion)
          .setParameter("departementCode", selectedDepartement)
          .setParameter("cp", inputedCodePostal)
          .setParameter("ville", selectedVilleInteger)
          .getResultList();

      nbrOfResults = searchResults.size();

    } catch (IllegalArgumentException | IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("searchByForm() : failed", e);
    }

    LOG.debug("searchByForm() {} results. With region:{}, dept:{}, cp:{}, ville:{}",
        nbrOfResults, selectedRegion, selectedDepartement, inputedCodePostal, selectedVille);

    return searchResults;
  }

  @Override
  public Site getSiteByID(String siteID) {

    Site site = null;

    LOG.debug("dao siteID : {}", siteID);

    try {
      site = em
          .createQuery("FROM Site as S WHERE S.siteID = :siteID", Site.class)
          .setParameter("siteID", Integer.valueOf(siteID))
          .getSingleResult();
      // site = em.find(Site.class, 1);
      Hibernate.initialize(site.getSecteurs());

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getSiteByID() : failed", e);
    }

    return site;
  }

  @Override
  public Secteur getSecteurByID(String secteurID) {

    Secteur secteur = null;

    LOG.debug("dao secteurID : {}", secteurID);

    try {

      // secteur = em
      // .createQuery("FROM Secteur as S WHERE S.secteurID = :secteurID",
      // Secteur.class)
      // .setParameter("secteurID", Integer.valueOf(secteurID))
      // .getSingleResult();

      secteur = em.find(Secteur.class, Integer.valueOf(secteurID));
      // Initialise la liste des voies
      Hibernate.initialize(secteur.getVoies());

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getSiteByID() : failed", e);
    }

    return secteur;
  }

}
