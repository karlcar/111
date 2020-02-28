package springBoot.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springBoot.po.Blog;
import springBoot.service.BlogService;

@Controller
@RequestMapping("/admin")
public class BlogController {
	
	@Autowired
	private BlogService blogSdrvice;
	
	@GetMapping("/blogs")
	public String blogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						Blog blog,
						Model model) {
		model.addAttribute("page", blogSdrvice.listBlog(pageable, blog));
		return "admin/blogs";
	}
}
