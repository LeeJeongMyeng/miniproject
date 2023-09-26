package com.project.sample.common;

import com.project.sample.dao.MemberDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SampleInterceptor implements HandlerInterceptor {

    @Autowired
    private final JwtService jwtservice;
    @Autowired
    private final MemberDao memberDao;

    public SampleInterceptor(JwtService jwtservice,MemberDao memberDao) {

        this.jwtservice = jwtservice;
        this.memberDao = memberDao;

    }

    private static final String UNAUTHORIZED_MESSAGE = "만료된 토큰입니다.";
    private static final String FORBIDDEN_MESSAGE = "사업자 회원만 이용이 가능합니다.";
    private static final String LOGIN_REQUIRED_MESSAGE = "로그인 후 이용하시기 바랍니다.";


    // Interceptor선처리 Cookie에서 token값 추출
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor preHandle Uri : "+ request.getRequestURI());

        Cookie[] cookies = request.getCookies();
        //만약 토큰이있다면
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return validateToken(response, cookie.getValue());
                }
            }
        }
        //토큰이 없을 경우
        response.sendError(HttpServletResponse.SC_FORBIDDEN, LOGIN_REQUIRED_MESSAGE);
        return false;
    }

    //토큰값으로 권한 확인
    private boolean validateToken(HttpServletResponse response, String token) throws IOException {
        try {
            Claims claims = jwtservice.getClaims(token);
            System.out.println("/ctg/check-user-bn :실행");
            String user_id = (String) claims.get("user_id");
            if(user_id == null){
                throw new NullPointerException();
            }

            boolean is_business = memberDao.is_business(user_id);
            System.out.println(is_business);
            if (!is_business) {
                System.out.println("is_business?");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, FORBIDDEN_MESSAGE);
                System.out.println("is_business2?");
                return false;
            }
            //토큰이 유효하지않을때
        } catch (JwtException | NullPointerException e) {
            System.out.println("JwtException?");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, LOGIN_REQUIRED_MESSAGE);
            return false;
        }

        return true;
    }



    //컨트롤러 호출후 실행
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor postHandle");
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("Interceptor afterCompletion");
    }
}
