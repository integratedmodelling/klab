package org.integratedmodelling.klab.stats.repositories;

import java.util.List;

public interface KlabRestEntityRepo<M> {
	List<M> findAll();
	M findById();
}
