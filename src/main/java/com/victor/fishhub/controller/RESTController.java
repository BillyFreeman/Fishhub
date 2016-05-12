/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.controller;

import com.victor.fishhub.dao.entity.Location;
import com.victor.fishhub.dao.entity.wrapper.LocationListWrapper;
import com.victor.fishhub.service.ServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RESTController {

    @Autowired
    @Qualifier("fishhubService")
    ServiceLayer service;

    @RequestMapping(value = "/api/locations", method = RequestMethod.GET)
    public LocationListWrapper getAllLocations() {
        List<Location> locations = service.getLazyActiveLocations();
        return new LocationListWrapper(locations);
    }

    @RequestMapping(value = "/api/location/{id}", method = RequestMethod.GET)
    public Location getLocation(@PathVariable(value = "id") int id) {
        return service.getLocation(id);
    }
}
