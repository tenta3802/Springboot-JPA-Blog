package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length =100)
	private String title;
	
	@Lob //��뷮 ������
	private String content; //���ӳ�Ʈ ���̺귯��<html>�±װ� ������ ������ ��
	
	@ColumnDefault("0")
	private int count;
	
	@ManyToOne //Many = Board, User = One
	@JoinColumn(name="userId")
	private User user; //DB�� ������Ʈ�� ������ �� ����. 
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}
