package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Utils;

/**
 * A builder that creates no objects but stores the scales and metadata it sees.
 * Used to intercept resource content in observers or other functions that use
 * resources as source.
 * 
 * @author ferdinando.villa
 *
 */
public class VisitingDataBuilder implements IKlabData.Builder {

	class Descriptor {

		public Descriptor(String artifactName, String objectName, IGeometry scale2, boolean isObject) {
			this.artifactName = artifactName;
			this.name = objectName;
			this.scale = scale2;
			this.isState = !isObject;
			if (isObject) {
				objects.add(this);
			} else {
				states.add(this);
			}
		}

		IMetadata metadata = new Metadata();
		IGeometry scale = null;
		String name;
		String artifactName;
		boolean isState;
		IDataStorage<?> storage = null;
		
	}

	List<Descriptor> states = new ArrayList<>();
	List<Descriptor> objects = new ArrayList<>();
	List<INotification> notifications = new ArrayList<>();
	Descriptor current;
	private int maxObjects = -1;
	private VisitingDataBuilder parent;
	private boolean keepStates;
	private IGeometry geometry;
	private IConcept semantics;

	public VisitingDataBuilder() {
	}

	public VisitingDataBuilder(int maxObjectsToVisit) {
		this.maxObjects = maxObjectsToVisit;
	}
	
	public VisitingDataBuilder(VisitingDataBuilder visitingDataBuilder, Descriptor descriptor) {
		this.maxObjects = visitingDataBuilder.maxObjects;
		this.objects = visitingDataBuilder.objects;
		this.states = visitingDataBuilder.states;
		this.parent = visitingDataBuilder;
		this.current = descriptor;
		this.geometry = visitingDataBuilder.geometry;
		this.keepStates = visitingDataBuilder.keepStates;
	}

	/**
	 * Set up to keep the data for states, which are otherwise discarded. We must
	 * pass the scale here, as the builder normally does not keep it.
	 * 
	 * @param geometry
	 * @return
	 */
	public VisitingDataBuilder keepStates(IGeometry geometry) {
		this.keepStates = true;
		this.geometry = geometry;
		return this;
	}

	@Override
	public Builder startState(String name) {
		return new VisitingDataBuilder(this, new Descriptor(name, null, this.geometry, false));
	}

	@Override
	public void add(Object value) {
	}

	@Override
	public Builder finishState() {
		return parent;
	}

	@Override
	public Builder startObject(String artifactName, String objectName, IGeometry scale) {
		return new VisitingDataBuilder(this, new Descriptor(artifactName, objectName, scale, true));
	}

	@Override
	public Builder finishObject() {
		return parent;
	}

	@Override
	public Builder withMetadata(String property, Object object) {
		current.metadata.put(property, object);
		return this;
	}

	@Override
	public Builder addNotification(INotification notification) {
		this.notifications.add(notification);
		return this;
	}

	@Override
	public VisitedData build() {
		return new VisitedData(this);
	}

	@Override
	public void set(Object value, ILocator offset) {
		if (keepStates && current != null) {
			if (current.storage == null && value != null) {
				current.storage = (IDataStorage<?>) Klab.INSTANCE.getStorageProvider().createStorage(
						Utils.getArtifactType(value.getClass()),
						current.scale instanceof IScale ? ((IScale) current.scale) : Scale.create(current.scale));
			}
			if (value != null) {
			    // FIXME we should have a peer for null and put it, creating the storage even if the value is null.
			    current.storage.putObject(value, offset);
			}
		}
	}
	
	@Override
	public Builder withSemantics(IConcept semantics) {
		this.semantics = semantics;
		return this;
	}

}
