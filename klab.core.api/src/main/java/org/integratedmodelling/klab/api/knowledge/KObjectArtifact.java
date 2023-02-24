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

/**
 * A {@code IArtifact} representing a first-class object, which may own other
 * artifacts and has a name. Additional API for a direct observation.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface KObjectArtifact extends KArtifact {

	/**
	 * Objects have names.
	 *
	 * @return the object's name
	 */
	String getName();

	/**
	 * Objects can have child artifacts. Return all children of the specified class.
	 * 
	 * @param cls
	 *            class or interface for children. Pass IObservation.class for
	 *            everything.
	 * @return a collection of all the children of the requested class.
	 */
	<T extends KArtifact> Collection<T> getChildren(Class<T> cls);

}
