package com.Glebson.FolhadeRosto.dto;

import java.util.Date;

public class DrugDto {
    private String name;
    private String dosage;
    private Date starTreatment;
    private Date endTreatment;

    public DrugDto() {
    }

    public DrugDto(String name, String dosage, Date starTreatment, Date endTreatment) {
        this.name = name;
        this.dosage = dosage;
        this.starTreatment = starTreatment;
        this.endTreatment = endTreatment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Date getStarTreatment() {
        return starTreatment;
    }

    public void setStarTreatment(Date starTreatment) {
        this.starTreatment = starTreatment;
    }

    public Date getEndTreatment() {
        return endTreatment;
    }

    public void setEndTreatment(Date endTreatment) {
        this.endTreatment = endTreatment;
    }
}
