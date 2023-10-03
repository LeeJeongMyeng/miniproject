/*
package com.project.sample.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handle405(HttpRequestMethodNotSupportedException ex) {
        // 405 에러에 대한 커스텀 응답 구성
        return new ResponseEntity<>("POST method is required for this endpoint.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<String> handle400(ResponseStatusException ex) {
        HttpStatus status = ex.getStatus();

        switch (status) {
            case BAD_REQUEST:
                return new ResponseEntity<>("잘못된 접근입니다.(400)", HttpStatus.BAD_REQUEST);
            case UNAUTHORIZED:
                return new ResponseEntity<>("로그인 이후 이용하시기 바랍니다.(401)", HttpStatus.UNAUTHORIZED);
            case FORBIDDEN:
                return new ResponseEntity<>("권한이 존재하지 않습니다.(403)", HttpStatus.FORBIDDEN);
            case NOT_FOUND:
                return new ResponseEntity<>("페이지를 찾을 수 없습니다.(404)", HttpStatus.NOT_FOUND);
            default:
                throw ex; // 예상치 못한 상태 코드는 다시 던져서 Spring의 기본 에러 핸들러가 처리하게 함.
        }
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>("Null 값이 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle500(Exception ex) {
        return new ResponseEntity<>("에러발생(500)", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}*/
