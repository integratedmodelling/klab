//package org.integratedmodelling.klab.stats.repositories;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//public class KlabRestEntityRepoImpl<M> implements KlabRestEntityRepo<M>{
//	
//	@Autowired
//	MongoTemplate template;
//	
//	private M m;
//
//	@Override
//	public List<M> findAll() {
//		return (List<M>) template.findAll(m.getClass());
//	}
//
//	@Override
//	public M findById() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
