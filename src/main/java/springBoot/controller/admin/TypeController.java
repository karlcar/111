package springBoot.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springBoot.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	@GetMapping("/types")
	public String list(@PageableDefault(size = 3,sort= {"id"}, direction = Direction.DESC) 
						Pageable pageable, 
						Model model) {
		model.addAttribute("page", typeService.ListType(pageable));
		
		return "admin/types";
	}
	
	
	
	
	
}
