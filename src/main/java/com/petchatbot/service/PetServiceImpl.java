package com.petchatbot.service;

import com.petchatbot.domain.dto.PetListDto;
import com.petchatbot.domain.model.*;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.requestAndResponse.PetReq;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService{

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;

    @Transactional
    @Override
    public void registerPet(PetReq petRegReq, String email) {
        Member findMember = getFindMember(email);
        Pet pet = createPetEntity(petRegReq);
        petRepository.save(pet);
        findMember.addPet(pet);
    }

    @Override
    @Transactional
    public void changePetInfo(ChangePetInfoReq petInfoReq) {
        Pet findPet = getFindPet(petInfoReq);
        setPet(petInfoReq, findPet);
    }

    @Override
    public List<PetListDto> petList(String email) {
        Member member = memberRepository.findByMemberEmail(email);
        List<Pet> petList = member.getPetList();

        List<PetListDto> pets = new ArrayList<>();
        for (Pet pet: petList){
            //log.info("pet 정보 ={}", pet.getPetName());
            String petName = pet.getPetName();
            Long petSerial = pet.getPetSerial();
            Species petSpecies = pet.getPetSpecies();
            PetListDto petListDto = new PetListDto(petName, petSerial, petSpecies);
            pets.add(petListDto);
        }
        return pets;
    }

    @Override
    public PetReq petInfo(Long petSerial) {
        Pet pet = petRepository.findByPetSerial(petSerial);
        PetReq petReq = extracted(pet);
        return petReq;
    }

    @Override
    public void petDelete(Long petSerial) {
        Pet findPet = petRepository.findByPetSerial(petSerial);
        petRepository.delete(findPet);
    }

    private PetReq extracted(Pet pet) {
        Species petSpecies = pet.getPetSpecies();
        String petBreed = pet.getPetBreed();
        String petName = pet.getPetName();
        int petAge = pet.getPetAge();
        PetGender petGender = pet.getPetgender();
        Neutralization petNeutralization = pet.getPetNeutralization();
        PetReq petReq = new PetReq(petSpecies, petBreed, petName, petAge, petGender, petNeutralization);
        return petReq;
    }

    private void setPet(ChangePetInfoReq petInfoReq, Pet findPet) {
        Species petSpecies = petInfoReq.getPetSpecies();
        String petBreed = petInfoReq.getPetBreed();
        String petName = petInfoReq.getPetName();
        int petAge = petInfoReq.getPetAge();
        PetGender petGender = petInfoReq.getPetGender();
        Neutralization petNeutralization = petInfoReq.getPetNeutralization();
        findPet.changePetInfo(petSpecies, petBreed, petName, petAge, petGender, petNeutralization);
    }

    private Pet getFindPet(ChangePetInfoReq petInfoReq) {
        Long petSerial = petInfoReq.getPetSerial();
        log.info("petSerial={}", petSerial);
        Pet findPet = petRepository.findByPetSerial(petSerial);
        return findPet;
    }

    private Member getFindMember(String email) {
        Member findMember = memberRepository.findByMemberEmail(email);
        return findMember;
    }

    private Pet createPetEntity(PetReq petRegReq) {
        Species petSpecies = petRegReq.getPetSpecies();
        String petBreed = petRegReq.getPetBreed();
        log.info("petSpecies={}", petSpecies);
        String petName = petRegReq.getPetName();
        int petAge = petRegReq.getPetAge();
        PetGender petGender = petRegReq.getPetGender();
        Neutralization petNeutralization = petRegReq.getPetNeutralization();
        Pet pet = new Pet(petSpecies, petBreed, petName, petAge, petGender, petNeutralization);
        return pet;
    }

}
