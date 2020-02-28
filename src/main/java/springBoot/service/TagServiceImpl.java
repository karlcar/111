package springBoot.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springBoot.NotFoundException;
import springBoot.dao.TagRepository;
import springBoot.po.Tag;

@Service
public class TagServiceImpl implements TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	@Transactional
	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Transactional
	@Override
	public Tag getTag(Long id) {
		return  tagRepository.findById(id).orElse(null);
	}

	@Override
	public Tag getTagByName(String name) {
		return tagRepository.findByName(name);
	}
	
	@Transactional
	@Override
	public Page<Tag> ListTag(Pageable pageable) {
		return tagRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Tag updateTag(Long id, Tag tag) {
		Tag t = tagRepository.findById(id).orElse(null);
		if(t == null) {
			throw new NotFoundException("不存在该标签");
		}
		BeanUtils.copyProperties(tag, t);
		return tagRepository.save(t);
	}

	@Transactional
	@Override
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}

}
