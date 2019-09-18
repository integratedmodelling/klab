package org.integratedmodelling.weather.data;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.utils.Parameters;

import com.vividsolutions.jts.geom.Geometry;

public class WeatherEvent {

	long id;
	Geometry boundingBox;
	long startMs;
	long endMs;
	int durationHours;
	double areaMd;
	double precMmHour;
	
	
	public WeatherEvent(Map<String, Object> data) {
		Parameters<String> d = Parameters.wrap(data);
		this.areaMd = d.get("area_m2", Double.class);
		this.startMs = d.get("start_long", Long.class);
		this.endMs = d.get("end_long", Long.class);
		this.precMmHour = d.get("precipitation_mm", Double.class);
		this.durationHours =  d.get("duration_hours", Integer.class);
		this.id = d.get("id", Long.class);
	}

	public Map<String, Object> asData() {
		Map<String, Object> ret = new HashMap<>();
		ret.put("area_m2", this.areaMd);
		ret.put("start_long", this.startMs);
		ret.put("end_long", this.endMs);
		ret.put("precipitation_mm", this.precMmHour);
		ret.put("duration_hours", this.durationHours);
		ret.put("id", this.id);
		return ret;
	}

	public Long getId() {
		return id;
	}

}
