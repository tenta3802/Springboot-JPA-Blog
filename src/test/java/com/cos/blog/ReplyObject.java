package com.cos.blog;

import org.junit.jupiter.api.Test;

import com.cos.blog.model.Reply;

public class ReplyObject {
	
	@Test
	public void ����Ʈ���׽�Ʈ() {
		Reply reply = Reply.builder()
			.id(1)
			.user(null)
			.board(null)
			.content("�ȳ�")
			.build();
	
	System.out.println(reply); //������Ʈ ��½ÿ� toSting�� �ڵ� ȣ���.
	}
}
