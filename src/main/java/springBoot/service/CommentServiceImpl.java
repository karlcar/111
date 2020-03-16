package springBoot.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springBoot.dao.CommentRepository;
import springBoot.po.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public List<Comment> listCommentByBlogId(Long blogId) {
		Sort sort = new Sort(Sort.Direction.DESC,"createTime");
		return commentRepository.findByBlogId(blogId, sort);
	}
	
	@Transactional
	@Override
	public Comment saveComment(Comment comment) {
		Long parentCommentId = comment.getParentComment().getId();
		if(parentCommentId != -1) {	//有没有父评论
			comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
		}else {
			comment.setParentComment(null);
		}
		comment.setCreateTime(new Date());
		return commentRepository.save(comment);
	}

}
