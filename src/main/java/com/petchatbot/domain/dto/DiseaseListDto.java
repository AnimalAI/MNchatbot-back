package com.petchatbot.domain.dto;

import lombok.Data;

@Data
public class DiseaseListDto {
    private String diseaseId;
    private String diseaseName;

    public DiseaseListDto() {
    }

    public DiseaseListDto(String diseaseId, String diseaseName) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
    }
}
