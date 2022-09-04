package com.petchatbot.config;

public abstract class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String PASSWORD_WRONG = "패스워드 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String JOIN_FAIL = "회원 가입 입력란에서 Null값 발생";
    public static final String CHANGE_PW = "비밀번호 변경 성공";
    public static final String CHANGE_PW_FAIL = "비밀번호 변경 실패";
    public static final String WRONG_EMAIL_CODE = "인증번호 실패";
    public static final String SUCCESS_EMAIL_CODE = "인증번호 성공";
    public static final String DUPLICATE_EMAIL = "중복된 이메일";
    public static final String GET_EMAIL = "사용자 이메일";
    public static final String AVAILABLE_EMAIL = "사용가능한 이메일";
    public static final String ENTER_JOIN_INFORMATION = "회원 가입 정보 입력 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String INCORRECT_APPROACH = "잘못된 접근";
    public static final String SEND_EMAIL = "이메일 발송";

    public static final String ADD_PET = "반려동물추가 성공";
    public static final String SUCCESS_CHANGE_PET_INFO = "반려동물 프로필 변경 성공";
    public static final String WRONG_CHANGE_PET_INFO = "반려동물 프로필 변경 성공";
    public static final String SUCCESS_GET_PET_INFO = "반려동물 정보 제공";
    public static final String FAIL_GET_PET_INFO = "반려동물 정보 없음";
    public static final String SUCCESS_GET_PET_LIST = "반려동물 list 정보 제공";
    public static final String FAIL_GET_PET_LIST = "반려동물 list 정보 없음";

    public static final String SUCCESS_DELETE_PET = "반려동물 삭제 성공";

    public static final String NULL_SEARCH_DISEASES = "질병백과 검색어 없음";
    public static final String SUCCESS_SEARCH_DISEASES = "질병백과 검색결과 제공";
    public static final String FAIL_SEARCH_DISEASES = "질병백과 검색결과 없음";
    public static final String SUCCESS_GET_DISEASE_INFO = "질병 정보 제공";


    public static final String SEND_EMAIL_FAIL = "이메일 발송 실패";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
}
