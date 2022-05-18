package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

//ORM -> Java(�ٸ���� ����)object->���̺�� �������ִ� ���
@Entity // User Ŭ������ �ڵ����� MySQL�� ���̺��� ������ �ȴ�.
public class User {

	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //������Ʈ���� ����� DB�� �ѹ��� ������ ���󰣴�.
	private int id;// ������, auto_increment
		
	@Column(nullable=false, length=30)
	private String username; //���̵�
	
	@Column(nullable=false, length=100) //��� ->�ؽ�(��й�ȣ ��ȣȭ)
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
	@ColumnDefault("'user'")
	private String role;//Enum�� ���°� ����.//admin,user,manager�� ������ ��
	
	@CreationTimestamp//�ð��� �ڵ� �Է�
	private Timestamp createDate;
}
