package springBoot.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springBoot.NotFoundException;
import springBoot.dao.TypeRepository;
import springBoot.po.Type;

@Service
public class TypeServiceImpl implements TypeService {
	

	@Autowired
	private TypeRepository typeRepository;
	
	@Transactional
	@Override
	public Type saveType(Type type) {
		return typeRepository.save(type);
	}
	
	@Transactional
	@Override
	public Type getType(Long id) {
		return typeRepository.findById(id).orElse(null);
	}
	
	@Override
	public Type getTypeByName(String name) {
		return typeRepository.findByName(name);
	}
	
	@Transactional
	@Override
	public Page<Type> ListType(Pageable pageable) {
		return typeRepository.findAll(pageable); 
	}
	
	@Transactional
	@Override
	public Type updateType(Long id, Type type) {
		Type t = typeRepository.findById(id).orElse(null);
		if(t == null) {
			throw new NotFoundException("不存在该类型");
		}
		BeanUtils.copyProperties(type, t);
		return typeRepository.save(t);
	}
	
	@Transactional
	@Override
	public void deleteType(Long id) {
		typeRepository.deleteById(id);
	}

	@Override
	public List<Type> listType() {
		return typeRepository.findAll();
	}

	@Override
	public List<Type> listTypeTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
//		Pageable pageable = new PageRequest(0, size, sort);		//过时
		Pageable pageable = PageRequest.of(0, size, sort);		//获取第一页即0
		return typeRepository.findTop(pageable);
	}
	
	

}
