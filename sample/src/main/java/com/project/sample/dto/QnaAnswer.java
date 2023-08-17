package com.project.sample.dto;

import lombok.Data;

import java.util.Date;

@Data
public class QnaAnswer {
    private Qna qna; //문의글
    private String manager; //답변관리자
    private String answerContent; //답변내용
    private Date regDate; //등록날짜
}
