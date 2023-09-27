package com.project.sample.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

//@Controller
//public class RootController implements ErrorController {
//    // 에러 경로 설정
//    private final String ERROR_PATH = "/error";
//
//    @RequestMapping(ERROR_PATH)
//    public ResponseEntity<String> handleErrors(HttpServletRequest request) {
//        int statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        // 원하는 처리 로직을 구현합니다.
//        // 예: statusCode에 따라 다른 응답을 반환하거나, 특정 에러 페이지를 보여줍니다.
//
//        return ResponseEntity.status(statusCode).body("Error occurred");
//    }
//
//
//    public String getErrorPath() {
//        return ERROR_PATH;
//    }
//}
