package org.ladle.dao.hibernate.object;

/**
 * Classe des utilisateurs pour Hibernate
 * @author Coyote
 */
public class Utilisateur {

	private Integer utilisateurID;
	private Integer villeID;
	private String pseudo;
	private String genre;
	private String nom;
	private String prenom;
	private String email;
	private String mdp;
	private byte[] salt;
	private byte role;
	
	/**
	 * Constructeurs
	 */
	
	public Utilisateur() {}

	public Utilisateur(Integer villeID, String pseudo, String genre, String nom, String prenom,	
			String email, String mdp, byte[] salt, byte role) {
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

	public byte getRole() {
		return role;
	}

	public void setRole(byte role) {
		this.role = role;
	}
	
}
