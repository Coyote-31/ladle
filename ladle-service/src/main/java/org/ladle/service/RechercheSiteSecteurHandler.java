package org.ladle.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Departement;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Ville;
import org.ladle.dao.RechercheSiteSecteurDao;

/**
 * Classe de gestion de recherche des sites et des secteurs.
 *
 * @author Coyote
 */
@Stateless
public class RechercheSiteSecteurHandler {

  @SuppressWarnings("unused")
  private static final Logger LOG = LogManager.getLogger(RechercheSiteSecteurHandler.class);

  @EJB(name = "RechercheSiteSecteurDaoImpl")
  private RechercheSiteSecteurDao rechercheSiteSecteurDao;

  public List<Region> getAllRegions() {
    return rechercheSiteSecteurDao.getAllRegions();
  }

  public List<Departement> getAllDepartements() {
    return rechercheSiteSecteurDao.getAllDepartements();
  }

  public List<Departement> getDepartementsByRegionCode(String regionCode) {
    return rechercheSiteSecteurDao.getDepartementsByRegionCode(regionCode);
  }

  public List<Ville> getVillesByCP(String codePostal) {
    return rechercheSiteSecteurDao.getVillesByCP(codePostal);
  }

  /**
   * Renvoit une liste d'objet[]<br>
   * Avec en index :<br>
   * <code>
   * 0 = Region<br>
   * 1 = Departement<br>
   * 2 = Ville<br>
   * 3 = Site<br>
   * 4 = Secteur<br>
   * </code>
   *
   * @param selectedRegion
   * @param selectedDepartement
   * @param inputedCodePostal
   * @param selectedVille
   * @return
   */
  public List<Object[]> searchByForm(String selectedRegion, String selectedDepartement, String inputedCodePostal,
      String selectedVille) {

    // Gestion des paramètres null et 'all'
    if ("all".equals(selectedRegion)) {
      selectedRegion = null;
    }
    if ("all".equals(selectedDepartement)) {
      selectedDepartement = null;
    }
    if ("all".equals(selectedVille)) {
      selectedVille = null;
    }
    if ("".equals(inputedCodePostal)) {
      inputedCodePostal = null;
    }

    return rechercheSiteSecteurDao.searchByForm(
        selectedRegion,
        selectedDepartement,
        inputedCodePostal,
        selectedVille);
  }

}
