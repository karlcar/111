package springBoot.service;

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
}
