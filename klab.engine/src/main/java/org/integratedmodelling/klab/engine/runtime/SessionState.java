package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.engine.runtime.Session.ROIListener;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.LoadApplicationRequest;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

import com.ibm.icu.text.NumberFormat;

/**
 * Recoverable session state. A generic map with specific fields and
 * save/restore methods. Also manages observation history and queue, scenarios,
 * geometry of interest in time and space, and application state and views.
 * <p>
 * TODO create also a WorkspaceState object that handles workspace modification
 * requests. That could be driven by a set of desktop-only actor messages to
 * enable interactive coding.
 * 
 * @author Ferd
 *
 */
public class SessionState extends Parameters<String> {

	public final static String GEOCODING_STRATEGY_KEY = "geocodingstrategy";
	public final static String SPATIAL_RESOLUTION_KEY = "spatialresolution";
	public final static String SPATIAL_RESOLUTION_UNIT_KEY = "spatialresolutionunit";
	public final static String TIME_START_KEY = "timestart";
	public final static String TIME_END_KEY = "timeend";
	public final static String TIME_STEP_KEY = "timestep";

	public interface Listener {

//		public void onChange(SpatialExtent extent);

		
		
	}

	private Session session;
	private ObservationQueue observationQueue;
	private List<SessionActivity> history = new ArrayList<>();
	long startTime = System.currentTimeMillis();
	private Set<String> scenarios = new HashSet<>();
	private Map<IConcept, IConcept> roles = new HashMap<>();
	private Double spatialGridSize;
	private String spatialGridUnits;
	private AtomicBoolean lockSpace = new AtomicBoolean(false);
	Map<String, Listener> roiListeners = Collections.synchronizedMap(new LinkedHashMap<>());
	private ScaleReference regionOfInterest;

	public SessionState(Session session) {
		this.session = session;
		this.observationQueue = new ObservationQueue(session);
		this.regionOfInterest = new ScaleReference();
	}

	public Future<IArtifact> submit(String urn) {
		this.observationQueue.submit(urn, null);
		return null;
	}

	public boolean activateScenario(String scenario) {
		return this.scenarios.add(scenario);
	}

	public boolean deactivateScenario(String scenario) {
		return this.scenarios.remove(scenario);
	}

	public IGeometry getGeometry() {
		return Geometry.create(this.regionOfInterest);
	}

	@Override
	public Object put(String key, Object value) {
		switch (key) {
		case GEOCODING_STRATEGY_KEY:
			break;
		case SPATIAL_RESOLUTION_KEY:
			break;
		case TIME_END_KEY:
			break;
		case TIME_START_KEY:
			break;
		case TIME_STEP_KEY:
			break;
		}
		return super.put(key, value);
	}

	public Set<String> getActiveScenarios() {
		return scenarios;
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

	public void addRole(IConcept role, IConcept target) {

	}

	public void removeRole(IConcept role, IConcept target) {

	}

	public void resetRoles() {
		this.roles.clear();
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
//		System.out.println("ZIO CAN " + request);
	}

	public void register(ViewAction action) {
		// TODO Auto-generated method stub
//		System.out.println("ZIO CAN " + action);

	}

	public void resetContext() {
		// TODO Auto-generated method stub
//		System.out.println("ZIO CAN RESET CONTEXT");

	}

	public void register(ObservationRequest request) {
		// TODO Auto-generated method stub
//		System.out.println("ZIO CAN " + request);

	}

	public void register(ScaleReference extent) {
		// TODO Auto-generated method stub
//		System.out.println("ZIO CAN " + scaleRef);

		Envelope envelope = Envelope.create(extent.getEast(), extent.getWest(), extent.getSouth(), extent.getNorth(),
				Projection.getLatLon());
		ScaleReference scale = new ScaleReference();

		if (!lockSpace.get() || this.spatialGridSize == null) {
			Pair<Integer, String> rres = envelope.getResolutionForZoomLevel();
			this.spatialGridSize = (double) rres.getFirst();
			this.spatialGridUnits = rres.getSecond();
		}

		Pair<Double, String> resolution = new Pair<>(this.spatialGridSize, this.spatialGridUnits);
		Unit sunit = Unit.create(resolution.getSecond());
		int scaleRank = envelope.getScaleRank();
		scale.setEast(envelope.getMaxX());
		scale.setWest(envelope.getMinX());
		scale.setNorth(envelope.getMaxY());
		scale.setSouth(envelope.getMinY());
		scale.setSpaceUnit(resolution.getSecond());
		scale.setSpaceResolution(resolution.getFirst());
		scale.setSpaceResolutionConverted(sunit.convert(resolution.getFirst(), Units.INSTANCE.METERS).doubleValue());
		scale.setSpaceResolutionDescription(
				NumberFormat.getInstance().format(scale.getSpaceResolutionConverted()) + " " + this.spatialGridUnits);
		scale.setResolutionDescription(
				NumberFormat.getInstance().format(scale.getSpaceResolutionConverted()) + " " + this.spatialGridUnits);
		scale.setSpaceScale(scaleRank);

		session.getMonitor().send(IMessage.MessageClass.UserContextDefinition, IMessage.Type.ScaleDefined, scale);

		for (Listener listener : roiListeners.values()) {
//			listener.onChange(extent);
		}

		this.regionOfInterest = extent;

	}

	public void register(SpatialExtent extent) {
		// TODO Auto-generated method stub
//		System.out.println("ZIO CAN " + extent);
	}

	public void setContext(IRuntimeScope runtimeContext) {
		// TODO Auto-generated method stub
//		System.out.println("ZIO CAN SET CONTEXT");
	}

}
