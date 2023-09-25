package com.project.sample.dto;

import lombok.Data;

import java.util.Date;

//회원
@Data
public class Member {
    private String user_id;
    private String email; //회원이메일
    private String password; //비밀번호
    private String name; //이름
    private String phone_number; //핸드폰번호
    private int postal_code; //우편번호
    private String address; //주소
    private String extra_address; //부가주소
    private String detail_address; //상세주소
    private boolean is_business; //사업자유무
    private boolean is_active; //가입상태
    private Date join_date; //가입일자
    private Date leave_date; //탈퇴일자
    //데이터베이스에서는 사업자 유무를 가지고 판단후에 사업자 번호확인하는 식으로 진행하자
    private String business_number; //사업자번호
}
