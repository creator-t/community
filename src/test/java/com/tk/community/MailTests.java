package com.tk.community;

import com.tk.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {
	@Autowired
	private MailClient mailClient;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Test
	public void testSampleMail(){
		mailClient.sentMail("15029104275tk@gmail.com","Test","Welcome Community!");
		
	}
	
	@Test
	public void testHTML(){
		Context context = new Context();
		context.setVariable("username","KaiTian");
		String process = templateEngine.process("mail/demo", context);
		System.out.println(process);
		mailClient.sentMail("15029104275tk@gmail.com","HTML",process);
		
	}
}
