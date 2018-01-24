package com.myspring.controller;

import java.util.List;

import com.myspring.anno.Autowried;
import com.myspring.anno.Component;
import com.myspring.entity.User;
import com.myspring.server.UserService;

@Component
public class UserController {

	@Autowried
	private UserService userService;
	
	public List<User> getAllUser() {
		return userService.getAllUser();
	}
	
	public List<User> getUserByUsername(String username){
		return userService.getUserByUsername(username);
	}
}
