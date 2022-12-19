package com.tk.community.controller;

import com.tk.community.service.AlphaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaHello {
	@Autowired
	private AlphaService alphaService;
	
	@RequestMapping("/hello")
	@ResponseBody
	public String seeHello() {
		return "hello spring boot.";
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public String getData() {
		return alphaService.find();
	}
	
	@RequestMapping("/http")
	public void http(HttpServletRequest request, HttpServletResponse response) {
		//request
		System.out.println(request.getMethod());
		System.out.println(request.getContextPath());
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = request.getHeader(name);
			System.out.println(name + ": " + value);
		}
		System.out.println(request.getParameter("code"));
		
		//response
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(200);
		try (PrintWriter writer = response.getWriter()) {
			writer.write("<h1>TK网</h1>");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	// /students?current=1&limit=30
	@RequestMapping(path = "/students", method = RequestMethod.GET)
	@ResponseBody
	public String getStudents(
			@RequestParam(name = "current", required = false, defaultValue = "1") int current,
			@RequestParam(name = "limit", required = false, defaultValue = "30") int limit) {
		return ("current:" + current + "limit:" + limit);
	}
	
	// /student/101
	@RequestMapping(path = "/student/{id}") // 路径不要随便加空格
	@ResponseBody
	public String getStudent(
			@PathVariable(name = "id", required = true) int id) {
		return ("student id:" + id);
	}
	
	//Post
	@RequestMapping(path = "/student", method = RequestMethod.POST)
	@ResponseBody
	public String insertStudent(String name, int age) {
		return "name" + name + "age" + age;
	}
	
	//动态HTML
	@RequestMapping(path = "/teacher", method = RequestMethod.GET)
	public ModelAndView getTeacher() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("name", "张三");
		mav.addObject("age", "23");
		mav.setViewName("/demo/teacher");
		return mav;
		
	}
	
	@RequestMapping(path = "/school", method = RequestMethod.GET)
	public String getSchool(Model model) {
		model.addAttribute("name", "北京大学");
		model.addAttribute("age", 80);
		return "/demo/teacher";
		
	}
	
	//返回JSON
	@RequestMapping(path = "/emp", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getEmp() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "张三");
		map.put("age", 20);
		map.put("salary", 8000.00);
		
		return map;
		
		
	}
	
	@RequestMapping(path = "/emps", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getEmps() {
		List<Map<String, Object>> list = new ArrayList<>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "张三");
		map.put("age", 20);
		map.put("salary", 8000.00);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "李四");
		map.put("age", 31);
		map.put("salary", 10000.00);
		list.add(map);
		
		return list;
		
		
	}
	
	
}
