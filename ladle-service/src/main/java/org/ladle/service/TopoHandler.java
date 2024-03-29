package org.ladle.service;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Topo;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.dao.RegionDao;
import org.ladle.dao.TopoDao;

/**
 * Classe de gestion des topos.
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
   * Renvoit la région correspondant à l'ID.
   *
   * @param id de la région
   * @return La région de classe Region
   */
  public Region getRegionByID(Integer id) {

    return regionDao.getRegionByID(id);
  }

  /**
   * Renvoit la liste de toutes les régions
   *
   * @return List of Region
   */
  public List<Region> getAllRegions() {
    return regionDao.getAllRegions();
  }

  /**
   * Renvoit le topo qui possède cet ID.
   *
   * @param topoID
   * @return topo de type Topo
   */
  public Topo getTopoByID(Integer id) {

    return topoDao.getTopoByID(id);
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

  /**
   * Ajoute un nouveau topo dans la BDD.
   *
   * @param topo de type Topo
   */
  public void persist(Topo topo) {

    topoDao.persist(topo);
  }

  /**
   * Met à jour le topo dans la BDD.
   *
   * @param topo de type Topo
   */
  public void update(Topo topo) {

    topoDao.update(topo);
  }

  /**
   * Supprime le topo de la BDD.
   *
   * @param topo de type Topo
   */
  public void remove(Topo topo) {

    topoDao.remove(topo);

  }

  /**
   * Ajoute un utilisateur à la liste de demande de prêt pour un topo.
   *
   * @param topo
   * @param utilisateur
   */
  public void addDemandePret(Topo topo, Utilisateur utilisateur) {

    topoDao.addDemandePret(topo, utilisateur);

  }

  /**
   * Renvoit la liste des topos que possède cet utilisateur.
   *
   * @param utilisateur propriétaire des topos de type Utilisateur
   * @return liste de type Topo
   */
  public List<Topo> getOwnTopos(Utilisateur utilisateur) {

    return topoDao.getOwnTopos(utilisateur);
  }

  /**
   * Renvoit la liste des topos que cet utilisateur a en cours d'emprunt.
   *
   * @param utilisateur
   * @return Une liste de Topo
   */
  public List<Topo> getLoanTopos(Utilisateur utilisateur) {

    return topoDao.getLoanTopos(utilisateur);
  }

  /**
   * Renvoit la liste des topos dont cet utilisateur à fait une demande de prêt.
   *
   * @param utilisateur qui a fait la demande de prêt.
   * @return liste Set de type Topo
   */
  public Set<Topo> getDemandePretTopos(Utilisateur utilisateur) {

    return topoDao.getDemandePretTopos(utilisateur);
  }

  /**
   * Renvoit l'utilisateur correspondant à l'ID
   *
   * @param userID
   * @return Utilisateur
   */
  public Utilisateur getUserByID(Integer userID) {

    return topoDao.getUserByID(userID);
  }

  /**
   * Accepte la demande de l'utilisateur pour ce topo.
   * Ajoute à l'utilisateur le topo en cours de pret,
   * retire l'utilisateur de la liste de demande de prêt.
   * Rend le topo indisponible.
   *
   * @param topo
   * @param user
   */
  public void acceptDemandTopo(Topo topo, Utilisateur user) {

    topoDao.acceptDemandTopo(topo, user);
  }

  /**
   * Refuse ou annule la demande de l'utilisateur pour ce topo.
   * Retire l'utilisateur demandeur de la liste du topo.
   *
   * @param topo
   * @param user
   */
  public void refuseDemandTopo(Topo topo, Utilisateur user) {

    topoDao.refuseDemandTopo(topo, user);
  }

  /**
   * Supprime l'utilisateur en cours de prêt dans le topo
   * et change le statut du topo en disponible.
   *
   * @param topo
   */
  public void cancelPretTopo(Topo topo) {

    topoDao.cancelPretTopo(topo);
  }

}
