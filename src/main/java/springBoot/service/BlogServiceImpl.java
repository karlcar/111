package springBoot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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
import springBoot.util.MarkdownUtils;
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
	
	@Transactional
	@Override
	public Blog getAndConvert(Long id) {		//将文本从markdown转换成html
		Blog blog = blogRepository.findById(id).orElse(null);
		if(blog == null) {
			throw new NotFoundException("博客不存在");
		}
		Blog b = new Blog();				//
		BeanUtils.copyProperties(blog, b);	//这里两行是为了不要操作的时候直接动数据库，先本地动完再去数据库
		
		String content = b.getContent();
		b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
		//浏览次数功能
		blogRepository.updateViews(id);
		
		return b;
	}
	
	//tags展示页面的展示方法实现
	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Join join = root.join("tags");	//关联查询Blog对象类中的tags
				return cb.equal(join.get("id"), tagId);
			}
		}, pageable);
	}

	//这是归档页面的展示实现
	@Override
	public Map<String, List<Blog>> archiveBlog() {
		List<String> years = blogRepository.findGroupYear();
		Map<String, List<Blog>> map = new HashMap<>();
		for(String year : years) {	//遍历一遍符合的blog进行展示
			map.put(year, blogRepository.findByYearResultGroup(year));
		}
		return map;
	}
	
	//归档页面的博客条数
	@Override
	public Long countBlog() {
		return blogRepository.count();
	}

	
	
	
	
	
	
	
}
