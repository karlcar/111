package springBoot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springBoot.NotFoundException;
import springBoot.dao.BlogRepository;
import springBoot.po.Blog;
import springBoot.po.Type;
import springBoot.util.MyBeanUtils;
import springBoot.vo.BlogQuery;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Override
	public Blog getBlog(Long id) {
		return blogRepository.findById(id).orElse(null);
	}
	
	//动态组合查询条件进行分页查询 
	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
		return blogRepository.findAll(new Specification<Blog>() {
			
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {	//root代表你要查询的对象，cq代表装查询条件的容器，cb代表具体条件的表达式
				List<Predicate> predicates = new ArrayList<>();		//组合的条件
				//title的查询条件
				if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicates.add(cb.like(root.<String>get("title"),"%"+ blog.getTitle() + "%"));		//构建like表达式，属性的名字和属性的值
				} 
				//分类的查询条件
				if(blog.getTypeId() != null) {	//因为long类型id不存在""的情况  
					predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
				} 
				
				//是否推荐
				if(blog.isRecommend()) {
					predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
				}
				
				cq.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}
	
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		if(blog.getId() == null) {	//新增
			blog.setCreateTime(new Date());		//初始化创建时间
			blog.setUpdateTime(new Date());		//初始化更新时间
			blog.setViews(0);					//初始化浏览人数
		}else {						//修改
			blog.setUpdateTime(new Date());		//初始化更新时间
		}
		
		return blogRepository.save(blog);
	}
	
	@Transactional 
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b = blogRepository.findById(id).orElse(null);
		if(b == null) {
			throw new NotFoundException("该博客不存在");
		}
//		BeanUtils.copyProperties(blog, b);
		BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());				//此两行为了在修改之后，不会初始，直接将原本没有修改的也一并写入数据库
		return blogRepository.save(b);
	}
	
	@Transactional
	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		return blogRepository.findAll(pageable);
	}

	@Override
	public List<Blog> listRecommendBlogTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
		Pageable pageable = PageRequest.of(0, size, sort);
		return blogRepository.findTop(pageable);
	}

	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		return blogRepository.findByQuery(query, pageable);
	}

	
	
	
	
	
}
