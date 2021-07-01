package org.ladle.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Topo;
import org.ladle.dao.RegionDao;
import org.ladle.dao.TopoDao;

/**
 * Classe de gestion des topos
 *
 * @author Coyote
 */
@Stateless
public class TopoHandler {

  // private static final Logger LOG = LogManager.getLogger(TopoHandler.class);

  @EJB(name = "TopoDaoImpl")
  private TopoDao topoDao;

  @EJB(name = "RegionDaoImpl")
  private RegionDao regionDao;

  /**
   * Renvoit la liste de toutes les régions
   *
   * @return List of Region
   */
  public List<Region> getAllRegions() {
    return regionDao.getAllRegions();
  }

  /**
   * Recherche les topos dans la BDD correspondant aux paramètres
   *
   * @param regionID (Integer)
   * @param pseudo   (String)
   * @param keywords (String)
   * @return List of Topo
   */
  public List<Topo> searchTopos(Integer regionID, String pseudo, String keywords) {

    return topoDao.searchTopos(regionID, pseudo, keywords);
  }

}
