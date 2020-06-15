package org.integratedmodelling.klab.hub.service;

import java.util.Collection;

import org.integratedmodelling.klab.hub.api.GenericModel;

public interface GenericHubService<M extends GenericModel> {
	abstract M create(M model);
	abstract M update(M model);
	abstract M delete(M model);
	abstract Collection<M> getAll();
	abstract M getByName(String name);
	abstract M getById(String id);
	abstract boolean exists(String name);
}
