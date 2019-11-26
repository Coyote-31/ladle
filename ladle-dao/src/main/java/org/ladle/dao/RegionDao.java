package org.ladle.dao;

import java.util.List;

import javax.ejb.Local;

import org.ladle.dao.hibernate.object.Region;

@Local
public interface RegionDao {
	
	public List<Region> getAllRegions();

}
