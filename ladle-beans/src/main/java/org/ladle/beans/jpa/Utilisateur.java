package org.ladle.beans.jpa;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * Classe des utilisateurs pour Hibernate
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "[utilisateur]", schema = "[ladle_db]")
public class Utilisateur implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "utilisateur_id")
  private Integer utilisateurID;
  @ManyToOne
  @JoinColumn(name = "ville_id", referencedColumnName = "ville_id", nullable = false)
  private Ville ville;
  @Column(name = "pseudo", length = 30, nullable = false)
  private String pseudo;
  @Column(name = "genre", length = 45, nullable = false)
  private String genre;
  @Column(name = "nom", length = 40, nullable = false)
  private String nom;
  @Column(name = "prenom", length = 40, nullable = false)
  private String prenom;
  @Column(name = "email", length = 90, nullable = false)
  private String email;
  @Column(name = "mdp", length = 64, nullable = false)
  private String mdp;
  @Column(name = "salt", nullable = false)
  private byte[] salt;
  @Column(name = "role", nullable = false)
  private Integer role;
  @Column(name = "email_sha", length = 64)
  private String emailSHA;
  @Column(name = "date_email", nullable = false)
  private Timestamp dateEmail;
  @Column(name = "date_compte", nullable = false)
  private Timestamp dateCompte;
  @Column(name = "token_login", length = 64)
  private String tokenLogin;
  @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Commentaire> commentaires;
  /**
   * Liste des topos de l'utilisateur
   */
  @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Topo> topos;
  /**
   * Liste des topos prêtés à l'utilisateur
   */
  @OneToMany(mappedBy = "pretUtilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Topo> pretTopos;

  /**
   * Constructeurs
   */

  public Utilisateur() {
    super();
    commentaires = new ArrayList<>();
    topos = new ArrayList<>();
    pretTopos = new ArrayList<>();
  }

  public Utilisateur(
      Ville ville,
      String pseudo,
      String genre,
      String nom,
      String prenom,
      String email,
      String mdp,
      byte[] salt,
      Integer role,
      String emailSHA,
      Timestamp dateEmail,
      Timestamp dateCompte) {
    super();
    this.ville = ville;
    this.pseudo = pseudo;
    this.genre = genre;
    this.nom = nom;
    this.prenom = prenom;
    this.email = email;
    this.mdp = mdp;
    this.salt = salt.clone();
    this.role = role;
    this.emailSHA = emailSHA;
    this.dateEmail = (Timestamp) dateEmail.clone();
    this.dateCompte = (Timestamp) dateCompte.clone();
  }

  /**
   * Getters & Setters
   */

  public Integer getUtilisateurID() {
    return utilisateurID;
  }

  public void setUtilisateurID(Integer utilisateurID) {
    this.utilisateurID = utilisateurID;
  }

  public Ville getVille() {
    return ville;
  }

  public void setVille(Ville ville) {
    this.ville = ville;
  }

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMdp() {
    return mdp;
  }

  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  public byte[] getSalt() {
    return salt.clone();
  }

  public void setSalt(byte[] salt) {
    this.salt = salt.clone();
  }

  public Integer getRole() {
    return role;
  }

  public void setRole(Integer role) {
    this.role = role;
  }

  public String getEmailSHA() {
    return emailSHA;
  }

  public void setEmailSHA(String emailSHA) {
    this.emailSHA = emailSHA;
  }

  public Timestamp getDateEmail() {
    return (Timestamp) dateEmail.clone();
  }

  public void setDateEmail(Timestamp dateEmail) {
    this.dateEmail = (Timestamp) dateEmail.clone();
  }

  public Timestamp getDateCompte() {
    return (Timestamp) dateCompte.clone();
  }

  public void setDateCompte(Timestamp dateCompte) {
    this.dateCompte = (Timestamp) dateCompte.clone();
  }

  public String getTokenLogin() {
    return tokenLogin;
  }

  public void setTokenLogin(String tokenLogin) {
    this.tokenLogin = tokenLogin;
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
    commentaire.setUtilisateur(this);
  }

  public void removeCommentaire(Commentaire commentaire) {
    commentaires.remove(commentaire);
    commentaire.setUtilisateur(null);
  }

  public List<Topo> getTopos() {
    List<Topo> toposCPY = new ArrayList<>();

    for (Topo topo : topos) {
      toposCPY.add(topo);
    }
    return toposCPY;
  }

  public void addTopo(Topo topo) {
    topos.add(topo);
    topo.setUtilisateur(this);
  }

  public void removeTopo(Topo topo) {
    topos.remove(topo);
    topo.setUtilisateur(null);
  }

  public List<Topo> getPretTopos() {
    List<Topo> pretToposCPY = new ArrayList<>();

    for (Topo topo : pretTopos) {
      pretToposCPY.add(topo);
    }
    return pretToposCPY;
  }

  public void addPretTopo(Topo topo) {
    pretTopos.add(topo);
    topo.setPretUtilisateur(this);
  }

  public void removePretTopo(Topo topo) {
    pretTopos.remove(topo);
    topo.setPretUtilisateur(null);
  }

}
