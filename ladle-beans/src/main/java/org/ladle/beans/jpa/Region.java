package org.ladle.beans.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

/**
 * Classe des régions pour hibernate
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "[region]", schema = "[ladle_db]")
public class Region implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "region_id")
  private Integer regionID;
  @NaturalId
  @Column(name = "region_code", length = 3, nullable = false)
  private String regionCode;
  @Column(name = "nom", nullable = false)
  private String nom;
  @Column(name = "soundex", nullable = false)
  private String soundex;
  @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Departement> departements;
  @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Topo> topos;

  /**
   * Constructeurs
   */

  public Region() {
    super();
    departements = new ArrayList<>();
    topos = new ArrayList<>();
  }

  public Region(String regionCode, String nom, String soundex) {
    super();
    this.regionCode = regionCode;
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

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
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

  public List<Departement> getDepartements() {
    List<Departement> departementsCPY = new ArrayList<>();

    for (Departement departement : departements) {
      departementsCPY.add(departement);
    }
    return departementsCPY;
  }

  public void addDepartement(Departement departement) {
    departements.add(departement);
    departement.setRegion(this);
  }

  public void removeDepartement(Departement departement) {
    departements.remove(departement);
    departement.setRegion(null);
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
    topo.setRegion(this);
  }

  public void removeTopo(Topo topo) {
    topos.remove(topo);
    topo.setRegion(null);
  }

}
