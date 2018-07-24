package com.example.demo.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.example.demo.test.entity.Permission;

@Component
public interface PermissionMapper {

	public List<Permission> findAll();
	
	public List<Permission> findById(@Param("id") Integer id);
}
