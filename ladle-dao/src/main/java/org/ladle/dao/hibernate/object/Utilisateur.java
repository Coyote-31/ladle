package org.ladle.dao.hibernate.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe des utilisateurs pour Hibernate
 * @author Coyote
 */
@Entity
@Table(name="utilisateur")
public class Utilisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="utilisateur_id")
	private Integer utilisateurID;
	@Column(name="ville_id")
	private Integer villeID;
	@Column(name="pseudo")
	private String pseudo;
	@Column(name="genre")
	private String genre;
	@Column(name="nom")
	private String nom;
	@Column(name="prenom")
	private String prenom;
	@Column(name="email")
	private String email;
	@Column(name="mdp")
	private String mdp;
	@Column(name="salt")
	private byte[] salt;
	@Column(name="role")
	private Integer role;
	
	/**
	 * Constructeurs
	 */
	
	public Utilisateur() {}

	public Utilisateur(Integer villeID, String pseudo, String genre, String nom, String prenom,	
			String email, String mdp, byte[] salt, Integer role) {
		super();
		this.villeID = villeID;
		this.pseudo = pseudo;
		this.genre = genre;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mdp = mdp;
		this.salt = salt;
		this.role = role;
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

	public Integer getVilleID() {
		return villeID;
	}

	public void setVilleID(Integer villeID) {
		this.villeID = villeID;
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
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
	
}
