package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						Model model){
    	model.addAttribute("page", blogService.listBlog(pageable));
    	model.addAttribute("types", typeService.listTypeTop(6));
    	model.addAttribute("tags", tagService.listTagTop(10));
        return "index";
    }
    
    
    
    
}
