package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

// ����ڰ� ��û->����(html ����)
// @Controller

// ����ڰ� ��û->����(Data)
@RestController
public class HttpControllerTest {
	
	//���ͳ� ������ ��û�� ������ GET��û�ۿ� �� �� ����.
	//http://localhost:8080/http/get(select)
	@GetMapping("/http/get")
	public String getTest() {
		return "get ��û";
	}
	
	//http://localhost:8080/http/post(insert)
	@PostMapping("/http/post")
	public String postTest() {
		return "post ��û";
	}
	
	//http://localhost:8080/http/put(update)
	@PutMapping("/http/put")
	public String putTest() {
		return "put ��û";
	}
	
	//http://localhost:8080/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete ��û";
	}
}
