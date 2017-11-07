/*******************************************************************************
 * Copyright (C) 2007, 2015:
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
package org.integratedmodelling.klab.api.provenance;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IConcept;
import org.integratedmodelling.kim.api.IMetadata;
import org.integratedmodelling.kim.api.IModel;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISpace;
import org.integratedmodelling.klab.api.observations.ITime;

/**
 * The k.LAB view of provenance is made up of actions that link an actor to a result. It
 * is built using direct actions (in the "active voice") and can be accessed backwards to
 * build the OPM graph, which is a "passive voice" model. This makes it much easier to
 * build through observations and resolutions. The data models is that of a graph where
 * the vertices are Node and the edges are Actions. Actions are linked to each other in a
 * causal graph that is independent from the primary graph. Actions that are not caused by
 * another action are called "primary" and can be obtained in chronological order. All
 * nodes and actions are timestamped with their time of creation and hold arbitrary
 * metadata using the standard Dublin Core tags from {@link IMetadata}. Because k.LAB is
 * an intelligent system, we always have at least two agents: a User and k.LAB itself.
 * Primary actions are typically caused by users, secondary by the AI in k.LAB. Provenance
 * is used to document the model resolution strategy and to build the IReport that
 * documents the model results.
 * 
 * @author Ferd
 */
public interface IProvenance {

    /**
     * Everything except Actions is a node. Used only to allow generalizing the API.
     * 
     * @author Ferd
     */
    abstract interface Node {

        /**
         * @return
         */
        String getName();

        /**
         * A node is usually a contextualization, in which case it should produce the
         * observation being contextualized.
         * 
         * @return
         */
        IObservation getObservation();

    }

    /**
     * Actions are the edges of the provenance graph. Action type determines the role of
     * their vertices and can be translated into OPM relationships, which typically
     * represent the inverse action.
     * 
     * @author Ferd
     */
    interface Action {

        /**
         * If the action was caused by another action, return the action that caused it.
         * 
         * @return
         */
        Action getCause();

        /**
         * Actions are made by agents. We keep them with the actions and out of the graph.
         * 
         * @return
         */
        Agent getAgent();

    }

    /**
     * An agent in k.LAB is anything that makes observations.
     * 
     * @author Ferd
     */
    interface Agent extends Node {

    }

    /**
     * An Artifact can be any of the first-class citizens in k.LAB: Observation or Model
     * (when the model has been produced by another, such as a learning model).
     * Observations are the final results of a successful contextualization. We also allow
     * Observations that represent groups of observations (e.g. all the Subjects created
     * by resolving a subject observable), to avoid creating monster graphs.
     * 
     * @author Ferd
     */
    interface Artifact extends Node {

        /**
         * @return
         */
        ISemantic getArtifact();

        /**
         * @return
         */
        Agent getConsumer();

        /**
         * 
         * @return
         */
        IConcept getObservable();

        /**
         * @return
         */
        Agent getOwner();

        /**
         * 
         */
        IModel getModel();

        /**
         * @return
         */
        Collection<Artifact> getAntecedents();

        /**
         * @return
         */
        Collection<Artifact> getConsequents();

        /**
         * Return the temporal extent implied for this artifact by the provenance chain.
         * This includes (in order of preemption) any specific temporal constraint
         * specified by the model, and the temporal scale of all the observations
         * upstream. Temporal elements are merged upwards until a complete scale is
         * defined. Returns null only if there is no time upstream of this observation.
         * 
         * @return
         */
        ITime getTime();

        /**
         * Return the spatial extent implied for this artifact by the provenance chain.
         * This includes (in order of preemption) any specific temporal constraint
         * specified by the model, and the temporal scale of all the observations
         * upstream. Temporal elements are merged upwards until a complete scale is
         * defined. Returns null only if there is no space upstream of this observation.
         * 
         * @return
         */
        ISpace getSpace();

        /**
         * Trace the nearest artifact of the passed concept (or with the passed
         * role/trait) up the provenance chain.
         * 
         * @param cls
         * @return
         */
        Artifact trace(IConcept concept);

        /**
         * Collect all artifacts of the passed concept (or with the passed role/trait) up
         * the provenance chain.
         * 
         * @param concept
         * @return
         */
        Collection<Artifact> collect(IConcept concept);

        /**
         * Trace the nearest artifact with the passed role within the passed observation
         * up the provenance chain.
         * 
         * @param cls
         * @return
         */
        Artifact trace(IConcept role, IDirectObservation roleContext);

        /**
         * Collect all artifacts with the passed role within the passed observation up the
         * provenance chain.
         * 
         * @param concept
         * @return
         */
        Collection<Artifact> collect(IConcept role, IDirectObservation roleContext);

    }

    /**
     * True if there's nothing to see.
     * 
     * @return true if empty
     */
    boolean isEmpty();

    /**
     * Temporary - to be improved. Collect metadata for a node, merging with upstream
     * metadata as needed.
     * 
     * @param node
     * @return collect metadata from node
     */
    public IMetadata collectMetadata(Object node);

    /**
     * Return all the primary actions in chronological order.
     * 
     * @return
     */
    List<Action> getPrimaryActions();

    /**
     * Create a new primary event, not caused by another action.
     * 
     * @param actor
     * @param action
     * @param result
     * @return
     */
    Action add(Node actor, Action action, Node result);

    /**
     * Create a new event (actor creates result through action) caused by another action.
     * Return the action for the event to allow more fluent idioms.
     * 
     * @param actor
     * @param action
     * @param result
     * @param cause
     * @return
     */
    Action add(Node actor, Action action, Node result, Action cause);

    /**
     * There is always a root observation/artifact, with consumer = the user.
     * 
     * @return
     */
    Artifact getRootArtifact();

    /**
     * Return all artifacts.
     * 
     * @return
     */
    Collection<Artifact> getArtifacts();

}
