package org.integratedmodelling.kim.api;

import java.util.List;

public interface IKimAction extends IKimStatement {

    enum Type {
        SET,
        CHANGE,
        INTEGRATE,
        DO,
        MOVE,
        DESTROY
    };

    /**
     * Trigger for action
     * @author Ferd
     *
     */
    enum Trigger {

        /**
         * Definition: before resolution, after calling any external contextualizers (using...). For
         * instantiators, 'self' is the context of the new instances.
         */
        DEFINITION,

        /**
         * State initialization is called after all the context has been initialized (with individual
         * on definition actions) in a state model. Self is the state itself.
         */
        STATE_INITIALIZATION,

        /**
         * Instantiation: before resolution of EACH new instance from an instantiator. Not accepted
         * within contextualizers. 'self' is the new instance, 'context' their context on which the
         * instantiator was called.
         */
        INSTANTIATION,

        /**
         * Instantiation: after resolution of EACH new instance from an instantiator. Not accepted
         * within contextualizers. 'self' is the new instance, 'context' their context on which the
         * instantiator was called.
         */
        RESOLUTION,

        /**
         * Termination: just after 'move away' or deactivate(). Cannot change the outcome of deactivation but
         * object can still "do" things within the action.
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

    /**
     * The type of action.
     * 
     * @return action type
     */
    Type getType();

    /**
     * The trigger for the action.
     * 
     * @return
     */
    Trigger getTrigger();

    /**
     * If the action has a target state, return its formal name in the model. 
     * 
     * @return state targeted
     */
    String getTargetStateId();

    /**
     * The types of events that trigger the action, or an empty collection if trigger
     * is not Trigger.EVENT.
     * @return
     */
    List<IKimConcept> getTriggeringEvents();

    /**
     * The actual computables for this action.
     * 
     * @return
     */
    List<IComputableResource> getComputation();
    
}
