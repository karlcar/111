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

import springBoot.po.Type;
import springBoot.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	//分页
	@GetMapping("/types")
	public String list(@PageableDefault(size = 3,sort= {"id"}, direction = Direction.DESC) 
						Pageable pageable, 
						Model model) {
		model.addAttribute("page", typeService.ListType(pageable));
		
		return "admin/types";
	}
	
	//去到新增页面
	@GetMapping("/types/input")
	public String input() {
		return "admin/types-input";
	}
	
	//新增提交
	@PostMapping("/types")
	public String post(Type type) {
		Type t = typeService.saveType(type);
		if(t == null) {
			//
		}else {
			// 
		}
		return "redirect:/admin/types";
	}
	
}
