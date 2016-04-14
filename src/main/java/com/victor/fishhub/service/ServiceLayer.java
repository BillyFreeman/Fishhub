package com.victor.fishhub.service;

import com.victor.fishhub.dao.entity.Location;
import java.util.List;

/**
 *
 * @author Віктор
 */
public interface ServiceLayer {

    void updateLocation(Location location);

    List<Location> getAllLocations();

    List<Location> getLazyActiveLocations();

    List<Location> getNotActiveLocations();

    void deactivateLocation(int id);
    
    void deleteLocation(Location location);
    
    Location getLocation(int id);
}
