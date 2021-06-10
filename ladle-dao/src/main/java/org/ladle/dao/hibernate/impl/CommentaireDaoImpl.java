package org.ladle.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Commentaire;
import org.ladle.dao.CommentaireDao;

@Stateless
public class CommentaireDaoImpl implements CommentaireDao {

  private static final Logger LOG = LogManager.getLogger(CommentaireDaoImpl.class);

  @PersistenceContext(unitName = "ladleMySQLPU", type = PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public CommentaireDaoImpl() {
    super();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Commentaire> getCommentairesBySiteID(Integer siteID) {

    List<Commentaire> commentaires = new ArrayList<>();

    try {
      String hql = "FROM Commentaire C WHERE C.site.siteID = :siteID "
                   + "ORDER BY C.dateCreation DESC, C.commentaireID DESC";

      Query query = em.createQuery(hql, Commentaire.class);
      query.setParameter("siteID", siteID);
      commentaires = query.getResultList();

      LOG.debug("Found : {} commentaires", commentaires.size());

    } catch (IllegalArgumentException | QueryTimeoutException e) {
      LOG.error("Error ! Failed to get commentaires from siteID = {}", siteID, e);
    }
    return commentaires;
  }

  @Override
  public void persistCommentaire(Commentaire commentaire) {

    try {
      em.persist(commentaire);
      em.flush();
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("Persist commentaire failed", e);
    }
  }

  @Override
  public void removeCommentaireByID(Integer commentaireID) {
    try {
      Commentaire commentaireToDelete = em.find(Commentaire.class, commentaireID);
      em.remove(commentaireToDelete);
      flushAndClear();
      LOG.debug("Remove success of commentaire ID:{}", commentaireID);
    } catch (IllegalArgumentException | TransactionRequiredException e) {
      LOG.error("Remove commentaire failed", e);
    }

  }

  /**
   * Helper method to flush and clear the persistence context
   */
  void flushAndClear() {
    em.flush();
    em.clear();
  }

}
