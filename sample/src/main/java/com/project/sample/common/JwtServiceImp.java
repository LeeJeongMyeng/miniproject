package com.project.sample.common;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImp implements JwtService{

    @Value("${secretkey}")
    private String secretkey;

    //토큰 생성
    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();
        //토큰의 유효시간 millisecond기준 => 5분
        expTime.setTime(expTime.getTime()+ 1000*60*30);
        //key를 byte로 변환
        System.out.println(secretkey);
        byte[] secretkeyByte = DatatypeConverter.parseBase64Binary(secretkey);

        //알고리즘을과 문자열로된 key로 비밀 키를 구성하는 key클래스
        Key signKey = new SecretKeySpec(secretkeyByte, SignatureAlgorithm.HS256.getJcaName());

        //JWT의 Header 정보표시할것 정의
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        //Claims => JWT의 Body부분
        Map<String, Object> ClaimsMap = new HashMap<>();
        ClaimsMap.put(key,value);

        // 위 header/claims를 통해서 jwt토큰 생성
        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap) //헤더정보
                .setClaims(ClaimsMap) //클레임정보 담는곳
                .setExpiration(expTime) //유효시간
                .signWith(SignatureAlgorithm.HS256, signKey); //키와 함께 암호화

        return builder.compact();
    }

    //Claims 생성
    @Override
    public Claims getClaims(String token) {
        if(token !=null && !token.isEmpty()){
            try{
                System.out.println(secretkey);
                byte[] secretkeyByte = DatatypeConverter.parseBase64Binary(secretkey);
                Key signKey = new SecretKeySpec(secretkeyByte, SignatureAlgorithm.HS256.getJcaName());
                return (Claims) Jwts.parser().setSigningKey(signKey).parseClaimsJws(token).getBody();
            }catch (ExpiredJwtException e){
                //만료됨
            }catch (JwtException e){
                //유효하지않음
            }
        }
        return null;
    }
}
