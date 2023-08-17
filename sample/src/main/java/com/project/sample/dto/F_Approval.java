package com.project.sample.dto;

import lombok.Data;

//플리마켓 신청글
@Data
public class F_Approval {
    private int ano; //신청글 번호
    private String email; //신청자 이메일
    private int fno; //개최글번호
    private char approvalWhether; //승인여부 Y/N/W
}
