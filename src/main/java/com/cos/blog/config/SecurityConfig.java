package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration      // �� ��� : ������ �����̳ʿ��� ��ü�� ������ �� �ְ� �ϴ� �� (IoC ����)
@EnableWebSecurity  // ��ť��Ƽ ���� �߰� = ������ ��ť��Ƽ�� Ȱ��ȭ �Ǿ� �ִµ�, � ������ �ش� ���Ͽ��� �ϰڴٶ�� ��
@EnableGlobalMethodSecurity(prePostEnabled = true) // Ư�� �ּҷ� ������ �ϸ� ���� �� ������ �̸� üũ�ϰڴٶ�� ��
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean //Ioc�� �ȴ�.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf��ū ��Ȱ��ȭ (�׽�Ʈ�ÿ� �ɾ�δ°� ����)
                .authorizeRequests()
                .antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
                .permitAll()   // ���� ������ ������ ���
                .anyRequest()   // ������ �ƴ� ��� ��û�� �Ʒ���
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm");
                //.loginProcessingUrl("/auth/loginProc")  // ������ ��ť��Ƽ�� �ش� �ּҷ� ��û���� �α����� ����ä�� ��� �α�������.
                //.defaultSuccessUrl("/"); // �α��� �� �� url�� �̵�
    }
}