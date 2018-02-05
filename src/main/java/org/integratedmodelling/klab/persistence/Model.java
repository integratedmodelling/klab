package org.integratedmodelling.klab.persistence;

/*******************************************************************************
 * Copyright (C) 2007, 2016:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.Metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Boilerplate model metadata - the bean that gets stored in the model kbox.
 * @author Ferd
 *
 */
public class Model {

    /**
     * Mediation type necessary for the model represented to observe the given observable.
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

    private String              id;
    private String              name;
    private String              serverId;
    private String              projectUrn;
    private String              projectId;
    private boolean             privateModel;
    private String              namespaceId;
    private boolean             inScenario;
    private boolean             reification;
    private boolean             hasDirectData;
    // private boolean computed;
    private boolean             hasDirectObjects;
    private boolean             spatial;
    private boolean             temporal;
    private boolean             resolved;
    private boolean             primaryObservable;
    private long                timeMultiplicity;
    private long                spaceMultiplicity;
    private long                scaleMultiplicity;
    private String              dereifyingAttribute;
    private String              observable;
    private String              observationType;
    private Metadata            metadata;
    private Mediation           mediation             = Mediation.NONE;
    private long                timeStart             = -1;
    private long                timeEnd               = -1;
    private Set<String>         neededCapabilities    = new HashSet<>();
    private Map<String, Object> ranks;
    private boolean             abstractObservable;
    private int                 minSpatialScaleFactor = ISpace.MIN_SCALE_RANK;
    private int                 maxSpatialScaleFactor = ISpace.MAX_SCALE_RANK;
    private int                 minTimeScaleFactor    = ITime.MIN_SCALE_RANK;
    private int                 maxTimeScaleFactor    = ITime.MAX_SCALE_RANK;

    @JsonIgnore
    transient private IConcept  observableConcept;
    @JsonIgnore
    transient Shape   shape;

    /*
     * Lombock should really have something with less side-effects than
     * 
     * @builder for this.
     */
    public Model copy() {
        Model ret = new Model();
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
        ret.metadata = metadata == null ? null : metadata.copy();
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Model) {
            return (name == null && ((Model) o).getName() == null) ||
                    (name != null &&
                            ((Model) o).getName() != null &&
                            name.equals(((Model) o).getName()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getProjectUrn() {
        return projectUrn;
    }

    public void setProjectUrn(String projectUrn) {
        this.projectUrn = projectUrn;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public boolean isPrivateModel() {
        return privateModel;
    }

    public void setPrivateModel(boolean privateModel) {
        this.privateModel = privateModel;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public boolean isInScenario() {
        return inScenario;
    }

    public void setInScenario(boolean inScenario) {
        this.inScenario = inScenario;
    }

    public boolean isReification() {
        return reification;
    }

    public void setReification(boolean reification) {
        this.reification = reification;
    }

    public boolean isHasDirectData() {
        return hasDirectData;
    }

    public void setHasDirectData(boolean hasDirectData) {
        this.hasDirectData = hasDirectData;
    }

    public boolean isHasDirectObjects() {
        return hasDirectObjects;
    }

    public void setHasDirectObjects(boolean hasDirectObjects) {
        this.hasDirectObjects = hasDirectObjects;
    }

    public boolean isSpatial() {
        return spatial;
    }

    public void setSpatial(boolean spatial) {
        this.spatial = spatial;
    }

    public boolean isTemporal() {
        return temporal;
    }

    public void setTemporal(boolean temporal) {
        this.temporal = temporal;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public boolean isPrimaryObservable() {
        return primaryObservable;
    }

    public void setPrimaryObservable(boolean primaryObservable) {
        this.primaryObservable = primaryObservable;
    }

    public long getTimeMultiplicity() {
        return timeMultiplicity;
    }

    public void setTimeMultiplicity(long timeMultiplicity) {
        this.timeMultiplicity = timeMultiplicity;
    }

    public long getSpaceMultiplicity() {
        return spaceMultiplicity;
    }

    public void setSpaceMultiplicity(long spaceMultiplicity) {
        this.spaceMultiplicity = spaceMultiplicity;
    }

    public long getScaleMultiplicity() {
        return scaleMultiplicity;
    }

    public void setScaleMultiplicity(long scaleMultiplicity) {
        this.scaleMultiplicity = scaleMultiplicity;
    }

    public String getDereifyingAttribute() {
        return dereifyingAttribute;
    }

    public void setDereifyingAttribute(String dereifyingAttribute) {
        this.dereifyingAttribute = dereifyingAttribute;
    }

    public String getObservable() {
        return observable;
    }

    public void setObservable(String observable) {
        this.observable = observable;
    }

    public String getObservationType() {
        return observationType;
    }

    public void setObservationType(String observationType) {
        this.observationType = observationType;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Mediation getMediation() {
        return mediation;
    }

    public void setMediation(Mediation mediation) {
        this.mediation = mediation;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Set<String> getNeededCapabilities() {
        return neededCapabilities;
    }

    public void setNeededCapabilities(Set<String> neededCapabilities) {
        this.neededCapabilities = neededCapabilities;
    }

    public Map<String, Object> getRanks() {
        return ranks;
    }

    public void setRanks(Map<String, Object> ranks) {
        this.ranks = ranks;
    }

    public boolean isAbstractObservable() {
        return abstractObservable;
    }

    public void setAbstractObservable(boolean abstractObservable) {
        this.abstractObservable = abstractObservable;
    }

    public int getMinSpatialScaleFactor() {
        return minSpatialScaleFactor;
    }

    public void setMinSpatialScaleFactor(int minSpatialScaleFactor) {
        this.minSpatialScaleFactor = minSpatialScaleFactor;
    }

    public int getMaxSpatialScaleFactor() {
        return maxSpatialScaleFactor;
    }

    public void setMaxSpatialScaleFactor(int maxSpatialScaleFactor) {
        this.maxSpatialScaleFactor = maxSpatialScaleFactor;
    }

    public int getMinTimeScaleFactor() {
        return minTimeScaleFactor;
    }

    public void setMinTimeScaleFactor(int minTimeScaleFactor) {
        this.minTimeScaleFactor = minTimeScaleFactor;
    }

    public int getMaxTimeScaleFactor() {
        return maxTimeScaleFactor;
    }

    public void setMaxTimeScaleFactor(int maxTimeScaleFactor) {
        this.maxTimeScaleFactor = maxTimeScaleFactor;
    }

    public IConcept getObservableConcept() {
        return observableConcept;
    }

    public void setObservableConcept(IConcept observableConcept) {
        this.observableConcept = observableConcept;
    }
    
    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
