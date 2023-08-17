package com.project.sample.dto;

import lombok.Data;

//회원
@Data
public class Member {
    private String email; //회원이메일
    private String password; //비밀번호
    private String name; //이름
    private String personalNumber;
    private String phoneNumber; //핸드폰번호
    private String address; //주소
    private char businessWhether; //사업자유무
    //데이터베이스에서는 사업자 유무를 가지고 판단후에 사업자 번호확인하는 식으로 진행하자
    private String businessNumber; //사업자번호
}
