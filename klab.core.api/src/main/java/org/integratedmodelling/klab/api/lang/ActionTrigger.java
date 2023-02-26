package org.integratedmodelling.klab.api.lang;

/**
 * Trigger for actions. Also gets propagated to contextualizables.
 * 
 * @author Ferd
 *
 */
public enum ActionTrigger {

	/**
	 * Definition, i.e. initialization. Only legal with perdurants. Bound to 'on
	 * definition' [set to | do ] For instantiators, 'self' is the context of the
	 * new instances.
	 */
	DEFINITION,

	/**
	 * State initialization is called after all the context has been initialized
	 * (with individual on definition actions) in a state model. Self is the state
	 * itself.
	 */
	STATE_INITIALIZATION,

	/**
	 * Instantiation: before resolution of EACH new instance from an instantiator.
	 * Not accepted within contextualizers. 'self' is the new instance, 'context'
	 * their context on which the instantiator was called.
	 */
	INSTANTIATION,

	/**
	 * The default trigger for resources that provide the main content for models
	 * (including instantiating resources) unless the model is for an occurrent, in
	 * which case the resource gets the TRANSITION trigger.
	 */
	RESOLUTION,

	/**
	 * Termination: just after 'move away' or deactivate(). Cannot change the
	 * outcome of deactivation but object can still "do" things within the action.
	 */
	TERMINATION,

	/**
	 * Triggered by events (types returned by getTriggeredEvents())
	 */
	EVENT,

	/**
	 * Triggered by temporal transitions.
	 */
	TRANSITION
}