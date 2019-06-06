package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public abstract class DirectObservation extends Observation implements IDirectObservation {

    String name;
	
	// contains the IDs of any subjective observations that we have made.
	private Set<String> subjectivelyObserved = new HashSet<>();

    protected DirectObservation(String name, Observable observable, Scale scale, IRuntimeContext context) {
        super(observable, scale, context);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Collection<IState> getStates() {
      return getRuntimeContext().getChildren(this, IState.class);
    }

    @Override
    public <T extends IArtifact> Collection<T> getChildren(Class<T> cls) {
        return getRuntimeContext().getChildren(this, cls);
    }
    
    @Override
    public IArtifact.Type getType() {
    	return IArtifact.Type.OBJECT;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public IDirectObservation at(ILocator locator) {
//    	Logging.INSTANCE.warn("RELOCATION OF DIRECT OBSERVATION IS UNIMPLEMENTED!");
    	return this;
    }

	public void addSubjectiveObservation(ISubjectiveObservation subjectiveObservation) {
		subjectivelyObserved.add(subjectiveObservation.getId());
	}
	
	/**
	 * The set of IDs of any subjective observation that contain this as an observer.
	 * 
	 * @return
	 */
	public Collection<String> getSubjectiveObservationIds() {
		return subjectivelyObserved;
	}

	@Override
	public Collection<IArtifact> getChildArtifacts() {
		return getRuntimeContext().getChildArtifactsOf(this);
	}
	
	
	
    
}
