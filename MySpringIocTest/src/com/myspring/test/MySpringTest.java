package com.myspring.test;

import java.util.List;

import com.myspring.controller.UserController;
import com.myspring.core.MySpringBeans;
import com.myspring.entity.User;


public class MySpringTest {

	//测试是否生效
	public static void main(String[] args) throws Exception {
		MySpringBeans msb=new MySpringBeans();
		UserController userControlelr=(UserController) msb.getBeanByClassName("UserController");
		List<User> userList=userControlelr.getUserByUsername("name1");
		userList.forEach(System.out::println);
	}
}
