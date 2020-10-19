package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.LoadApplicationRequest;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Recoverable session state. A generic map with specific fields and
 * save/restore methods. Also manages observation history and queue, scenarios,
 * geometry of interest in time and space, and application state and views.
 * 
 * @author Ferd
 *
 */
public class SessionState extends Parameters<String> {

	private ISession session;
	private ObservationQueue observationQueue;
	private List<SessionActivity> history = new ArrayList<>();
	long startTime = System.currentTimeMillis();

	public SessionState(ISession session) {
		this.session = session;
		this.observationQueue = new ObservationQueue(session);
	}

	public Future<IArtifact> submit(String urn) {
		this.observationQueue.submit(urn, null);
		return null;
	}

	public boolean activateScenario(String scenario) {
		return false;
	}

	public boolean deactivateScenario(String scenario) {
		return false;
	}

	@Override
	public Object put(String key, Object value) {
		// TODO "listen" to specific keys to update internal state.
		return super.put(key, value);
	}

	public Set<String> getActiveScenarios() {
		return null;
	}

	/**
	 * Set and activate the passed application. All UI actions will automatically
	 * reflect on the view state. Only one application can be active in a session at
	 * any time, although the state of previous applications will be saved until
	 * session ends or deactivation.
	 * 
	 * @param applicationName
	 * @param view
	 */
	public void setApplication(String applicationName, Layout view) {

	}

	/**
	 * Make the current application the current one and reload its view.
	 * 
	 * @param applicationName
	 */
	public void activateApplication(String applicationName) {

	}

	/**
	 * Deactivate the application and
	 * 
	 * @param applicationName
	 */
	public void deactivateApplication(String applicationName) {

	}

	public IGeometry getGeometryOfInterest() {
		return null;
	}

	/**
	 * 
	 */
	public void save() {

	}

	/**
	 * 
	 */
	public void restore() {

	}

	public void register(LoadApplicationRequest request) {
		// TODO Auto-generated method stub

	}

	public void register(ViewAction action) {
		// TODO Auto-generated method stub

	}

	public void resetContext() {
		// TODO Auto-generated method stub

	}

	public void register(ObservationRequest request) {
		// TODO Auto-generated method stub

	}

	public void register(ScaleReference scaleRef) {
		// TODO Auto-generated method stub

	}

	public void register(SpatialExtent extent) {
		// TODO Auto-generated method stub

	}

	public void setContext(IRuntimeScope runtimeContext) {
		// TODO Auto-generated method stub
		
	}

}
