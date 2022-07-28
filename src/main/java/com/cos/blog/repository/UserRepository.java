package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
//�ڵ����� bean����� �ȴ�
//@Repository ������ �����ϴ�.
public interface UserRepository extends JpaRepository<User,Integer>{
	//SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);
}


// JPA Naming ����
// SELECT * FROM user WHERE username = ?1 AND password = ?2;
// User findByUsernameAndPassword(String username, String password);

//	@Query(value="SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
//	User login(String username, String password);