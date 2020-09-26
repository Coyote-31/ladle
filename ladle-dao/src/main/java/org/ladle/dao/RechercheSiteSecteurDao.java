package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Departement;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Ville;

@Local
public interface RechercheSiteSecteurDao {

  List<Region> getAllRegions();

  List<Departement> getAllDepartements();

  List<Departement> getDepartementsByRegionCode(String regionCode);

  List<Ville> getVillesByCP(String codePostal);

}
