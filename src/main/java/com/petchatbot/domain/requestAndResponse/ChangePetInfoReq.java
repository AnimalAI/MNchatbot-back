package com.petchatbot.domain.requestAndResponse;

import com.petchatbot.domain.model.Species;
import com.petchatbot.domain.model.Neutralization;
import com.petchatbot.domain.model.PetGender;
import lombok.Data;

@Data
public class ChangePetInfoReq {
    private Long petSerial;
    private Species petSpecies;
    private String petBreed;
    private String petName;
    private int petAge;
    private PetGender petGender ;
    private Neutralization petNeutralization ;
}
