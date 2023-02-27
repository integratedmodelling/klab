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
package org.integratedmodelling.klab.api.identities;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.services.runtime.KChannel;

/**
 * The Interface IRuntimeIdentity is implemented by all identities that can be
 * interacted with during their lifetime. Interaction is driven by
 * {@link IBehavior} objects, loaded and stopped explicitly.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public abstract interface KRuntimeIdentity extends KIdentity {

	/**
	 * Stop all existing behaviors. Return whether at least one was stopped.
	 * 
	 * @return
	 */
	boolean stop();

	/**
	 * A runtime identity exposes a monitor to report conditions and affect
	 * contextualization.
	 *
	 * @return a
	 *         {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor}
	 *         object.
	 */
	KChannel getMonitor();

	/**
	 * At runtime, each identity has a state which in the simplest case is an
	 * unstructured map.
	 * 
	 * @return
	 */
	KParameters<String> getState();

}
