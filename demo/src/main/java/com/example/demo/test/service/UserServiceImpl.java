package com.example.demo.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.test.entity.SysUser;
import com.example.demo.test.mapper.UserMapper;

@Service
public class UserServiceImpl {
	@Autowired
	private UserMapper daoMapper;
	
	public void insertUserInfo(SysUser user) throws Exception{
		daoMapper.addUser(user);
	}
}
