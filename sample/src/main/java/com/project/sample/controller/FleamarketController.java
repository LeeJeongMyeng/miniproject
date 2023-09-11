package com.project.sample.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sample.dto.Application_FM;
import com.project.sample.dto.FleamarketDto;
import com.project.sample.dto.Member;
import com.project.sample.service.FleamarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//플리마켓관련 컨트롤러
@RestController
public class FleamarketController {

    private final FleamarketService service;

    @Autowired
    public FleamarketController(FleamarketService service) {
        this.service = service;
    }


    @PostMapping("/ctg/reg_FleaMarket")
    public int reg_FleaMarket(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                              @RequestParam("FleamarketDto") String fleamarketDtoStr,
                              @RequestParam("method") String method) throws JsonProcessingException {

        // JSON 형태를 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        FleamarketDto fleamarketDto = objectMapper.readValue(fleamarketDtoStr, FleamarketDto.class);

        // 등록 or 수정 후 fno 받아옴
        int fno = service.reg_FleaMarket(fleamarketDto, method);

        System.out.println(files != null && !files.isEmpty());

        // MultipartFile 데이터가 존재하는 경우에만 파일 등록을 실행
        if (files != null && !files.isEmpty()) {
            service.reg_FleaMarket_files(files, fno, method);
        }
        return 0;
    }
    // 메인화면 게시글 리스트+ 썸네일 사진 하나씩
    @PostMapping("/ctg/get_FleaMarket_List")
    public Map<String,Object> get_FleaMarket_List(@RequestBody FleamarketDto fleamarketDto){
        Map<String,Object> map = new HashMap<String,Object>();
        System.out.println("get_FleaMarket_List");
        map.put("FleamarketList",service.get_FleaMarket_List(fleamarketDto));

        return map;
//        return 0;
    }

    //상세조회
    @GetMapping("/ctg/get_FleaMarket")
    public Map<String,Object> get_FleaMarket(@RequestParam int fno){
        Map<String,Object> map = new HashMap<String,Object>();
        System.out.println(fno);
        //게시글정보
       map.put("FleaMarket",service.get_FleaMarket(fno));
       //게시글 이미지 사진정보
       map.put("FleaMarket_files",service.get_FleaMarket_files(fno));

        return map;
    }
    //게시글 삭제
    @GetMapping("/ctg/del_FleaMarket")
    public int del_FleaMarket(@RequestParam int fno){
        System.out.println("삭제Con");
        return service.del_FleaMarket(fno);
    }

    //특정 플리마켓 게시글에 대해 신청하기
    @GetMapping("/ctg/application_FM")
    public int application_FM(@RequestParam int fno,@RequestParam String userno){
        System.out.println("플리마켓 신청 실행");
        int Successnum = 0;
        //중복검사
        int Checknum = service.Check_Application(fno,userno);
        System.out.println(Checknum);
        //신청 진행
        if(Checknum==0){
           Successnum =  service.application_FM(fno, userno);
        }


        return Successnum;
    }
    //신청자(승인/거절/대기) 목록가져오기
    @PostMapping("/ctg/get_application_FM")
    public Map<String , Object> get_application_FM(@RequestBody Application_FM applicationFm) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.out.println("/ctg/get_application_FM");
        //데이터 가져오기
        return service.get_application_FM(applicationFm);
    }

    //승인/거절/대기로 업데이트
    @PostMapping("/ctg/upt_application_FM")
    public int upt_application_FM(@RequestBody Application_FM applicationFm) {
        System.out.println("/ctg/upt_application_FM 실행");

            //상태수정
           int result = service.upt_application_FM(applicationFm);

           if(result==1){
               //승인인원 재할당
               result =service.upt_apl_FM_ACount(applicationFm);
           }

        System.out.println("상태 업데이트:"+result);

        //데이터 가져오기
        return result;
    }




}
