package org.ladle.beans.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe JPA des voies de la table "voie" pour hibernate.
 *
 * @author Coyote
 */
@Entity
@Table(name = "[voie]", schema = "[ladle_db]")
public class Voie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "voie_id")
  private Integer voieID;
  @Column(name = "secteur_id", nullable = false)
  private Integer secteurID;
  @Column(name = "numero", length = 8, nullable = false)
  private String numero;
  @Column(name = "cotation", length = 8)
  private String cotation;
  @Column(name = "nom", length = 45)
  private String nom;
  @Column(name = "hauteur")
  private Integer hauteur;
  @Column(name = "degaine")
  private Integer degaine;
  @Column(name = "remarque")
  private String remarque;

  /**
   * Constructeurs
   */

  public Voie() {
  }

  public Voie(
      Integer secteurID,
      String numero,
      String cotation,
      String nom,
      Integer hauteur,
      Integer degaine,
      String remarque) {
    super();
    this.secteurID = secteurID;
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

  public Integer getSecteurID() {
    return secteurID;
  }

  public void setSecteurID(Integer secteurID) {
    this.secteurID = secteurID;
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
