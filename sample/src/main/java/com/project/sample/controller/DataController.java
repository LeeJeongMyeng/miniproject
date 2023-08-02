package com.project.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
    
@GetMapping("/")
public String home(){
    return "Data준비중 ...";
}

}
