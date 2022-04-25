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
 * The individual (instance). Individuals are <em>not</em> involved or generated
 * during the main k.LAB observation activities. They can, however, be created
 * as k.IM entities (<code>define class id as {}</code>) when a namespace uses
 * an OWL vocabulary, and can be output as RDF export of contextualization
 * results.
 * <p>
 * Serializing provenance (into a RDF-exportable provenance.kim that can be
 * output from a contextualization or stored along with resources) is currently
 * the main internal activity that uses individuals, although users are free to
 * define k.IM entities for any other purpose.
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
