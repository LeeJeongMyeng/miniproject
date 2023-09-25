package com.project.sample.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sample.dto.FileDto;
import com.project.sample.dto.Notic;
import com.project.sample.service.JwtService;
import com.project.sample.service.MemberService;
import com.project.sample.service.NoticService;
import io.jsonwebtoken.Claims;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//게시글관련 컨트롤러
@Controller
public class CommonController {

    @Value("${fileDownloadPath}")
    private String fileDownloadPath;
    //String fileDownloadPath ="C:\\pandora3\\workspace\\pandora3\\WebContent\\resources\\files\\ctg\\";

    private final JwtService jwtService;
    @Autowired
    public CommonController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    //파일다운로드(공지사항 첨부파일)
    @GetMapping("/ctg/filedownload")
    public ResponseEntity<FileSystemResource> downloadFile(@RequestParam String filename) throws IOException {
        // 파일 경로 설정 (다운로드할 파일이 저장된 경로)
        System.out.println("파일 다운로드 시작");
        String filePath = fileDownloadPath + filename; // 실제 파일 경로로 대체해주세요

        // FileSystemResource를 사용하여 파일을 읽어옴
        FileSystemResource file = new FileSystemResource(filePath);
        
        //파일이 존재한다면
        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename); // 다운로드할 파일 이름 설정

            //빌드
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //토큰 확인하기
    @GetMapping("/ctg/account_check")
    public ResponseEntity account_check(@CookieValue(value = "token",required = false) String token){
        System.out.println("토근 존재유무(/ctg/account_check) 실행");
        Claims claims =  jwtService.getClaims(token);
        if(claims != null){
            ObjectMapper mapper = new ObjectMapper();
            String member = (String) claims.get("user_id");
            return new ResponseEntity<>(member, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("",HttpStatus.OK);
        }
    }

    @PostMapping("/ctg/check-user-bn")
    public  ResponseEntity<?> checkUserBN() {
        // 실제 비즈니스 로직으로 권한 확인 및 데이터 반환을 수행하고 결과를 반환합니다.
        return ResponseEntity.ok(true);
    }



}
