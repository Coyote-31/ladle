package org.ladle.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Commentaire;
import org.ladle.dao.CommentaireDao;

/**
 * Classe de gestion des commentaires des sites.
 *
 * @author Coyote
 */
@Stateless
public class CommentaireHandler {

  private static final Logger LOG = LogManager.getLogger(CommentaireHandler.class);

  @EJB(name = "CommentaireDaoImpl")
  private CommentaireDao commentaireDao;

  /**
   * Renvoit tous les commentaires depuis l'ID d'un site
   *
   * @param siteID
   * @return
   */
  public List<Commentaire> getCommentairesBySiteID(Integer siteID) {

    LOG.debug("getCommentairesBySiteID for ID = {}", siteID);
    return commentaireDao.getCommentairesBySiteID(siteID);
  }

  /**
   * Persiste le commentaire dans la BDD
   *
   * @param commentaire
   */
  public void persistCommentaire(Commentaire commentaire) {
    commentaireDao.persistCommentaire(commentaire);
  }

}
