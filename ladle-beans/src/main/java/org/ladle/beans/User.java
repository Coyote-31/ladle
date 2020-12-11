package org.ladle.beans;

import java.sql.Timestamp;

/**
 * Classe utilitaire de stockage d'informations d'un utilisateur.
 *
 * @author Coyote
 */
public class User {

  private String login;
  private String pseudo;
  private String genre;
  private String prenom;
  private String nom;
  private String email;
  private String cp;
  private String ville;
  private Integer villeID;
  private String mdp;
  private String mdp2;
  private byte[] salt;
  private String mdpSecured;
  private String emailSHA;
  private Timestamp dateEmail;
  private Timestamp dateCompte;

  public User() {
    super();
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
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

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCp() {
    return cp;
  }

  public void setCp(String cp) {
    this.cp = cp;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public Integer getVilleID() {
    return villeID;
  }

  public void setVilleID(Integer villeID) {
    this.villeID = villeID;
  }

  public String getMdp() {
    return mdp;
  }

  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  public String getMdp2() {
    return mdp2;
  }

  public void setMdp2(String mdp2) {
    this.mdp2 = mdp2;
  }

  public byte[] getSalt() {
    return salt.clone();
  }

  public void setSalt(byte[] salt) {
    this.salt = salt.clone();
  }

  public String getMdpSecured() {
    return mdpSecured;
  }

  public void setMdpSecured(String mdpSecured) {
    this.mdpSecured = mdpSecured;
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

}
