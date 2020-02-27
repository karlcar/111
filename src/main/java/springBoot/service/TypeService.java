package springBoot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import springBoot.po.Type;

public interface TypeService {
	
	Type saveType(Type type);
	
	Type getType(Long id);
	
	Page<Type> ListType(Pageable pageable);
	
	Type updateType(Long id, Type type);
	
	void deleteType(Long id);
	
	
}
