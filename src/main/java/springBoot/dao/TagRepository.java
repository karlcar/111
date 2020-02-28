package springBoot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springBoot.po.Tag;


public interface TagRepository extends JpaRepository<Tag, Long> {
	
	Tag findByName(String name);
}
