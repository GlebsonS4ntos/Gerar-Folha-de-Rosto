package com.Glebson.FolhadeRosto.dto;

public class ConditionDto {
    private String ciapCode;
    private String description;

    public ConditionDto() {
    }

    public ConditionDto(String ciapCode, String description) {
        this.ciapCode = ciapCode;
        this.description = description;
    }

    public String getCiapCode() {
        return ciapCode;
    }

    public void setCiapCode(String ciapCode) {
        this.ciapCode = ciapCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
