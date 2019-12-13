package com.example.medlink_deliverable2.models;

import java.util.*;

/**
 * @author Daoud
 *
 */
public class Category {

    private String name;
    private List<Service> service;
    private List<Category> subCategory;
    private List<Category> parentCategory;
    private String role;
    private String id;

    //Constructor
    public Category(String name, String id, String role) {
        this.name = name;
        this.id = id;
        this.role = role;
        service = new ArrayList<Service>();
        subCategory = new ArrayList<Category>();
        parentCategory = new ArrayList<Category>();
    }

    public Category(){} //DO NOT REMOVE, needed for database

    //getters
    public String getName() {
        return name;
    }

    public String getId(){return id;}

    public String getRole(){return role;}

    public List<Service> getService() {
        return service;
    }


    //TODO: get rid of this since no more categories?
    public List<Category> getSubCategory() {
        return subCategory;
    }

    public List<Category> getParentCategory() {
        return parentCategory;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

    public void addService(Service service){
        this.service.add(service);
    }

    public void setSubCategory(List<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public void setParentCategory(List<Category> parentCategory) {
        this.parentCategory = parentCategory;
    }

    //get the number of services that a category has

    public int getNumServices() {
        return this.service.size();
    }

    public String toString(){
        return (this.name);
    }
}
