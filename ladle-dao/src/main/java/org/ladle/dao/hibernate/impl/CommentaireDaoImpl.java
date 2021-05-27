package org.ladle.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;

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
      String hql = "FROM Commentaire C WHERE C.site.siteID = :siteID";

      Query query = em.createQuery(hql, Commentaire.class);
      query.setParameter("siteID", siteID);
      commentaires = query.getResultList();

      LOG.debug("Found : {} commentaires", commentaires.size());

    } catch (IllegalArgumentException | QueryTimeoutException e) {
      LOG.error("Error ! Failed to get commentaires from siteID = {}", siteID, e);
    }
    return commentaires;
  }

}
