package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// ������ �ȵ� ����ڵ��� ������ �� �ִ� ��θ� /auth/** ���Ϸ� ���
// �׳� �ּҰ� /�̸� index.jsp ���
// static ���Ͽ� �ִ� /js/**, /css/**, /image/**

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) { // Data�� �������ִ� ��Ʈ�ѷ� �Լ�

		// POST������� key=value �����͸� ��û(īī��������)

		RestTemplate rt = new RestTemplate();

		// HttpHeader ������Ʈ ����
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody ������Ʈ ����
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "cba6c02c1d3e60665fa691bae0c8c14e");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader�� HttpBody�� �ϳ��� ������Ʈ�� ���
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);

		// Http ��û�ϱ� - post������� - �׸��� response ������ ���� ����.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);
		
		// Gson, Json simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("īī�� ������ ��ū :"+oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();

		// HttpHeader ������Ʈ ����
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader�� HttpBody�� �ϳ��� ������Ʈ�� ���
		HttpEntity<MultiValueMap<String, String>> kakaoTokenfileRequest2 = 
				new HttpEntity<>(headers2);

		// Http ��û�ϱ� - post������� - �׸��� response ������ ���� ����.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoTokenfileRequest2,
				String.class);
		
		System.out.println(response2.getBody());
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//User ������Ʈ: username, password, email
		System.out.println("īī�� ���̵�(��ȣ) : "+kakaoProfile.getId());
		System.out.println("īī�� �̸��� : "+kakaoProfile.getKakao_account().getEmail());

		System.out.println("��α׼��� �������� : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("��α׼��� �̸��� : "+kakaoProfile.getKakao_account().getEmail());
		// UUID�� -> �ߺ����� �ʴ� � Ư�� ���� ������ �˰���
		System.out.println("��α׼��� �н����� : "+cosKey);
		
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.build();
		
		// ������ Ȥ�� ������ üũ �ؼ� ó��		
		User originUser = userService.ȸ��ã��(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("���� ȸ���� �ƴϱ⿡ �ڵ� ȸ�������� �����մϴ�.");
			userService.ȸ������(kakaoUser);
		}
		
		System.out.println("�ڵ� �α����� �����մϴ�.");
		//�α��� ó��
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
}
