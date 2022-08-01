package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

// ������ �ȵ� ����ڵ��� ������ �� �ִ� ��θ� /auth/** ���Ϸ� ���
// �׳� �ּҰ� /�̸� index.jsp ���
// static ���Ͽ� �ִ� /js/**, /css/**, /image/**


@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {		
		return"user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return"user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) { //Data�� �������ִ� ��Ʈ�ѷ� �Լ�
		
		// POST������� key=value �����͸� ��û(īī��������)
		
		
		RestTemplate rt = new RestTemplate();
		
		
		//HttpHeader ������Ʈ ����
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody ������Ʈ ����
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type" , "authorization_code");
		params.add("client_id" , "cba6c02c1d3e60665fa691bae0c8c14e");
		params.add("redirect_uri" , "http://localhost:8000/auth/kakao/callback");
		params.add("code" , code);

		//HttpHeader�� HttpBody�� �ϳ��� ������Ʈ�� ���
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params, headers);
		
		//Http ��û�ϱ� - post������� - �׸��� response ������ ���� ����.
		ResponseEntity<String> response = rt.exchange (
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
				);
				
		
		return "īī�� ��ū ��û �Ϸ�: ��ū��û�� ���� ����: "+ response;
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return"user/updateForm";
	}
}
	