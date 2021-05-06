package org.ladle.dao;

import javax.ejb.Local;

import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;

@Local
public interface EditeSiteSecteurDao {

  /**
   * Met à jour le secteur dans la BDD
   *
   * @param secteurUpdated
   */
  void update(Secteur secteurUpdated);

  /**
   * Met à jour le site dans la BDD
   *
   * @param siteUpdated
   */
  void update(Site siteUpdated);

  /**
   * Insère le nouveau secteur dans la BDD et retourne sont ID
   *
   * @param secteur
   * @return l'ID du secteur
   */
  Integer persist(Secteur secteur);

}
