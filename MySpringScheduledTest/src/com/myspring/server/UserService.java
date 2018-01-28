package com.myspring.server;

import java.util.List;

import com.myspring.anno.Autowried;
import com.myspring.anno.Component;
import com.myspring.dao.UserDao;
import com.myspring.entity.User;

@Component
public class UserService {

	@Autowried
	private UserDao userDao;
	
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}
	
	public List<User> getUserByUsername(String username){
		return userDao.getUserByUsername(username);
	}
}
