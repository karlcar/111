package springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springBoot.dao.UserRepository;
import springBoot.po.User;
import springBoot.util.MD5Utils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository UserRepository;

	@Override
	public User checkUser(String username, String password) {
		User user = UserRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
		return user;
	}
	
	
}
