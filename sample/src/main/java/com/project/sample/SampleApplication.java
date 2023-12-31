package com.project.sample;

import com.project.sample.dto.Member;
import jdk.nashorn.internal.objects.NativeRegExp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootApplication
public class SampleApplication {



	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer crossconfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:8800")
						.allowedMethods("*");
			}
		};
	}
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
		return new HiddenHttpMethodFilter();
	}

	@Bean
	MappingJackson2JsonView jsonView() {
		return new MappingJackson2JsonView();
	}





}
