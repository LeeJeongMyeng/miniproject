package com.project.sample.controller;


import com.project.sample.dto.FleamarketDto;
import com.project.sample.service.FleamarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//플리마켓관련 컨트롤러
@RestController
public class FleamarketController {

    private final FleamarketService service;

    @Autowired
    public FleamarketController(FleamarketService service) {
        this.service = service;
    }


    @PostMapping("/ctg/reg_FleaMarket")
    public int reg_FleaMarket(@RequestBody FleamarketDto fleamarketDto){
        System.out.println();
        //여기서 등록하고 바로 해당 게시글번호 뽑아옴
        int success = service.reg_FleaMarket(fleamarketDto);

        return success;
    }

    @PostMapping("/ctg/reg_FleaMarket_files")
    public int reg_FleaMarket_files(@RequestPart("files") List<MultipartFile> files){

        for(MultipartFile file:files){
            System.out.println(file.getOriginalFilename());
        }

        service.reg_FleaMarket_files(files);

        return 0;
    }


}
