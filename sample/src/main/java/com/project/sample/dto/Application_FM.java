package com.project.sample.dto;

import lombok.Data;

//플리마켓 신청글
@Data
public class Application_FM {
    private int count; //중복신청 확인 용 and 상대업데이트시 신청갯수 임시 할당용
    private int ano; //신청글 번호
    private String userno; //신청자 이메일
    private int fno; //개최글번호
    private String state; //승인여부 Y/N/W


    //데이터 받아올떄 추가로 필요한 필드
    private String regDate; //신청한 날짜
    private String name; //유저이름
    private String email; //이메일
    private String address; //주소
    private String phoneNumber; //핸드폰번호
}
