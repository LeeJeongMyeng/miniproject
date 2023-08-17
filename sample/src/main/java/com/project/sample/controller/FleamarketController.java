package com.project.sample.controller;


import com.project.sample.service.FleamarketService;
import com.project.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//플리마켓관련 컨트롤러
@RestController
public class FleamarketController {

    private final FleamarketService service;

    @Autowired
    public FleamarketController(FleamarketService service) {
        this.service = service;
    }
}
