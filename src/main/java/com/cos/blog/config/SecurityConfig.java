package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration      // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 (IoC 관리)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf토큰 비활성화 (테스트시에 걸어두는게 좋음)
                .authorizeRequests() //인증절차에 대한 설정을 진행
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
                .authenticated(); //위 페이지 외 인증이 되어야 접근가능
    }
}
