package com.myspring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.myspring.anno.Component;
import com.myspring.entity.User;

@Component
public class UserDao {

	private static final List<User> userList=new ArrayList<>();
	
	static {
		for (int i = 0; i < 10; i++) {
			userList.add(new User(Long.parseLong(i+""), "name"+i, i%3==1?"男":"女"));
		}
	}
	
	public List<User> getAllUser() {
		return userList;
	}
	
	public List<User> getUserByUsername(String username){
		return userList.stream().filter(u->Objects.equals(username, u.getName())).collect(Collectors.toList());
	}
	
}
