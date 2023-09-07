package com.project.sample.dto;


import lombok.Data;

import java.util.Date;

//플리마켓 개시글
@Data
public class FleamarketDto {
    private int fno; //플리마켓 게시글 번호
    private String userno; //유저이메일
    private String email;
    private String title;//제목
    private String content; //내용
    private int approvalCnt; //최대승인수
    private int curCnt; //현재승인수
    private String endDate; //모집종료날짜
    private Date regDate; //게시날짜
    private Date uptDate; //수정날짜

    private String origin_file_name;
    private String uuid_file_name;
}