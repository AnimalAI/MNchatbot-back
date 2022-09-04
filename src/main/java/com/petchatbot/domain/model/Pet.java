package com.petchatbot.domain.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petSerial;

    @ManyToOne
    @JoinColumn(name="member_serial")
    private Member member;
    @Enumerated(EnumType.STRING)
    private Species petSpecies;

    private String petBreed;
    private String petName;
    private int petAge;
    @Enumerated(EnumType.STRING)
    private PetGender petgender;
    @Enumerated(EnumType.STRING)
    private Neutralization petNeutralization;

    public Pet() {
    }

    public Pet(Species petSpecies, String petBreed, String petName, int petAge, PetGender petGender, Neutralization petNeutralization) {
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petgender = petGender;
        this.petNeutralization = petNeutralization;
    }

    public void changePetInfo(Species petSpecies, String petBreed, String petName, int petAge, PetGender petgender, Neutralization petNeutralization){
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petgender = petgender;
        this.petNeutralization = petNeutralization;
    }

    public void addMember(Member member) {
        this.member = member;
    }

}