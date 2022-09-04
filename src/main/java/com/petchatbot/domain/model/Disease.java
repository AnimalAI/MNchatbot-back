package com.petchatbot.domain.model;


import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document("animal_diseases")
@Getter

public class Disease {

    @Id
    private String id;
    @Field(name = "ds_name")
    private String dsName; // 질병이름
    @Field(name = "ds_aml_breed")
    private String dsAmlBreed; // 축종
    @Field(name = "ds_definition")
    private String dsDefinition; // 정의
    @Field(name = "ds_cause")
    private String dsCause; //
    @Field(name = "ds_pathogenesis")
    private String dsPathogenesis;
    @Field(name = "ds_epidemiology")
    private String dsEpidemiology;
    @Field(name = "ds_symptom")
    private String dsSymptom; // 증상
    @Field(name = "ds_diagnosis")
    private String dsDiagnosis;
    @Field(name = "ds_treatment")
    private String dsTreatment; // 치료
    @Field(name = "ds_prevention")
    private String dsPrevention; // 예방
    @Field(name = "ds_prognosis")
    private String dsPrognosis;
    @Field(name = "ds_advice")
    private String dsAdvice; // 조언

}
