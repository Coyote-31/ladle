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

/**
 * Implémentation de la DAO pour la recherche de site ou de secteur.
 *
 * @author Coyote
 */
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
      String selectedVille,
      String selectedCotaEqual,
      String selectedCotaNumChar,
      String selectedSectEqual,
      String selectedSectNum,
      String selectedOfficiel) {

    List<Object[]> searchResults = new ArrayList<>();

    int nbrOfResults = 0;

    // Conversion du all et du null de la variable ville
    Integer selectedVilleInteger;
    if ((selectedVille != null) && !"all".equals(selectedVille)) {
      selectedVilleInteger = Integer.valueOf(selectedVille);
    } else {
      selectedVilleInteger = null;
    }

    // Formattage du signe de cotation
    String selectedCotaSign;
    switch (selectedCotaEqual) {
      case "all":
        selectedCotaSign = ">=";
        selectedCotaNumChar = null;
        break;
      case "supEq":
        selectedCotaSign = ">=";
        break;
      case "infEq":
        selectedCotaSign = "<=";
        break;
      case "equal":
        selectedCotaSign = "=";
        break;
      default:
        selectedCotaSign = ">=";
        break;
    }

    // Formattage du signe du nombre de secteur
    String selectedSectSign;
    switch (selectedSectEqual) {
      case "supEq":
        selectedSectSign = ">=";
        break;
      case "infEq":
        selectedSectSign = "<=";
        break;
      case "equal":
        selectedSectSign = "=";
        break;
      default:
        selectedSectSign = ">=";
        break;
    }

    // Conversion de la variable selectedSectNum en integer
    Integer selectedSectNumInteger;
    if ((selectedSectNum != null)) {
      selectedSectNumInteger = Integer.valueOf(selectedSectNum);
    } else {
      selectedSectNumInteger = null;
    }

    // Formattage de la variable Officiel (site)
    Boolean selectedOfficielBool;
    switch (selectedOfficiel) {
      case "all":
        selectedOfficielBool = null;
        break;
      case "yes":
        selectedOfficielBool = true;
        break;
      case "no":
        selectedOfficielBool = false;
        break;
      default:
        selectedOfficielBool = null;
        break;
    }

    // Gère 2 requètes : Avec Cotation et sans Cotation
    // Ce qui résout le problème des Sites sans Secteurs ni Voies.
    String hqlQuery = "";

    // Cas sans cotation : Affiche les Sites sans secteur :
    if ("all".equals(selectedCotaEqual)) {

      hqlQuery = "SELECT r, d, v, si, sec, vx "
                 + "FROM Region r "
                 + "JOIN r.departements d "
                 + "   WITH d.departementCode = :departementCode OR :departementCode is null "
                 + "JOIN d.villes v "
                 + "   WITH (v.cp = :cp OR :cp is null ) "
                 + "   AND (v.id = :ville OR :ville is null) "
                 + "JOIN v.sites si "
                 + "   WITH (size(si.secteurs) " + selectedSectSign + " :sectNum) "
                 + "   AND (si.officiel = :officiel OR :officiel is null) "
                 + "LEFT JOIN si.secteurs sec "
                 + "LEFT JOIN sec.voies vx "
                 + "   WITH (:cotaNumChar is null) "
                 + "WHERE r.regionCode = :regionCode OR :regionCode is null ";

      // Cas avec cotation : N'affiche pas les Sites sans secteur :
    } else {

      hqlQuery = "SELECT r, d, v, si, sec, vx "
                 + "FROM Region r "
                 + "JOIN r.departements d "
                 + "   WITH d.departementCode = :departementCode OR :departementCode is null "
                 + "JOIN d.villes v "
                 + "   WITH (v.cp = :cp OR :cp is null ) "
                 + "   AND (v.id = :ville OR :ville is null) "
                 + "JOIN v.sites si "
                 + "   WITH (size(si.secteurs) " + selectedSectSign + " :sectNum) "
                 + "   AND (si.officiel = :officiel OR :officiel is null) "
                 + "LEFT JOIN si.secteurs sec "
                 + "JOIN sec.voies vx "
                 + "   WITH (vx.cotation " + selectedCotaSign + " :cotaNumChar OR :cotaNumChar is null) "
                 + "WHERE r.regionCode = :regionCode OR :regionCode is null ";
    }

    try {
      searchResults = em.createQuery(hqlQuery)
          .setParameter("regionCode", selectedRegion)
          .setParameter("departementCode", selectedDepartement)
          .setParameter("cp", inputedCodePostal)
          .setParameter("ville", selectedVilleInteger)
          .setParameter("cotaNumChar", selectedCotaNumChar)
          .setParameter("sectNum", selectedSectNumInteger)
          .setParameter("officiel", selectedOfficielBool)
          .getResultList();

      nbrOfResults = searchResults.size();

    } catch (IllegalArgumentException | IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("searchByForm() : failed", e);
    }

    LOG.debug("searchByForm() {} results.\n    -> With region:{}, dept:{}, cp:{}, ville:{}, "
              + "cotation{}{}, nrbSecteurs{}{}, officiel:{}",
        nbrOfResults,
        selectedRegion,
        selectedDepartement,
        inputedCodePostal,
        selectedVille,
        selectedCotaSign,
        selectedCotaNumChar,
        selectedSectSign,
        selectedSectNum,
        selectedOfficielBool);

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

      Hibernate.initialize(site.getSecteurs());

    } catch (IllegalStateException | PersistenceException | ClassCastException | NumberFormatException e) {
      LOG.error("getSiteByID() : failed", e);
    }

    return site;
  }

  @Override
  public Secteur getSecteurByID(String secteurID) {

    Secteur secteur = null;

    LOG.debug("dao secteurID : {}", secteurID);

    try {

      secteur = em.find(Secteur.class, Integer.valueOf(secteurID));
      // Initialise la liste des voies
      Hibernate.initialize(secteur.getVoies());

    } catch (IllegalStateException | PersistenceException | ClassCastException | NumberFormatException e) {
      LOG.error("getSiteByID() : failed", e);
    }

    return secteur;
  }

  @Override
  public List<Secteur> getLast3SecteursUpdated() {

    final int MAX_RESULTS = 3;
    List<Secteur> secteurs = new ArrayList<>();
    String hql = "FROM Secteur as S "
                 + "ORDER BY S.dateLastMaj DESC";

    try {
      secteurs = em
          .createQuery(hql, Secteur.class)
          .setMaxResults(MAX_RESULTS)
          .getResultList();

    } catch (IllegalStateException | IllegalArgumentException | PersistenceException | ClassCastException e) {
      LOG.error("getLast3SecteursUpdated() : failed", e);
    }

    return secteurs;
  }

  @Override
  public List<Ville> getVillesByNom(String nom) {

    final int MAX_RESULTS = 19;
    List<Ville> villes = new ArrayList<>();

    try {
      villes = em
          .createQuery("FROM Ville as V "
                       + "WHERE V.nom LIKE CONCAT('%',:nom,'%') ORDER BY LENGTH(V.nom)",
              Ville.class)
          .setParameter("nom", nom)
          .setMaxResults(MAX_RESULTS).getResultList();

    } catch (IllegalStateException | PersistenceException | ClassCastException e) {
      LOG.error("getVillesByNom() : failed", e);
    }

    return villes;
  }

  @Override
  public Ville getVilleByID(String id) {

    Ville ville = null;

    LOG.debug("dao villeID : {}", id);

    try {

      ville = em.find(Ville.class, Integer.valueOf(id));

    } catch (IllegalStateException | PersistenceException | ClassCastException | NumberFormatException e) {
      LOG.error("getVilleByID() : failed", e);
    }

    return ville;
  }

}
