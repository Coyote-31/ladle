package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Topo;
import org.ladle.beans.jpa.Utilisateur;

@Local
public interface TopoDao {

  /**
   * Recherche les topos dans la BDD correspondant aux paramètres.
   *
   * @param regionID (Integer)
   * @param pseudo   (String)
   * @param keywords (String)
   * @return List of Topo
   */
  List<Topo> searchTopos(Integer regionID, String pseudo, String keywords);

  /**
   * Ajoute un nouveau topo dans la BDD.
   *
   * @param topo de type Topo
   */
  void persist(Topo topo);

  /**
   * Met à jour le topo dans la BDD.
   *
   * @param topo de type Topo
   */
  void update(Topo topo);

  /**
   * Renvoit la liste des topos que possède cet utilisateur.
   *
   * @param utilisateur propriétaire des topos de type Utilisateur
   * @return liste de type Topo
   */
  List<Topo> getOwnTopos(Utilisateur utilisateur);

  /**
   * Renvoit le topo qui possède cet ID.
   *
   * @param topoID
   * @return topo de type Topo
   */
  Topo getTopoByID(Integer id);

}
