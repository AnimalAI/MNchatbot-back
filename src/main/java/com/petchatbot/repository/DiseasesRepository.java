package com.petchatbot.repository;

import com.petchatbot.domain.model.Disease;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseasesRepository extends MongoRepository<Disease, String> {

    List<Disease> findByDsNameRegex(String name);

}

