package org.ladle.beans.jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe des Topos pour hibernate
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "[topo]", schema = "[ladle_db]")
public class Topo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "topo_id")
  private Integer topoID;
  @ManyToOne
  @JoinColumn(name = "utilisateur_id", referencedColumnName = "utilisateur_id", nullable = false)
  private Utilisateur utilisateur;
  @ManyToOne
  @JoinColumn(name = "region_id", referencedColumnName = "region_id", nullable = false)
  private Region region;
  @Column(name = "disponible", nullable = false)
  private boolean disponible;
  @ManyToOne
  @JoinColumn(name = "pret_utilisateur_id", referencedColumnName = "utilisateur_id")
  private Utilisateur pretUtilisateur;
  @Column(name = "nom", length = 80, nullable = false)
  private String nom;
  @Column(name = "description", length = 2000)
  private String description;
  @Column(name = "lieu", length = 80)
  private String lieu;
  @Column(name = "parution_date", nullable = false)
  private Timestamp parutionDate;
  /**
   * Liste des demandes de prêt des utilisateurs pour ce topo
   */
  @ManyToMany
  @JoinTable(name = "demande_pret",
             joinColumns = { @JoinColumn(name = "topo_id") },
             inverseJoinColumns = { @JoinColumn(name = "utilisateur_id") })
  private Set<Utilisateur> demandePretUtilisateurs = new HashSet<>();

  /**
   * Constructeurs
   */

  public Topo() {
    super();
  }

  public Topo(
      Utilisateur utilisateur,
      Region region,
      boolean disponible,
      Utilisateur pretUtilisateur,
      String nom,
      String description,
      String lieu,
      Timestamp parutionDate) {
    super();
    this.utilisateur = utilisateur;
    this.region = region;
    this.disponible = disponible;
    this.pretUtilisateur = pretUtilisateur;
    this.nom = nom;
    this.description = description;
    this.lieu = lieu;
    this.parutionDate = (Timestamp) parutionDate.clone();
  }

  /**
   * Getters & Setters
   */

  public Integer getTopoID() {
    return topoID;
  }

  public void setTopoID(Integer topoID) {
    this.topoID = topoID;
  }

  public Utilisateur getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public boolean isDisponible() {
    return disponible;
  }

  public void setDisponible(boolean disponible) {
    this.disponible = disponible;
  }

  public Utilisateur getPretUtilisateur() {
    return pretUtilisateur;
  }

  public void setPretUtilisateur(Utilisateur pretUtilisateur) {
    this.pretUtilisateur = pretUtilisateur;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLieu() {
    return lieu;
  }

  public void setLieu(String lieu) {
    this.lieu = lieu;
  }

  public Timestamp getParutionDate() {
    return (Timestamp) parutionDate.clone();
  }

  public void setParutionDate(Timestamp parutionDate) {
    this.parutionDate = (Timestamp) parutionDate.clone();
  }

  public Set<Utilisateur> getDemandePretUtilisateurs() {
    Set<Utilisateur> demandePretUtilisateursCPY = new HashSet<>();

    for (Utilisateur utilisateurFOR : demandePretUtilisateurs) {
      demandePretUtilisateursCPY.add(utilisateurFOR);
    }
    return demandePretUtilisateursCPY;
  }

  public Set<Utilisateur> getDemandePretUtilisateursByRef() {
    return demandePretUtilisateurs;
  }

  public void addDemandePretUtilisateur(Utilisateur utilisateur) {
    demandePretUtilisateurs.add(utilisateur);
    utilisateur.getDemandePretToposByRef().add(this);
  }

  public void removeDemandePretUtilisateur(Utilisateur utilisateur) {
    demandePretUtilisateurs.remove(utilisateur);
    utilisateur.getDemandePretToposByRef().remove(this);
  }

  public boolean isDemandePret(Utilisateur utilisateur) {

    for (Utilisateur user : demandePretUtilisateurs) {
      if (user.getPseudo().equals(utilisateur.getPseudo())) {
        return true;
      }
    }
    return false;
  }

}
