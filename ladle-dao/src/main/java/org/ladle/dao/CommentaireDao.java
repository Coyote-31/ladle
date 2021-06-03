package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Commentaire;

@Local
public interface CommentaireDao {

  /**
   * Renvoit la liste des commentaires d'un site depuis son ID.
   *
   * @param siteID
   * @return
   */
  List<Commentaire> getCommentairesBySiteID(Integer siteID);

  /**
   * Persiste le commentaire dans la BDD
   *
   * @param commentaire
   */
  void persistCommentaire(Commentaire commentaire);

}
