package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.data.Metadata;

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

		public Descriptor(String artifactName, String objectName, IScale scale2, boolean isObject) {
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
		IScale scale = null;
		String name;
		String artifactName;
		boolean isState;
	}

	List<Descriptor> states = new ArrayList<>();
	List<Descriptor> objects = new ArrayList<>();
	Descriptor current;
	private int maxObjects = -1;
	private VisitingDataBuilder parent;

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
	}

	/**
	 * Number of objects visited.
	 * 
	 * @return
	 */
	public int getObjectCount() {
		return objects.size();
	}

	public IScale getObjectScale(int n) {
		return objects.get(n).scale;
	}

	public IMetadata getObjectMetadata(int n) {
		return objects.get(n).metadata;
	}

	public String getObjectName(int n) {
		return objects.get(n).name;
	}

	public int getStateCount() {
		return states.size();
	}

	public IMetadata getStateMetadata(int n) {
		return states.get(n).metadata;
	}

	@Override
	public Builder startState(String name) {
		return new VisitingDataBuilder(this, new Descriptor(name, null, null, false));
	}

	@Override
	public void add(Object value) {
	}

	@Override
	public Builder finishState() {
		return parent;
	}

	@Override
	public Builder startObject(String artifactName, String objectName, IScale scale) {
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
		return this;
	}

	@Override
	public IKlabData build() {
		return null;
	}

}
