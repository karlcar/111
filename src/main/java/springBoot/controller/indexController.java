package springBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import springBoot.NotFoundException;


@Controller
public class indexController {

    @RequestMapping("/")
    public String index(){
//        int i = 9/0;
    	
    	//模拟一篇博客找不到跳转404
//    	String blog = null;
//    	if(blog == null) {
//    		throw new NotFoundException("博客不存在");
//    	}
    	System.out.println("--------------index------------");
        return "index";
    }
    
    @RequestMapping("/blog")
    public String blog() {
    	return "blog";
    }
    
    @RequestMapping("/types")
    public String Type() {
    	return "types";
    }
    
    @RequestMapping("/tags")
    public String Tag() {
    	return "tags";
    }
    
    
}
