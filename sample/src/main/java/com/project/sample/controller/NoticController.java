package com.project.sample.controller;


import com.project.sample.dto.Member;
import com.project.sample.dto.Notic;
import com.project.sample.dto.Notic2;
import com.project.sample.service.NoticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//게시글관련 컨트롤러
@RestController
public class NoticController {

    private final NoticService service;
    @Autowired
    public NoticController(NoticService service) {
        this.service = service;
    }

    //공지사항 리스트 가져오기
    @PostMapping("/ctg/get_Notic_List")
    public Notic2 get_Notic_List(@RequestBody Notic notic ){

        System.out.println("공지사항 리스트 조회(/ctg/get_Notic_List) 실행");

        return service.get_Notic_List(notic);
    }

    //공지사항 상세조회
    @GetMapping("/ctg/get_Notic")
    public Map<String, Object> get_Notic( @RequestParam int notice_id ){

        System.out.println("공지사항 상세조회(/ctg/get_Notic) 실행");

        return service.get_Notic(notice_id);
    }



}
