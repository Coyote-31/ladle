package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Region;

/**
 * Classe d'interface DAO de gestion des régions.
 *
 * @author Coyote
 */
@Local
public interface RegionDao {

  /**
   * Renvoit la liste de toutes les régions.
   *
   * @return Une liste composée de classe Region
   */
  List<Region> getAllRegions();

  /**
   * Renvoit la région correspondant à l'ID
   *
   * @param id de la région
   * @return Une région de type Region
   */
  Region getRegionByID(Integer id);

}
