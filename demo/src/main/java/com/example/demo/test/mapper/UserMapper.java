package com.example.demo.test.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.test.entity.SysUser;

@Component
public interface UserMapper {

	public void addUser(SysUser sysUser);
	
	public SysUser findUser(SysUser sysUser);
}
