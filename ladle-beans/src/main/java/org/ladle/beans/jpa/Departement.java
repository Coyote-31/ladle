package org.ladle.beans.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe JPA des départements de la table "departement" pour hibernate.
 *
 * @author Coyote
 */
@Entity
@Table(name = "[departement]", schema = "[ladle_db]")
public class Departement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "departement_id")
  private Integer departementID;
  @Column(name = "region_code")
  private String regionCode;
  @Column(name = "departement_code")
  private String departementCode;
  @Column(name = "nom")
  private String nom;
  @Column(name = "soundex")
  private String soundex;

  /**
   * Constructeurs
   */

  public Departement() {
  }

  public Departement(
      String regionCode,
      String departementCode,
      String nom,
      String soundex) {
    super();
    this.regionCode = regionCode;
    this.departementCode = departementCode;
    this.nom = nom;
    this.soundex = soundex;
  }

  /**
   * Getters & Setters
   */

  public Integer getDepartementID() {
    return departementID;
  }

  public void setDepartementID(Integer departementID) {
    this.departementID = departementID;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
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

}
