package com.tk.community.service;

import com.tk.community.dao.UserMapper;
import com.tk.community.entity.User;
import com.tk.community.util.CommunityUtil;
import com.tk.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MailClient mailClient;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Value("${community.path.domain}")
	private String domain;
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	public User findUserById(int id) {
		return userMapper.selectById(id);
	}
	
	public Map<String, Object> register(User user) {
		Map<String, Object> map = new HashMap<>();
		
		//空值处理
		if (user == null) {
			throw new IllegalArgumentException("参数不能为空！");
		}
		if (StringUtils.isBlank(user.getUsername())) {
			map.put("usernameMsg", "账号不能为空！");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			map.put("passwordMsg", "密码不能为空！");
		}
		if (StringUtils.isBlank(user.getEmail())) {
			map.put("emailMsg", "邮箱不能为空！");
		}
		
		//验证账号
		User u = userMapper.selectByName(user.getUsername());
		if (u != null) {
			map.put("usernameMsg", "该账号名已存在！");
			return map;
		}
		
		//验证邮箱
		u = userMapper.selectByEmail(user.getEmail());
		if (u != null) {
			map.put("emailMsg", "该邮箱已被注册！");
			return map;
		}
		
		//注册用户
		user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
		user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
		user.setType(0);
		user.setStatus(0);
		user.setActivationCode(CommunityUtil.generateUUID());
		user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
		user.setCreateTime(new Date());
		userMapper.insertUser(user);
		
		//激活邮件
		Context context = new Context();
		context.setVariable("email", user.getEmail());
		String url = domain + contextPath + "/activation" + user.getId() + "/" + user.getActivationCode();
		context.setVariable("url", url);
		String content = templateEngine.process("/mail/activation", context);
		mailClient.sentMail(user.getEmail(), "激活账号", content);
		
		return map;
		
	}
}
