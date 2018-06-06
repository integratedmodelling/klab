package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ITask;

/**
 * A {@link ITask} that can produce child tasks so that a hierarchical configuration
 * of tasks is possible.
 * 
 * @author ferdinando.villa
 *
 * @param <T> 
 */
public interface ITaskTree<T extends IObservation> extends ITask<T> {

	ITaskTree<T> createChild();
	
	boolean isChildTask();
}
