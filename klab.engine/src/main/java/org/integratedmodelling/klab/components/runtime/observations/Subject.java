package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IIndividual;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.ObserveInContextTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CollectionUtils;

public class Subject extends CountableObservation implements ISubject {

	public Subject(String name, Observable observable, Scale scale, IRuntimeScope context) {
		super(name, observable, scale, context);
	}

	protected Subject(Subject other) {
		super(other);
	}

	@Override
	public Collection<IEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IProcess> getProcesses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ISubject> getSubjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRelationship> getRelationships() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRelationship> getIncomingRelationships(ISubject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRelationship> getOutgoingRelationships(ISubject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<IConcept, IConfiguration> getConfigurations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIndividual instantiate(IOntology ontology) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITask<IArtifact> observe(String urn, String... scenarios) {
		return new ObserveInContextTask(this, urn, CollectionUtils.arrayToList(scenarios));
	}

	/**
	 * Listener consumers are called as things progress. The observation listener is
	 * first called with null as a parameter when starting, then (if no error
	 * occurs) another time with the observation as argument. The observation may be
	 * empty. If an exception is thrown, the error listener is called with the
	 * exception as argument.
	 * 
	 * @param urn
	 * @param scenarios
	 * @param observationListener
	 * @param errorListener
	 * @return
	 */
	public ITask<IArtifact> observe(String urn, Collection<String> scenarios,
			Consumer<IArtifact> observationListener, Consumer<Throwable> errorListener) {
		return new ObserveInContextTask(this, urn, scenarios, observationListener, errorListener,
				getParentIdentity(Engine.class).getTaskExecutor());
	}

}
