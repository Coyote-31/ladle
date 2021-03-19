package org.ladle.beans;

public class VoieForm {

  // Loggeur
  // private static final Logger LOG = LogManager.getLogger(VoieForm.class);

  // Voie ID
  private String voieID;

  // Numéro
  private String numero;
  private boolean numeroErr;

  // Cotation
  private String cotation;
  private boolean cotationErr;

  // Nom
  private String nom;
  private boolean nomErr;

  // Hauteur
  private String hauteur;
  private boolean hauteurErr;

  // Dégaines
  private String degaine;
  private boolean degaineErr;

  // Remarques
  private String remarque;
  private boolean remarqueErr;

  // Constructeur
  public VoieForm() {
    super();
  }

  public String getVoieID() {
    return voieID;
  }

  public void setVoieID(String voieID) {
    this.voieID = voieID;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {

    final String NUMERO_REGEX = "^[1-9][0-9]{0,2}(bis|ter)?$";
    final int MAX_NUMERO_LENGTH = 6;

    if (numero.matches(NUMERO_REGEX) && (numero.length() <= MAX_NUMERO_LENGTH)) {
      numeroErr = false;
    } else {
      numeroErr = true;
    }

    this.numero = numero;
  }

  public boolean isNumeroErr() {
    return numeroErr;
  }

  public String getCotation() {
    return cotation;
  }

  public void setCotation(String cotation) {
    final String COTATION_REGEX = "^[3-9]([abc]\\+?)?$";
    final int MAX_COTATION_LENGTH = 3;

    if ((cotation == null) || cotation.isEmpty()) {
      cotationErr = false;
    } else if (cotation.matches(COTATION_REGEX) && (cotation.length() <= MAX_COTATION_LENGTH)) {
      cotationErr = false;
    } else {
      cotationErr = true;
    }

    this.cotation = cotation;
  }

  public boolean isCotationErr() {
    return cotationErr;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    final int MAX_NOM_LENGTH = 45;

    if ((nom == null) || nom.isEmpty()) {
      nomErr = false;
    } else if (nom.length() <= MAX_NOM_LENGTH) {
      nomErr = false;
    } else {
      nomErr = true;
    }

    this.nom = nom;
  }

  public boolean isNomErr() {
    return nomErr;
  }

  public String getHauteur() {
    return hauteur;
  }

  public void setHauteur(String hauteur) {
    final String HAUTEUR_REGEX = "^[1-9][0-9]{0,2}$";
    final int MAX_HAUTEUR_LENGTH = 3;

    if ((hauteur == null) || hauteur.isEmpty()) {
      hauteurErr = false;
    } else if (hauteur.matches(HAUTEUR_REGEX) && (hauteur.length() <= MAX_HAUTEUR_LENGTH)) {
      hauteurErr = false;
    } else {
      hauteurErr = true;
    }

    this.hauteur = hauteur;
  }

  public boolean isHauteurErr() {
    return hauteurErr;
  }

  public String getDegaine() {
    return degaine;
  }

  public void setDegaine(String degaine) {
    final String DEGAINE_REGEX = "^([0-9])|([1-9][0-9])$";
    final int MAX_DEGAINE_LENGTH = 2;

    if ((degaine == null) || degaine.isEmpty()) {
      degaineErr = false;
    } else if (degaine.matches(DEGAINE_REGEX) && (degaine.length() <= MAX_DEGAINE_LENGTH)) {
      degaineErr = false;
    } else {
      degaineErr = true;
    }

    this.degaine = degaine;
  }

  public boolean isDegaineErr() {
    return degaineErr;
  }

  public String getRemarque() {
    return remarque;
  }

  public void setRemarque(String remarque) {
    final int MAX_REMARQUE_LENGTH = 255;

    if ((remarque == null) || remarque.isEmpty()) {
      remarqueErr = false;
    } else if (remarque.length() <= MAX_REMARQUE_LENGTH) {
      remarqueErr = false;
    } else {
      remarqueErr = true;
    }

    this.remarque = remarque;
  }

  public boolean isRemarqueErr() {
    return remarqueErr;
  }

}
