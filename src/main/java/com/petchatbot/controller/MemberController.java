package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.domain.dto.CodeDto;
import com.petchatbot.domain.dto.EmailCodeDto;
import com.petchatbot.domain.dto.EmailDto;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.requestAndResponse.ChangePwReq;
import com.petchatbot.domain.requestAndResponse.JoinReq;
import com.petchatbot.domain.dto.MemberDto;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.service.EmailService;
import com.petchatbot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailService emailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/validateDuplicateEmail")
    public ResponseEntity<String> validateDuplicateEmail(@RequestBody EmailDto emailDto) {
        log.info("validate email={}", emailDto.getReceiveMail());
        if (memberService.isExistingMember(emailDto.getReceiveMail())){
            return new ResponseEntity(DefaultRes.res(StatusCode.CONFLICTPERMALINK, ResponseMessage.DUPLICATE_EMAIL), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.AVAILABLE_EMAIL), HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinReq joinReq) {
        String email = joinReq.getMemberEmail();
        String password = joinReq.getMemberPassword();

        String rawPassword = joinReq.getMemberPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword); // ???????????? ?????????

        // ?????? ??????
        MemberDto memberDto = new MemberDto(email, encodedPassword);
        log.info("email={}, password={}", email, rawPassword);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ENTER_JOIN_INFORMATION), HttpStatus.OK);
    }

    // ????????? ??????
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        try {
            log.info("memberEmail={}", emailDto);
            int RandomNumber = makeRandomNumber();

            emailService.sendEmail(emailDto, "???????????? ???????????? ?????? ????????? ?????????.", RandomNumber);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SEND_EMAIL, RandomNumber), HttpStatus.OK);

        } catch (MessagingException e){
            return new ResponseEntity(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.SEND_EMAIL_FAIL, null), HttpStatus.OK);
        }
    }


    // ???????????? ?????? (????????????)
    @PostMapping("/enterEmailCode/join")
    public ResponseEntity<String> enterEmailCode(@RequestBody EmailCodeDto ecCode){
        log.info("enterEmailCode memberEmail={}, memberPassword={}, sendCode={}, receivedCode={}",
                ecCode.getMemberEmail(), ecCode.getMemberPassword(), ecCode.getSendCode(), ecCode.getReceivedCode());
        int sendCode = ecCode.getSendCode();
        int receivedCode = ecCode.getReceivedCode();

        if (sendCode == receivedCode){
            String memberEmail = ecCode.getMemberEmail();
            String rawPassword = ecCode.getMemberPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

            MemberDto memberDto = new MemberDto(memberEmail, encodedPassword);
            memberService.join(memberDto);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.WRONG_EMAIL_CODE), HttpStatus.OK);
        }
    }

    // ???????????? ?????? (???????????? ??????)
    @PostMapping("/enterEmailCode/changePw")
    public ResponseEntity<String> enterEmailCode_password(@RequestBody CodeDto codeDto){
        int sendCode = codeDto.getSendCode();
        int receivedCode = codeDto.getReceivedCode();

        if (sendCode == receivedCode){
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_EMAIL_CODE), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.WRONG_EMAIL_CODE), HttpStatus.OK);
        }
    }

    @PostMapping("/changePw")
    public ResponseEntity<String> change_password(@RequestBody ChangePwReq changePwReq) {
        String memberEmail = changePwReq.getMemberEmail();
        String memberNewPassword = changePwReq.getMemberNewPassword();
        log.info("changePwReq.email={}", memberEmail);
        log.info("changePwReq.password={}", memberNewPassword);
        MemberDto memberDto = new MemberDto(memberEmail, memberNewPassword);

        memberService.changePassword(memberDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CHANGE_PW), HttpStatus.OK);
    }

    public int makeRandomNumber() {
        // ????????? ?????? 111111 ~ 999999 (6?????? ??????)
        Random r = new Random();
        int checkNum = r.nextInt(888888) + 111111;
        return checkNum;
    }



}

