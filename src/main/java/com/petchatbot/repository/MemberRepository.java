package com.petchatbot.repository;


import com.petchatbot.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByMemberEmail(String memberEmail);

}