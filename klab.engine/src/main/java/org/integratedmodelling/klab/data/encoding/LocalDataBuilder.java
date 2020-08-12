package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.scale.Scale;

/**
 * A {@link IKlabData} builder that organizes raw data into a local artifact.
 * Artifacts that are pre-built by the context are used directly. The build()
 * step moves the finished artifacts to the data object where they can be
 * retrieved.
 * 
 * @author ferdinando.villa
 *
 */
public class LocalDataBuilder implements IKlabData.Builder {

	IState state = null;
	IObservation observation = null;
	IRuntimeScope context = null;
	long offset = 0;
	List<INotification> notifications = new ArrayList<>();
	Metadata metadata = new Metadata();
	IObservable observable;
	String objectName;
	IGeometry scale;
	LocalDataBuilder parent;

	public LocalDataBuilder(IRuntimeScope context) {
		this.context = context;
		this.observable = context.getTargetSemantics();
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

	public LocalDataBuilder(IObservable observable, String objectName, IGeometry scale, LocalDataBuilder parent) {
		this.context = parent.context;
		this.parent = parent;
		this.notifications = parent.notifications;
		this.observable = observable;
		this.scale = scale;
		this.objectName = objectName;
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
	public void add(Object value) {
		if (state != null) {
			state.set(state.getGeometry().at(offset++), value);
		} else {
			throw new IllegalStateException("data builder: cannot add items: no state set");
		}
	}

	@Override
	public void add(Object value, ILocator locator) {
		if (state != null) {
			state.set(locator, value);
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
		} else if (!parent.state.equals(this.state)) {
			// FIXME this will currently complain
			((Artifact) parent.state).chain(this.state);
		}
		return parent;
	}

	@Override
	public Builder startObject(String artifactName, String objectName, IGeometry scale) {

//		IObservable observable = context.getSemantics(artifactName);
		if (observable == null) {
			throw new IllegalArgumentException(
					"data builder: cannot find semantics for the artifact named " + artifactName);
		}
		return new LocalDataBuilder(observable, objectName, scale, this);
	}

	@Override
	public Builder finishObject() {

		this.observation = (IDirectObservation) context.newObservation(observable, objectName,
				(scale instanceof Scale ? (Scale) scale : Scale.create((IGeometry) scale)), metadata);

		// for (String key : metadata.keySet()) {
		// this.observation.getMetadata().put(key, metadata.get(key));
		// }
		if (parent.observation == null) {
			parent.observation = this.observation;
		} else {
			if (!(parent.observation instanceof ObservationGroup)) {
				IObservation obs = parent.observation;
				parent.observation = new ObservationGroup((Observable) context.getTargetSemantics(),
						(Scale) context.getScale(), context, context.getTargetSemantics().getArtifactType());
				((ObservationGroup) parent.observation).chain(obs);
			}
			((Artifact) parent.observation).chain(this.observation);
		}

		return parent;
	}

	@Override
	public Builder withMetadata(String property, Object object) {
//		IArtifact artifact = this.state == null ? this.observation : this.state;
//		if (artifact == null) {
//			throw new IllegalStateException("data builder: cannot set property: no observation is set");
//		}
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
}
