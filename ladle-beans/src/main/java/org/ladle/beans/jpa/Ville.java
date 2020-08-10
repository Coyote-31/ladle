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
  @Column(name = "departement_code")
  private String departementCode;
  @Column(name = "nom")
  private String nom;
  @Column(name = "soundex")
  private String soundex;
  @Column(name = "latitude")
  private BigDecimal latitude;
  @Column(name = "longitude")
  private BigDecimal longitude;

  /**
   * Constructeurs
   */

  public Ville() {
  }

  public Ville(
      String departementCode,
      String nom,
      String soundex,
      BigDecimal latitude,
      BigDecimal longitude) {
    super();
    this.departementCode = departementCode;
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
