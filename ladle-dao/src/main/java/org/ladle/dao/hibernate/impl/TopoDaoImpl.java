package org.ladle.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TransactionRequiredException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Topo;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.dao.TopoDao;

@Stateless
public class TopoDaoImpl implements TopoDao {

  private static final Logger LOG = LogManager.getLogger(TopoDaoImpl.class);

  @PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.TRANSACTION)
  private EntityManager em;

  @Override
  public List<Topo> searchTopos(Integer regionID, String pseudo, String keywords) {

    List<Topo> resultTopos = new ArrayList<>();

    if (regionID == 0) {
      regionID = null;
    }
    if (pseudo.isEmpty()) {
      pseudo = null;
    }

    LOG.debug("Parameters : regionID = {} / pseudo = {} / keywords = {}",
        regionID,
        pseudo,
        keywords);

    try {
      String hql = "FROM Topo T "
                   + "WHERE (T.region.regionID = :regionID OR :regionID is null) "
                   + "AND (T.utilisateur.pseudo LIKE CONCAT('%', :pseudo, '%') OR :pseudo is null) "
                   + "AND (T.disponible is TRUE) "
                   + "AND ( T.nom LIKE CONCAT('%', :keywords, '%') "
                   + "OR T.lieu LIKE CONCAT('%', :keywords, '%') "
                   + "OR T.description LIKE CONCAT('%', :keywords, '%'))";

      resultTopos = em.createQuery(hql, Topo.class)
          .setParameter("regionID", regionID)
          .setParameter("pseudo", pseudo)
          .setParameter("keywords", keywords)
          .getResultList();

    } catch (IllegalArgumentException e) {
      LOG.error("Error ! Failed to searchTopos()", e);
    }

    LOG.debug("Topos size results : {}", resultTopos.size());

    return resultTopos;
  }

  @Override
  public void persist(Topo topo) {
    try {
      em.persist(topo);
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("em.persist(topo) failed", e);
    }
  }

  @Override
  public void update(Topo topo) {
    try {
      em.merge(topo);
    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("Update topo failed", e);
    }
  }

  @Override
  public List<Topo> getOwnTopos(Utilisateur utilisateur) {

    List<Topo> ownTopos = new ArrayList<>();

    try {
      ownTopos = em.find(Utilisateur.class, utilisateur.getUtilisateurID()).getTopos();

    } catch (IllegalArgumentException e) {
      LOG.error("getOwnTopos() failed", e);
    }

    return ownTopos;
  }

  @Override
  public Topo getTopoByID(Integer id) {

    Topo topo = null;

    try {
      topo = em.find(Topo.class, id);
    } catch (IllegalArgumentException e) {
      LOG.error("getTopoByID() failed", e);
    }

    return topo;
  }

}
