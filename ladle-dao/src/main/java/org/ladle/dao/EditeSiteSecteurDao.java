package org.ladle.dao;

import javax.ejb.Local;

import org.ladle.beans.jpa.Secteur;

@Local
public interface EditeSiteSecteurDao {

  /**
   * Met à jour le secteur dans la BDD
   *
   * @param secteurUpdated
   */
  void update(Secteur secteurUpdated);

}
