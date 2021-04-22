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
   * Met à jour dans la BDD le secteur
   *
   * @param secteurUpdated
   */
  public void update(Secteur secteurUpdated) {
    editeSiteSecteurDao.update(secteurUpdated);
  }

  /**
   * Met à jour dans la BDD le site
   *
   * @param siteUpdated
   */
  public void update(Site siteUpdated) {
    editeSiteSecteurDao.update(siteUpdated);
  }

}
