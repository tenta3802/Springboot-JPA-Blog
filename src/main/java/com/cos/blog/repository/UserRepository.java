package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
//�ڵ����� bean����� �ȴ�
//@Repository ������ �����ϴ�.
public interface UserRepository extends JpaRepository<User,Integer>{

}
