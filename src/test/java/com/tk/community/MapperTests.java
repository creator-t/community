package com.tk.community;

import com.tk.community.dao.DiscussPostMapper;
import com.tk.community.dao.UserMapper;
import com.tk.community.entity.DiscussPost;
import com.tk.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private DiscussPostMapper discussPostMapper;
	@Test
	public void testSelectUser() {
		User user = userMapper.selectById(1);
		User user1 = userMapper.selectByEmail("test@qq.com");
		User user2 = userMapper.selectByName("田凯");
		
		System.out.println("user" + user + "user1" + user1 + "user2" + user2);
		
		
	}
	
	@Test
	public void testInsertUser() {
		User user = new User();
		user.setUsername("田凯");
		user.setPassword("123456");
		user.setSalt("abc");
		user.setEmail("test@qq.com");
		user.setStatus(1);
		user.setActivationCode("eqk4");
		Date date = new Date();
		user.setCreateTime(date);
		user.setHeaderUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.gmass.co%2Fblog%2Femail-header%2F&psig=AOvVaw1dz13BYIRlITcR0ZtCYe5c&ust=1671350681524000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCLjo9s6YgPwCFQAAAAAdAAAAABAE");
		int i = userMapper.insertUser(user);
		System.out.println(i);
	}
	
	@Test
	public void testUpdateUser() {
		int i = userMapper.updateHeader(1, "https://p.qqan.com/up/2021-1/16110237145764738.jpg");
		int i1 = userMapper.updatePassword(1, "1234567");
		int i2 = userMapper.updateStatus(1, 0);
		System.out.println(i + i1 + i2);
	}
	
	@Test
	public void testSelectPosts(){
		List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10);
		for (DiscussPost post: list
		     ) {
			System.out.println(post);
		}
		int i = discussPostMapper.selectDiscussPostRows(0);
		System.out.println(i);
		
	}
}
