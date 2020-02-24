package org.integratedmodelling.weather.data;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.utils.Parameters;

import com.vividsolutions.jts.geom.Geometry;

public class WeatherEvent {

	public static final String BOUNDING_BOX = "bounding_box";
	public static final String ID = "id";
	public static final String DURATION_HOURS = "duration_hours";
	public static final String PRECIPITATION_MM = "precipitation_mm";
	public static final String END_LONG = "end_long";
	public static final String START_LONG = "start_long";
	public static final String AREA_M2 = "area_m2";

	long id;
	Geometry boundingBox;
	long startMs;
	long endMs;
	int durationHours;
	double areaMd;
	double precMmHour;

	public WeatherEvent(Map<String, Object> data) {
		Parameters<String> d = Parameters.wrap(data);
		this.areaMd = d.get(AREA_M2, Double.class);
		this.startMs = d.get(START_LONG, Long.class);
		this.endMs = d.get(END_LONG, Long.class);
		this.precMmHour = d.getNotNull(PRECIPITATION_MM, Double.class);
		this.durationHours = d.getNotNull(DURATION_HOURS, Integer.class);
		this.boundingBox = d.get(BOUNDING_BOX, Geometry.class);
		this.id = d.get(ID, Long.class);
	}

	public Map<String, Object> asData() {
		Map<String, Object> ret = new HashMap<>();
		ret.put(AREA_M2, this.areaMd);
		ret.put(START_LONG, this.startMs);
		ret.put(END_LONG, this.endMs);
		ret.put(PRECIPITATION_MM, this.precMmHour);
		ret.put(DURATION_HOURS, this.durationHours);
		ret.put(ID, this.id);
		ret.put(BOUNDING_BOX, this.boundingBox);
		return ret;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "WeatherEvent [id=" + id + ", boundingBox=" + boundingBox + ", startMs=" + startMs + ", endMs=" + endMs
				+ ", durationHours=" + durationHours + ", areaMd=" + areaMd + ", precMmHour=" + precMmHour + "]";
	}
	
}
