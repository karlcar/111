package springBoot.service;

import java.util.List;

import springBoot.po.Comment;

public interface CommentService {
	
	//获取博客评论列表
	List<Comment> listCommentByBlogId(Long blogId);
	
	//保存前端发送过来的评论信息
	Comment saveComment(Comment comment);
	
	
	
}
