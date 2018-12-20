/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;

/**
 * Model metadata, describing enough about an existing model to rank it for
 * resolution. Provides a proxy for storage in a model kbox and the bean for
 * network transfer in remote resolutions. Each possible incarnation of the same
 * model (for example to resolve an inherent quality through dereification) is
 * stored and handled separately to simplify kbox query.
 * <p>
 * Because Jackson insists that serializing transient fields as a default is a
 * good thing, ensure that any object mapper used is configured with
 * 
 * <pre>
 * mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true)
 * </pre>
 * 
 * before use.
 * 
 * @author Ferd
 * @version $Id: $Id
 */
public class ModelReference {

	/**
	 * Mediation type necessary for the model represented to observe the given
	 * observable.
	 * 
	 * @author Ferd
	 *
	 */
	public static enum Mediation {

		/**
		 * Directly observes the stated observable
		 */
		NONE,
		/**
		 * Must contextualize direct observables and extract a quality (or presence)
		 */
		DEREIFY_QUALITY,
		/**
		 * Must extract observed trait from classified type
		 */
		EXTRACT_TRAIT,
		/**
		 * Infers the presence of the inherent observable from the value of its observed
		 * quality
		 */
		INFER_INHERENT_PRESENCE,

	}

	private String id;
	private String name;
//	private String urn;

	private String serverId;
	private String projectUrn;
	private String projectId;
	private boolean privateModel;
	private String namespaceId;
	private boolean inScenario;
	private boolean reification;
	private boolean hasDirectData;
	// private boolean computed;
	private boolean hasDirectObjects;
	private boolean spatial;
	private boolean temporal;
	private boolean resolved;
	private boolean primaryObservable;
	private long timeMultiplicity;
	private long spaceMultiplicity;
	private long scaleMultiplicity;
	private String dereifyingAttribute;
	private String observable;
	private String observationType;
	private Map<String, String> metadata;
	private Mediation mediation = Mediation.NONE;
	private long timeStart = -1;
	private long timeEnd = -1;
	private Set<String> neededCapabilities = new HashSet<>();
	private Map<String, Double> ranks;
	private boolean abstractObservable;
	private int minSpatialScaleFactor = ISpace.MIN_SCALE_RANK;
	private int maxSpatialScaleFactor = ISpace.MAX_SCALE_RANK;
	private int minTimeScaleFactor = ITime.MIN_SCALE_RANK;
	private int maxTimeScaleFactor = ITime.MAX_SCALE_RANK;
	private int priority = 0;

	transient private IConcept observableConcept;
	transient private IShape shape;

	/**
	 * Copy.
	 *
	 * @return the model reference
	 */
	/*
	 * Lombock should really have something with less side-effects than
	 *
	 * @builder for this.
	 */
	public ModelReference copy() {
		ModelReference ret = new ModelReference();
		ret.id = id;
		ret.name = name;
		ret.serverId = serverId;
		ret.projectId = projectId;
		ret.projectUrn = projectUrn;
		ret.privateModel = privateModel;
		ret.namespaceId = namespaceId;
		ret.inScenario = inScenario;
		ret.reification = reification;
		ret.hasDirectData = hasDirectData;
		ret.hasDirectObjects = hasDirectObjects;
		ret.spatial = spatial;
		ret.temporal = temporal;
		ret.resolved = resolved;
		ret.primaryObservable = primaryObservable;
		ret.timeMultiplicity = timeMultiplicity;
		ret.spaceMultiplicity = spaceMultiplicity;
		ret.scaleMultiplicity = scaleMultiplicity;
		ret.dereifyingAttribute = dereifyingAttribute;
		ret.observable = observable;
		ret.observationType = observationType;
		ret.metadata = metadata == null ? null : new HashMap<>(metadata);
		ret.mediation = mediation;
		ret.timeStart = timeStart;
		ret.timeEnd = timeEnd;
		ret.neededCapabilities = neededCapabilities == null ? null : new HashSet<>(neededCapabilities);
		ret.ranks = ranks == null ? null : new HashMap<>(ranks);
		ret.minSpatialScaleFactor = minSpatialScaleFactor;
		ret.maxSpatialScaleFactor = maxSpatialScaleFactor;
		ret.minTimeScaleFactor = minTimeScaleFactor;
		ret.maxTimeScaleFactor = maxTimeScaleFactor;
		ret.observableConcept = observableConcept;
		ret.shape = shape;

		return ret;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ModelReference) {
			return (name == null && ((ModelReference) o).getName() == null) || (name != null
					&& ((ModelReference) o).getName() != null && name.equals(((ModelReference) o).getName()));
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the server id.
	 *
	 * @return the server id
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * Sets the server id.
	 *
	 * @param serverId
	 *            the new server id
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * Gets the project urn.
	 *
	 * @return the project urn
	 */
	public String getProjectUrn() {
		return projectUrn;
	}

	/**
	 * Sets the project urn.
	 *
	 * @param projectUrn
	 *            the new project urn
	 */
	public void setProjectUrn(String projectUrn) {
		this.projectUrn = projectUrn;
	}

	/**
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId
	 *            the new project id
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * Checks if is private model.
	 *
	 * @return a boolean.
	 */
	public boolean isPrivateModel() {
		return privateModel;
	}

	/**
	 * Sets the private model.
	 *
	 * @param privateModel
	 *            the new private model
	 */
	public void setPrivateModel(boolean privateModel) {
		this.privateModel = privateModel;
	}

	/**
	 * Gets the namespace id.
	 *
	 * @return the namespace id
	 */
	public String getNamespaceId() {
		return namespaceId;
	}

	/**
	 * Sets the namespace id.
	 *
	 * @param namespaceId
	 *            the new namespace id
	 */
	public void setNamespaceId(String namespaceId) {
//		if (namespaceId.startsWith("file:")) {
//			System.out.println("HOSTIA");
//		}
		this.namespaceId = namespaceId;
	}

	/**
	 * Checks if is in scenario.
	 *
	 * @return a boolean.
	 */
	public boolean isInScenario() {
		return inScenario;
	}

	/**
	 * Sets the in scenario.
	 *
	 * @param inScenario
	 *            the new in scenario
	 */
	public void setInScenario(boolean inScenario) {
		this.inScenario = inScenario;
	}

	/**
	 * Checks if is reification.
	 *
	 * @return a boolean.
	 */
	public boolean isReification() {
		return reification;
	}

	/**
	 * Sets the reification.
	 *
	 * @param reification
	 *            the new reification
	 */
	public void setReification(boolean reification) {
		this.reification = reification;
	}

	/**
	 * Checks if is checks for direct data.
	 *
	 * @return a boolean.
	 */
	public boolean isHasDirectData() {
		return hasDirectData;
	}

	/**
	 * Sets the checks for direct data.
	 *
	 * @param hasDirectData
	 *            the new checks for direct data
	 */
	public void setHasDirectData(boolean hasDirectData) {
		this.hasDirectData = hasDirectData;
	}

	/**
	 * Checks if is checks for direct objects.
	 *
	 * @return a boolean.
	 */
	public boolean isHasDirectObjects() {
		return hasDirectObjects;
	}

	/**
	 * Sets the checks for direct objects.
	 *
	 * @param hasDirectObjects
	 *            the new checks for direct objects
	 */
	public void setHasDirectObjects(boolean hasDirectObjects) {
		this.hasDirectObjects = hasDirectObjects;
	}

	/**
	 * Checks if is spatial.
	 *
	 * @return a boolean.
	 */
	public boolean isSpatial() {
		return spatial;
	}

	/**
	 * Sets the spatial.
	 *
	 * @param spatial
	 *            the new spatial
	 */
	public void setSpatial(boolean spatial) {
		this.spatial = spatial;
	}

	/**
	 * Checks if is temporal.
	 *
	 * @return a boolean.
	 */
	public boolean isTemporal() {
		return temporal;
	}

	/**
	 * Sets the temporal.
	 *
	 * @param temporal
	 *            the new temporal
	 */
	public void setTemporal(boolean temporal) {
		this.temporal = temporal;
	}

	/**
	 * Checks if is resolved.
	 *
	 * @return a boolean.
	 */
	public boolean isResolved() {
		return resolved;
	}

	/**
	 * Sets the resolved.
	 *
	 * @param resolved
	 *            the new resolved
	 */
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	/**
	 * Checks if is primary observable.
	 *
	 * @return a boolean.
	 */
	public boolean isPrimaryObservable() {
		return primaryObservable;
	}

	/**
	 * Sets the primary observable.
	 *
	 * @param primaryObservable
	 *            the new primary observable
	 */
	public void setPrimaryObservable(boolean primaryObservable) {
		this.primaryObservable = primaryObservable;
	}

	/**
	 * Gets the time multiplicity.
	 *
	 * @return the time multiplicity
	 */
	public long getTimeMultiplicity() {
		return timeMultiplicity;
	}

	/**
	 * Sets the time multiplicity.
	 *
	 * @param timeMultiplicity
	 *            the new time multiplicity
	 */
	public void setTimeMultiplicity(long timeMultiplicity) {
		this.timeMultiplicity = timeMultiplicity;
	}

	/**
	 * Gets the space multiplicity.
	 *
	 * @return the space multiplicity
	 */
	public long getSpaceMultiplicity() {
		return spaceMultiplicity;
	}

	/**
	 * Sets the space multiplicity.
	 *
	 * @param spaceMultiplicity
	 *            the new space multiplicity
	 */
	public void setSpaceMultiplicity(long spaceMultiplicity) {
		this.spaceMultiplicity = spaceMultiplicity;
	}

	/**
	 * Gets the scale multiplicity.
	 *
	 * @return the scale multiplicity
	 */
	public long getScaleMultiplicity() {
		return scaleMultiplicity;
	}

	/**
	 * Sets the scale multiplicity.
	 *
	 * @param scaleMultiplicity
	 *            the new scale multiplicity
	 */
	public void setScaleMultiplicity(long scaleMultiplicity) {
		this.scaleMultiplicity = scaleMultiplicity;
	}

	/**
	 * Gets the dereifying attribute.
	 *
	 * @return the dereifying attribute
	 */
	public String getDereifyingAttribute() {
		return dereifyingAttribute;
	}

	/**
	 * Sets the dereifying attribute.
	 *
	 * @param dereifyingAttribute
	 *            the new dereifying attribute
	 */
	public void setDereifyingAttribute(String dereifyingAttribute) {
		this.dereifyingAttribute = dereifyingAttribute;
	}

	/**
	 * Gets the observable.
	 *
	 * @return the observable
	 */
	public String getObservable() {
		return observable;
	}

	/**
	 * Sets the observable.
	 *
	 * @param observable
	 *            the new observable
	 */
	public void setObservable(String observable) {
		this.observable = observable;
	}

	/**
	 * Gets the observation type.
	 *
	 * @return the observation type
	 */
	public String getObservationType() {
		return observationType;
	}

	/**
	 * Sets the observation type.
	 *
	 * @param observationType
	 *            the new observation type
	 */
	public void setObservationType(String observationType) {
		this.observationType = observationType;
	}

	/**
	 * Gets the metadata.
	 *
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * Sets the metadata.
	 *
	 * @param metadata
	 *            the new metadata
	 */
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	/**
	 * Gets the mediation.
	 *
	 * @return the mediation
	 */
	public Mediation getMediation() {
		return mediation;
	}

	/**
	 * Sets the mediation.
	 *
	 * @param mediation
	 *            the new mediation
	 */
	public void setMediation(Mediation mediation) {
		this.mediation = mediation;
	}

	/**
	 * Gets the time start.
	 *
	 * @return the time start
	 */
	public long getTimeStart() {
		return timeStart;
	}

	/**
	 * Sets the time start.
	 *
	 * @param timeStart
	 *            the new time start
	 */
	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * Gets the time end.
	 *
	 * @return the time end
	 */
	public long getTimeEnd() {
		return timeEnd;
	}

	/**
	 * Sets the time end.
	 *
	 * @param timeEnd
	 *            the new time end
	 */
	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}

	/**
	 * Gets the needed capabilities.
	 *
	 * @return the needed capabilities
	 */
	public Set<String> getNeededCapabilities() {
		return neededCapabilities;
	}

	/**
	 * Sets the needed capabilities.
	 *
	 * @param neededCapabilities
	 *            the new needed capabilities
	 */
	public void setNeededCapabilities(Set<String> neededCapabilities) {
		this.neededCapabilities = neededCapabilities;
	}

	/**
	 * Gets the ranks.
	 *
	 * @return the ranks
	 */
	public Map<String, Double> getRanks() {
		return ranks;
	}

	/**
	 * Sets the ranks.
	 *
	 * @param ranks
	 *            the ranks
	 */
	public void setRanks(Map<String, Double> ranks) {
		this.ranks = ranks;
	}

	/**
	 * Checks if is abstract observable.
	 *
	 * @return a boolean.
	 */
	public boolean isAbstractObservable() {
		return abstractObservable;
	}

	/**
	 * Sets the abstract observable.
	 *
	 * @param abstractObservable
	 *            the new abstract observable
	 */
	public void setAbstractObservable(boolean abstractObservable) {
		this.abstractObservable = abstractObservable;
	}

	/**
	 * Gets the min spatial scale factor.
	 *
	 * @return the min spatial scale factor
	 */
	public int getMinSpatialScaleFactor() {
		return minSpatialScaleFactor;
	}

	/**
	 * Sets the min spatial scale factor.
	 *
	 * @param minSpatialScaleFactor
	 *            the new min spatial scale factor
	 */
	public void setMinSpatialScaleFactor(int minSpatialScaleFactor) {
		this.minSpatialScaleFactor = minSpatialScaleFactor;
	}

	/**
	 * Gets the max spatial scale factor.
	 *
	 * @return the max spatial scale factor
	 */
	public int getMaxSpatialScaleFactor() {
		return maxSpatialScaleFactor;
	}

	/**
	 * Sets the max spatial scale factor.
	 *
	 * @param maxSpatialScaleFactor
	 *            the new max spatial scale factor
	 */
	public void setMaxSpatialScaleFactor(int maxSpatialScaleFactor) {
		this.maxSpatialScaleFactor = maxSpatialScaleFactor;
	}

	/**
	 * Gets the min time scale factor.
	 *
	 * @return the min time scale factor
	 */
	public int getMinTimeScaleFactor() {
		return minTimeScaleFactor;
	}

	/**
	 * Sets the min time scale factor.
	 *
	 * @param minTimeScaleFactor
	 *            the new min time scale factor
	 */
	public void setMinTimeScaleFactor(int minTimeScaleFactor) {
		this.minTimeScaleFactor = minTimeScaleFactor;
	}

	/**
	 * Gets the max time scale factor.
	 *
	 * @return the max time scale factor
	 */
	public int getMaxTimeScaleFactor() {
		return maxTimeScaleFactor;
	}

	/**
	 * Sets the max time scale factor.
	 *
	 * @param maxTimeScaleFactor
	 *            the new max time scale factor
	 */
	public void setMaxTimeScaleFactor(int maxTimeScaleFactor) {
		this.maxTimeScaleFactor = maxTimeScaleFactor;
	}

	/**
	 * Gets the observable concept.
	 *
	 * @return the observable concept
	 */
	public IConcept getObservableConcept() {
		return observableConcept;
	}

	/**
	 * Sets the observable concept.
	 *
	 * @param observableConcept
	 *            the new observable concept
	 */
	public void setObservableConcept(IConcept observableConcept) {
		this.observableConcept = observableConcept;
	}

	/**
	 * Gets the shape.
	 *
	 * @return the shape
	 */
	public IShape getShape() {
		return shape;
	}

	/**
	 * Sets the shape.
	 *
	 * @param shape
	 *            the new shape
	 */
	public void setShape(IShape shape) {
		this.shape = shape;
	}

	/**
	 * Gets the urn.
	 *
	 * @return the urn
	 */
	public String getUrn() {
		// FIXME make this the URN
		return name;
		// return urn;
	}

//	/**
//	 * Sets the urn.
//	 *
//	 * @param urn
//	 *            the new urn
//	 */
//	public void setUrn(String urn) {
//		this.urn = urn;
//	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
