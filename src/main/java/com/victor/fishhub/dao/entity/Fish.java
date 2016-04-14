/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "fishlist")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Fish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "translit")
    private String translit;
    @Column(name = "lat_name")
    private String latinName;
    @Column(name = "mid_weight")
    @XmlTransient
    @JsonIgnore
    private Double midWeight;
    @Column(name = "max_weight")
    @XmlTransient
    @JsonIgnore
    private Double maxWeight;
    @Column(name = "min_allowed_size")
    @XmlTransient
    @JsonIgnore
    private Integer minAllowedSize;
    @Column(name = "best_catching_start")
    @XmlTransient
    @JsonIgnore
    private String bestCatchStart;
    @Column(name = "best_catching_end")
    @XmlTransient
    @JsonIgnore
    private String bestCatchEnd;
    @Column(name = "best_catching_time")
    @XmlTransient
    @JsonIgnore
    private String bestCatchTime;
    @Column(name = "spec_conditions")
    @XmlTransient
    @JsonIgnore
    private String weatherConditions;
    @Column(name = "best_wat_temperature")
    @XmlTransient
    @JsonIgnore
    private Integer bestWaterTemperature;
    @Column(name = "spawn_temperature")
    @XmlTransient
    @JsonIgnore
    private Integer spawnTemperature;
    @Column(name = "spawn_start")
    @XmlTransient
    @JsonIgnore
    private String spawnStart;
    @Column(name = "spawn_end")
    @XmlTransient
    @JsonIgnore
    private String spawnEnd;
    @Column(name = "rel_img_path")
    private String imgPath;

    @ElementCollection
    private Map<String, Integer> activityRatio;

    public Fish() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslit() {
        return translit;
    }

    public void setTranslit(String translit) {
        this.translit = translit;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public Double getMidWeight() {
        return midWeight;
    }

    public void setMidWeight(Double midWeight) {
        this.midWeight = midWeight;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMinAllowedSize() {
        return minAllowedSize;
    }

    public void setMinAllowedSize(Integer minAllowedSize) {
        this.minAllowedSize = minAllowedSize;
    }

    public String getBestCatchStart() {
        return bestCatchStart;
    }

    public void setBestCatchStart(String bestCatchStart) {
        this.bestCatchStart = bestCatchStart;
    }

    public String getBestCatchEnd() {
        return bestCatchEnd;
    }

    public void setBestCatchEnd(String bestCatchEnd) {
        this.bestCatchEnd = bestCatchEnd;
    }

    public String getBestCatchTime() {
        return bestCatchTime;
    }

    public void setBestCatchTime(String bestCatchTime) {
        this.bestCatchTime = bestCatchTime;
    }

    public String getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(String weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public Integer getBestWaterTemperature() {
        return bestWaterTemperature;
    }

    public void setBestWaterTemperature(Integer bestWaterTemperature) {
        this.bestWaterTemperature = bestWaterTemperature;
    }

    public Integer getSpawnTemperature() {
        return spawnTemperature;
    }

    public void setSpawnTemperature(Integer spawnTemperature) {
        this.spawnTemperature = spawnTemperature;
    }

    public String getSpawnStart() {
        return spawnStart;
    }

    public void setSpawnStart(String spawnStart) {
        this.spawnStart = spawnStart;
    }

    public String getSpawnEnd() {
        return spawnEnd;
    }

    public void setSpawnEnd(String spawnEnd) {
        this.spawnEnd = spawnEnd;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Map<String, Integer> getActivityRatio() {
        return activityRatio;
    }

    public void setActivityRatio(Map<String, Integer> activityRatio) {
        this.activityRatio = activityRatio;
    }

    @Override
    public int hashCode() {
        final int BASE = 24;
        int result = 1;
        result = BASE * result + this.id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() == obj.getClass()) {
            return this.id.equals(((Fish) obj).getId());
        }
        return false;
    }
}
