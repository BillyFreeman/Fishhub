/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.dao;

import com.victor.fishhub.dao.entity.DailyWeather;
import com.victor.fishhub.dao.entity.Fish;
import com.victor.fishhub.dao.entity.Location;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "hibernateDao")
class FishhubDaoHibernateImpl implements FishhubDao {

    @Autowired
    private SessionFactory factory;

    @Override
    public List<Location> getLazyActiveLocations() {
        Session session = null;
        Transaction tx = null;
        List locations = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            locations = session.createQuery("SELECT l FROM Location l WHERE l.active = true").list();
            tx.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return locations;
    }

    @Override
    public List<Location> getAllLocations() {
        Session session = null;
        Transaction tx = null;
        List<Location> locations = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            locations = session.createQuery("SELECT l FROM Location l").list();
            for (Location l : locations) {
                initWeatherList(l.getWeatherList());
            }
            tx.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return locations;
    }

    @Override
    public List<Location> getActiveLocations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Location> getNotActiveLocations() {
        Session session = null;
        Transaction tx = null;
        List<Location> locations = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            locations = session.createQuery("SELECT l FROM Location l WHERE l.active = false").list();
            for (Location l : locations) {
                initWeatherList(l.getWeatherList());
            }
            tx.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return locations;
    }

    @Override
    public Location getLocation(int id) {
        Session session = null;
        Transaction tx = null;
        Location location = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            location = session.get(Location.class, id);
            initWeatherList(location.getWeatherList());
            initFishList(location.getFishlist());
            tx.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return location;
    }

    @Override
    public List<Location> getLocations(int... id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateLocation(Location location) {
        Session session = null;
        Transaction tx = null;
        List<Location> locations = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(location);
            tx.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteLocation(Location location) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(location);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateDailyWeatherList(List<DailyWeather> weather) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deactivateLocation(int id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            Location location = session.load(Location.class, id);
            location.setActive(false);
            session.save(location);
            tx.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Fish> getDefaultFishlist() {
        Session session = null;
        Transaction tx = null;
        List fishlist = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            fishlist = session.createQuery("FROM Fish f WHERE f.translit IN ('karas','karp','plotva')").list();
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return fishlist;
    }

    private List<DailyWeather> initWeatherList(List<DailyWeather> weatherList) {
        Hibernate.initialize(weatherList);

        for (DailyWeather dw : weatherList) {
            Hibernate.initialize(dw.getH3WeatherList());
        }
        return weatherList;
    }

    private List<Fish> initFishList(List<Fish> fishlist) {
        Hibernate.initialize(fishlist);
        return fishlist;
    }

}
