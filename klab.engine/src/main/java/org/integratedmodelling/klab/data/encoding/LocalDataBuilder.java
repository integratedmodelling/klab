package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.provenance.Artifact;

/**
 * A builder that encodes the data into an existing (or creates a new) local
 * artifact. The build() step merely moves the finished artifact to the data
 * object for retrieval, which is even unnecessary if the target artifact is
 * pre-built by the runtime.
 * 
 * @author ferdinando.villa
 *
 */
public class LocalDataBuilder implements IKlabData.Builder {

	IState state = null;
	IDirectObservation observation = null;
	IRuntimeContext context = null;
	long offset = 0;
	List<INotification> notifications = new ArrayList<>();
	Metadata metadata = new Metadata();
	LocalDataBuilder parent;
	
	public LocalDataBuilder(IRuntimeContext context) {
		this.context = context;
		if (context.getTargetArtifact() instanceof IState) {
			this.state = (IState) context.getTargetArtifact();
		}
	}

	private LocalDataBuilder(IState state, LocalDataBuilder parent) {
		this.context = parent.context;
		this.parent = parent;
		this.notifications = parent.notifications;
		this.observation = parent.observation;
		this.state = state;
	}
	
	private LocalDataBuilder(IDirectObservation obs, LocalDataBuilder parent) {
		this.context = parent.context;
		this.parent = parent;
		this.notifications = parent.notifications;
		this.observation = obs;
	}

	@Override
	public Builder startState(String name) {
		IState s = null;
		if (context.getArtifact(name) instanceof IState) {
			s = (IState) context.getArtifact(name);
		} else {
			throw new IllegalArgumentException("cannot set the builder context to " + name + ": state does not exist");
		}
		return new LocalDataBuilder(s, this);
	}

	@Override
	public void add(double doubleValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), doubleValue);
		} else {
			throw new IllegalStateException("data builder: cannot add items: no state set");
		}
	}

	@Override
	public void add(float floatValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), floatValue);
		} else {
			throw new IllegalStateException("data builder: cannot add items: no state set");
		}
	}

	@Override
	public void add(int intValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), intValue);
		} else {
			throw new IllegalStateException("data builder: cannot add items: no state set");
		}
	}

	@Override
	public void add(long longValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), longValue);
		} else {
			throw new IllegalStateException("data builder: cannot add items: no state set");
		}
	}

	@Override
	public Builder finishState() {
		if (observation != null) {
			// TODO add state to observation
			throw new KlabUnsupportedFeatureException("ADD STATE TO OBJECT!");
		}
		for (String key : metadata.keySet()) {
			this.state.getMetadata().put(key, metadata.get(key));
		}
		if (parent.state == null) {
			parent.state = this.state;
		} else {
			((Artifact)parent.state).chain(this.state);
		}
		return parent;
	}

	@Override
	public Builder startObject(String artifactName, String objectName, IScale scale) {
		// TODO Auto-generated method stub
		IObservable observable = ((IRuntimeContext)context).getSemantics(artifactName);
		if (observable == null) {
			throw new IllegalArgumentException("data builder: cannot find semantics for the artifact named " + artifactName);
		}
		return new LocalDataBuilder((IDirectObservation) context.newObservation(observable, objectName, scale), this);
	}

	@Override
	public Builder finishObject() {

		for (String key : metadata.keySet()) {
			this.observation.getMetadata().put(key, metadata.get(key));
		}
		if (parent.observation == null) {
			parent.observation = this.observation;
		} else {
			((Artifact)parent.observation).chain(this.observation);
		}

		return parent;
	}

	@Override
	public Builder setProperty(String property, Object object) {
		IArtifact artifact = this.state == null ? this.observation : this.state;
		if (artifact == null) {
			throw new IllegalStateException("data builder: cannot set property: no observation is set");
		}
		metadata.put(property, object);
		return this;
	}

	@Override
	public Builder addNotification(INotification notification) {
		notifications.add(notification);
		return null;
	}

	@Override
	public IKlabData build() {
		return new LocalData(this);
	}

	@Override
	public void add(boolean booleanValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), booleanValue);
		} else {
			throw new IllegalStateException("data builder: cannot add items: no state set");
		}
	}

	@Override
	public void add(IConcept conceptValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), conceptValue);
		} else {
			throw new IllegalStateException("data builder: cannot add items: no state set");
		}
	}

}
