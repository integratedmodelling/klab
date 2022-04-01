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
package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

import org.integratedmodelling.klab.api.auth.ITaskIdentity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;

/**
 * A ITask computes an observational artifact, delegating to a Java Future that
 * returns it when available.
 * 
 * TODO add observation and error listeners, scenarios and options for resolution
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T> the type of observation being resolved
 */
public interface ITask<T extends IArtifact> extends ITaskIdentity, Future<T> {

	/**
	 * The "resolvable" unit that this task is dedicated to resolving (not
	 * necessarily successfully).
	 * 
	 * @return
	 */
	IResolvable getResolvable();
	
	void addObservationListener(BiConsumer<ITask<?>, T> listener);
	
	void addErrorListener(BiConsumer<ITask<?>, Throwable> listener);
	
	void addScenarios(Collection<String> scenarios);

    boolean start();

}
