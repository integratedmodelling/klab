package org.integratedmodelling.klab.engine.runtime.api;

import java.util.Map;

import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewComponent;

import akka.actor.typed.ActorRef;

/**
 * Provides the actor interface to any identity in the k.LAB runtime by exposing
 * an Akka actor reference.
 * 
 * @author Ferd
 *
 */
public interface IActorIdentity<T> extends IRuntimeIdentity {

	/**
	 * An actor may have an associated view.
	 * 
	 * @author Ferd
	 *
	 */
	interface View {

		/**
		 * The layout. Never null if there is a view.
		 * 
		 * @return
		 */
		Layout getLayout();

		/**
		 * Static layout components indexed by their action ID. Used to marshall
		 * notifications and add components in dynamic views.
		 * 
		 * @return
		 */
		Map<String, ViewComponent> getStaticComponents();
	}

	/**
	 * Get the actor peer for the identity. If the actor needs to be created, ask
	 * the relevant parent's actor to create it first.
	 * 
	 * @return the actor reference. Never null.
	 */
	ActorRef<T> getActor();

//	/**
//	 * When a behavior is loaded, the identity gets a "runtime" actor that is
//	 * dedicated to performing tasks which may be triggered by the behavior itself,
//	 * such as spawning other actors. These can be used safely while a behavior
//	 * executes without risking a deadlock if the receiver of an action is the
//	 * actor itself.
//	 * 
//	 * @return
//	 */
//	ActorRef<T> getRuntimeActor();


	/**
	 * Set the actor in the identity.
	 * 
	 * @param actor
	 * @param scope
	 */
	void instrument(ActorRef<T> actor);

	/**
	 * Actors have a state that is manipulated through the "set" statement in
	 * k.Actors.
	 * 
	 * @return
	 */
	Map<String, Object> getState();

	/**
	 * If the actor has a view associated, return it. Otherwise return null.
	 * 
	 * @return
	 */
	View getView();

	// must be in the API for now. Called to create the view.
	void setLayout(Layout layout);

//	// same - called by actors at behavior load to provide the runtime actor
//	void setRuntimeActor(ActorRef<T> runtimeActor);

}
