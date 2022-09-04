package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.domain.dto.DiseaseDto;
import com.petchatbot.domain.dto.DiseaseListDto;
import com.petchatbot.domain.model.Disease;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.service.DiseasesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DiseasesController {

    private final DiseasesService diseasesService;

    @GetMapping("/disease/dsList/{dsName}")
    public ResponseEntity<List<DiseaseListDto>> searchDiseases(@PathVariable("dsName") String dsName){

        List<DiseaseListDto> diseaseList = diseasesService.searchDiseases(dsName);

        if (diseaseList.isEmpty()){ //검색 결과 없음
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_SEARCH_DISEASES, null), HttpStatus.OK);
        }

        // 검색결과 list Response
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_SEARCH_DISEASES, diseaseList), HttpStatus.OK);
    }

    @GetMapping("/disease/{dsId}")
    public ResponseEntity<DiseaseDto> diseaseInfo(@PathVariable("dsId") String dsId){
        DiseaseDto diseaseInfo = diseasesService.getDiseaseInfo(dsId);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_DISEASE_INFO, diseaseInfo), HttpStatus.OK);
    }
}
