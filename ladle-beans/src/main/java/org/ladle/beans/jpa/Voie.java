package org.ladle.beans.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Classe JPA des voies de la table "voie" pour hibernate.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "[voie]", schema = "[ladle_db]")
public class Voie implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "voie_id")
  private Integer voieID;
  @ManyToOne
  @JoinColumn(name = "secteur_id", nullable = false)
  private Secteur secteur;
  @Column(name = "numero", length = 8, nullable = false)
  private String numero;
  @Column(name = "cotation", length = 8)
  private String cotation;
  @Column(name = "nom", length = 45)
  private String nom;
  @Column(name = "hauteur")
  @Min(1)
  @Max(999)
  private Integer hauteur;
  @Column(name = "degaine")
  @Min(0)
  @Max(99)
  private Integer degaine;
  @Column(name = "remarque")
  private String remarque;

  /**
   * Constructeurs
   */

  public Voie() {
  }

  public Voie(
      Secteur secteur,
      String numero,
      String cotation,
      String nom,
      Integer hauteur,
      Integer degaine,
      String remarque) {
    super();
    this.secteur = secteur;
    this.numero = numero;
    this.cotation = cotation;
    this.nom = nom;
    this.hauteur = hauteur;
    this.degaine = degaine;
    this.remarque = remarque;
  }

  /**
   * Getters & Setters
   */

  public Integer getVoieID() {
    return voieID;
  }

  public void setVoieID(Integer voieID) {
    this.voieID = voieID;
  }

  public Secteur getSecteur() {
    return secteur;
  }

  public void setSecteur(Secteur secteur) {
    this.secteur = secteur;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getCotation() {
    return cotation;
  }

  public void setCotation(String cotation) {
    this.cotation = cotation;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public Integer getHauteur() {
    return hauteur;
  }

  public void setHauteur(Integer hauteur) {
    this.hauteur = hauteur;
  }

  public Integer getDegaine() {
    return degaine;
  }

  public void setDegaine(Integer degaine) {
    this.degaine = degaine;
  }

  public String getRemarque() {
    return remarque;
  }

  public void setRemarque(String remarque) {
    this.remarque = remarque;
  }

}
