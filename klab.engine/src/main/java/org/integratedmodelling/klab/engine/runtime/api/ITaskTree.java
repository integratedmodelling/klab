package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ITask;

/**
 * A {@link ITask} that can produce child tasks so that a hierarchical
 * configuration of tasks is possible. This is used to keep track of
 * sub-operations (e.g. resolving instantiated objects) and in the future to
 * wrap remote executions in a well-defined task structure.
 * 
 * @author ferdinando.villa
 *
 * @param <T>
 */
public interface ITaskTree<T extends IObservation> extends ITask<T> {

	/**
	 * Create a child task.
	 * 
	 * @return a child task, never null.
	 */
	ITaskTree<T> createChild();

	/**
	 * True if this task is the child of another. Use the {@link IIdentity} API to
	 * navigate the hierarchy.
	 * 
	 * @return true if a child
	 */
	boolean isChildTask();

	/**
	 * The ID of the observation context where this task was invoked. May be
	 * different from the actual context of the observation created.
	 * 
	 * @return
	 */
	String getContextId();
}
