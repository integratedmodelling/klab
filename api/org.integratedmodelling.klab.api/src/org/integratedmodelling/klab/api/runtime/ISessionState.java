package org.integratedmodelling.klab.api.runtime;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
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
		public void scaleChanged(ScaleReference scale);
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
}
