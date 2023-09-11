package com.project.sample.dto;

import lombok.Data;

import java.util.List;

@Data
public class Notic2 {
    private int currentPage; //현재페이지
    private int totPage;
    private int totCnt; //전체갯수
    private int onePageCnt; //한페이지 갯수
    private List<Notic> noticList;

}
