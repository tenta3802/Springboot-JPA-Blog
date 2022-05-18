package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {
	
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// ���ϸ��� �⺻���: src/main/resources/static
		// ���ϸ�: /home.html
		// Ǯ���: src/main/resources/static/home.html
		return "/home.html";
	}
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		// prefix: /WEB-LNF/views/
		// suffix: .jsp
		// Ǯ���� : /WEB-LNF/views//test.jsp.jsp
		return "test";
	}
}
