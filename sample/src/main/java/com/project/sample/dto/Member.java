package com.project.sample.dto;

import lombok.Data;

import java.util.Date;

//회원
@Data
public class Member {
    private String userno;
    private String email; //회원이메일
    private String password; //비밀번호
    private String name; //이름
    private String personalNumber;
    private String phoneNumber; //핸드폰번호
    private int postcode; //우편번호
    private String address; //주소
    private String extraAddress; //부가주소
    private String detailAddress; //상세주소
    private boolean bnCheck; //사업자유무
    private boolean state; //가입상태
    private Date joinDate; //가입일자
    private Date delDate; //탈퇴일자
    //데이터베이스에서는 사업자 유무를 가지고 판단후에 사업자 번호확인하는 식으로 진행하자
    private String bnNumber; //사업자번호
}
