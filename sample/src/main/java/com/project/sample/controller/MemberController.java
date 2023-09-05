package com.project.sample.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sample.dto.Member;
import com.project.sample.service.JwtService;
import com.project.sample.service.JwtServiceImp;
import com.project.sample.service.MemberService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//회원관련 컨트롤러
@RestController
public class MemberController {

    private final MemberService service;
    private final JwtService jwtService;

    @Autowired
    public MemberController(MemberService service,JwtService jwtService) {
        this.jwtService = jwtService;
        this.service = service;
    }


    //이름/주민 중복검사
    @PostMapping("/ctg/Check_SignUp_name")
    public Map<String, Integer> Check_SignUp_name(@RequestBody Member member) throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("checkNum", service.Check_SignUp_name(member));

        return map;
    }


    //이메일 중복검사
    @PostMapping("/ctg/Check_SignUp_email")
//    public Object Check_SignUp_email(@RequestParam("email") String email) throws Exception{
    public Map<String, Integer> Check_SignUp_email(@RequestBody Member member) throws Exception {

        System.out.println(member.getEmail());
        //JSONObject json = new JSONObject();

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("checkNum", service.Check_SignUp_email(member.getEmail()));

        //json.put("result",map);

        return map;
    }

    //회원가입
    @PostMapping("/ctg/Ins_Ctg_Member")
    public Map<String, Integer> Ins_Ctg_Member(@RequestBody Member member) throws IllegalAccessException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("checkNum", service.Ins_Ctg_Member(member));
        return map;
    }

    //로그인
    @PostMapping("/ctg/SignIn_Ctg_Member")
    public ResponseEntity SignIn_Ctg_Member(@RequestBody Member member, HttpServletResponse res) throws IllegalAccessException {
       Member member2 = service.SignIn_Ctg_Member(member);
        //System.out.println("member not empyt? =>"+member.getName());
        if(member2 != null){
            System.out.println("member not empyt? =>"+member2.getName());
            //토큰화시키기
            String token = jwtService.getToken("member", member2);

            // 쿠키로 만들어서 자바스크립트로는 접근할 수 없도록함
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            res.addCookie(cookie);

            return new ResponseEntity<>(member2,HttpStatus.OK);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        //eturn map;
    }


    //토큰 확인하기
    @GetMapping("/ctg/account_check")
    public ResponseEntity account_check(@CookieValue(value = "token",required = false) String token){
        Claims claims =  jwtService.getClaims(token);

        if(claims != null){
            // 아래 주석처럼 데이터 변환을하면
            // java.util.LinkedHashMap cannot be cast to com.project.sample.dto.Member] with root cause 캐스트할수 없다는 에러가난다
            //Member member = (member)claims.get("member");
            //해서 아래처럼 사용해야함
            ObjectMapper mapper = new ObjectMapper();
            Member member = mapper.convertValue(claims.get("member"), Member.class);
            return new ResponseEntity<>(member,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
    }

    @PostMapping("/ctg/logout")
    public ResponseEntity logout (HttpServletResponse res)throws IllegalAccessException {
        System.out.println("로그아웃");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

}
