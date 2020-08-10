package org.ladle.beans.jpa;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe JPA des sites de la table "site" pour hibernate.
 *
 * @author Coyote
 */
@Entity
@Table(name = "[site]", schema = "[ladle_db]")
public class Site {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "site_id")
  private Integer siteID;
  @Column(name = "ville_id", nullable = false)
  private Integer villeID;
  @Column(name = "nom", length = 45, nullable = false)
  private String nom;
  @Column(name = "officiel", nullable = false)
  private boolean officiel;
  @Column(name = "date_last_maj", nullable = false)
  private Date dateLastMaj;
  @Column(name = "descriptif")
  private String descriptif;
  @Column(name = "acces")
  private String acces;
  @Column(name = "latitude")
  private BigDecimal latitude;
  @Column(name = "longitude")
  private BigDecimal longitude;

  /**
   * Constructeurs
   */

  public Site() {
  }

  public Site(
      Integer villeID,
      String nom,
      boolean officiel,
      Date dateLastMaj,
      String descriptif,
      String acces,
      BigDecimal latitude,
      BigDecimal longitude) {
    super();
    this.villeID = villeID;
    this.nom = nom;
    this.officiel = officiel;
    this.dateLastMaj = (Date) dateLastMaj.clone();
    this.descriptif = descriptif;
    this.acces = acces;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Getters & Setters
   */

  public Integer getSiteID() {
    return siteID;
  }

  public void setSiteID(Integer siteID) {
    this.siteID = siteID;
  }

  public Integer getVilleID() {
    return villeID;
  }

  public void setVilleID(Integer villeID) {
    this.villeID = villeID;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public boolean isOfficiel() {
    return officiel;
  }

  public void setOfficiel(boolean officiel) {
    this.officiel = officiel;
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

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

}
