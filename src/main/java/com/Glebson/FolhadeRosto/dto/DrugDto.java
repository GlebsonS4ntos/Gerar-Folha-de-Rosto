package com.Glebson.FolhadeRosto.dto;

import java.time.LocalDate;

public class DrugDto {
    private String name;
    private String dosage;
    private LocalDate starTreatment;
    private LocalDate endTreatment;

    public DrugDto() {
    }

    public DrugDto(String name, String dosage, LocalDate starTreatment, LocalDate endTreatment) {
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

    public LocalDate getStarTreatment() {
        return starTreatment;
    }

    public void setStarTreatment(LocalDate starTreatment) {
        this.starTreatment = starTreatment;
    }

    public LocalDate getEndTreatment() {
        return endTreatment;
    }

    public void setEndTreatment(LocalDate endTreatment) {
        this.endTreatment = endTreatment;
    }
}
