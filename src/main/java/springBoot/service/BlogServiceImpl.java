package springBoot.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import springBoot.NotFoundException;
import springBoot.dao.BlogRepository;
import springBoot.po.Blog;
import springBoot.po.Type;

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
	public Page<Blog> listBlog(Pageable pageable, Blog blog) {
		return blogRepository.findAll(new Specification<Blog>() {
			
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {	//root代表你要查询的对象，cq代表装查询条件的容器，cb代表具体条件的表达式
				List<Predicate> predicates = new ArrayList<>();		//组合的条件
				//title的查询条件
				if((!blog.getTitle().equals("")) && blog.getTitle() != null) {
					predicates.add(cb.like(root.<String>get("title"),"%"+ blog.getTitle() + "%"));		//构建like表达式，属性的名字和属性的值
				}
				//分类的查询条件
				if(blog.getType().getId() != null) {	//因为long类型id不存在""的情况  
					predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getType().getId()));
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

	@Override
	public Blog saveBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b = blogRepository.findById(id).orElse(null);
		if(b == null) {
			throw new NotFoundException("该博客不存在");
		}
		BeanUtils.copyProperties(blog, b);
		return blogRepository.save(b);
	}

	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}
	
}
