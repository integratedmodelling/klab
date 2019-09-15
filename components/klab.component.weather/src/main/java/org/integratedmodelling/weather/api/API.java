package org.integratedmodelling.weather.api;

public interface API extends org.integratedmodelling.klab.api.API {

	interface WEATHER {

		public static final String CAPABILITIES = "/weather/capabilities";
		public static final String GET_DATA = "/weather/get-data";
		public static final String GET_STATIONS = "/weather/get-stations";
		public static final String GET_STATION = "/weather/get-station";
		public static final String UPLOAD = "/weather/upload";

	}
}
