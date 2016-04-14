package com.victor.fishhub.dao;

import com.victor.fishhub.dao.entity.DailyWeather;
import com.victor.fishhub.dao.entity.Fish;
import com.victor.fishhub.dao.entity.Location;
import java.util.List;

/**
 *
 * @author Віктор
 */
public interface FishhubDao {

    List<Location> getLazyActiveLocations();

    List<Location> getAllLocations();

    List<Location> getActiveLocations();

    List<Location> getNotActiveLocations();

    Location getLocation(int id);

    List<Location> getLocations(int... id);

    void addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocation(Location location);

    void updateDailyWeatherList(List<DailyWeather> weather);

    void deactivateLocation(int id);
    
    List<Fish> getDefaultFishlist();

}
