package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html ������ �ƴ϶� data�� �������ִ� controller = RestController
@RestController
public class DummyController {

	@Autowired //������ ����(DI)
	private UserRepository userRepository;
	
	//save�Լ��� id�� �������� ������ insert�� ���ְ�
	//save�Լ��� id�� �����ϸ� �ش� id�� ���� �����Ϳ� ������ update�� ���ְ�
	//save�Լ��� id�� �����ϸ� �ش� id�� ���� �����Ͱ� ������ insert�� �Ѵ�.
	//email, password
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
		userRepository.deleteById(id);
	}catch(EmptyResultDataAccessException e) {
		return "������ �����Ͽ����ϴ�. �ش� id�� DB�� �����ϴ�.";
	}
	return "�����Ǿ����ϴ�.id:"+id;
	}

	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {//json �����͸� ��û=>java object(MessageConverter�� Jackson ���̺귯���� ��ȯ�ؼ� �޾��ش�.)
		System.out.println("id: "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("email: "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
				return new IllegalArgumentException("������ �����Ͽ����ϴ�.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		// userRepository.save(user);
		
		//��Ƽ äŷ
		return user;
	}
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//���������� 2���� �����͸� ���Ϲ޾� �� ����
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	//{id} �ּҷ� �Ķ���͸� ���� ���� �� ����.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user /4�� ã���� ���� �����ͺ��̽����� ��ã�ƿ��� �Ǹ� user�� null�� �� �� �Ƴ�?
		// �׷� return null �� ������ ���ݾ�.. �׷� ���α׷��� ������ ���� �ʰڴ�?
		// Optional�� ���� User ��ü�� ���μ� �������״� null���� �ƴ��� �Ǵ��ؼ� return ��!
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
 				return new IllegalArgumentException("�ش� ������ �����ϴ�. id:"+id);
			}
		});
		// ��û : �� ������
		// user ��ü = �ڹ� ������Ʈ
		// ��ȯ (����������� ������ �� �ִ� ������)->json
		// ��������Ʈ MessageConverter��� �ְ� ����ÿ� �ڵ� �۵�
		// ���࿡ �ڹ� ������Ʈ�� �����ϰ� �Ǹ� MessageCoverter�� Jackson ���̺귯���� ȣ���ؼ�
		// user ������Ʈ�� json���� ��ȯ�ؼ� ���������� �����ݴϴ�.
		return user;
	}
	//http://localhost:8000/blog/dummy/join(��û)
	//http�� body�� username, password, email �����͸� ������ (��û)
	@PostMapping("/dummy/join")
	public String join(User user) {//key=value (��ӵ� ��Ģ)           
		System.out.println("username:"+user.getUsername());
		System.out.println("password:"+user.getPassword());
		System.out.println("email:"+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "ȸ�������� �Ϸ�Ǿ����ϴ�.";
	}
}
