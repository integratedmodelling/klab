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
package org.integratedmodelling.klab.api.auth;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The Interface IRuntimeIdentity is implemented by all identities that can be
 * interacted with during their lifetime. Interaction is driven by
 * {@link IBehavior} objects, loaded and stopped explicitly.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public abstract interface IRuntimeIdentity extends IIdentity {

//	/**
//	 * Stop a behavior if needed, passing the string returned by
//	 * {@link #load(IBehavior, IContextualizationScope)}. Return true if the
//	 * behavior was indeed stopped.
//	 * 
//	 * @param behaviorId
//	 * @return
//	 */
//	boolean stop(String behaviorId);

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
	IMonitor getMonitor();

	/**
	 * At runtime, each identity has a state which in the simplest case is an
	 * unstructured map.
	 * 
	 * @return
	 */
	IParameters<String> getState();

}
