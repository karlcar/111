package springBoot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import springBoot.po.Type;
import springBoot.service.BlogService;
import springBoot.service.TypeService;
import springBoot.vo.BlogQuery;

@Controller
public class TypeShowController {
	
	@Autowired
	private TypeService typeService;
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/types/{id}")
	public String types(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						Model model,
						@PathVariable Long id) {
		List<Type> types = typeService.listTypeTop(10000); 
		if(id == -1) {
			id = types.get(0).getId();	//就是将id设置成types页面get(0)第一个的id
		}
		BlogQuery blogQuery = new BlogQuery();
		blogQuery.setTypeId(id); 
		model.addAttribute("types", types);
		model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
		model.addAttribute("activeTypeId", id);		//标签选中状态是蓝色，将状态回传回去
		
		return "types";
	}
}
