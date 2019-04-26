package com.poisonedyouth.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
	private static List<User> userList = new ArrayList<>();

	private static int usersCount = 3;

	static {
		userList.add(new User(1, "Adam", new Date()));
		userList.add(new User(2, "Eve", new Date()));
		userList.add(new User(3, "Jack", new Date()));
	}

	public List<User> findAll() {
		return userList;
	}

	public User save(User user) {
		if (!userList.contains(user)) {
			if (user.getId() == null) {
				user.setId(++usersCount);
			}
			userList.add(user);
		}
		return user;
	}

	public User findById(Integer id) {
		return userList.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
	}

	public User deleteById(Integer id) {
		User user = findById(id);
		userList.remove(user);
		return user;
	}
}
