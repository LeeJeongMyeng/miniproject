package com.project.sample.common;

import com.project.sample.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SampleInterceptor implements HandlerInterceptor {

    @Autowired
    private final JwtService jwtservice;

    public SampleInterceptor(JwtService jwtservice) {
        this.jwtservice = jwtservice;
    }

    //Controller실행되기 이전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor preHandle Uri : "+ request.getRequestURI());
        //세션 확인
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("member"));

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    System.out.println("preHandle token Value: "+token);
                    // JWT 라이브러리를 사용하여 토큰 검증 및 데이터 추출
                    try {
                        Claims claims = jwtservice.getClaims(token);

                        String email = (String) claims.get("email");
                        boolean is_business = (boolean) claims.get("_business");

                        System.out.println("preHandle token's email Value : "+ email);
                        System.out.println("preHandle token's email Value : "+ is_business);
                        // 필요한 권한 확인
                        if (!is_business) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한이 없습니다!");
                            return false;
                        }

                    } catch (JwtException e) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 토큰입니다.");
                        return false;
                    }

                    break;
                }
            }
        }

        return true;

    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//
//        String requestURI = request.getRequestURI();
//        System.out.println("[interceptor] : " + requestURI);
//        HttpSession session = request.getSession(false);
//
//        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
//            // 로그인 되지 않음
//            System.out.println("[미인증 사용자 요청]");
//
//            //로그인으로 redirect
//            response.sendRedirect("/login?redirectURL=" + requestURI);
//            return false;
//        }
//        // 로그인 되어있을 떄
//        return true;
//    }





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
