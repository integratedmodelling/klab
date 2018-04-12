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
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.services.IReasonerService;

/**
 * The individual (instance). In k.LAB, individuals do not need to be generated unless the runtime context
 * allows inconsistent observations to be produces, in which case they should be requested through
 * {@link ISubject#instantiate(IOntology)} on the root context, and validated using the
 * {@link IReasonerService}. They can also be requested for RDF export of contextualization results.
 * 
 * @author Ferd
 *
 */
public interface IIndividual extends ISemantic {

    Collection<IIndividual> getIndividuals(IProperty property);

    Collection<Object> getData(IProperty property);

    Collection<IProperty> getObjectRelationships();

    Collection<IProperty> getDataRelationships();

    boolean is(ISemantic type);

    IMetadata getMetadata();
}
