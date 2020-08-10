package org.ladle.beans.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe JPA des villes de la table "ville" pour hibernate.
 *
 * @author Coyote
 */
@Entity
@Table(name = "[ville]", schema = "[ladle_db]")
public class Ville {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ville_id")
  private Integer villeID;
  @Column(name = "departement_code", length = 3, nullable = false)
  private String departementCode;
  @Column(name = "cp", length = 5, nullable = false)
  private String cp;
  @Column(name = "nom", nullable = false)
  private String nom;
  @Column(name = "soundex", nullable = false)
  private String soundex;
  @Column(name = "latitude", nullable = false)
  private BigDecimal latitude;
  @Column(name = "longitude", nullable = false)
  private BigDecimal longitude;

  /**
   * Constructeurs
   */

  public Ville() {
  }

  public Ville(
      String departementCode,
      String cp,
      String nom,
      String soundex,
      BigDecimal latitude,
      BigDecimal longitude) {
    super();
    this.departementCode = departementCode;
    this.cp = cp;
    this.nom = nom;
    this.soundex = soundex;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Getters & Setters
   */

  public Integer getVilleID() {
    return villeID;
  }

  public void setVilleID(Integer villeID) {
    this.villeID = villeID;
  }

  public String getDepartementCode() {
    return departementCode;
  }

  public void setDepartementCode(String departementCode) {
    this.departementCode = departementCode;
  }

  public String getCp() {
    return cp;
  }

  public void setCp(String cp) {
    this.cp = cp;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getSoundex() {
    return soundex;
  }

  public void setSoundex(String soundex) {
    this.soundex = soundex;
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
