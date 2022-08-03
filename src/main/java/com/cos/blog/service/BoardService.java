package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

// �������� ������Ʈ ��ĵ�� ���ؼ� Bean�� ����� ����. Ioc�� ����.
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void �۾���(Board board, User user) { // title,content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> �۸��(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board �ۻ󼼺���(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("�� �󼼺��� ����: ���̵� ã�� �� �����ϴ�.");
		});
	}

	@Transactional
	public void �ۻ����ϱ�(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void �ۼ����ϱ�(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> {
				return new IllegalArgumentException("�� ã�� ����: ���̵� ã�� �� �����ϴ�.");
			});//����ȭ �Ϸ�
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//�ش� �Լ��� �����(Ʈ������� Service�� ����� ��) Ʈ������� ����˴ϴ�. �̶� ��Ƽüŷ - �ڵ� ������Ʈ�� DB flush
	}
	
	@Transactional
	public void ��۾���(ReplySaveRequestDto replySaveRequestDto) {
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("BoardService: "+ result);
	}
	
	@Transactional
	public void ��ۻ���(int replyId) {
		replyRepository.deleteById(replyId);
	}
}