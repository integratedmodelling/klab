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
package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.services.IReasonerService;

/**
 * The individual (instance). In k.LAB, individuals do not need to be generated
 * unless the runtime context allows inconsistent observations to be produces,
 * in which case they should be requested through
 * {@link ISubject#instantiate(IOntology)} on the root context, and validated
 * using the {@link IReasonerService}. They can also be requested for RDF export
 * of contextualization results.
 * <p>
 * This interface exists for completeness but is not used in k.LAB at the
 * present time, and the few methods that use it are unimplemented. The main
 * product of k.LAB reasoning are {@link IObservation} which are semantic
 * {@link IArtifact}, and the semantics of provenance describes the outcomes of
 * k.LAB sessions, rather than the instantiation of the concepts.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IIndividual extends ISemantic {

	/**
	 * <p>
	 * getIndividuals.
	 * </p>
	 *
	 * @param property a
	 *                 {@link org.integratedmodelling.klab.api.knowledge.IProperty}
	 *                 object.
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<IIndividual> getIndividuals(IProperty property);

	/**
	 * <p>
	 * getData.
	 * </p>
	 *
	 * @param property a
	 *                 {@link org.integratedmodelling.klab.api.knowledge.IProperty}
	 *                 object.
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<Object> getData(IProperty property);

	/**
	 * <p>
	 * getObjectRelationships.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<IProperty> getObjectRelationships();

	/**
	 * <p>
	 * getDataRelationships.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<IProperty> getDataRelationships();

	/**
	 * <p>
	 * is.
	 * </p>
	 *
	 * @param type a {@link org.integratedmodelling.klab.api.knowledge.ISemantic}
	 *             object.
	 * @return a boolean.
	 */
	boolean is(ISemantic type);

	/**
	 * <p>
	 * getMetadata.
	 * </p>
	 *
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IMetadata}
	 *         object.
	 */
	IMetadata getMetadata();
}
