package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//ORM -> Java(�ٸ���� ����)object->���̺�� �������ִ� ���
@Entity // User Ŭ������ �ڵ����� MySQL�� ���̺��� ������ �ȴ�.
// @DynamicInsert // insert�ÿ� null�� �ʵ带 ���ܽ����ش�.
public class User {

	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //������Ʈ���� ����� DB�� �ѹ��� ������ ���󰣴�.
	private int id;// ������, auto_increment
		
	@Column(nullable=false, length=100, unique=true)
	private String username; //���̵�
	
	@Column(nullable=false, length=100) //��� ->�ؽ�(��й�ȣ ��ȣȭ)
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
	//@ColumnDefault("user")
	//DB�� RoleType�̶�°� ����.
	@Enumerated(EnumType.STRING)
	private RoleType role;//Enum�� ���°� ����.//ADMIN,USER
	
	private String oauth; //kakao, google
	
	@CreationTimestamp//�ð��� �ڵ� �Է�
	private Timestamp createDate;
}
