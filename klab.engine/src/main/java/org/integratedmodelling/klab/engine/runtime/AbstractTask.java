package org.integratedmodelling.klab.engine.runtime;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.provenance.Activity;
import org.integratedmodelling.klab.utils.NameGenerator;

public abstract class AbstractTask<T extends IObservation> implements ITaskTree<T> {
	
	AbstractTask() {
		this.activity = new Activity(token);
	}
	
	AbstractTask(AbstractTask<T> parent) {
		this.context = parent.context;
		this.session = parent.session;
		this.scenarios = parent.scenarios;
		this.parentTask = parent;
		this.activity = new Activity(token);
	}
	
	Activity activity;
	Monitor monitor;
	Subject context;
	Session session;
	String token = "t" + NameGenerator.shortUUID();
	String[] scenarios;
	AbstractTask<T> parentTask = null;
	
	@Override
	public boolean isChildTask() {
		return parentTask != null;
	}
	
	public Activity getActivity() {
		return activity;
	}
}
