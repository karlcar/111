package springBoot.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springBoot.po.Type;
import springBoot.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	//1、分页
	@GetMapping("/types")
	public String list(@PageableDefault(size = 3,sort= {"id"}, direction = Direction.DESC) 
						Pageable pageable, 
						Model model) {
		model.addAttribute("page", typeService.ListType(pageable));
		
		return "admin/types";
	}
	
	//2、去到新增页面
	@GetMapping("/types/input")
	public String input(Model model) {
		model.addAttribute("type", new Type());
		return "admin/types-input";
	}
	
	//3、新增提交
	@PostMapping("/types")
	public String post(@Valid Type type,BindingResult result, RedirectAttributes attributes) { //BindingResult接收校验的结果,@Valid必须和BindingResult连在一起
		Type t1 = typeService.getTypeByName(type.getName());
		if(t1 != null) {
			result.rejectValue("name", "nameError", "不能添加重复分类");
		}
		
		if(result.hasErrors()) {
			return "admin/types-input";
		}
		
		Type t = typeService.saveType(type);
		if(t != null) {
			attributes.addFlashAttribute("message", "新增成功");
		}else {
			attributes.addFlashAttribute("message", "新增失败");
		}
		return "redirect:/admin/types";
	}
	
}
