package com.Glebson.FolhadeRosto.dto;

import java.time.LocalDate;

public class CitizenDto {
    private String name;
    private String socialName;
    private String cpf;
    private String cns;
    private LocalDate birthDate;
    private String gender;
    private String motherName;
    private String fatherName;

    public CitizenDto() {
    }

    public CitizenDto(String name, String socialName, String cpf, String cns, LocalDate birthDate, String gender,
            String motherName, String fatherName) {
        this.name = name;
        this.socialName = socialName;
        this.cpf = cpf;
        this.cns = cns;
        this.birthDate = birthDate;
        this.gender = gender;
        this.motherName = motherName;
        this.fatherName = fatherName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
