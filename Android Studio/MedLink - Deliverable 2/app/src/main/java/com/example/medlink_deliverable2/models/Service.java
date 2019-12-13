package com.example.medlink_deliverable2.models;

import java.util.*;

/**
 * @author Daoud
 *
 */
public class Service {

    private double rate;
    private String provider;
    private List<Category> categories;

    public Service(double rate, String provider) {
        this.rate = rate;
        this.provider = provider;
        categories = new ArrayList<Category>();
    }

    public Service(){} //DO NOT REMOVE, needed for database

    //getters
    public double getRate() {
        return rate;
    }

    public String getProvider() {
        return provider;
    }

    //setters
    public List<Category> getCategory() {
        return categories;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setCategory(List<Category> category) {
        this.categories = category;
    }

    //get the number of categories that a service has
    public int getNumCategory() {
        return categories.size();
    }

    public String toString(){
        return (this.provider + ", " + this.rate + ", " + this.getCategory().toString());
    }
}
