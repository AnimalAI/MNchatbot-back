package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
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
import org.springframework.security.core.Authentication;
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

    // 이메일 중복 확인
    @PostMapping("/validateDuplicateEmail")
    public ResponseEntity<String> validateDuplicateEmail(@RequestBody EmailDto emailDto) {
        if (memberService.isExistingMember(emailDto.getReceiveMail())){
            log.info("--중복된 이메일--");
            return new ResponseEntity(DefaultRes.res(StatusCode.CONFLICTPERMALINK, ResponseMessage.DUPLICATE_EMAIL), HttpStatus.OK);
        }
        log.info("validate email={}", emailDto.getReceiveMail());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.AVAILABLE_EMAIL), HttpStatus.OK);
    }

//    @PostMapping("/join")
//    public ResponseEntity<String> join(@RequestBody JoinReq joinReq) {
//        String email = joinReq.getMemberEmail();
//        String password = joinReq.getMemberPassword();
//
//        String rawPassword = joinReq.getMemberPassword();
//        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword); // 패스워드 암호화
//
//        // 성공 로직
//        MemberDto memberDto = new MemberDto(email, encodedPassword);
//        log.info("email={}, password={}", email, rawPassword);
//        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ENTER_JOIN_INFORMATION), HttpStatus.OK);
//    }


    // 이메일 전송
    @PostMapping("/sendEmailCode")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        log.info("sendEmailCode email={}", emailDto);
        try {
            int RandomNumber = makeRandomNumber();

            emailService.sendEmail(emailDto, "멍냥챗봇 인증번호 발송 이메일 입니다.", RandomNumber);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SEND_EMAIL, RandomNumber), HttpStatus.OK);

        } catch (MessagingException e){
            log.info("--이메일 인증코드 발송 실패--");
            return new ResponseEntity(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.SEND_EMAIL_FAIL, null), HttpStatus.OK);
        }
    }

    // 사용자 이메일 확인
    @GetMapping("/member/email")
    public ResponseEntity<String> enterEmailCode(Authentication authentication) {
        String email = extractEmail(authentication);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.GET_EMAIL, email), HttpStatus.OK);
    }


    // 인증번호 입력 & 회원가입
    @PostMapping("/enterEmailCode/join")
    public ResponseEntity<String> enterEmailCode(@RequestBody EmailCodeDto ecCode){
        int sendCode = ecCode.getSendCode();
        int receivedCode = ecCode.getReceivedCode();

        if (sendCode == receivedCode){
            String memberEmail = ecCode.getMemberEmail();
            String rawPassword = ecCode.getMemberPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

            MemberDto memberDto = new MemberDto(memberEmail, encodedPassword);
            memberService.join(memberDto);
            log.info("join email={}", ecCode.getMemberEmail());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER), HttpStatus.OK);
        }
        else{
            log.info("--잘못된 인증코드 입력으로 회원가입 실패--");
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.WRONG_EMAIL_CODE), HttpStatus.OK);
        }
    }

    // 인증코드 입력 (비밀번호 변경을 위함)
    @PostMapping("/enterEmailCode")
    public ResponseEntity<String> enterEmailCode_password(@RequestBody CodeDto codeDto){
        int sendCode = codeDto.getSendCode();
        int receivedCode = codeDto.getReceivedCode();

        if (sendCode == receivedCode){
            log.info("비밀번호 설정을 위한 인증통과");
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_EMAIL_CODE), HttpStatus.OK);
        }
        else{
            log.info("--잘못된 인증코드 입력--");
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.WRONG_EMAIL_CODE), HttpStatus.OK);
        }
    }

    // 비밀번호 변경
    @PatchMapping("/member/changePw")
    public ResponseEntity<String> change_password(@RequestBody ChangePwReq changePwReq, Authentication authentication) {
        String memberEmail = extractEmail(authentication);
        String memberNewPassword = changePwReq.getMemberNewPassword();
        log.info("changePw email={}", memberEmail);
        MemberDto memberDto = new MemberDto(memberEmail, memberNewPassword);
        memberService.changePassword(memberDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CHANGE_PW), HttpStatus.OK);
    }

    // 회원탈퇴
    @DeleteMapping("/member/delete/{memberEmail}")
    public ResponseEntity<String> withdrawal(@PathVariable("memberEmail") String memberEmail){
        log.info("delete email={}", memberEmail);
        EmailDto emailDto = new EmailDto(memberEmail);
        memberService.withdrawal(emailDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_USER), HttpStatus.OK);
    }

    // 비밀번호 암호화를 위한 난수얻기
    public int makeRandomNumber() {
        // 난수의 범위 111111 ~ 999999 (6자리 난수)
        Random r = new Random();
        int checkNum = r.nextInt(888888) + 111111;
        return checkNum;
    }

    // JWT토큰을 통해 사용자 이메일 얻기
    private String extractEmail(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        return memberEmail;
    }

}

