package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
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

public class Observer extends KimObject implements IObserver {

	private Observable observable;
	private String name;
	private Namespace namespace;
	private Contextualization behavior;
	private List<IObservable> states = new ArrayList<>();
	private String urn = null;

	public Observer(IKimObserver statement, Namespace namespace, Monitor monitor) {

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
		this.setErrors(statement.isErrors());
	}

	public Observer(SpatialExtent regionOfInterest, ITime time, Observable observable, Namespace namespace) {
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
					Resources.INSTANCE.getResourceData(urn, builder, monitor);
					return builder.getObjectCount() > 0 ? builder.getObjectScale(0).getExtents() : new ArrayList<>();
				}
				Envelope envelope = Envelope.create(regionOfInterest.getEast(), regionOfInterest.getWest(),
						regionOfInterest.getSouth(), regionOfInterest.getNorth(), Projection.getLatLon());

				double resolution = regionOfInterest.getGridUnit() == null
						? (double) envelope.getResolutionForZoomLevel().getFirst()
						: regionOfInterest.getGridResolution();

				ISpace space =  Space.create(Shape.create(envelope), resolution);
//				ITime time = Time.INSTANCE.getGenericCurrentExtent(Resolution.Type.YEAR);
				
				return /*Collections.singletonList(space); */ Lists.newArrayList(time, space);
			}
		};
	}

	public Observer(Shape shape, ITime time, Observable observable, Namespace namespace) {
		
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
					Resources.INSTANCE.getResourceData(urn, builder, monitor);
					return builder.getObjectCount() > 0 ? builder.getObjectScale(0).getExtents() : new ArrayList<>();
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
		return obj instanceof Observer && ((Observer) obj).getName().equals(getName());
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
