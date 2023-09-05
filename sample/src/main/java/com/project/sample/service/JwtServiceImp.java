package com.project.sample.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImp implements JwtService{

    @Value("secretkey")
    private String secretkey;

    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();
        //millisecond기준 => 30분
        expTime.setTime(expTime.getTime()+ 1000*60*30);
        //key를 byte로 변환
        System.out.println(secretkey);
        byte[] secretkeyByte = DatatypeConverter.parseBase64Binary(secretkey);
        Key signKey = new SecretKeySpec(secretkeyByte, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");


        Map<String, Object> ClaimsMap = new HashMap<>();
        ClaimsMap.put(key,value);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap) //헤더정보
                .setClaims(ClaimsMap) //클레임정보 담는곳
                .setExpiration(expTime) //유효시간
                .signWith(SignatureAlgorithm.HS256, signKey); //키와 함께 암호화

        return builder.compact();
    }
}
