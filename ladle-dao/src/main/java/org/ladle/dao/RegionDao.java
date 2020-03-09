package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.beans.jpa.Region;

@Local
public interface RegionDao {
	
	public List<Region> getAllRegions();

}
