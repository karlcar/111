package springBoot.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import springBoot.po.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByBlogId(Long blogId,Sort sort);
	
}
