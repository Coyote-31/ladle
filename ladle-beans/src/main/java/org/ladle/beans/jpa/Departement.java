package org.ladle.beans.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
  @ManyToOne
  @JoinColumn(name = "region_code", referencedColumnName = "region_code")
  private Region region;
  @Column(name = "departement_code", length = 3, nullable = false)
  private String departementCode;
  @Column(name = "nom", nullable = false)
  private String nom;
  @Column(name = "soundex", nullable = false)
  private String soundex;

  /**
   * Constructeurs
   */

  public Departement() {
  }

  public Departement(
      Region region,
      String departementCode,
      String nom,
      String soundex) {
    super();
    this.region = region;
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

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
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
