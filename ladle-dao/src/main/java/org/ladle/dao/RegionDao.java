package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Region;

@Local
public interface RegionDao {

  List<Region> getAllRegions();

  void find(Region region);

}
