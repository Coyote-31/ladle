package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Departement;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;
import org.ladle.beans.jpa.Ville;

/**
 * Classe d'interface DAO de gestion de la recherche de site ou de secteur.
 *
 * @author Coyote
 */
@Local
public interface RechercheSiteSecteurDao {

  /**
   * Renvoit la liste de toutes les régions.
   *
   * @return Une liste de Region
   */
  List<Region> getAllRegions();

  /**
   * Renvoit la liste de tous les départements.
   *
   * @return Une liste de Departement
   */
  List<Departement> getAllDepartements();

  /**
   * Renvoit la liste de tous les départements d'une région.
   *
   * @param regionCode : Le code de la région
   * @return Une liste de Departement
   */
  List<Departement> getDepartementsByRegionCode(String regionCode);

  /**
   * Renvoit toutes les villes ayant ce code postal.
   *
   * @param codePostal
   * @return Une liste de Ville
   */
  List<Ville> getVillesByCP(String codePostal);

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
   * @param selectedCotaNumChar
   * @param selectedCotaEqual
   * @param selectedSectNum
   * @param selectedSectEqual
   * @param selectedOfficiel
   * @return
   */
  List<Object[]> searchByForm(
      String selectedRegion,
      String selectedDepartement,
      String inputedCodePostal,
      String selectedVille,
      String selectedCotaEqual,
      String selectedCotaNumChar,
      String selectedSectEqual,
      String selectedSectNum,
      String selectedOfficiel);

  /**
   * Retourne le Site grâce à son ID
   *
   * @param siteID
   * @return
   */
  Site getSiteByID(String siteID);

  /**
   * Retourne le Secteur grâce à son ID
   *
   * @param secteurID
   * @return
   */
  Secteur getSecteurByID(String secteurID);

  /**
   * Renvoit une liste de villes correspondant au nom (19 resultats max)
   *
   * @param name
   * @return
   */
  List<Ville> getVillesByNom(String nom);

  /**
   * Renvoit une ville depuis son ID
   *
   * @param id
   * @return
   */
  Ville getVilleByID(String id);

}
