package com.project.sample.dto;

import lombok.Data;

import java.util.List;

@Data
public class Notic {
    private int notice_id; //게시글번호

    private String title; //제목
    private String content; //내용
    private boolean is_important; //중요여부
    private String reg_date; //생성일자
    private String upt_date; //수정일자
    private String end_date; //종료일자
    private boolean has_attachment; //파일존재여부
    private boolean delDateCheck; //종료글여부
    private boolean is_state; //종료글여부

    private int file_id; //파일번호
    private String origin_file_name; //본래 파일 이름
    private String chg_source_filename; //경로+uuid+파일이름
    private String extension; // 확장자
    private int filesize; //파일사이즈


    //게시판 종류 테이블 관련된거
    private String cd;
    private String cd_nm;
    private String ref_1;

    //페이징용
    private int rownum;

    private int currentPage; //현재페이지
    private int totPage;
    private int totCnt; //전체갯수
    private int onePageCnt; //한페이지 갯수

    private int st_rownum;
    private int en_rownum;

    private int Search_Mode;
}
