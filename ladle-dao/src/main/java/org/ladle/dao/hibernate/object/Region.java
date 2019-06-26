package org.ladle.dao.hibernate.object;

/**
 * Classe de test de BDD avec hibernate
 * @author Coyote
 */
public class Region {

	private Integer regionID;
	private String nom;
	private String soundex;
	
	/**
	 * Constructeurs
	 */
	
	public Region() {}

	public Region(String nom, String soundex) {
		super();
		this.nom = nom;
		this.soundex = soundex;
	}

	/**
	 * Getters & Setters
	 */
	
	public Integer getRegionID() {
		return regionID;
	}

	public void setRegionID(Integer regionID) {
		this.regionID = regionID;
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
	
}
