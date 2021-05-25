package org.ladle.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;
import org.ladle.dao.EditeSiteSecteurDao;

/**
 * Classe de gestion des ajouts et editions des Sites et des Secteur.
 *
 * @author Coyote
 */
@Stateless
public class EditeSiteSecteurHandler {

  @SuppressWarnings("unused")
  private static final Logger LOG = LogManager.getLogger(EditeSiteSecteurHandler.class);

  @EJB(name = "EditeSiteSecteurDaoImpl")
  private EditeSiteSecteurDao editeSiteSecteurDao;

  /**
   * Persiste dans la BDD le site
   *
   * @param site
   * @return l'ID du site
   */
  public Integer persist(Site site) {
    return editeSiteSecteurDao.persist(site);
  }

  /**
   * Met à jour dans la BDD le site
   *
   * @param siteUpdated
   */
  public void update(Site siteUpdated) {
    editeSiteSecteurDao.update(siteUpdated);
  }

  /**
   * Supprime le site de la BDD
   *
   * @param site
   */
  public void remove(Site site) {
    editeSiteSecteurDao.remove(site);
  }

  /**
   * Persiste dans la BDD le secteur
   *
   * @param secteur
   * @return l'ID du secteur
   */
  public Integer persist(Secteur secteur) {
    return editeSiteSecteurDao.persist(secteur);
  }

  /**
   * Met à jour dans la BDD le secteur
   *
   * @param secteurUpdated
   */
  public void update(Secteur secteurUpdated) {
    editeSiteSecteurDao.update(secteurUpdated);
  }

  /**
   * Supprime le secteur de la BDD
   *
   * @param secteur
   */
  public void remove(Secteur secteur) {
    editeSiteSecteurDao.remove(secteur);
  }

}
