package com.petchatbot.domain.requestAndResponse;

import com.petchatbot.domain.model.Species;
import com.petchatbot.domain.model.Neutralization;
import com.petchatbot.domain.model.PetGender;
import lombok.Data;

@Data
public class PetReq {
    private Species petSpecies;
    private String petBreed;
    private String petName;
    private int petAge;
    private PetGender petGender;
    private Neutralization petNeutralization;

    public PetReq() {
    }

    public PetReq(Species petSpecies, String petBreed, String petName, int petAge, PetGender petGender, Neutralization petNeutralization) {
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petGender = petGender;
        this.petNeutralization = petNeutralization;
    }
}
