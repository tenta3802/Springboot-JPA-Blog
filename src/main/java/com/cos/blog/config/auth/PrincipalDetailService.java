package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service // Bean ���
public class PrincipalDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	// �������� �α��� ��û�� ����ç �� username, password ���� 2���� ����ä�µ�
	// password �κ� ó���� �˾Ƽ� ��
	// username�� DB�� �ִ����� Ȯ�����ָ� ��.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()-> {
					return new UsernameNotFoundException("�ش� ����ڸ� ã�� �� �����ϴ�.:"+username);
				});
		return new PrincipalDetail(principal); //��ť��Ƽ�� ���ǿ� ���� ������ ������ ��
	}
}
