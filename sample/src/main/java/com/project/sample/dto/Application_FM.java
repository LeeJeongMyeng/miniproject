package com.project.sample.dto;

import lombok.Data;

//플리마켓 신청글
@Data
public class Application_FM {
    private int count; //중복신청 확인 용
    private int ano; //신청글 번호
    private String userno; //신청자 이메일
    private int fno; //개최글번호
    private String state; //승인여부 Y/N/W
}
