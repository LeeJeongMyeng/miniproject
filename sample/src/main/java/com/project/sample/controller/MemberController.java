package com.project.sample.controller;


import com.project.sample.dto.Member;
import com.project.sample.common.JwtService;
import com.project.sample.service.MemberService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
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


    //이메일 중복검사
    @PostMapping("/ctg/Check_SignUp_email")
//    public Object Check_SignUp_email(@RequestParam("email") String email) throws Exception{
    public Map<String, Integer> Check_SignUp_email(@RequestBody Member member) throws Exception {

        System.out.println("이메일 중복검사(/ctg/Check_SignUp_email) 실행");
        //JSONObject json = new JSONObject();

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("checkNum", service.Check_SignUp_email(member.getEmail()));

        //json.put("result",map);

        return map;
    }
    //사업자번호 중복검사
    @PostMapping("/ctg/BN_Check")
    public int BN_Check(@RequestBody Member member){

        System.out.println("사업자번호 중복검사(/ctg/BN_Check) 실행");

        return  service.BN_Check(member);
    }

    //회원가입
    @PostMapping("/ctg/Ins_Ctg_Member")
    public int Ins_Ctg_Member(@RequestBody Member member) throws IllegalAccessException {

        System.out.println("회원가입(/ctg/Ins_Ctg_Member) 실행");

        return service.Ins_Ctg_Member(member);
    }

    //로그인
    @PostMapping("/ctg/SignIn_Ctg_Member")
    public ResponseEntity SignIn_Ctg_Member(@RequestBody Member member, HttpServletResponse res) throws IllegalAccessException {

       System.out.println("로그인(/ctg/SignIn_Ctg_Member) 실행");

        String userid = service.SignIn_Ctg_Member(member);

        if(userid != null){
            //토큰화시키기
            String token = jwtService.getToken("user_id", userid);
            // 쿠키로 만들어서 자바스크립트로는 접근할 수 없도록함
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            res.addCookie(cookie);
            return new ResponseEntity<>(userid,HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }




    @PostMapping("/ctg/logout")
    public ResponseEntity logout (HttpServletResponse res)throws IllegalAccessException {
        System.out.println("로그아웃(/ctg/logout) 실행");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0); // 유효기간을 0으로 설정하여 바로 만료시킵니다.
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);

        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    //내정보조회(회원정보,본인작성글,본인 신청글)
    @PostMapping("/ctg/get_My_Info")
    public Member get_My_Info(@RequestBody Member member) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println("/ctg/get_My_Info 실행");
        return service.get_My_Info(member);
    }

    @PostMapping("/ctg/Check_Password")
    public boolean Check_Password(@RequestBody Member member){
        System.out.println(member.getUser_id());
        System.out.println(member.getPassword());
        return service.Check_Password(member);
    }

}
