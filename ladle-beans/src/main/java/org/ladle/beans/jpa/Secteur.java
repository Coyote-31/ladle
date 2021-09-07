package org.ladle.beans.jpa;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe JPA des secteurs de la table "secteur" pour hibernate.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "[secteur]", schema = "[ladle_db]")
public class Secteur implements Serializable {

  private static final Logger LOG = LogManager.getLogger(Secteur.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "secteur_id")
  private Integer secteurID;
  @ManyToOne
  @JoinColumn(name = "site_id", nullable = false)
  private Site site;
  @Column(name = "nom", length = 80, nullable = false)
  private String nom;
  @Column(name = "date_last_maj", nullable = false)
  private Date dateLastMaj;
  @Column(name = "descriptif", length = 2000)
  private String descriptif;
  @Column(name = "acces", length = 2000)
  private String acces;
  @Column(name = "plan")
  private byte[] plan;
  @OneToMany(mappedBy = "secteur", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Voie> voies;

  /**
   * Constructeurs
   */

  public Secteur() {
    voies = new ArrayList<>();
  }

  public Secteur(
      Site site,
      String nom,
      Date dateLastMaj,
      String descriptif,
      String acces,
      byte[] plan) {
    super();
    this.site = site;
    this.nom = nom;
    this.dateLastMaj = (Date) dateLastMaj.clone();
    this.descriptif = descriptif;
    this.acces = acces;
    this.plan = plan.clone();
  }

  /**
   * Getters & Setters
   */

  public Integer getSecteurID() {
    return secteurID;
  }

  public void setSecteurID(Integer secteurID) {
    this.secteurID = secteurID;
  }

  public Site getSite() {
    return site;
  }

  public void setSite(Site site) {
    this.site = site;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public Date getDateLastMaj() {
    return (Date) dateLastMaj.clone();
  }

  public void setDateLastMaj(Date dateLastMaj) {
    this.dateLastMaj = (Date) dateLastMaj.clone();
  }

  public String getDescriptif() {
    return descriptif;
  }

  public void setDescriptif(String descriptif) {
    this.descriptif = descriptif;
  }

  public String getAcces() {
    return acces;
  }

  public void setAcces(String acces) {
    this.acces = acces;
  }

  public byte[] getPlan() {
    if (plan == null) {
      return null;
    }
    return plan.clone();
  }

  public void setPlan(byte[] plan) {
    if (plan == null) {
      this.plan = null;
      return;
    }
    this.plan = plan.clone();
  }

  public String getPlanBase64() {
    if (plan != null) {
      return Base64.getEncoder().encodeToString(plan);
    } else {
      return null;
    }
  }

  public void setPlanBase64(String plan) {
    if (plan != null) {
      this.plan = Base64.getDecoder().decode(plan);
    } else {
      this.plan = null;
    }
  }

  public List<Voie> getVoies() {
    List<Voie> voiesCPY = new ArrayList<>();

    for (Voie voie : voies) {
      voiesCPY.add(voie);
    }
    return voiesCPY;
  }

  public void addVoie(Voie voie) {
    voies.add(voie);
    voie.setSecteur(this);
  }

  public void removeVoie(Voie voie) {
    voies.remove(voie);
    voie.setSecteur(null);
  }

  // -------------------
  // Utility fonctions
  // -------------------

  public Integer getPlanWidth() {

    if ((plan == null)) {
      return null;

    } else {
      Integer secteurPlanWidth = null;

      try {
        BufferedImage bufferedSecteurPlan = ImageIO.read(new ByteArrayInputStream(plan));
        secteurPlanWidth = bufferedSecteurPlan.getWidth();

      } catch (IllegalArgumentException | IOException e) {
        LOG.error("Error BufferedImage to get width", e);
      }

      LOG.debug("secteurPlanWidth : {}", secteurPlanWidth);
      return secteurPlanWidth;
    }
  }

  public Integer getPlanHeight() {
    if ((plan == null)) {
      return null;

    } else {
      Integer secteurPlanHeight = null;

      try {
        BufferedImage bufferedSecteurPlan = ImageIO.read(new ByteArrayInputStream(plan));
        secteurPlanHeight = bufferedSecteurPlan.getHeight();

      } catch (IllegalArgumentException | IOException e) {
        LOG.error("Error BufferedImage to get heigth", e);
      }

      LOG.debug("secteurPlanHeight : {}", secteurPlanHeight);
      return secteurPlanHeight;
    }
  }

}
