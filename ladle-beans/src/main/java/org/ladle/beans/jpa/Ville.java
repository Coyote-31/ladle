package org.ladle.beans.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
 * Classe JPA des villes de la table "ville" pour hibernate.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "[ville]", schema = "[ladle_db]")
public class Ville implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ville_id")
  private Integer villeID;
  @ManyToOne
  @JoinColumn(name = "departement_code", referencedColumnName = "departement_code", nullable = false)
  private Departement departement;
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
  @OneToMany(mappedBy = "ville")
  private List<Site> sites;
  @OneToMany(mappedBy = "ville")
  private List<Utilisateur> utilisateurs;

  /**
   * Constructeurs
   */

  public Ville() {
  }

  public Ville(
      Departement departement,
      String cp,
      String nom,
      String soundex,
      BigDecimal latitude,
      BigDecimal longitude) {
    super();
    this.departement = departement;
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

  public Departement getDepartement() {
    return departement;
  }

  public void setDepartement(Departement departement) {
    this.departement = departement;
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

  public List<Site> getSites() {
    List<Site> sitesCPY = new ArrayList<>();

    for (Site site : sites) {
      sitesCPY.add(site);
    }
    return sitesCPY;
  }

  public void addSite(Site site) {
    sites.add(site);
    site.setVille(this);
  }

  public void removeSite(Site site) {
    sites.remove(site);
    site.setVille(null);
  }

  public List<Utilisateur> getUtilisateurs() {
    List<Utilisateur> utilisateursCPY = new ArrayList<>();

    for (Utilisateur utilisateur : utilisateurs) {
      utilisateursCPY.add(utilisateur);
    }
    return utilisateursCPY;
  }

  public void addUtilisateur(Utilisateur utilisateur) {
    utilisateurs.add(utilisateur);
    utilisateur.setVille(this);
  }

  public void removeUtilisateur(Utilisateur utilisateur) {
    utilisateurs.remove(utilisateur);
    utilisateur.setVille(null);
  }

}
