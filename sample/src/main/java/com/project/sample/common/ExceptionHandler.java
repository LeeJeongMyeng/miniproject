package com.project.sample.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {



    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handle405(HttpRequestMethodNotSupportedException ex) {
        // 405 에러에 대한 커스텀 응답 구성
        return new ResponseEntity<>("POST method is required for this endpoint.", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
