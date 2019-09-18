package org.integratedmodelling.weather.data;

import java.text.NumberFormat;
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
import org.integratedmodelling.klab.data.table.persistent.PersistentTable;
import org.integratedmodelling.klab.data.table.persistent.PersistentTable.PersistentTableBuilder;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.weather.WeatherComponent;
import org.joda.time.DateTime;

import com.vividsolutions.jts.geom.Geometry;

import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public enum WeatherEvents {

	INSTANCE;

	public final static String TRMM_EVENTS_LOCATION = "trmm.catalog.location";
	public final static String TRMM_PRECIPITATION_THRESHOLD = "trmm.precipitation.threshold";

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

		int lower = 0;
		int higher = 0;

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

//			Range x = new Range();
//			Range y = new Range();
//			Range t = new Range();
//			Range p = new Range();
//			Range s = new Range();

			DateTime start = new DateTime(1998, 1, 1, 0, 0);

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

				Map<String, Object> data = new HashMap<>();
				data.put("bounding_box", boundingBox);
				data.put("precipitation_mm", mmPerHour);
				data.put("area_m2", spacespanv);
				data.put("start_long", startTimeMs);
				data.put("end_long", startTimeMs + durationMs);
				data.put("duration_hours", (long) timespanv);
				data.put("id", (long) n);

				WeatherEvent event = new WeatherEvent(data);
				ebox.store(event, monitor);

				System.out.println("#" + used + "/" + n + ": event at " + longitude + "," + latitude + " went from "
						+ new Date(startTimeMs) + " to " + new Date(startTimeMs + durationMs) + " dropping "
						+ totalprecipv + " mm of shit" + " over "
						+ NumberFormat.getInstance().format(spacespanv / 1000000.0) + "km2.\nBounding box is "
						+ boundingBox);

//				
//				
//				x.include(xminv);
//				x.include(xmaxv);
//				y.include(yminv);
//				y.include(ymaxv);
//				t.include(tstartv);
//				s.include(timespanv);
//				p.include(totalprecipv);
//
//				if (n % 100000 == 0) {
//					System.out.println("Done " + n);
//					System.out.println("x: " + x + "; y = " + y + "; t = " + t + "; s = " + s + "; p = " + p);
//				}
			}

//			System.out.println("FINISHED\n");
//			System.out.println("FINAL x: " + x + "; y = " + y + "; t = " + t + "; s = " + s + "; p = " + p);

		} catch (Throwable t) {
			throw new KlabIOException(t);
		}

		System.out.println("GOT " + lower + " rain events and " + higher + " flood events");
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

	public static void main(String[] args) {
		INSTANCE.setup();
	}

	/**
	 * For now, substitute 2014 to any year after it, and 1998 to any year before
	 * it.
	 * 
	 * @param scale
	 * @return
	 */
	public Iterable<WeatherEvent> getEvents(IScale scale) {

		// space

		// time

		return ebox.query("");
	}

}
