package com.vikky.rest.webservices.webrestservices.test1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDAO {
	
	private static List<User> users = new ArrayList<User>();
	
	static {
		users.add(new User(1, "user1", new Date()));
		users.add(new User(2, "user2", new Date()));
		users.add(new User(3, "user3", new Date()));
		users.add(new User(4, "user4", new Date()));
		users.add(new User(5, "user5", new Date()));

	}

	
	public List<User> findAll(){
		return users;
	}
	
	public User add(User user) {
		if(user.getId() == null) {
			user.setId(users.size() +1);
		}
		users.add(user);
		return user;
	}
	
	
	public User findOne(int id) {
		for(User user: users) {
			if(user.getId() == id) {
				return user;
			}
		}
		
		return null;
	}
}
