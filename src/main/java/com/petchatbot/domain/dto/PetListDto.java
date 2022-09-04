package com.petchatbot.domain.dto;

import com.petchatbot.domain.model.Species;
import lombok.Data;

@Data
public class PetListDto {
    private String petName;
    private Long petSerial;
    private Species petSpecies;


    public PetListDto(String petName, Long petSerial, Species petSpecies) {
        this.petName = petName;
        this.petSerial = petSerial;
        this.petSpecies = petSpecies;
    }
}
