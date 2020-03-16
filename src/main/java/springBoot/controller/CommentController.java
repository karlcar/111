package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import springBoot.po.Comment;
import springBoot.service.BlogService;
import springBoot.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private BlogService blogService;
	@Value("${comment.avatar}")		//在application里面设置了
	private String avatar;
	
	//返回博客评论列表
	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId, Model model) {
		model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
		return "blog :: commentList";
	}
	
	//前端点击发布之后后端这里接收信息
	@PostMapping("/comments")
	public String post(Comment comment) {
		Long blogId = comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogId));
		comment.setAvatar(avatar); 
		commentService.saveComment(comment);
		return "redirect:/comments/" + blogId;
	}
	
}
