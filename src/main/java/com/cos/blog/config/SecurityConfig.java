package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;

@Configuration      // �� ��� : ������ �����̳ʿ��� ��ü�� ������ �� �ְ� �ϴ� �� (IoC ����)
@EnableWebSecurity  // ��ť��Ƽ ���� �߰� = ������ ��ť��Ƽ�� Ȱ��ȭ �Ǿ� �ִµ�, � ������ �ش� ���Ͽ��� �ϰڴٶ�� ��
@EnableGlobalMethodSecurity(prePostEnabled = true) // Ư�� �ּҷ� ������ �ϸ� ���� �� ������ �̸� üũ�ϰڴٶ�� ��
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //Ioc�� �ȴ�.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// ��ť��Ƽ�� ��� �α������ִµ� password�� ����ä�� �ϴµ�
	// �ش� password�� ���� �ؽ��� �Ǿ� ȸ�������� �Ǿ����� �˾ƾ�
	// ���� �ؽ��� ��ȣȭ�ؼ� DB�� �ִ� �ؽ��� ���� �� ����
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
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
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")  // ������ ��ť��Ƽ�� �ش� �ּҷ� ��û���� �α����� ����ä�� ��� �α�������.
                .defaultSuccessUrl("/"); // �α��� �� �� url�� �̵�
    }
}