package com.example.demo.test.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOPDemo {
	
	@Pointcut(value="execution(public * com.example.demo.test.*.*(..))")
	public void cutPoint(){
		
	}
	
	@Before(value ="cutPoint()&&args(userName,age)")
	public void TestAop(String userName,int age){
		System.out.println("我是"+userName+"\n年龄"+age);
	}
}
