package org.integratedmodelling.klab.api.knowledge;

import java.util.List;

import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A view model is a complex resolvable that provides a "recipe" to produce a
 * {@link IKnowledgeView}. It can list the observables that are needed to
 * produce the view through specific (void) contextualizers.
 * <p>
 * Implementations may define any view class using define classes in k.IM
 * (define <class> <id> as ....). The classes that are recognized by the engine
 * can be supported in the modeler for drag-and-drop computation and in the
 * explorer to observe by name referencing. Such classes in the current
 * implementation include tables and graphs.
 * 
 * @author Ferd
 *
 */
public interface IViewModel extends IResolvable, IKimObject {

	/**
	 * The schedule according to which the model will be computed. Returned by
	 * getSchedule().
	 * 
	 * @author Ferd
	 *
	 */
	interface Schedule {

		boolean isStart();

		boolean isInit();

		boolean isEnd();

		boolean isTemporal();

		ITime.Resolution getResolution();

	}

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
	IKnowledgeView compileView(IObservation target, IContextualizationScope scope);

	/**
	 * The namespace
	 * 
	 * @return
	 */
	INamespace getNamespace();

	/**
	 * Because view models are not semantic and are declared as queries rather than
	 * using the semantic k.IM machinery, they may specify when they want to be run.
	 * If the model specifies no schedule, a default schedule is produced that is
	 * both temporal and init, i.e. the model will be scheduled at initialization
	 * and each natural transition implied by the primary target's scale.
	 * <p>
	 * Specific view types may modify the default schedule according to their
	 * definition. For example, tables that compare start and end value will
	 * mandatorily be run only at termination.
	 * 
	 * @return
	 */
	Schedule getSchedule();

	/**
	 * The target observable. This one may return null but if it does, a runtime
	 * error will be generated when the view is computed.
	 * 
	 * @return
	 */
	IObservable getTargetObservable();

}
