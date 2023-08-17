package com.project.sample.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Qna {
    private int qno; //게시글번호
    private String email; //사용자아이디
    private String title; //게시글제목
    private String content; //게시글 내용
    private boolean secretWhether; //비밀글여부
    private boolean answer; //답변여부
    private Date regDate; //등록날짜
    private Date uptDate; //수정날짜

}
