package com.tk.community.service;

import com.tk.community.dao.AlphaDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton") //实例化单例：singleton还是多例：prototype
public class AlphaService {
	@Autowired
	private AlphaDao alphaDao;

	public String find(){
		return alphaDao.select();
	}
	public AlphaService(){
		System.out.println("实例化AlphaService");
	}
	@PostConstruct
	public void init(){
		System.out.println("初始化AlphaService");
	}
	@PreDestroy
	public void destroy1(){
		System.out.println("销毁AlphaService");
	}

}
