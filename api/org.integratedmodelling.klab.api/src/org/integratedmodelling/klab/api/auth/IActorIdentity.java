package org.integratedmodelling.klab.api.auth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewComponent;

/**
 * Provides the actor interface to any identity in the k.LAB runtime by exposing
 * an Akka actor reference.
 * 
 * @author Ferd
 *
 */
public interface IActorIdentity<T> extends IRuntimeIdentity {

	/**
	 * The main message for anything sent through k.Actors. Dispatched to receivers
	 * running applications, unless direct (application ID is null, i.e. it's for
	 * this actor).
	 * 
	 * @author Ferd
	 */
	public interface KlabMessage extends Serializable {

		/**
		 * Message may contain one or more of these, which trigger messages at the
		 * recipient's side which the sender can intercept to synchronize operations
		 * when requested or needed. Because concurrent message exchange is problematic
		 * during message execution, we use the Actors instance to organize these.
		 * 
		 * @author Ferd
		 */
		public interface Semaphore {

			public enum Type {
				/**
				 * if a LOAD semaphore is in a Load message, wait for the semaphore to go green
				 * before moving on.
				 */
				LOAD,

				/**
				 * if a FIRE semaphore is in a KActorsCall message, wait for the action to fire
				 * before moving on to the next instruction.
				 */
				FIRE
			}

			Type getType();

			/**
			 * For notification: return true if a potential deadlock warning was issued
			 * 
			 * @return
			 */
			boolean isWarned();

			void setWarned();
		}

		/**
		 * Get another message identical to this but with no application ID, intended
		 * for execution and not for dispatching.
		 * 
		 * @return
		 */
		KlabMessage direct();

		/**
		 * Add a semaphore for the recipient to honor after message execution and return
		 * self for fluency.
		 * 
		 * @param semaphor
		 */
		KlabMessage withSemaphore(Semaphore semaphor);

		/**
		 * Get all semaphors of the specified type.
		 * 
		 * @param type
		 * @return
		 */
		List<Semaphore> getSemaphores(Semaphore.Type type);
	}

	
	/**
	 * An actor may have an associated view.
	 * 
	 * @author Ferd
	 *
	 */
	public interface View {

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
	 * Opaque reference to an actor, entirely implementation-dependent but needed in the API.
	 * 
	 * @author Ferd
	 *
	 */
	public interface Reference {
		void tell(KlabMessage message);
	}

	/**
	 * Get the actor peer for the identity. If the actor needs to be created, ask
	 * the relevant parent's actor to create it first.
	 * 
	 * @return the actor reference. Never null.
	 */
	Reference getActor();

	/**
	 * Set the actor in the identity.
	 * 
	 * @param actor
	 * @param scope
	 */
	void instrument(Reference actor);

	/**
	 * If the actor has a view associated, return it. Otherwise return null.
	 * 
	 * @return
	 */
	View getView();

	// must be in the API for now. Called to create the view.
	void setView(View layout);


    /**
     * Load a specified behavior in a specified runtime scope. Returns a string
     * identifying the running behavior, which can be passed to
     * {@link #stop(String)}.
     * 
     * @param behavior
     * @param scope
     */
    String load(IBehavior behavior, IContextualizationScope scope);

    boolean stop(String behaviorId);
}
