package springBoot.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springBoot.po.Blog;
import springBoot.service.BlogService;
import springBoot.service.TypeService;
import springBoot.vo.BlogQuery;

@Controller
@RequestMapping("/admin")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private TypeService typeService; 
	
	//查询
	@GetMapping("/blogs")
	public String blogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						BlogQuery blog,
						Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		return "admin/blogs";
	}
	
	//查询只刷新下面的列表，即局部刷新
	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						BlogQuery blog,
						Model model) {
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		return "admin/blogs :: blogList";
	}
	
}
