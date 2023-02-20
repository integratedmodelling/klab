package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;

public abstract class AbstractKlabMessage implements KlabMessage {

	Map<Semaphore.Type, List<Semaphore>> semaphores = new HashMap<>();

	@Override
	public KlabMessage withSemaphore(Semaphore semaphore) {
		List<Semaphore> sem = semaphores.get(semaphore.getType());
		if (sem == null) {
			sem = new ArrayList<>();
			semaphores.put(semaphore.getType(), sem);
		}
		sem.add(semaphore);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Semaphore> getSemaphores(Semaphore.Type type) {
		return semaphores.containsKey(type) ? semaphores.get(type) : Collections.EMPTY_LIST;
	}

}
