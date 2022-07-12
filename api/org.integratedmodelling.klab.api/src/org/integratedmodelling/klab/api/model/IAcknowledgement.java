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
package org.integratedmodelling.klab.api.model;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.IComputationProvider;
import org.integratedmodelling.klab.api.resolution.IResolvable;

/**
 * An observer is the k.LAB object corresponding to an <code>observe</code> statement in k.IM, used to specify
 * an acknowledged observation. The k.LAB runtime can instantiate an (acknowledged) observation by running it.
 *
 * The {@link #getChildren()} method of an IObserver only returns other IObservers.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IAcknowledgement extends IActiveKimObject, INamespaceQualified, IResolvable, IComputationProvider {

    /**
     * The concept this observes, a direct observable.
     *
     * @return observed concept
     */
    IObservable getObservable();

    /**
     * Specifications for states are reported as observables, whose {@link org.integratedmodelling.klab.api.knowledge.IObservable#getValue()} is
     * guaranteed not to return null.
     *
     * @return all stated indirect observations for the resulting observation.
     */
    List<IObservable> getStates();

    /**
     * Metadata can be associated to all observers in k.IM.
     *
     * @return metadata (never null).
     */
    IMetadata getMetadata();

}
