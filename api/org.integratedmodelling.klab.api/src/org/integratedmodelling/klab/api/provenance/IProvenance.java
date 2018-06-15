/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.provenance;

import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The k.LAB view of provenance is made up of actions that link an actor to a
 * result. It is built using direct actions (in the "active voice") and can be
 * accessed backwards to build the OPM graph, which is a "passive voice" model.
 * This makes it much easier to build through observations and resolutions. The
 * data models is more compact than the W3's provenance model: it's a graph
 * where the vertices are
 * {@link org.integratedmodelling.klab.api.provenance.IProvenance.Node}s and the
 * edges are {@link org.integratedmodelling.klab.api.provenance.IActivity}s.
 * Actions may be linked to each other in a causal graph that is independent
 * from the primary graph. Actions that are not caused by another action are
 * called "primary" and can be obtained in chronological order. All nodes and
 * actions are timestamped with their time of creation and hold arbitrary
 * metadata using the standard Dublin Core tags from
 * {@link org.integratedmodelling.klab.api.knowledge.IMetadata}.
 * <p>
 * Because k.LAB is an intelligent system, we always have at least two agents: a
 * User (personified by a
 * {@link org.integratedmodelling.klab.api.auth.IUserIdentity} and the k.LAB
 * engine, personifying the AI in the system through a
 * {@link org.integratedmodelling.klab.api.auth.IEngineIdentity}. Primary
 * actions are typically caused by users (through
 * <strong>acknowledgement</strong> observations), secondary by the AI in k.LAB
 * (through <strong>computation</strong> or <strong>detection</strong>
 * observations). Provenance is used to document the model resolution strategy
 * and to build the IReport that documents the model results.
 * <p>
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IProvenance {

	/**
	 * Everything except Actions is a node. Used only to allow generalizing the API.
	 * 
	 * @author Ferd
	 */
	abstract interface Node {

		long getTimestamp();

		/**
		 * Return the graph we're part of.
		 * 
		 * @return the provenance graph
		 */
		IProvenance getProvenance();

		/**
		 * Workflows that end in disappointment produce these.
		 * 
		 * @return true if empty
		 */
		boolean isEmpty();
	}

	/**
	 * True if there's nothing to see.
	 *
	 * @return true if empty
	 */
	boolean isEmpty();

	/**
	 * Return all the primary actions in chronological order.
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<IActivity> getPrimaryActions();

	/**
	 * There is always a root observation/artifact, with consumer = the user.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.provenance.IArtifact}
	 *         object.
	 */
	IArtifact getRootArtifact();

	/**
	 * Return all artifacts.
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<IArtifact> getArtifacts();

}
