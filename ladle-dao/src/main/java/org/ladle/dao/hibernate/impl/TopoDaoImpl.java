package org.ladle.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TransactionRequiredException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.ladle.beans.jpa.Topo;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.dao.TopoDao;

/**
 * Implémentation de la DAO pour la gestion des topos.
 *
 * @author Coyote
 */
@Stateless
public class TopoDaoImpl implements TopoDao {

  private static final Logger LOG = LogManager.getLogger(TopoDaoImpl.class);

  @PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.TRANSACTION)
  private EntityManager em;

  @Override
  public Topo getTopoByID(Integer id) {

    Topo topo = null;

    try {
      topo = em.find(Topo.class, id);

      Hibernate.initialize(topo.getDemandePretUtilisateurs());

    } catch (IllegalArgumentException e) {
      LOG.error("getTopoByID() failed", e);
    }

    return topo;
  }

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

      for (Topo topo : resultTopos) {
        Hibernate.initialize(topo.getDemandePretUtilisateurs());
      }

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
      // Récupère la liste Set<Utilisateur> demandePretUtilisateurs du topo
      Topo topoUpdated = em.find(Topo.class, topo.getTopoID());

      // MAJ du topo
      topoUpdated.setUtilisateur(topo.getUtilisateur());
      topoUpdated.setParutionDate(topo.getParutionDate());
      topoUpdated.setRegion(topo.getRegion());
      topoUpdated.setLieu(topo.getLieu());
      topoUpdated.setNom(topo.getNom());
      topoUpdated.setDisponible(topo.isDisponible());
      topoUpdated.setDescription(topo.getDescription());

      em.merge(topoUpdated);
    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("Update topo failed", e);
    }
  }

  @Override
  public void remove(Topo topo) {
    try {
      Topo topoToRemove = em.find(Topo.class, topo.getTopoID());
      em.remove(topoToRemove);
    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("Remove topo failed", e);
    }
  }

  @Override
  public void addDemandePret(Topo topo, Utilisateur utilisateur) {

    try {
      Topo topoUpdated = em.find(Topo.class, topo.getTopoID());
      Utilisateur utilisateurUpdated = em.find(Utilisateur.class, utilisateur.getUtilisateurID());
      topoUpdated.addDemandePretUtilisateur(utilisateurUpdated);
      em.merge(topoUpdated);

    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("addDemandePret() failed for topoID : {} userID : {}",
          topo.getTopoID(),
          utilisateur.getUtilisateurID(),
          e);
    }
  }

  @Override
  public List<Topo> getOwnTopos(Utilisateur utilisateur) {

    List<Topo> ownTopos = new ArrayList<>();

    try {
      ownTopos = em.find(Utilisateur.class, utilisateur.getUtilisateurID()).getTopos();

      for (Topo topo : ownTopos) {
        Hibernate.initialize(topo.getDemandePretUtilisateurs());
      }

    } catch (IllegalArgumentException e) {
      LOG.error("getOwnTopos() failed", e);
    }

    return ownTopos;
  }

  @Override
  public List<Topo> getLoanTopos(Utilisateur utilisateur) {

    List<Topo> loanTopos = new ArrayList<>();

    try {
      loanTopos = em.find(Utilisateur.class, utilisateur.getUtilisateurID()).getPretTopos();

    } catch (IllegalArgumentException e) {
      LOG.error("getLoanTopos() failed", e);
    }

    return loanTopos;
  }

  @Override
  public Set<Topo> getDemandePretTopos(Utilisateur utilisateur) {

    Set<Topo> demandePretTopos = new HashSet<>();

    try {
      demandePretTopos = em.find(Utilisateur.class, utilisateur.getUtilisateurID()).getDemandePretTopos();

    } catch (IllegalArgumentException e) {
      LOG.error("getDemandePretTopos() failed", e);
    }

    return demandePretTopos;
  }

  @Override
  public Utilisateur getUserByID(Integer userID) {

    Utilisateur utilisateur = null;

    try {
      utilisateur = em.find(Utilisateur.class, userID);
    } catch (IllegalArgumentException e) {
      LOG.error("getUserByID failed", e);
    }

    return utilisateur;
  }

  @Override
  public void acceptDemandTopo(Topo topo, Utilisateur user) {

    Topo topoUpdated = null;
    Utilisateur userUpdated = null;

    try {
      // Récupère le topo
      topoUpdated = em.find(Topo.class, topo.getTopoID());

      // Récupère l'utilisateur
      userUpdated = em.find(Utilisateur.class, user.getUtilisateurID());

      // Ajoute à l'utilisateur le topo en cours de prêt
      userUpdated.addPretTopo(topoUpdated);

      // Retire l'utilisateur de la liste de demande du topo
      topoUpdated.removeDemandePretUtilisateur(userUpdated);

      // Change l'état du topo à indisponible
      topoUpdated.setDisponible(false);

      // Met à jour l'utilisateur dans la BDD
      em.merge(userUpdated);

      // Met à jour le topo dans la BDD
      em.merge(topoUpdated);

    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("acceptDemandTopo() failed for topoID : {} userID : {}",
          topo.getTopoID(),
          user.getUtilisateurID(),
          e);
    }
  }

  @Override
  public void refuseDemandTopo(Topo topo, Utilisateur user) {

    Topo topoUpdated = null;
    Utilisateur userUpdated = null;

    try {
      // Récupère le topo
      topoUpdated = em.find(Topo.class, topo.getTopoID());

      // Récupère l'utilisateur
      userUpdated = em.find(Utilisateur.class, user.getUtilisateurID());

      // retire l'utilisateur de la liste de demande du topo
      topoUpdated.removeDemandePretUtilisateur(userUpdated);

      // Met à jour le topo dans la BDD
      em.merge(topoUpdated);

    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("refuseDemandTopo() failed for topoID : {} userID : {}",
          topo.getTopoID(),
          user.getUtilisateurID(),
          e);
    }
  }

  @Override
  public void cancelPretTopo(Topo topo) {

    Topo topoUpdated = null;

    try {
      // Récupère le topo
      topoUpdated = em.find(Topo.class, topo.getTopoID());

      // Supprime l'utilisateur en cours de prêt dans le topo
      topoUpdated.setPretUtilisateur(null);

      // Change le statut du topo en disponible
      topoUpdated.setDisponible(true);

      // Met à jour le topo dans la BDD
      em.merge(topoUpdated);

    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("cancelPretTopo() failed for topoID : {}", topo.getTopoID(), e);
    }
  }

}
