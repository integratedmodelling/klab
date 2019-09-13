package org.integratedmodelling.klab.hub.repository;

import java.util.List;

import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlabGroupRepository extends CrudRepository<KlabGroup, Long>{
	public List<KlabGroup> findAll();
	
	public KlabGroup findById(String string);
}
