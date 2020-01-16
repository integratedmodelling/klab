package org.integratedmodelling.weather.observation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.ThiessenLocator;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.weather.data.Weather;
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
	public WeatherData(IScale scale, ILocator locator, IState elevation, Map<String, IState> states,
			List<WeatherStation> stations, IMonitor monitor) {

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

			IState state = states.get(var);

			for (WeatherStation ws : stations) {
				ILocator ofs = grid.getCellAt(new double[] { ws.getLongitude(), ws.getLatitude() }, true);
				double sval = state.get(ofs, Double.class);
				if (!Double.isNaN(sval)) {
					ws.refData.put(var, sval);
				}
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
	public WeatherData(IScale scale, int startYear, int endYear, Map<String, IState> states,
			List<WeatherStation> stations, IMonitor monitor) throws KlabException {

		Grid grid = Space.extractGrid(scale);

		this.scale = scale;
		this.stations = stations;
		this.locator = new ThiessenLocator<>(scale, stations);

		for (WeatherStation ws : stations) {
			ws.train(startYear, endYear);
		}

		this.type = "SIMULATED";

		/*
		 * record adjustment factors for available reference maps in each station
		 */
		for (String var : states.keySet()) {

			IState state = states.get(var);

			for (WeatherStation ws : stations) {
				ILocator ofs = grid.getCellAt(new double[] { ws.getLongitude(), ws.getLatitude() }, true);
				double sval = state.get(ofs, Double.class);
				if (!Double.isNaN(sval)) {
					ws.refData.put(var, sval);
				}
			}
		}
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

		if (time.getYear() != currentSimulatedYear) {
			currentSimulatedYear = time.getYear();
			for (WeatherStation station : stations) {
				station.generateData(time.getYear(), variables);
			}
		}

		if (buffers == null) {
			buffers = new HashMap<>();
		}

		for (String var : variables) {
			if (!buffers.containsKey(var)) {
				buffers.put(var, new double[(int) scale.getSpace().size()]);
			}
		}

		/*
		 * determine offset for passed day vs. start of period
		 */
		int ofs = time.getDayOfYear();

		/*
		 * TODO check if this was returned before and just get cached data if so.
		 */

		for (String var : variables) {

			// TODO use (FLOAT) storage, not arrays
			double[] state = buffers.get(var);

			for (ILocator loc : scale) {

				Offset offset = loc.as(Offset.class);
				WeatherStation representativeStation = locator.get(offset.linear);
				double data = Double.NaN;

				if (representativeStation != null) {

//					data = representativeStation.getData().get(var)[ofs];

					if (elevation != null && var.equals(Weather.MAX_TEMPERATURE_C)
							|| var.equals(Weather.MIN_TEMPERATURE_C)) {

						double refvalue = elevation.get(loc, Double.class);
						if (!Double.isNaN(refvalue)) {
							data += ((representativeStation.getElevation() - refvalue) * 6.4 / 1000.0);
						}
					} else if (representativeStation.refData.containsKey(var)) {
						IState refstate = statesForVar.get(var);
						double refvalue = refstate.get(loc, Double.class);
						if (!Double.isNaN(refvalue)) {
							data = (data * refvalue) / representativeStation.refData.get(var);
						}
					}
				}

				state[(int) offset.linear] = data;
			}
		}

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
	public void defineState(String var, IState state) throws KlabIOException {

		for (ILocator loc : scale) {

			Offset offset = loc.as(Offset.class);

			WeatherStation representativeStation = locator.get(offset.linear);
			double data = Double.NaN;

			if (representativeStation != null) {

//				data = representativeStation.getData().get(var)[transition == null ? 0 : transition.getTimeIndex()];

				if (elevation != null
						&& (var.equals(Weather.MAX_TEMPERATURE_C) || var.equals(Weather.MIN_TEMPERATURE_C))) {

					double refvalue = elevation.get(loc, Double.class);
					if (!Double.isNaN(data) && !Double.isNaN(refvalue)) {
						if (refvalue < 0) {
							/*
							 * happens, although it shouldn't - maybe we should warn as typically it's a
							 * missing nodata spec.
							 */
							refvalue = 0;
						}
						data += (representativeStation.getElevation() - refvalue) * 6.4 / 1000.0;
					}
				} else if (representativeStation.refData.containsKey(var)) {

					IState refstate = statesForVar.get(var);
					double refvalue = refstate.get(loc, Double.class);
					double refdata = representativeStation.refData.get(var);
					if (!Double.isNaN(refvalue) && !Double.isNaN(refdata)) {
						data = (data * refvalue) / (refdata <= 0 ? 1 : refdata);
					}
				}
			}

			state.set(loc, data);
		}
	}

}
