package org.ladle.beans.jpa;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe des commentaires pour Hibernate
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "[commentaire]", schema = "[ladle_db]")
public class Commentaire implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "commentaire_id")
  private Integer commentaireID;
  @ManyToOne
  @JoinColumn(name = "utilisateur_id", nullable = false)
  private Utilisateur utilisateur;
  @ManyToOne
  @JoinColumn(name = "site_id", nullable = false)
  private Site site;
  @Column(name = "date_creation", nullable = false)
  private Timestamp dateCreation;
  @Column(name = "contenu", length = 2000, nullable = false)
  private String contenu;

  /**
   * Constructeurs
   */

  public Commentaire() {
    super();
  }

  public Commentaire(
      Utilisateur utilisateur,
      Site site,
      Timestamp dateCreation,
      String contenu) {
    super();
    this.utilisateur = utilisateur;
    this.site = site;
    this.dateCreation = (Timestamp) dateCreation.clone();
    this.contenu = contenu;
  }

  /**
   * Getters & Setters
   */

  public Integer getCommentaireID() {
    return commentaireID;
  }

  public void setCommentaireID(Integer commentaireID) {
    this.commentaireID = commentaireID;
  }

  public Utilisateur getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
  }

  public Site getSite() {
    return site;
  }

  public void setSite(Site site) {
    this.site = site;
  }

  public Timestamp getDateCreation() {
    return (Timestamp) dateCreation.clone();
  }

  public void setDateCreation(Timestamp dateCreation) {
    this.dateCreation = (Timestamp) dateCreation.clone();
  }

  public String getContenu() {
    return contenu;
  }

  public void setContenu(String contenu) {
    this.contenu = contenu;
  }

}
