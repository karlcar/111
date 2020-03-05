package springBoot.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springBoot.po.Blog;
import springBoot.po.Type;
import springBoot.po.User;
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
	public String blogs(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						BlogQuery blog,
						Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		return LIST;
	} 
	
	//查询只刷新下面的列表，即局部刷新
	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						BlogQuery blog,
						Model model) {
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		return "admin/blogs :: blogList";
	}
	
	//给下面input和editInput封装
	private void setTypeAndTag(Model model) {
		model.addAttribute("types", typeService.listType());	//初始化分类
		model.addAttribute("tags", tagService.listTag());	//初始化标签
	}
	
	//新增跳转到博客新增的页面
	@GetMapping("/blogs/input")
	public String input(Model model) {
		setTypeAndTag(model); 	//调用上面的封装方法
		model.addAttribute("blog", new Blog());
		return INPUT;
	}
	
	//跳转到修改的页面（与新增类似）
	@GetMapping("/blogs/{id}/input")
	public String editInput(@PathVariable Long id,Model model) {
		setTypeAndTag(model); 	//调用上面的封装方法
		Blog blog = blogService.getBlog(id);
		blog.init();	//为了将tagIds变成字符串
		model.addAttribute("blog", blog);
		return INPUT;
	}		
	
	//博客新增提交功能
	@PostMapping("/blogs")
	public String post(Blog blog,HttpSession session,RedirectAttributes attributes) {
		blog.setUser((User)session.getAttribute("user"));
		blog.setType(typeService.getType(blog.getType().getId()));		//这里是前端页面获取的type赋值给参数blog，然后new type并获取里面的id
		blog.setTags(tagService.listTag(blog.getTagIds()));
		
		Blog b = blogService.saveBlog(blog);
		if(b != null) {
			attributes.addFlashAttribute("message", "新增成功");
		}else {
			attributes.addFlashAttribute("message", "新增失败");
		}
		return REDIRECT_LIST;
	}
	
	//删除功能
	@GetMapping("/blogs/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes attributes) {
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "删除成功");
		return REDIRECT_LIST;
	}
	 
	
	
	
}
