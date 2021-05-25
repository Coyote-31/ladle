package org.ladle.dao;

import javax.ejb.Local;

import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;

@Local
public interface EditeSiteSecteurDao {

  /**
   * Insère le nouveau site dans la BDD et retourne son ID
   *
   * @param site
   * @return l'ID du site
   */
  Integer persist(Site site);

  /**
   * Met à jour le site dans la BDD
   *
   * @param siteUpdated
   */
  void update(Site siteUpdated);

  /**
   * Supprime le site de la BDD
   *
   * @param site
   */
  void remove(Site site);

  /**
   * Insère le nouveau secteur dans la BDD et retourne son ID
   *
   * @param secteur
   * @return l'ID du secteur
   */
  Integer persist(Secteur secteur);

  /**
   * Met à jour le secteur dans la BDD
   *
   * @param secteurUpdated
   */
  void update(Secteur secteurUpdated);

  /**
   * Supprime le secteur de la BDD
   *
   * @param secteur
   */
  void remove(Secteur secteur);

}
