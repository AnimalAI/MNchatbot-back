package com.petchatbot.domain.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Data
public class DiseaseDto {

    private String dsName;

    private String dsAmlBreed;

    private String dsDefinition;

    private String dsCause;

    private String dsPathogenesis;

    private String dsEpidemiology;

    private String dsSymptom;

    private String dsDiagnosis;

    private String dsTreatment;

    private String dsPrevention;

    private String dsPrognosis;

    private String dsAdvice;

    public DiseaseDto() {
    }

    public DiseaseDto(String dsName, String dsAmlBreed, String dsDefinition, String dsCause, String dsPathogenesis, String dsEpidemiology, String dsSymptom, String dsDiagnosis, String dsTreatment, String dsPrevention, String dsPrognosis, String dsAdvice) {
        this.dsName = dsName;
        this.dsAmlBreed = dsAmlBreed;
        this.dsDefinition = dsDefinition;
        this.dsCause = dsCause;
        this.dsPathogenesis = dsPathogenesis;
        this.dsEpidemiology = dsEpidemiology;
        this.dsSymptom = dsSymptom;
        this.dsDiagnosis = dsDiagnosis;
        this.dsTreatment = dsTreatment;
        this.dsPrevention = dsPrevention;
        this.dsPrognosis = dsPrognosis;
        this.dsAdvice = dsAdvice;
    }
}
