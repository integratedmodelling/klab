package org.integratedmodelling.klab.engine.runtime.api;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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

	/**
	 * Set the actor in the identity.
	 * 
	 * @param actor
	 * @param scope
	 */
	void instrument(ActorRef<T> actor);

//	/**
//	 * 
//	 * @param name
//	 * @param listener
//	 */
//	void setStateChangeListener(String name, BiConsumer<String, Object> listener);
//
//	/**
//	 * 
//	 * @param name
//	 */
//	void removeStateChangeListener(String name);

	/**
	 * If the actor has a view associated, return it. Otherwise return null.
	 * 
	 * @return
	 */
	View getView();

	// must be in the API for now. Called to create the view.
	void setLayout(Layout layout);

}