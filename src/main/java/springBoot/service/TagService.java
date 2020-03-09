package springBoot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import springBoot.po.Tag;


public interface TagService {
	
	Tag saveTag(Tag tag);
	
	Tag getTag(Long id);
	
	Tag getTagByName(String name);
	
	Page<Tag> ListTag(Pageable pageable);
	
	Tag updateTag(Long id, Tag tag);
	
	void deleteTag(Long id);
	
	List<Tag> listTag();	//blogs搜索条件的标签里面的内容列表
	
	List<Tag> listTag(String ids);	//提交时候获取多个值的id的集合
	
	List<Tag> listTagTop(Integer size);		//index页面的tag的条目展示
}
