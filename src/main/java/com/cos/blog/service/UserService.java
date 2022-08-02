package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// �������� ������Ʈ ��ĵ�� ���ؼ� Bean�� ����� ����. Ioc�� ����.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true)
	public User ȸ��ã��(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
			return user;
	}
	
	@Transactional
	public void ȸ������(User user) {
		String rawPassword = user.getPassword(); //1234 ����
		String encPassword = encoder.encode(rawPassword); //�ؽ�
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void ȸ������(User user) {
		// �����ÿ��� ���Ӽ� ���ؽ�Ʈ User ������Ʈ�� ����ȭ��Ű��, ����ȭ�� User ������Ʈ�� ����
		// select�� �ؼ� User ������Ʈ�� DB�� ���� �������� ������ ����ȭ�� �ϱ� ���ؼ�
		// ����ȭ�� ������Ʈ�� �����ϸ� �ڵ����� DB�� update���� �����ش�
		User persistence = userRepository.findById(user.getId()).orElseThrow(()-> {
			return new IllegalArgumentException("ȸ�� ã�� ����");		
		});
		
		// Validate üũ=> oauth �ʵ忡 ���� ������ ���� ����
		if(persistence.getOauth() == null || persistence.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistence.setPassword(encPassword);
			persistence.setEmail(user.getEmail());
		}

		// ȸ������ �Լ� ����� = ���� ���� = Ʈ������� ���� = commit�� �ڵ����� �˴ϴ�.
		// ����ȭ�� persistance ��ü�� ��ȭ�� �����Ǹ� ��Ƽüŷ�� �Ǿ� update���� ������.
	}
}


