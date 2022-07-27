package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// �������� ������Ʈ ��ĵ�� ���ؼ� Bean�� ����� ����. Ioc�� ����.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void ȸ������(User user) {
		userRepository.save(user);
	}
}
