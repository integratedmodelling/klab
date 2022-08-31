package org.integratedmodelling.klab.api.runtime;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionConstraint;
import org.integratedmodelling.klab.api.runtime.monitoring.IInspector;
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

	/**
	 * Submit an observation request for the passed URN. If the URN is null and a
	 * context has been predefined, just create the context as the main artifact
	 * with no error.
	 * 
	 * @param urn
	 * @return
	 */
	Future<IArtifact> submit(String urn);

	/**
	 * Like {@link #submit(String)} but with listeners to be called when the
	 * observation is done or errors are encountered.
	 * 
	 * @param urn
	 * @param observationListener
	 * @param errorListener
	 * @return
	 */
	Future<IArtifact> submit(String urn, BiConsumer<ITask<?>, IArtifact> observationListener,
			BiConsumer<ITask<?>, Throwable> errorListener);

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
	 * indicated and notified of the completion of any submitted observation or the
	 * context (but not of any other observation made).
	 * 
	 * @param listener
	 * @param applicationId
	 * @return
	 */
	String addApplicationListener(Listener listener, String applicationId);

	/**
	 * Add an application-specific listener whose newObservation() method will be
	 * notified of all observations that are notified to the view.
	 * 
	 * @param listener
	 * @param appId
	 * @return
	 */
	String addApplicationGlobalListener(Listener listener, String appId);

	/**
	 * Remove the global listener indicated
	 * 
	 * @param listenerId
	 */
	void removeGlobalListener(String listenerId);

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

	/**
	 * The most general of the notifiers, called any time the view is notified (all
	 * new observations, excluded those in folder and those explicitly hidden from
	 * view).
	 * 
	 * @param observation
	 */
	void notifyObservation(IObservation observation);

	/**
	 * Put a download file in the staging area
	 * 
	 * @param file
	 * @return
	 */
	String stageDownload(File file);

	/**
	 * Retrieve the file from the staging area and eliminate the record.
	 * 
	 * @return
	 */
	File getStagedFile(String id);

	/**
	 * Return all resolution constraints configured into this session, or the empty
	 * list.
	 * 
	 * @return
	 */
	Collection<IResolutionConstraint> getResolutionConstraints();

	/**
	 * A session may carry an inspector for debugging. Each operation decides what
	 * to let the inspector see, examining its configuration at key points. A
	 * session that returns null here isn't "armed" for debugging but code can still
	 * call {@link #notifyInspector(Object...)} without consequences.
	 * 
	 * @return
	 */
	IInspector getInspector();

}
