package springBoot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import springBoot.po.Comment;
import springBoot.po.User;
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
	public String post(Comment comment,HttpSession session) {
		Long blogId = comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogId));
		//如果是管理员
		User user = (User) session.getAttribute("user");
		if(user != null) {
			comment.setAvatar(user.getAvatar());
			comment.setAdminComment(true);
//			comment.setNickname(user.getNickname());
		}else {
			comment.setAvatar(avatar); 		//普通访客
		}
		
		commentService.saveComment(comment);
		return "redirect:/comments/" + blogId;
	}
	
}
