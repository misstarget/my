package com.example.demo.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.test.entity.SysUser;
import com.example.demo.test.mapper.UserMapper;

@Service
@Transactional(rollbackFor=Exception.class,propagation=Propagation.MANDATORY)
public class SecondeServiceImpl {

	@Autowired
	private UserMapper daoMapper;
	
	public void exception() throws Exception{
		SysUser user = new SysUser();
		user.setAge(13);
		user.setUserName("xxx");
		daoMapper.addUser(user);
	}
	
	
}
