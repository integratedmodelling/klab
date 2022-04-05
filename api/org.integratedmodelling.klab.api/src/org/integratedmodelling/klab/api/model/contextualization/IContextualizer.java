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
package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A contextualizer builds the observation of an observable in a context based
 * on what the provenance model implies. There are two types of contextualizers:
 * those that <strong>explain</strong> a single instance of an observation
 * (<strong>resolvers</strong>) and those that <strong>instantiate</strong> zero
 * or more observations (to be explained by other contextualizers). These have
 * additional methods and correspond to
 * {@link org.integratedmodelling.klab.api.model.contextualization.IResolver}
 * and
 * {@link org.integratedmodelling.klab.api.model.contextualization.IInstantiator}
 * respectively.
 * <p>
 * Contextualizers can be contributed by components and are managed by
 * {@link org.integratedmodelling.klab.api.runtime.dataflow.IActuator}s during
 * the execution of
 * {@link org.integratedmodelling.klab.api.runtime.dataflow.IDataflow}s. In KDL
 * dataflow specifications, contextualizers are called in <code> compute </code>
 * statements.
 * <p>
 * In a workflow engine such as Ptolemy, contextualizers represent context-aware
 * actors specified by {@link org.integratedmodelling.kim.api.IServiceCall}s.
 * <p>
 * To provide a new contextualizer, extend one of the non-abstract child
 * interfaces and provide the specifications of its k.LAB identity using a KDL
 * file specifying a component definition.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public abstract interface IContextualizer {

	/**
	 * Contextualizers can expose a type so that an artifact chain can be
	 * established when intermediate computations of different types are required
	 * for an observation. Returning null will use the type declared by the service
	 * prototype; the type returned here may specialize it (in case it's VALUE) and
	 * must be compatible.
	 * 
	 * @return
	 */
	IArtifact.Type getType();

	/**
	 * If the resource that we serve needs to be contextualized before use, do that,
	 * set any internal state for the subsequent call, and return a contextualizable
	 * with the recontextualized resource. In case no action is needed, simply return
	 * the resource as passed.
	 * 
	 * @param resource
	 * @param target
	 * @param scope
	 */
	void notifyContextualizedResource(IContextualizable resource, IArtifact target, IContextualizationScope scope);

}
