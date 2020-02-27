package springBoot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springBoot.po.User;

//如查数据库里面的用户名信息，通过dao层进行查询
public interface UserRepository extends JpaRepository<User, Long>{
	//继承的JpaRepository已经拥有了增删改查
	User findByUsernameAndPassword(String username, String password);
	
}
