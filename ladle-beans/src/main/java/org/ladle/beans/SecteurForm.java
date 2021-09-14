package org.ladle.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.ladle.beans.jpa.Site;

/**
 * Classe représentant le secteur à éditer du formulaire.
 * Elle test chaque champs lors d'un set
 * Et gère sa liste d'erreurs.
 *
 * @author Coyote
 */
public class SecteurForm {

  // Loggeur
  // private static final Logger LOG = LogManager.getLogger(SecteurForm.class);

  // Site
  private Site site;

  // SecteurID
  private String secteurID;

  // Nom
  private String nom;
  private boolean nomErr;

  // Date de la dernière mise à jour
  private Timestamp dateLastMaj;

  // Descriptif
  private String descriptif;
  private boolean descriptifErr;

  // Accès
  private String acces;
  private boolean accesErr;

  // Plan
  private byte[] plan;
  private boolean planErr;

  // Voies
  private List<VoieForm> voies;

  // Constructeur
  public SecteurForm() {
    voies = new ArrayList<>();
  }

  // GETTER & SETTER

  public Site getSite() {
    return site;
  }

  public void setSite(Site site) {
    this.site = site;
  }

  public String getSecteurID() {
    return secteurID;
  }

  public void setSecteurID(String secteurID) {
    this.secteurID = secteurID;
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

  public Timestamp getDateLastMaj() {
    return (Timestamp) dateLastMaj.clone();
  }

  public void setDateLastMaj(Timestamp dateLastMaj) {
    this.dateLastMaj = (Timestamp) dateLastMaj.clone();
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

  public byte[] getPlan() {
    if (plan == null) {
      return null;
    }
    return plan.clone();
  }

  public void setPlan(byte[] plan) {
    if (plan == null) {
      this.plan = null;
      return;
    }
    this.plan = plan.clone();
  }

  public String getPlanBase64() {
    if (plan != null) {
      return Base64.getEncoder().encodeToString(plan);
    } else {
      return null;
    }
  }

  public void setPlanBase64(String plan) {
    if (plan != null) {
      this.plan = Base64.getDecoder().decode(plan);
    } else {
      this.plan = null;
    }
  }

  public boolean isPlanErr() {
    return planErr;
  }

  public void setPlanErr(boolean isErr) {
    planErr = isErr;
  }

  public List<VoieForm> getVoies() {
    List<VoieForm> voiesCPY = new ArrayList<>();
    for (VoieForm voie : voies) {
      voiesCPY.add(voie);
    }
    return voiesCPY;
  }

  public void setVoies(Iterable<VoieForm> voies) {
    List<VoieForm> voiesCPY = new ArrayList<>();
    for (VoieForm voie : voies) {
      voiesCPY.add(voie);
    }
    this.voies = voiesCPY;
  }

  public void addVoie(VoieForm voie) {
    voies.add(voie);
  }

  public void removeVoie(VoieForm voie) {
    voies.remove(voie);
  }

  // Test les erreurs dans l'objet
  public boolean isValid() {
    // Initialise la variable à renvoyer
    boolean secteurFormValid = false;

    // Recherche une erreur dans la liste de voies
    boolean voiesFormValid = true;

    for (VoieForm voie : voies) {
      if (voie.isNumeroErr()
          || voie.isCotationErr()
          || voie.isNomErr()
          || voie.isHauteurErr()
          || voie.isDegaineErr()
          || voie.isRemarqueErr()) {

        voiesFormValid = false;
      }
    }

    if (!nomErr
        && !descriptifErr
        && !accesErr
        && !planErr
        && voiesFormValid) {

      secteurFormValid = true;
    }

    return secteurFormValid;
  }
}
