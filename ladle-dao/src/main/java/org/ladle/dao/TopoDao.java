package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Topo;

@Local
public interface TopoDao {

  /**
   * Recherche les topos dans la BDD correspondant aux paramètres
   *
   * @param regionID (Integer)
   * @param pseudo   (String)
   * @param keywords (String)
   * @return List of Topo
   */
  List<Topo> searchTopos(Integer regionID, String pseudo, String keywords);

}
