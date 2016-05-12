package com.victor.fishhub.dao;

import com.victor.fishhub.dao.entity.DailyWeather;
import com.victor.fishhub.dao.entity.Fish;
import com.victor.fishhub.dao.entity.Location;
import java.util.List;

public interface FishhubDao {

    //get active locations list without any ralations initialization
    List<Location> getLazyActiveLocations();

    List<Location> getAllLocations();

    List<Location> getActiveLocations();

    List<Location> getNotActiveLocations();

    Location getLocation(int id);

    List<Location> getLocations(int... id);

    void addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocation(Location location);

    void deactivateLocation(int id);
    
    void updateDailyWeatherList(List<DailyWeather> weather);

    List<Fish> getDefaultFishlist();

}
