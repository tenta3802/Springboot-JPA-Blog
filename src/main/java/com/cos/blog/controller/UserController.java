package com.cos.blog.controller;

import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.GetMapping;

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
}
	