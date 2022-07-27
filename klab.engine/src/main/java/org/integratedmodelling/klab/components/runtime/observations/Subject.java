package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.engine.runtime.ObserveInContextTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Subject extends CountableObservation implements ISubject {

    INamespace resolutionNamespace;

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

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ITask<IObservation> observe(IObservable observable) {
        return (ITask<IObservation>) (ITask) ObserveInContextTask.create(this, observable.getDeclaration(), false);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ITask<IObservation> observe(String resolvableUrn) {
        return (ITask<IObservation>) (ITask) ObserveInContextTask.create(this, resolvableUrn, false);
    }

    @Override
    public ITask<ISubject> observe(IObservable observable, IGeometry geometry) {
        // TODO Auto-generated method stub
        return null;
    }
}
