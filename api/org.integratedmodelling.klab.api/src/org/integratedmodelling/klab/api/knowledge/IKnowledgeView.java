package org.integratedmodelling.klab.api.knowledge;

import java.util.List;

import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A Knowledge view is a complex resolvable that can produce a set of
 * observables that are sufficient to produce the view. Once these have been
 * resolved and observed, the view is given a chance to expose the
 * contextualized scope in a specific way through the session monitor.
 * <p>
 * Implementation may define any views using define classes in k.IM (define
 * <class> <id> as ....). The classes that are recognized by the engine can be
 * supported in the modeler for drag-and-drop computation and in the explorer to
 * observe by name referencing. Such classes in the current implementation
 * include tables and graphs.
 * 
 * @author Ferd
 *
 */
public interface IKnowledgeView extends IResolvable, IKimObject {

	/**
	 * The view class is simply a string for the time being. Engine implementations
	 * should connect known classes to specific types of views. When views are
	 * specified in k.IM, the class is the one specified in the optional class
	 * argument of the correspondent define statement.
	 * 
	 * @return
	 */
	String getViewClass();

	/**
	 * Return all the observables, mandatory and optional, required to produce the
	 * view. These will be observed in sequence when the view is contextualized.
	 * 
	 * @return
	 */
	List<IObservable> getObservables();

	/**
	 * Called after successful contextualization of all observables returned by
	 * {@link #getObservables()}. Expected to produce the view in any format
	 * suitable to the implementation and broadcast it to any client through the
	 * scope monitor.
	 * 
	 * @param scope
	 */
	void compileView(IObservation target, IContextualizationScope scope);

	/**
	 * The namespace
	 * 
	 * @return
	 */
	INamespace getNamespace();

}
