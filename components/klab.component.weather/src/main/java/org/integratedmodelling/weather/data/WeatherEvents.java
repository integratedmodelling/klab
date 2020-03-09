package org.integratedmodelling.weather.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.DataType;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.table.persistent.PersistentTable;
import org.integratedmodelling.klab.data.table.persistent.PersistentTable.PersistentTableBuilder;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.URLUtils;
import org.integratedmodelling.weather.WeatherComponent;
import org.joda.time.DateTime;

import com.vividsolutions.jts.geom.Geometry;

import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public enum WeatherEvents {

	INSTANCE;

	static private String[] trmm_urls = new String[] {
			"http://www.integratedmodelling.org/downloads/All_Precip_1998-2014_TRMM_Standard.nc" };

	public final static String TRMM_EVENTS_LOCATION = "trmm.catalog.location";
	public final static String TRMM_PRECIPITATION_THRESHOLD = "trmm.precipitation.threshold";

	public final static String TRMM_FIRSTEVENT_PROPERTY = "trmm.eventtime.first";
	public final static String TRMM_LASTEVENT_PROPERTY = "trmm.eventtime.last";

	DateTime trmmStart = new DateTime(1998, 1, 1, 0, 0, 0);
	DateTime trmmEnd = new DateTime(2015, 1, 1, 0, 0, 0);
	Range eventRange = null;

	/*
	 * the DB
	 */
	PersistentTable<Long, WeatherEvent> ebox;

	private WeatherEvents() {
		ebox = new PersistentTableBuilder<Long, WeatherEvent>("weatherevents", Long.class, WeatherEvent.class)
				.column("precipitation_mm", DataType.DOUBLE, true).column("id", DataType.LONG, true)
				.column("area_m2", DataType.DOUBLE).column("start_long", DataType.LONG, true)
				.column("end_long", DataType.LONG, true).column("duration_hours", DataType.INT)
				.column("bounding_box", DataType.SHAPE, true).build((event) -> {
					return event.asData();
				}, (data) -> {
					return new WeatherEvent(data);
				}, (event) -> {
					return event.getId();
				});
	}

	public void setup() {

		if (ebox.count() > 1000000) {

			Logging.INSTANCE.info("Weather event kbox is initialized.");

		} else {

			Properties properties = Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID);
			if (properties.containsKey(TRMM_EVENTS_LOCATION)) {
				Logging.INSTANCE.info("Initializing weather event kbox from local file.");
				setup(properties.getProperty(TRMM_EVENTS_LOCATION),
						Double.parseDouble(properties.getProperty(TRMM_PRECIPITATION_THRESHOLD, "0.1")), null);
			} else if (retrieveEventDatabase()) {
				Logging.INSTANCE.info("Initializing weather event kbox from downloaded file.");
				setup(properties.getProperty(TRMM_EVENTS_LOCATION),
						Double.parseDouble(properties.getProperty(TRMM_PRECIPITATION_THRESHOLD, "0.1")), null);
			} else {
				Logging.INSTANCE.info("Weather event kbox not initialized for lack of source data.");
			}
		}

	}

	/**
	 * Preprocessed events from White, Battisti & Skok dataset processed from TRRM
	 * data, limited to 1998-2014 at .25 deg resolution.
	 * <p>
	 * Time span is integer 3-h increments starting from Jan 1st, 1998.
	 * 
	 * <pre>
	 * Drizzle, very small droplets.
	 * Slight (fine) drizzle: Detectable as droplets only on the face, car windscreens and windows.
	 * Moderate drizzle: Windows and other surfaces stream with water.
	 * Heavy (thick) drizzle: Impairs visibility and is measurable in a raingauge, rates up to 1 mm per hour.
	 * Rain, drops of appreciable size and may be described as small to large drops. It is possible to have rain drops within drizzle!
	 * Slight rain: Less than 0.5 mm per hour.
	 * Moderate rain: Greater than 0.5 mm per hour, but less than 4.0 mm per hour.
	 * Heavy rain: Greater than 4 mm per hour, but less than 8 mm per hour.
	 * Very heavy rain: Greater than 8 mm per hour.
	 * Slight shower: Less than 2 mm per hour.
	 * Moderate shower: Greater than 2 mm, but less than 10 mm per hour.
	 * Heavy shower: Greater than 10 mm per hour, but less than 50 mm per hour.
	 * Violent shower: Greater than 50 mm per hour.
	 * </pre>
	 * 
	 * Suggest: .1 mm for base threshold, use 1mm for flood.
	 */
	public void setup(String url, double threshold, IMonitor monitor) {

//		int lower = 0;
//		int higher = 0;

		/*
		 * Open the THREDDS fucker. It's got 65 million events.
		 */
		try (NetcdfFile ncfile = NetcdfFile.open(url)) {

			Dimension events = ncfile.findDimension("events");
			Variable totalprecip = ncfile.findVariable("totalprecip");
			Variable tstart = ncfile.findVariable("tstart");
			Variable timespan = ncfile.findVariable("timespan");
			Variable spacespan = ncfile.findVariable("uniquegridboxspanSA");
			Variable xmin = ncfile.findVariable("xmin");
			Variable xmax = ncfile.findVariable("xmax");
			Variable ymin = ncfile.findVariable("ymin");
			Variable ymax = ncfile.findVariable("ymax");
			Variable xcenter = ncfile.findVariable("xcentermean");
			Variable ycenter = ncfile.findVariable("ycentermean");

			DateTime start = new DateTime(1998, 1, 1, 0, 0);

			long firstEvent = 0;
			long lastEvent = 0;

			/*
			 * Loop; one event per 'row'
			 */
			int used = 0;

			for (int n = 0; n < events.getLength(); n++) {

				float totalprecipv = totalprecip.read(new int[] { n }, new int[] { 1 }).getFloat(0);
				float timespanv = timespan.read(new int[] { n }, new int[] { 1 }).getFloat(0);

				double mmPerHour = totalprecipv / timespanv;

				if (mmPerHour < threshold) {
					continue;
				}

				used++;

				float tstartv = tstart.read(new int[] { n }, new int[] { 1 }).getFloat(0);
				float spacespanv = spacespan.read(new int[] { n }, new int[] { 1 }).getFloat(0);
				float xminv = xmin.read(new int[] { n }, new int[] { 1 }).getFloat(0);
				float xmaxv = xmax.read(new int[] { n }, new int[] { 1 }).getFloat(0);
				float yminv = ymin.read(new int[] { n }, new int[] { 1 }).getFloat(0);
				float ymaxv = ymax.read(new int[] { n }, new int[] { 1 }).getFloat(0);
				float xcenterv = xcenter.read(new int[] { n }, new int[] { 1 }).getFloat(0);
				float ycenterv = ycenter.read(new int[] { n }, new int[] { 1 }).getFloat(0);

				double longitude = toLongitude(xcenterv, CENTER);
				double latitude = toLatitude(ycenterv, CENTER);

				double lonMin = toLongitude(xminv, MIN);
				double latMin = toLatitude(yminv, MIN);
				double lonMax = toLongitude(xmaxv, MAX);
				double latMax = toLatitude(ymaxv, MAX);

				Geometry boundingBox = Envelope.create(lonMin, lonMax, latMin, latMax, Projection.getLatLon()).asShape()
						.getStandardizedGeometry();

				long startTimeMs = start.getMillis() + ((long) tstartv * 3 * 60 * 60 * 1000);
				long durationMs = (long) timespanv * 60 * 60 * 1000;

				if (firstEvent == 0 || firstEvent > startTimeMs) {
					firstEvent = startTimeMs;
				}
				if (lastEvent == 0 || lastEvent < startTimeMs) {
					lastEvent = startTimeMs;
				}

				Map<String, Object> data = new HashMap<>();
				data.put(WeatherEvent.BOUNDING_BOX, boundingBox);
				data.put(WeatherEvent.PRECIPITATION_MM, mmPerHour);
				data.put(WeatherEvent.AREA_M2, spacespanv);
				data.put(WeatherEvent.START_LONG, startTimeMs);
				data.put(WeatherEvent.END_LONG, startTimeMs + durationMs);
				data.put(WeatherEvent.DURATION_HOURS, (long) timespanv);
				data.put(WeatherEvent.ID, (long) n);

				WeatherEvent event = new WeatherEvent(data);
				ebox.store(event, monitor);

				System.out.println("#" + used + "/" + n + ": event at " + longitude + "," + latitude + " went from "
						+ new Date(startTimeMs) + " to " + new Date(startTimeMs + durationMs) + " dropping "
						+ totalprecipv + " mm of precipitation" + " over "
						+ NumberFormat.getInstance().format(spacespanv / 1000000.0) + "km2.\nBounding box is "
						+ boundingBox);
			}

			// record first and last event time to adjust for sloppy queries
			Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID).setProperty(TRMM_FIRSTEVENT_PROPERTY,
					firstEvent + "");
			Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID).setProperty(TRMM_LASTEVENT_PROPERTY,
					lastEvent + "");
			Extensions.INSTANCE.saveComponentProperties(WeatherComponent.ID);

		} catch (Throwable t) {
			throw new KlabIOException(t);
		}

//		System.out.println("GOT " + lower + " rain events and " + higher + " flood events");
	}

	private final static int MIN = 0;
	private final static int MAX = 1;
	private final static int CENTER = 2;

	private double toLongitude(float box, int where) {
		// box is [0, 1439]: 4 * -180 to +180
		double min = (box / 1440.0) * 360.0 - 180.0;
		switch (where) {
		case MAX:
			return min + 0.25;
		case CENTER:
			return min + (0.25 / 2.0);
		}
		return min;
	}

	private double toLatitude(float box, int where) {
		// box is [0, 399] 4 * -50 to + 50
		double min = (box / 400.0) * 180.0 - 90.0;
		switch (where) {
		case MAX:
			return min + 0.25;
		case CENTER:
			return min + (0.25 / 2.0);
		}
		return min;

	}

	/**
	 * Try getting the events file and putting it somewhere so we don't need to set
	 * up from the outside.
	 * 
	 * @return
	 */
	public static boolean retrieveEventDatabase() {

		boolean ret = false;
		for (String url : trmm_urls) {
			try {
				File output = File.createTempFile("trmm", ".nc");
				Logging.INSTANCE.info("Attempting retrieval of ~5GB source data from " + url + " into " + output);
				Logging.INSTANCE.info("Expect tens of minutes of wait or more. Interrupting the download will"
						+ " cause trouble unless the output file is cleaned.");
				URLUtils.copyChanneled(new URL(url), output);
				Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID).setProperty(TRMM_EVENTS_LOCATION,
						output.toURI().toURL().toString());
				Extensions.INSTANCE.saveComponentProperties(WeatherComponent.ID);
				Logging.INSTANCE.info("Data retrieval successful");
				ret = true;
				break;
			} catch (IOException e) {
			}
		}

		return ret;
	}

	public static void main(String[] args) {
		INSTANCE.setup();
	}

	/**
	 * For now, substitute 2014 to any year after it, and 1998 to any year before,
	 * and adjust intervals so that the same season is covered in the closest year.
	 * 
	 * @param scale
	 * @return
	 */
	public Iterable<WeatherEvent> getEvents(IScale scale, IMonitor monitor) {
		return getEvents(scale, Double.NaN, true, monitor);
	}

	/**
	 * Temporal range of event start in milliseconds
	 * 
	 * @return
	 */
	public Range getEventRange() {
		if (this.eventRange == null) {
			long start = Long.parseLong(Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID)
					.getProperty(TRMM_FIRSTEVENT_PROPERTY, "0"));
			long end = Long.parseLong(Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID)
					.getProperty(TRMM_LASTEVENT_PROPERTY, "0"));
			this.eventRange = new Range((double) start, (double) end, false, false);
		}
		return this.eventRange;
	}

	/**
	 * Get all weather events for a particular space and time. If we have no data
	 * coverage for the intended period, at least for now, substitute the events in
	 * the closest year ensuring that the same seasonal sequence is covered.
	 * <p>
	 * TODO pass a parameter (from the URN, defaulting at true) to enable adjusting
	 * the timestamps in the events to the requested time in case substitutions were
	 * made. This should be used by time-bound event contextualizers as the
	 * timestamps will be used to schedule each event and resolve it.
	 * <p>
	 * 
	 * @param scale
	 * @param minPrecipitation pass the minimum precipitation in mm per event, or
	 *                         NaN for any accepted.
	 * @return
	 */
	public Iterable<WeatherEvent> getEvents(IScale scale, double minPrecipitation, boolean adjustDates,
			IMonitor monitor) {

		if (scale.getSpace() == null || scale.getTime() == null) {
			return new ArrayList<>();
		}

		Geometry shape = ((Shape) scale.getSpace().getShape()).getStandardizedGeometry();
		TimeInstant start = (TimeInstant) scale.getTime().getStart();
		TimeInstant end = (TimeInstant) scale.getTime().getEnd();

		if (start == null || end == null || start.equals(end)) {
			return new ArrayList<>();
		}

		long startTime = start.getMilliseconds();
		long endTime = end.getMilliseconds();

		/*
		 * adjust if needed
		 */
		if (!getEventRange().contains(startTime) || !getEventRange().contains(endTime)) {
			// find the closest year in series and get that same span starting the same
			// Julian day
			long span = endTime - startTime;
			if (span > getEventRange().getWidth()) {
				// sorry, not enough observations
				return new ArrayList<>();
			}
			// unadjusted period to fit the span. We can only move backwards from here.
			TimeInstant startDate = new TimeInstant((long) getEventRange().getUpperBound() - (endTime - startTime));

			// year we must actually start from
			int startYear = startDate.asDate().getYear();

			// this is the day in the year we have to start from
			int startDay = start.getDayOfYear();
			// corresponding to the n-th year in the span, which we adjust backwards if
			// we're beyond
			if (startDay > startDate.getDayOfYear()) {
				startYear--;
			}

			// compute the new start
			DateTime newstart = new DateTime(startYear, 1, 1, 0, 0, 0).plusDays(startDay)
					.plusHours(start.asDate().getHourOfDay()).plusMinutes(start.asDate().getMinuteOfDay())
					.plusSeconds(start.asDate().getSecondOfDay());

			// new start goes back this number of days
			startTime = newstart.getMillis();
			endTime = startTime + span;

			// TODO this should go to the notifications, I guess through a specialized
			// adapter monitor passed upstream.
			Logging.INSTANCE.warn("storm events database: original range " + start + " to " + end + " adjusted to "
					+ new TimeInstant(startTime) + " to " + new TimeInstant(endTime)
					+ " to ensure coverage in timeseries");
		}

		String precQuery = "";
		if (!Double.isNaN(minPrecipitation)) {
			precQuery = " AND precipitation_mm >= " + minPrecipitation;
		}

		String query = "SELECT * from " + ebox.getName() + " WHERE " + "bounding_box && '" + shape + "'" + precQuery
				+ " AND ((" + (long) startTime + " BETWEEN start_long AND end_long) OR (" + (long) endTime
				+ "  BETWEEN start_long AND end_long));";

		return ebox.query(query + ";");
	}

	public boolean isOnline() {
		return ebox.count() > 100000;
	}

	public long getEventsCount() {
		return ebox.count();
	}

}
