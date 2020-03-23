package springBoot.service;

import java.util.List;
import java.util.Map;

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
	
	Page<Blog> listBlog(String query,Pageable pageable);	//index页面的全局搜索
	
	Blog getAndConvert(Long id);	//封装一个将文本从markdown转换成html的方法
	
	Page<Blog> listBlog(Long id,Pageable pageable);	//tags展示页面的查询展示
	
	Map<String,List<Blog>> archiveBlog();		//这是归档页面的展示，String是年份，List是展示的内容结果集合
	
	Long countBlog();	//归档页面的博客条数
	
	
}
