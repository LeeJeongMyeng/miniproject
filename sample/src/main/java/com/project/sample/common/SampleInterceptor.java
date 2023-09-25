package com.project.sample.common;

import com.project.sample.dao.MemberDao;
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
    @Autowired
    private final MemberDao memberDao;

    public SampleInterceptor(JwtService jwtservice,MemberDao memberDao) {

        this.jwtservice = jwtservice;
        this.memberDao = memberDao;

    }

    //Controller실행되기 이전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor preHandle Uri : "+ request.getRequestURI());
        //세션 확인
        //HttpSession session = request.getSession();
        //System.out.println(session.getAttribute("user_id"));

        //로그아웃상태 == 쿠키가 없을 때 접근 못하게 하기위해
        boolean check_token = false;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    //로그아웃 상태에서 쿠키가 없을때 대비
                    check_token = true;

                    String token = cookie.getValue();
                    System.out.println("preHandle token Value: "+token);
                    // JWT 라이브러리를 사용하여 토큰 검증 및 데이터 추출
                    try {
                        Claims claims = jwtservice.getClaims(token);
                        //System.out.println("claims is empty? : "+claims.isEmpty());
                        String user_id = (String) claims.get("user_id");
                        //boolean is_business = (boolean) claims.get("_business");

                        System.out.println("userid : "+user_id);

                        boolean is_business = memberDao.is_business(user_id);
                        System.out.println(is_business);
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
        if(!check_token){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "로그인 후 이용하시기 바랍니다.");
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
