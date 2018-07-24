package com.example.demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.test.entity.Msg;
import com.example.demo.test.service.UserServiceImpl;

@Controller
public class HomeController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

    @RequestMapping("/home")
    public String index(Model model){
        Msg msg =  new Msg("测试标题","测试内容","欢迎来到HOME页面,您拥有 ROLE_HOME 权限");
        model.addAttribute("msg", msg);
        return "home.html";
    }
    @RequestMapping("/login")
    @ResponseBody
    public  String login(){
        return userServiceImpl.toString();
    }
    
    @RequestMapping("/admin")
    @ResponseBody
    public String hello(){
        return "hello admin";
    }
    
    
    
}