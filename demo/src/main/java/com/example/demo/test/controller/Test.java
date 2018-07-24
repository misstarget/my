package com.example.demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.test.entity.SysUser;
import com.example.demo.test.service.UserServiceImpl;

@RestController
@RequestMapping(value="/myaop",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
public class Test {
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@RequestMapping(value="/test")
	public String aopTest(SysUser user) throws Exception{
		userServiceImpl.insertUserInfo(user);
		return userServiceImpl.toString();
	}
	
}
