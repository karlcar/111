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
import springBoot.po.Type;
import springBoot.service.BlogService;
import springBoot.service.TagService;
import springBoot.service.TypeService;
import springBoot.vo.BlogQuery;

@Controller
@RequestMapping("/admin")
public class BlogController {
	
	private static final String INPUT = "admin/blogs-input";		//博客新增
	private static final String LIST = "admin/blogs";			//博客列表
	private static final String REDIRECT_LIST = "redirect:/admin/blogs";		//更新过之后需要重定向 
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private TypeService typeService; 
	@Autowired
	private TagService tagService;
	
	//查询
	@GetMapping("/blogs")
	public String blogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						BlogQuery blog,
						Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		return LIST;
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
	
	//新增跳转到博客新增的页面
	@GetMapping("/blogs/input")
	public String input(Model model) {
		model.addAttribute("types", typeService.listType());	//初始化分类
		model.addAttribute("tags", tagService.listTag());	//初始化标签
		model.addAttribute("blog", new Blog());
		return INPUT;
	}
	
	
	 
	
	
	
}
