/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service;

import com.victor.fishhub.dao.FishhubDao;
import com.victor.fishhub.dao.entity.Location;
import com.victor.fishhub.service.ratioalgo.FishRatio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("fishhubService")
class SrviceLayerImpl implements ServiceLayer {

    @Autowired
    @Qualifier("hibernateDao")
    FishhubDao dao;

    @Autowired
    @Qualifier("fishRatio")
    FishRatio fishRatio;

    @Override
    public Location getLocation(int id) {
        Location location = dao.getLocation(id);
        if (location.getFishlist().isEmpty()) {
            location.setFishlist(dao.getDefaultFishlist());
        }
        fishRatio.calculate(location);
        return location;
    }

    @Override
    @Async
    public void updateLocation(Location location) {
        dao.updateLocation(location);
    }

    @Override
    public List<Location> getLazyActiveLocations() {
        List<Location> locations = dao.getLazyActiveLocations();
        for (Location l : locations) {
            l.setWeatherList(null);
            l.setFishlist(null);
        }
        return locations;
    }

    @Override
    public List<Location> getAllLocations() {
        return dao.getAllLocations();
    }

    @Override
    public void deactivateLocation(int id) {
        dao.deactivateLocation(id);
    }

    @Override
    public List<Location> getNotActiveLocations() {
        return dao.getNotActiveLocations();
    }

    @Override
    @Async
    public void deleteLocation(Location location) {
        dao.deleteLocation(location);
    }
}
