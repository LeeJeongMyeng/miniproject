package com.project.sample.controller;


import com.project.sample.dto.Member;
import com.project.sample.service.JwtService;
import com.project.sample.service.JwtServiceImp;
import com.project.sample.service.MemberService;
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
        member = service.SignIn_Ctg_Member(member);
        if(member != null){

            //토큰화시키기
            String token = jwtService.getToken("member", member);

            // 쿠키로 만들어서 자바스크립트로는 접근할 수 없도록함
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            res.addCookie(cookie);

            return ResponseEntity.ok().build();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        //eturn map;
    }
   /* @PostMapping("/ctg/SignIn_Ctg_Member")
    public Map<String, Object> SignIn_Ctg_Member(@RequestBody Member member) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        member = service.SignIn_Ctg_Member(member);
        if(member == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            JwtService jwtService = new JwtServiceImp();
            String token = jwtService.getToken("member", member);
            return map;
        }


        //eturn map;
    }*/

}
