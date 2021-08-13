package org.ladle.dao;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import org.ladle.beans.jpa.Topo;
import org.ladle.beans.jpa.Utilisateur;

@Local
public interface TopoDao {

  /**
   * Renvoit le topo qui possède cet ID.
   *
   * @param topoID
   * @return topo de type Topo
   */
  Topo getTopoByID(Integer id);

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
   * Supprime le topo de la BDD.
   *
   * @param topo de type Topo
   */
  void removeTopo(Topo topo);

  /**
   * Ajoute un utilisateur à la liste de demande de prêt pour un topo.
   *
   * @param topo
   * @param utilisateur
   */
  void addDemandePret(Topo topo, Utilisateur utilisateur);

  /**
   * Renvoit la liste des topos que possède cet utilisateur.
   *
   * @param utilisateur propriétaire des topos de type Utilisateur
   * @return liste de type Topo
   */
  List<Topo> getOwnTopos(Utilisateur utilisateur);

  /**
   * Renvoit la liste des topos que cet utilisateur a en cours d'emprunt.
   *
   * @param utilisateur
   * @return Une liste de Topo
   */
  List<Topo> getLoanTopos(Utilisateur utilisateur);

  /**
   * Renvoit la liste des topos dont cet utilisateur à fait une demande de prêt.
   *
   * @param utilisateur qui a fait la demande de prêt.
   * @return liste Set de type Topo
   */
  Set<Topo> getDemandePretTopos(Utilisateur utilisateur);

  /**
   * Renvoit l'utilisateur correspondant à l'ID
   *
   * @param userID
   * @return Utilisateur
   */
  Utilisateur getUserByID(Integer userID);

  /**
   * Accepte la demande de l'utilisateur pour ce topo.
   * Ajoute à l'utilisateur le topo en cours de pret,
   * retire l'utilisateur de la liste de demande de prêt.
   * Rend le topo indisponible.
   *
   * @param topo
   * @param user
   */
  void acceptDemandTopo(Topo topo, Utilisateur user);

  /**
   * Refuse ou annule la demande de l'utilisateur pour ce topo.
   * Retire l'utilisateur demandeur de la liste du topo.
   *
   * @param topo
   * @param user
   */
  void refuseDemandTopo(Topo topo, Utilisateur user);

}
