package org.integratedmodelling.klab.hub.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ResourceRepository<T, I extends Serializable> extends MongoRepository<T, I> {

    Page<T> findAll(Query query, Pageable pageable);

    List<T> findAll(Query query);
}