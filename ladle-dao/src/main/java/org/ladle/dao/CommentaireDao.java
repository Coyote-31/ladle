package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Commentaire;

/**
 * Classe d'interface DAO de gestion des commentaires.
 *
 * @author Coyote
 */
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
   * Renvoit le commentaire depuis son ID
   *
   * @param commentaireID
   * @return
   */
  Commentaire getCommentaireByID(Integer commentaireID);

  /**
   * Persiste le commentaire dans la BDD
   *
   * @param commentaire
   */
  void persistCommentaire(Commentaire commentaire);

  /**
   * Met à jour un commentaire dans la BDD
   *
   * @param commentaire
   */
  void updateCommentaire(Commentaire commentaire);

  /**
   * Supprime le commentaire de la BDD depuis son ID
   *
   * @param commentaireID
   */
  void removeCommentaireByID(Integer commentaireID);

}
