package springBoot.service;

import springBoot.po.User;

public interface UserService {
	
	User checkUser(String username, String password);
}
