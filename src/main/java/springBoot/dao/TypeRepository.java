package springBoot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springBoot.po.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {
	
}
