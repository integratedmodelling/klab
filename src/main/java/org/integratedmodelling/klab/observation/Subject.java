package org.integratedmodelling.klab.observation;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IIndividual;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.owl.Observable;

public class Subject extends DirectObservation implements ISubject {

    private Subject(String name, Observable observable, Scale scale) {
        super(name, observable, scale);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 2466999232658613114L;

    public static Subject create(String name, IObservable observable, IScale scale, @Nullable ISubject context) {
        return null;
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

}
