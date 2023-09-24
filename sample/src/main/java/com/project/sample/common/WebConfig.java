package com.project.sample.common;

import com.project.sample.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private final JwtService jwtservice;

    public WebConfig(JwtService jwtservice) {
        this.jwtservice = jwtservice;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor()) // LogInterceptor 등록
//                .order(1)	// 적용할 필터 순서 설정
//                .addPathPatterns("/**")
//                .excludePathPatterns("/error"); // 인터셉터에서 제외할 패턴

        registry.addInterceptor(new SampleInterceptor(jwtservice))//LoginCheckInterceptor 등록
                .order(1) // 적용할 필터 순서 설정 낮을수록 우선순위가 높음
                .addPathPatterns("/ctg/reg_FleaMarket","/ctg/application_FM");
                //.excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**");
    }
}
