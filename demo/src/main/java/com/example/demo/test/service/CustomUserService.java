package com.example.demo.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.test.entity.Permission;
import com.example.demo.test.entity.SysUser;
import com.example.demo.test.mapper.PermissionMapper;
import com.example.demo.test.mapper.UserMapper;

@Service
public class CustomUserService implements UserDetailsService { 

    @Autowired
    UserMapper userMapper;
    @Autowired
    PermissionMapper permissionMapper;

    public UserDetails loadUserByUsername(String  username) {
    	SysUser queryUser = new SysUser();
    	queryUser.setUserName(username);
        SysUser userInfo = userMapper.findUser(queryUser);
        if (userInfo != null) {
            List<Permission> permissions = permissionMapper.findById(userInfo.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                grantedAuthorities.add(grantedAuthority);
                }
            }
            
            return new User(userInfo.getUserName(), userInfo.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

}