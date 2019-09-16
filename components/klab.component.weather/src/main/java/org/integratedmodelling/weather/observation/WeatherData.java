package org.integratedmodelling.weather.observation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.ThiessenLocator;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.weather.data.WeatherFactory;
import org.integratedmodelling.weather.data.WeatherStation;
import org.joda.time.DateTime;

/**
 * "Client" weather object to assemble k.LAB observations based on Weather data.
 * 
 * @author ferdinando.villa
 *
 */
public class WeatherData {

	IScale scale;
	List<WeatherStation> stations;
	ThiessenLocator<WeatherStation> locator;
	Map<String, IState> statesForVar = new HashMap<>();
	IState elevation;
	String type;
	String source;
	Map<String, double[]> buffers;

	/*
	 * this is used only when the "representative" weather constructor is used.
	 */
	int currentSimulatedYear = -1;

	enum Reduction {
		RAW, MIN, MAX
	}

	enum Frequency {
		HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY
	}

	/*
	 * structure to hold the observations we want, initialized at
	 * initializeObservables()
	 */
	class Observation {
		public Observation(IObservable observable, String var, Reduction reduction, Time.Frequency frequency,
				IState refstate) {
			this.observable = observable;
			this.var = var;
			this.reduction = reduction;
			this.frequency = frequency;
			this.refstate = refstate;
		}

		IObservable observable;
		String var;
		Reduction reduction;
		Time.Frequency frequency;
		IState refstate;
	}

	/*
	 * initialized before making any observations. Can be redefined at any time.
	 */
	List<Observation> observables = new ArrayList<>();

	/**
	 * Regular weather driver: will use Thiessen polygons and assumes data are raw
	 * and from actual observations. Data may contain no-data; no weather generator
	 * is used.
	 * 
	 * @param scale
	 * @param states
	 * @param stations
	 * @param monitor
	 */
	public WeatherData(IScale scale, IState elevation, Map<String, IState> states, List<WeatherStation> stations,
			IMonitor monitor) {

		Grid grid = Space.extractGrid(scale);

		this.scale = scale;
		this.stations = stations;
		this.elevation = elevation;
		this.statesForVar = states;
		this.locator = new ThiessenLocator<>(scale, stations);

		/*
		 * record adjustment factors for available reference maps in each station
		 */
		for (String var : states.keySet()) {

			for (WeatherStation ws : stations) {
				long ofs = grid.getOffsetFromWorldCoordinates(ws.getLongitude(), ws.getLatitude());
//				if (states.get(var).getValue(ofs) != null && !Double.isNaN(((Number) (states.get(var).getValue(ofs))).doubleValue())) {
//					ws.refData.put(var, ((Number) (state.getValue(ofs))).doubleValue());
//				}
			}
		}
	}

	/**
	 * This one builds a "representative" weather that reflects patterns seen in
	 * this region in the passed year range. Should be passed one or more stations
	 * that have no no-data gaps (it's meant to use CRU stations) and will train a
	 * weather generator to create the observations, which can be asked for at any
	 * time including outside of the requested year.
	 * 
	 * @throws KlabException
	 */
	public WeatherData(IScale scale, int startYear, int endYear, Collection<IState> states,
			List<WeatherStation> stations, IMonitor monitor) throws KlabException {

//		this.scale = scale;
//		this.stations = stations;
//		this.locator = new ThiessenLocator<>(scale, stations);
//
//		for (WeatherStation ws : stations) {
//			ws.train(startYear, endYear);
//		}
//
//		this.type = "SIMULATED";
//
//		/*
//		 * record adjustment factors for available reference maps in each station
//		 */
//		for (IState state : states) {
//
//			if (state.getObservable().getSemantics().is(GeoNS.ELEVATION)) {
//				this.elevation = state;
//				continue;
//			}
//
//			String var = WeatherFactory.getVariableForObservable(state.getObservable().getSemantics());
//			if (var != null) {
//
//				statesForVar.put(var, state);
//
//				for (WeatherStation ws : stations) {
//					int ofs = scale.getSpace().getGrid().getOffsetFromWorldCoordinates(ws.longitude, ws.latitude);
//					if (state.getValue(ofs) != null && !Double.isNaN(((Number) (state.getValue(ofs))).doubleValue())) {
//						ws.refData.put(var, ((Number) (state.getValue(ofs))).doubleValue());
//					}
//				}
//			}
//		}
	}

	/**
	 * Workhorse of simulated weather observations: take data from the
	 * representative station, adjust as necessary based on lat/lon/altitude of
	 * point, and call setValue on the passed state for all values in a temporal
	 * slice. Regenerate the time series any time the year changes.
	 * 
	 * @param time   the time of the observation
	 * @param states alternate variable ID and double[] array matching the spatial
	 *               scale
	 */
	public Map<String, double[]> defineVariables(DateTime time, String... variables) {

//		if (time.getYear() != currentSimulatedYear) {
//			currentSimulatedYear = time.getYear();
//			for (WeatherStation station : stations) {
//				station.generateData(time.getYear(), variables);
//			}
//		}
//
//		if (buffers == null) {
//			buffers = new HashMap<>();
//		}
//
//		for (String var : variables) {
//			if (!buffers.containsKey(var)) {
//				buffers.put(var, new double[(int) scale.getSpace().getMultiplicity()]);
//			}
//		}
//
//		/*
//		 * determine offset for passed day vs. start of period
//		 */
//		int ofs = time.getDayOfYear();
//
//		/*
//		 * TODO check if this was returned before and just get cached data if so.
//		 */
//
//		for (String var : variables) {
//
//			double[] state = buffers.get(var);
//
//			for (int n : scale.getIndex(IScale.Locator.INITIALIZATION)) {
//
//				int spaceOffset = scale.getExtentOffset(scale.getSpace(), n);
//
//				WeatherStation representativeStation = locator.get(spaceOffset);
//				double data = Double.NaN;
//
//				if (representativeStation != null) {
//
//					data = representativeStation.getData().get(var)[ofs];
//
//					if (elevation != null && var.equals(WeatherFactory.MAX_TEMPERATURE_C)
//							|| var.equals(WeatherFactory.MIN_TEMPERATURE_C)) {
//
//						double refvalue = States.getDouble(elevation, spaceOffset);
//						if (!Double.isNaN(refvalue)) {
//							data += ((representativeStation.elevation - refvalue) * 6.4 / 1000.0);
//						}
//					} else if (representativeStation.refData.containsKey(var)) {
//						IState refstate = statesForVar.get(var);
//						double refvalue = States.getDouble(refstate, spaceOffset);
//						if (!Double.isNaN(refvalue)) {
//							data = (data * refvalue) / representativeStation.refData.get(var);
//						}
//					}
//				}
//
//				state[n] = data;
//			}
//		}

		return buffers;
	}

	/**
	 * Workhorse of weather observations: take data from the representative station,
	 * adjust as necessary based on lat/lon/altitude of point, and call setValue on
	 * the passed state for all values in a temporal slice.
	 * 
	 * @param state
	 * @param transition
	 * @throws KlabIOException
	 */
	public void defineState(IState state /* , @Nullable ITransition transition */) throws KlabIOException {

//		String var = WeatherFactory.getVariableForObservable(state.getObservable().getSemantics());
//
//		if (var == null) {
//			throw new KlabIOException(
//					"observable " + state.getObservable().getType() + " cannot be mapped to weather data");
//		}
//
//		for (int n : scale.getIndex(transition)) {
//
//			int spaceOffset = scale.getExtentOffset(scale.getSpace(), n);
//
//			WeatherStation representativeStation = locator.get(spaceOffset);
//			double data = Double.NaN;
//
//			if (representativeStation != null) {
//
//				data = representativeStation.getData().get(var)[transition == null ? 0 : transition.getTimeIndex()];
//
//				if (elevation != null && (var.equals(WeatherFactory.MAX_TEMPERATURE_C)
//						|| var.equals(WeatherFactory.MIN_TEMPERATURE_C))) {
//
//					double refvalue = States.getDouble(elevation, spaceOffset);
//					if (!Double.isNaN(data) && !Double.isNaN(refvalue)) {
//						if (refvalue < 0) {
//							/*
//							 * happens, although it shouldn't - maybe we should warn as typically it's a
//							 * missing nodata spec.
//							 */
//							refvalue = 0;
//						}
//						data += (representativeStation.elevation - refvalue) * 6.4 / 1000.0;
//					}
//				} else if (representativeStation.refData.containsKey(var)) {
//
//					IState refstate = statesForVar.get(var);
//					double refvalue = States.getDouble(refstate, spaceOffset);
//					double refdata = representativeStation.refData.get(var);
//					if (!Double.isNaN(refvalue) && !Double.isNaN(refdata)) {
//						data = (data * refvalue) / (refdata <= 0 ? 1 : refdata);
//					}
//				}
//			}
//
//			States.set(state, data, n);
//		}
	}

}
