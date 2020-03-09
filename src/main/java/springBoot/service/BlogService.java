package springBoot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import springBoot.po.Blog;
import springBoot.vo.BlogQuery;

public interface BlogService {
	
	Blog getBlog(Long id);	//查询
	
	Page<Blog> listBlog(Pageable pageable, BlogQuery blog);  //分页，Blog blog是封装了查询条件
	
	Blog saveBlog(Blog blog); //新增
	
	Blog updateBlog(Long id, Blog blog); //修改	
	
	void deleteBlog(Long id); //删除
	
	Page<Blog> listBlog(Pageable pageable);   //index页面左侧blog的分页
	
	List<Blog> listRecommendBlogTop(Integer size);	 //index页面最新推荐的分页
	
	
	
	
	
	
}
