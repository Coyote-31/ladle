package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Region;

@Local
public interface RechercheSiteSecteurDao {

  List<Region> getAllRegions();

}
