package org.integratedmodelling.klab.hub.licenses.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.licenses.dto.MongoLever;
import org.integratedmodelling.klab.hub.repository.MongoLeverRepository;
import org.integratedmodelling.klab.hub.services.GenericHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeverService implements GenericHubService<MongoLever>{
	
	MongoLeverRepository repository;
	
	@Autowired
	public LeverService(MongoLeverRepository repository) {
		super();
		this.repository = repository;
	}	

	@Override
	public MongoLever create(MongoLever model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MongoLever update(MongoLever model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String name) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public Collection<MongoLever> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MongoLever getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MongoLever getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
