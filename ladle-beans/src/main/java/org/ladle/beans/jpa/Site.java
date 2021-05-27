package org.ladle.beans.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Classe JPA des sites de la table "site" pour hibernate.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "[site]", schema = "[ladle_db]")
public class Site implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "site_id")
  private Integer siteID;
  @ManyToOne
  @JoinColumn(name = "ville_id", nullable = false)
  private Ville ville;
  @Column(name = "nom", length = 80, nullable = false)
  private String nom;
  @Column(name = "officiel", nullable = false)
  private boolean officiel;
  @Column(name = "date_last_maj", nullable = false)
  private Date dateLastMaj;
  @Column(name = "descriptif", length = 2000)
  private String descriptif;
  @Column(name = "acces", length = 2000)
  private String acces;
  @Column(name = "latitude")
  private BigDecimal latitude;
  @Column(name = "longitude")
  private BigDecimal longitude;
  @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Secteur> secteurs;
  @OneToMany(mappedBy = "commentaire", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Commentaire> commentaires;

  /**
   * Constructeurs
   */

  public Site() {
    secteurs = new ArrayList<>();
    commentaires = new ArrayList<>();
  }

  public Site(
      Ville ville,
      String nom,
      boolean officiel,
      Date dateLastMaj,
      String descriptif,
      String acces,
      BigDecimal latitude,
      BigDecimal longitude) {
    super();
    this.ville = ville;
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

  public Ville getVille() {
    return ville;
  }

  public void setVille(Ville ville) {
    this.ville = ville;
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

  public List<Secteur> getSecteurs() {
    List<Secteur> secteursCPY = new ArrayList<>();

    for (Secteur secteur : secteurs) {
      secteursCPY.add(secteur);
    }
    return secteursCPY;
  }

  public void addSecteur(Secteur secteur) {
    secteurs.add(secteur);
    secteur.setSite(this);
  }

  public void removeSecteur(Secteur secteur) {
    secteurs.remove(secteur);
    secteur.setSite(null);
  }

  public List<Commentaire> getCommentaires() {
    List<Commentaire> commentairesCPY = new ArrayList<>();

    for (Commentaire commentaire : commentaires) {
      commentairesCPY.add(commentaire);
    }
    return commentairesCPY;
  }

  public void addCommentaire(Commentaire commentaire) {
    commentaires.add(commentaire);
    commentaire.setSite(this);
  }

  public void removeCommentaire(Commentaire commentaire) {
    commentaires.remove(commentaire);
    commentaire.setSite(null);
  }

}
