package org.ladle.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Ville;

/**
 * Classe représentant le site à éditer du formulaire.
 * Elle test chaque champs lors d'un set
 * Et gère sa liste d'erreurs.
 *
 * @author Coyote
 */
public class SiteForm {

  // Loggeur
  // private static final Logger LOG = LogManager.getLogger(SiteForm.class);

  // Ville
  private Ville ville;

  // SiteID
  private String siteID;

  // Nom
  private String nom;
  private boolean nomErr;

  // Date de la dernière mise à jour
  private Date dateLastMaj;

  // Tag officiel LADLE
  private boolean officiel;

  // Descriptif
  private String descriptif;
  private boolean descriptifErr;

  // Accès
  private String acces;
  private boolean accesErr;

  // Secteurs
  private List<Secteur> secteurs;

  // Constructeur
  public SiteForm() {
    secteurs = new ArrayList<>();
  }

  // GETTER & SETTER

  public Ville getVille() {
    return ville;
  }

  public void setVille(Ville ville) {
    this.ville = ville;
  }

  public String getSiteID() {
    return siteID;
  }

  public void setSiteID(String siteID) {
    this.siteID = siteID;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    final int MAX_NOM_LENGTH = 80;
    if (nom.length() > MAX_NOM_LENGTH) {
      nomErr = true;
    } else {
      nomErr = false;
    }
    this.nom = nom;
  }

  public boolean isNomErr() {
    return nomErr;
  }

  public Date getDateLastMaj() {
    return (Date) dateLastMaj.clone();
  }

  public void setDateLastMaj(Date dateLastMaj) {
    this.dateLastMaj = (Date) dateLastMaj.clone();
  }

  public boolean isOfficiel() {
    return officiel;
  }

  public void setOfficiel(boolean officiel) {
    this.officiel = officiel;
  }

  public String getDescriptif() {
    return descriptif;
  }

  public void setDescriptif(String descriptif) {
    final int MAX_DESCRIPTIF_LENGTH = 2000;
    if (descriptif.length() > MAX_DESCRIPTIF_LENGTH) {
      descriptifErr = true;
    } else {
      descriptifErr = false;
    }
    this.descriptif = descriptif;
  }

  public boolean isDescriptifErr() {
    return descriptifErr;
  }

  public String getAcces() {
    return acces;
  }

  public void setAcces(String acces) {
    final int MAX_ACCES_LENGTH = 2000;
    if (acces.length() > MAX_ACCES_LENGTH) {
      accesErr = true;
    } else {
      accesErr = false;
    }
    this.acces = acces;
  }

  public boolean isAccesErr() {
    return accesErr;
  }

  public List<Secteur> getSecteurs() {
    List<Secteur> secteursCPY = new ArrayList<>();
    for (Secteur secteur : secteurs) {
      secteursCPY.add(secteur);
    }
    return secteursCPY;
  }

  public void setSecteurs(Iterable<Secteur> secteurs) {
    List<Secteur> secteursCPY = new ArrayList<>();
    for (Secteur secteur : secteurs) {
      secteursCPY.add(secteur);
    }
    this.secteurs = secteursCPY;
  }

  public void addSecteur(Secteur secteur) {
    secteurs.add(secteur);
  }

  public void removeSecteur(Secteur secteur) {
    secteurs.remove(secteur);
  }

  // Test les erreurs dans l'objet
  public boolean isValid() {
    // Initialise la variable à renvoyer
    boolean siteFormValid = false;

    if (!nomErr
        && !descriptifErr
        && !accesErr) {

      siteFormValid = true;
    }

    return siteFormValid;
  }
}
