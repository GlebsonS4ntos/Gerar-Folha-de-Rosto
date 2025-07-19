package com.Glebson.FolhadeRosto.dto;

public class AllergyDto {

    private String name;
    private String category;
    private String criticality;
    private String reaction;
    private String certainty;
    
    public AllergyDto() {
    }

    public AllergyDto(String name, String category, String criticality, String reaction, String certainty) {
        this.name = name;
        this.category = category;
        this.criticality = criticality;
        this.reaction = reaction;
        this.certainty = certainty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCriticality() {
        return criticality;
    }

    public void setCriticality(String criticality) {
        this.criticality = criticality;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getCertainty() {
        return certainty;
    }

    public void setCertainty(String certainty) {
        this.certainty = certainty;
    }
}