package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration      // �� ��� : ������ �����̳ʿ��� ��ü�� ������ �� �ְ� �ϴ� �� (IoC ����)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf��ū ��Ȱ��ȭ (�׽�Ʈ�ÿ� �ɾ�δ°� ����)
                .authorizeRequests() //���������� ���� ������ ����
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
                .authenticated(); //�� ������ �� ������ �Ǿ�� ���ٰ���
    }
}
