package com.tk.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class AlphaConfig {

	@Bean//用于自己设置bean的生成
	public SimpleDateFormat simpleDateFormat() {
		return new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	}
}
