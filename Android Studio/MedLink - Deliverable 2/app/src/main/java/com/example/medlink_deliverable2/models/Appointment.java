package com.example.medlink_deliverable2.models;

import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private String reservationSlots;
    private String date;
    private String serviceName;
    private String serviceId;
    private String clinicName;
    private String clinicId;
    private String patientName;
    private String patientId;

    public Appointment(String patientId, String patientName, String clinicId, String clinicName, String serviceName, String serviceId, String reservationSlot, String date){
        this.patientId = patientId;
        this.patientName = patientName;
        this.clinicId = clinicId;
        this.clinicName = clinicName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.date = date;
        this.reservationSlots = reservationSlot;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Appointment(){}

    //Getters
    public String getReservationSlots() {
        return reservationSlots;
    }

    public String getDate() {
        return date;
    }

    //Setters
    public void setReservationSlots(String reservationSlots) {
        this.reservationSlots = reservationSlots;
    }

    public void setDate(String date) {
        this.date = date;
    }
//
//    public void setService(Category service) {
//        this.service = service;
//    }

    public String toString(){
     return (this.date +", "+ this.reservationSlots + ", "); //temporarily removed service.toString since throwing errors left and right lmao
    }

}
