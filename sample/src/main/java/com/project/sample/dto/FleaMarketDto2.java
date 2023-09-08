package com.project.sample.dto;

import lombok.Data;

import java.util.List;

@Data
public class FleaMarketDto2 {
    private int currentPage; //현재페이지 반환할땐 필요없을거같음
    private int totPage;
    private int totCnt; //전체갯수 반환할땐 얘도 필요없을거같음
    private int onePageCnt; //한페이지 갯수 얘도 필요없을거같음 ㅋㅋㅋㅋ
    private List<FleamarketDto> fleamarketDtoList;
}
