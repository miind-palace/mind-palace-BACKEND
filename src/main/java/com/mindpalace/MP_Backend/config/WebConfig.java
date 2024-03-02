package com.mindpalace.MP_Backend.config;

import com.mindpalace.MP_Backend.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","https://mind-palace.co.kr","https://mindspalace.com/","mind-palace-fe.vercel.app")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
    //스프링 인터셉터 추가
    //excludePathPatterns에 인터셉터로 거르지 않는 화이트리스트 작성
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/css/**", "/*.ico", "/error", "/member/login", "/member/logout", "/member/save", "/member/mailCheck"
                        , "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs");
    }
}
