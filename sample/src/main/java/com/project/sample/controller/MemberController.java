package com.project.sample.controller;


import com.project.sample.dto.Member;
import com.project.sample.service.MemberService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//회원관련 컨트롤러
@RestController
public class MemberController {

    private final MemberService service;
    @Autowired
    public MemberController(MemberService service) {
        this.service = service;
    }



    //이름/주민 중복검사
    @PostMapping("/ctg/Check_SignUp_name")
    public Map<String,Integer> Check_SignUp_name (@RequestBody Member member) throws Exception{
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("checkNum",service.Check_SignUp_name(member));

        return map;
    }


    //이메일 중복검사
    @PostMapping("/ctg/Check_SignUp_email")
//    public Object Check_SignUp_email(@RequestParam("email") String email) throws Exception{
    public Map<String,Integer> Check_SignUp_email(@RequestBody Member member) throws Exception{

        System.out.println(member.getEmail());
        //JSONObject json = new JSONObject();

        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("checkNum",service.Check_SignUp_email(member.getEmail()));

        //json.put("result",map);

        return map;
    }

    //회원가입
    @PostMapping("/ctg/Ins_Ctg_Member")
    public Map<String,Integer> Ins_Ctg_Member(@RequestBody Member member){
        Map<String,Integer> map = new HashMap<String, Integer>();
        //map.put("checkNum",service.Ins_Ctg_Member(member));
        return map;
    }

}
