package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Departement;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Ville;

@Local
public interface RechercheSiteSecteurDao {

  List<Region> getAllRegions();

  List<Departement> getAllDepartements();

  List<Departement> getDepartementsByRegionCode(String regionCode);

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
   * @return
   */
  List<Object[]> searchByForm(String selectedRegion, String selectedDepartement, String inputedCodePostal,
      String selectedVille);

}
