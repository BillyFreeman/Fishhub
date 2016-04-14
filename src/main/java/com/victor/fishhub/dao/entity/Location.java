/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.dao.entity;

import com.victor.fishhub.dao.entity.converter.ActiveFlagConverter;
import com.victor.fishhub.dao.entity.converter.DoubleToStringConverter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "locations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Location implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "latitude")
    @Convert(converter = DoubleToStringConverter.class)
    private Double latitude;
    @Column(name = "longtitude")
    @Convert(converter = DoubleToStringConverter.class)
    private Double longtitude;
    @Column(name = "active", nullable = false)
    @NotNull
    @Convert(converter = ActiveFlagConverter.class)
    private Boolean active;
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<DailyWeather> weatherList;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "linked_fish_location", 
            joinColumns = {@JoinColumn(name = "place_id", nullable = false, updatable = false)}, 
            inverseJoinColumns = {@JoinColumn(name = "fish_id", nullable = false, updatable = false)})
    @Cascade(CascadeType.PERSIST)
    private List<Fish> fishlist;

    public Location() {
    }

    public Location(String title, Double latitude, Double longtitude, Boolean active) {
        this.title = title;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<DailyWeather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<DailyWeather> weatherList) {
        this.weatherList = weatherList;
    }

    public List<Fish> getFishlist() {
        return fishlist;
    }

    public void setFishlist(List<Fish> fishlist) {
        this.fishlist = fishlist;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() == obj.getClass()) {
            return this.id.equals(((Location) obj).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int BASE = 12;
        int result = 1;
        result = BASE * result + this.id;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Location:\t");
        builder
                .append("id: ").append(this.id).append("; ")
                .append("title: ").append(this.title).append("\n\t")
                .append("latitude: ").append(this.latitude).append("\n\t")
                .append("longtitude: ").append(this.longtitude).append("\n\t")
                .append("active: ").append(this.active).append("\n");
        for (DailyWeather dw : weatherList) {
            builder.append(dw.toString()).append("\n");
        }
        return builder.toString();
    }
}
