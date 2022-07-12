package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.SpatialExtent;

import com.google.common.collect.Lists;

public class Acknowledgement extends KimObject implements IAcknowledgement {

	private Observable observable;
	private String name;
	private Namespace namespace;
	private Contextualization behavior;
	private List<IObservable> states = new ArrayList<>();
	private String urn = null;

	public Acknowledgement(IKimAcknowledgement statement, Namespace namespace, Monitor monitor) {

		super(statement);
		this.observable = Observables.INSTANCE.declare(statement.getObservable(), monitor);
		/*
		 * resolving the observable for an acknowledged observation is always optional.
		 */
		this.observable.setOptional(true);
		this.namespace = namespace;
		this.name = statement.getName();
		this.behavior = new Contextualization(statement.getBehavior(), this);
		this.urn = statement.getUrn();
		if (statement.isErrors()) {
			this.addError("Syntax errors in k.IM observer specifications");
		}
		for (IKimObservable state : statement.getStates()) {
			this.states.add(Observables.INSTANCE.declare(state, monitor));
		}
	}

	public Acknowledgement(SpatialExtent regionOfInterest, ITime time, Observable observable, Namespace namespace) {
		super(null);

		this.namespace = namespace;
		this.observable = observable;
		this.name = "Region of interest";
		this.behavior = new Contextualization(null, this) {

			@Override
			public Collection<IExtent> getExtents(IMonitor monitor) throws KlabException {

				/*
				 * if the observer is URN-based, resolve the URN here and get the scale and the
				 * metadata from the resulting object.
				 */
				if (urn != null) {
					VisitingDataBuilder builder = new VisitingDataBuilder(1);
					IKlabData data = Resources.INSTANCE.getResourceData(urn, builder, monitor);
					return data.getObjectCount() > 0 ? data.getObjectScale(0).getExtents() : new ArrayList<>();
				}
				Envelope envelope = Envelope.create(regionOfInterest.getEast(), regionOfInterest.getWest(),
						regionOfInterest.getSouth(), regionOfInterest.getNorth(), Projection.getLatLon());

				double resolution = regionOfInterest.getGridUnit() == null
						? (double) envelope.getResolutionForZoomLevel().getFirst()
						: regionOfInterest.getGridResolution();

				ISpace space = Space.create(Shape.create(envelope), resolution);
//				ITime time = Time.INSTANCE.getGenericCurrentExtent(Resolution.Type.YEAR);

				return /* Collections.singletonList(space); */ Lists.newArrayList(time, space);
			}
		};
	}

	public Acknowledgement(String name, IScale scale, Observable observable, Namespace namespace) {

		super(null);

		this.namespace = namespace;
		this.observable = observable;
		this.name = name;
		this.behavior = new Contextualization(null, this) {

			@Override
			public Collection<IExtent> getExtents(IMonitor monitor) throws KlabException {
				if (urn != null) {
					VisitingDataBuilder builder = new VisitingDataBuilder(1);
					IKlabData data = Resources.INSTANCE.getResourceData(urn, builder, monitor);
					return data.getObjectCount() > 0 ? data.getObjectScale(0).getExtents() : new ArrayList<>();
				}
				return scale.getExtents();
			}
		};
	}

	public Acknowledgement(Shape shape, ITime time, Observable observable, Namespace namespace) {

		super(null);

		this.namespace = namespace;
		this.observable = observable;
		this.name = "Region of interest";
		this.behavior = new Contextualization(null, this) {

			@Override
			public Collection<IExtent> getExtents(IMonitor monitor) throws KlabException {

				/*
				 * if the observer is URN-based, resolve the URN here and get the scale and the
				 * metadata from the resulting object.
				 */
				if (urn != null) {
					VisitingDataBuilder builder = new VisitingDataBuilder(1);
					IKlabData data = Resources.INSTANCE.getResourceData(urn, builder, monitor);
					return data.getObjectCount() > 0 ? data.getObjectScale(0).getExtents() : new ArrayList<>();
				}

				double resolution = shape.getEnvelope().getResolutionForZoomLevel().getFirst();

//				ITime time = Time.INSTANCE.getGenericCurrentExtent(Resolution.Type.YEAR);
				ISpace space = Space.create(shape, resolution);

				return /* Collections.singletonList(space); */ Lists.newArrayList(time, space);
			}
		};
	}

	public String toString() {
		return "[" + getName() + "]";
	}

	@Override
	public String getId() {
		return name;
	}

	@Override
	public String getName() {
		return namespace.getId() + "." + getId();
	}

	@Override
	public Namespace getNamespace() {
		return namespace;
	}

	@Override
	public Contextualization getContextualization() {
		return behavior;
	}

	@Override
	public Observable getObservable() {
		return observable;
	}

	@Override
	public List<IObservable> getStates() {
		return states;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Acknowledgement && ((Acknowledgement) obj).getName().equals(getName());
	}

	@Override
	public List<IContextualizable> getComputation() {
		List<IContextualizable> ret = new ArrayList<>();
		for (Trigger trigger : Trigger.values()) {
			for (IAction action : behavior.getActions(trigger)) {
				ret.addAll(action.getComputation());
			}
		}
		return ret;
	}

}
