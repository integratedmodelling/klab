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
package org.integratedmodelling.klab.api.resolution;

import java.util.List;

import org.integratedmodelling.kim.api.IContextualizable;

/**
 * The computable interface applies to objects that can contribute computations
 * to a dataflow. If so, they must be able to provide a declarative form of the
 * computation as a list of resources that can be turned into KDL actuators.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IComputationProvider {

	/**
	 * Return the computations for the passed locator.
	 *
	 * @return a list of computable resources, possibly empty, including the
	 *         specific triggers which will depend both on the declaration and the
	 *         semantic context.
	 */
	List<IContextualizable> getComputation();

}
