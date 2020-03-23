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

import springBoot.po.Tag;
import springBoot.service.BlogService;
import springBoot.service.TagService;
import springBoot.vo.BlogQuery;

@Controller
public class TagShowController {
	
	@Autowired
	private TagService tagService;
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/tags/{id}")
	public String types(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Direction.DESC) 
						Pageable pageable, 
						Model model,
						@PathVariable Long id) {
		List<Tag> tags = tagService.listTagTop(10000); 
		if(id == -1) {
			id = tags.get(0).getId();	//就是将id设置成types页面get(0)第一个的id
		}
 		model.addAttribute("tags", tags);
		model.addAttribute("page", blogService.listBlog(id, pageable));
		model.addAttribute("activeTagId", id);		//标签选中状态是蓝色，将状态回传回去
		
		return "tags";
	}
}
