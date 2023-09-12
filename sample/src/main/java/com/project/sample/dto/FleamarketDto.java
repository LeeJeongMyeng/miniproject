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
    private String address; //장소
    private String detailAddress; //상세장소
    private int approvalCnt; //최대승인수
    private int curCnt; //현재승인수
    private String endDate; //모집종료날짜
    private String regDate; //게시날짜
    private String uptDate; //수정날짜


    private String state;
    private String fstate;

    private String origin_file_name;
    private String uuid_file_name;

    private int rownum;

    private int currentPage; //현재페이지
    private int totPage;
    private int totCnt; //전체갯수
    private int onePageCnt; //한페이지 갯수

    private int st_rownum;
    private int en_rownum;

}
