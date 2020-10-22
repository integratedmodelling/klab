package org.integratedmodelling.klab.api.runtime;

import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * State of a session, set by users with successive operations and persistent if
 * necessary. Will need work to honor all the functionalities.
 * 
 * @author Ferd
 *
 */
public interface ISessionState extends IParameters<String> {

	public interface Listener {
//		public void onChange(SpatialExtent extent);
	}

	Future<IArtifact> submit(String urn);

	boolean activateScenario(String scenario);

	boolean deactivateScenario(String scenario);

	IGeometry getGeometry();

	Set<String> getActiveScenarios();

	void activateApplication(String applicationName);

	void deactivateApplication(String applicationName);

	void addRole(IConcept role, IConcept target);

	void removeRole(IConcept role, IConcept target);

	void resetRoles();

	String save();

	void resetContext();

	void restore(String stateId);
}
