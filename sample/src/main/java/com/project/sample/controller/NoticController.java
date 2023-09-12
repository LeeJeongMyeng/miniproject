package com.project.sample.controller;


import com.project.sample.dto.Member;
import com.project.sample.dto.Notic;
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

    //게시글 리스트 가져오기
    @PostMapping("/ctg/get_Notic_List")
    public Map<String, Object> get_Notics(@RequestBody Notic notic){
        Map<String, Object> map = new HashMap<>();
        System.out.println("실행된 메서드 : /ctg/get_Notic_List");
        System.out.println("currentPage:"+notic.getCurrentPage());
        map.put("Notic_List",service.get_Notic_List(notic));
        return map;
    }

    @GetMapping("/ctg/get_Notic")
    public Map<String, Object> get_Notic(@RequestParam int ntno){
        System.out.println("실행된 메서드 : /ctg/get_Notic");
        System.out.println(ntno);
        return service.get_Notic(ntno);
    }

}
