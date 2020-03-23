package springBoot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import springBoot.po.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
	
	@Query("select t from Blog t where t.recommend = true")
	List<Blog> findTop(Pageable pageable);							//index页面最新推荐
	
	@Query("select t from Blog t where t.title like ?1 or t.content like ?1")		// ?1表示下面的第一个参数，即query
	Page<Blog> findByQuery(String query,Pageable pageable);			//index页面的全局搜索
	
	@Transactional
	@Modifying
	@Query("update Blog t set t.views = t.views + 1 where t.id= ?1")
	int updateViews(Long id);
}
