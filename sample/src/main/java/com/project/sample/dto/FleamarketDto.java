package com.project.sample.dto;


import lombok.Data;

import java.util.Date;

//플리마켓 개시글
@Data
public class FleamarketDto {
    private int post_id; //플리마켓 게시글 번호
    private String user_id; //유저이메일
    private String email;
    private String title;//제목
    private String content; //내용
    private String location; //장소
    private String sub_location; //상세장소
    private int max_applicants; //최대승인수
    private int current_count; //현재승인수
    private String end_date; //모집종료날짜
    private String reg_date; //게시날짜
    private String upt_date; //수정날짜


    private String state;
    private String fstate;

    private String origin_file_name; //기존 파일이름
    private String uuid_file_name; //저장된 파일이름

    private int rownum;

    private int currentPage; //현재페이지
    private int totPage; //전체 페이지 갯수
    private int totCnt; //전체갯수
    private int onePageCnt; //한페이지 갯수

    private int st_rownum;
    private int en_rownum;

}
