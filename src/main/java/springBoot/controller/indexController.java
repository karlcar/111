package springBoot.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import springBoot.service.BlogService;
import springBoot.service.TagService;
import springBoot.service.TypeService;
import springBoot.vo.BlogQuery;


@Controller
public class indexController {
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TagService tagService;
	
	//index页面的blog、type、tag、recommend的分页展示
    @RequestMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						Model model){
    	model.addAttribute("page", blogService.listBlog(pageable));
    	model.addAttribute("types", typeService.listTypeTop(6));
    	model.addAttribute("tags", tagService.listTagTop(10));
    	model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    } 
    
    //index页面的全局搜索（右上角）
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						Model model,
						@RequestParam String query) {  //这里的query是fragment里面的搜索框的输入域里面的name值query
    	model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));	//查询返回页面
    	model.addAttribute("query", query);		//查询之后要在搜索框保留搜索内容  
    	return "search";
    }
    
    //点击跳转到具体的博客详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
    	model.addAttribute("blog", blogService.getAndConvert(id));
    	return "blog";
    }
    
    //动态渲染footer部分
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
    
}
