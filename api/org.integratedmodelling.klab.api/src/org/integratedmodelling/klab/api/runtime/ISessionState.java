package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;

/**
 * State of a session, set by users with successive operations and persistent if
 * necessary. Will need work to honor all the functionalities.
 * 
 * @author Ferd
 *
 */
public interface ISessionState extends IParameters<String> {

	public interface Listener {

		/*
		 * 
		 */
		public void scaleChanged(ScaleReference scale);

		/**
		 * Null means context was reset
		 * 
		 * @param context
		 */
		public void newContext(ISubject context);

		/**
		 * 
		 * @param observation
		 * @param context
		 */
		public void newObservation(IObservation observation, ISubject context);

		/**
		 * Called with the <i>root</i> activity descriptor for the context every time an
		 * observation activity finishes in the context described.
		 * 
		 * @param history      the root activity, which generated the current context.
		 *                     Never null.
		 * @param rootActivity the activity where the notified change happened. If the
		 *                     listener is invoked on the root activity, this will be
		 *                     null.
		 */
		public void historyChanged(SessionActivity rootActivity, SessionActivity currentActivity);

	}

	Future<IArtifact> submit(String urn);

	boolean activateScenario(String scenario);

	boolean deactivateScenario(String scenario);

	/**
	 * The geometry of interest according to the activity in the session. Used to
	 * build the observer that will contain the first observation in case the latter
	 * is not an observer itself.
	 * 
	 * @return
	 */
	IGeometry getGeometry();

	Set<String> getActiveScenarios();

	void setActiveScenarios(Collection<String> scenarios);

	void activateApplication(String applicationName);

	void deactivateApplication(String applicationName);

	void addRole(IConcept role, IConcept target);

	void removeRole(IConcept role, IConcept target);

	void resetRoles();

	String addListener(Listener listener);

	void removeListener(String listenerId);

	String save();

	IObservation getObservation(IObservable observable);

	/**
	 * Retrieve a known artifact by name. May be a view (identified by either ID or
	 * full URN) or an observation or observation group ID.
	 * 
	 * @param name
	 * @return
	 */
	IArtifact getArtifact(String name);

	void resetContext();

	void restore(String stateId);

	List<SessionActivity> getHistory();

	String getRegionOfInterestName();

	/**
	 * Add a listener that will only be called if the current application is the one
	 * indicated.
	 * 
	 * @param listener
	 * @param applicationId
	 * @return
	 */
	String addApplicationListener(Listener listener, String applicationId);

	/**
	 * Get all the roles set explicitly in the session.
	 * 
	 * @return
	 */
	Map<IConcept, Collection<IConcept>> getRoles();

	/**
	 * A session can have many context, but the current session state has one (the
	 * last established) or none.
	 * 
	 * @return
	 */
	ISubject getCurrentContext();
}
