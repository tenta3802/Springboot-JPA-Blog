package com.cos.blog;

import org.junit.jupiter.api.Test;

import com.cos.blog.model.Reply;

public class ReplyObject {
	
	@Test
	public void 투스트링테스트() {
		Reply reply = Reply.builder()
			.id(1)
			.user(null)
			.board(null)
			.content("안녕")
			.build();
	
	System.out.println(reply); //오브젝트 출력시에 toSting이 자동 호출됨.
	}
}
