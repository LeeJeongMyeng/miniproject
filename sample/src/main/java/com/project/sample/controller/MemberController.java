package com.project.sample.controller;


import com.project.sample.service.FleamarketService;
import com.project.sample.service.MemberService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(name = "/ctg/Check_SignUp_email")
    public Object Check_SignUp_email(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String email = request.getParameter("email");
        System.out.println(email);
        JSONObject json = new JSONObject();

        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("checkNum",service.Check_SignUp_email(email));

        json.put("result",map);

        return json;
    }

}
