package com.project.sample.dto;

import lombok.Data;

//플리마켓 신청글
@Data
public class Application_FM {
    private int count; //중복신청 확인 용 and 상대업데이트시 신청갯수 임시 할당용
    private int applicant_id; //신청글 번호
    private String user_id; //신청자 이메일
    private int post_id; //개최글번호
    private String state; //승인여부 Y/N/W


    //데이터 받아올떄 추가로 필요한 필드
    private String reg_date; //신청한 날짜
    private String name; //유저이름
    private String email; //이메일
    private String address; //주소
    private String phone_number; //핸드폰번호
}
