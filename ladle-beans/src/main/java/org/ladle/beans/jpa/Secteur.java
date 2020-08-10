package org.ladle.beans.jpa;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe JPA des secteurs de la table "secteur" pour hibernate.
 *
 * @author Coyote
 */
@Entity
@Table(name = "[secteur]", schema = "[ladle_db]")
public class Secteur {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "secteur_id")
  private Integer secteurID;
  @Column(name = "site_id")
  private Integer siteID;
  @Column(name = "nom")
  private String nom;
  @Column(name = "date_last_maj")
  private Date dateLastMaj;
  @Column(name = "descriptif")
  private String descriptif;
  @Column(name = "acces")
  private String acces;
  @Column(name = "plan")
  private byte[] plan;

  /**
   * Constructeurs
   */

  public Secteur() {
  }

  public Secteur(
      Integer siteID,
      String nom,
      Date dateLastMaj,
      String descriptif,
      String acces,
      byte[] plan) {
    super();
    this.siteID = siteID;
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

  public Integer getSiteID() {
    return siteID;
  }

  public void setSiteID(Integer siteID) {
    this.siteID = siteID;
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
    return plan.clone();
  }

  public void setPlan(byte[] plan) {
    this.plan = plan.clone();
  }

}
