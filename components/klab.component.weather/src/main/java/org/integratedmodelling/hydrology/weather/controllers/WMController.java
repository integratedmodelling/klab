package org.integratedmodelling.hydrology.weather.controllers;

/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.weather.api.API;
import org.integratedmodelling.weather.data.Weather;
import org.integratedmodelling.weather.data.WeatherFactory;
import org.integratedmodelling.weather.data.WeatherStation;
import org.integratedmodelling.weather.rest.Capabilities;
import org.integratedmodelling.weather.rest.Station;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class WMController {

	private static Log logger = LogFactory.getLog(WMController.class);

	@RequestMapping(value = API.WEATHER.CAPABILITIES, method = RequestMethod.GET)
	public Capabilities capabilities() {
		return new Capabilities();
	}

	/**
	 * Return usable data between the passed start and end times. Accommodates
	 * various frequencies defaulting to daily.
	 * 
	 * @param bbox
	 * @param variables
	 * @return
	 */
	@RequestMapping(API.WEATHER.GET_DATA)
	public List<Map<String, Object>> getStationData(@RequestParam(value = "bbox") String bbox,
			@RequestParam(value = "variables", defaultValue = "PRCP,TMAX,TMIN") String variables,
			@RequestParam(value = "start") Long start, @RequestParam(value = "end") Long end,
			@RequestParam(value = "step") Long step,
			@RequestParam(value = "max-years-back", defaultValue = "10") int maxYearsBack,
			@RequestParam(value = "max-nodata-percentage", defaultValue = "23") int maxNodata,
			@RequestParam(value = "source", defaultValue = "ALL") String source) {

		List<Map<String, Object>> ret = new ArrayList<>();

		String[] vars = null;
		if (!variables.isEmpty()) {
			List<String> vv = StringUtils.splitOnCommas(variables);
			vars = vv.toArray(new String[vv.size()]);
		}

		if (!bbox.isEmpty()) {

			double[] coords = StringUtils.splitToDoubles(bbox);
			if (coords.length != 4) {
				throw new KlabValidationException("wrong bbox specification: " + bbox);
			}

			Shape shape = Shape.create(coords[0], coords[1], coords[2], coords[3], Projection.getLatLon());
			Collection<WeatherStation> wss = WeatherFactory.INSTANCE.within(shape, source, vars);
			Weather weather = new Weather(wss, start, end, step, maxYearsBack, vars, maxNodata, true);
			for (Map<String, Object> sd : weather.getStationData()) {
				ret.add(sd);
			}

		}
		return ret;
	}

	@RequestMapping(value = API.WEATHER.UPLOAD, method = RequestMethod.POST)
	public Station handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {

		File out = Configuration.INSTANCE.getDataPath("contrib");
		out.mkdirs();
		out = new File(out + File.separator + name + "." + MiscUtilities.getFileExtension(file.getOriginalFilename()));

		if (!file.isEmpty()) {

			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(out));
				stream.write(bytes);
				stream.close();

			} catch (Exception e) {
				throw new KlabException(e);
			}
		} else {
			throw new KlabException("file attachment was empty");
		}

		if (out.exists()) {
			
			WeatherStation ws = new WeatherStation(name, out);

			WeatherStation existing = WeatherFactory.INSTANCE.getDatabase().retrieve(ws.getId());
			if (existing != null) {
				logger.info("removing previously uploaded station " + ws.getId());
				WeatherFactory.INSTANCE.getDatabase().delete(ws.getId());
			}
			WeatherFactory.INSTANCE.getDatabase().store(ws, /* FIXME session monitor */null);
			logger.info("successfully stored uploaded station " + ws.getId());
			return new Station(ws, true, null, null);

		}

		return null;
	}

	/**
	 * With default parameters, returns the list of stations matching the
	 * requirements with minimal overhead. If include-data is true, also return all
	 * data in the passed range and variables, or all data if not filtered.
	 * 
	 * @param lat @param lon @param range @param bbox @param variables @param
	 *            timeRange @param verbose @return @throws
	 */
	@RequestMapping(value = API.WEATHER.GET_STATIONS, method = RequestMethod.GET)
	public List<Station> getStations(@RequestParam(value = "bbox") String bbox,
			@RequestParam(value = "variables", defaultValue = "") String variables,
			@RequestParam(value = "years", defaultValue = "") String timeRange,
			@RequestParam(value = "include-data", required = false) Boolean includeData,
			@RequestParam(value = "source", defaultValue = "ALL") String source) {

		List<Station> ret = new ArrayList<>();

		String[] vars = null;
		if (!variables.isEmpty()) {
			List<String> vv = StringUtils.splitOnCommas(variables);
			vars = vv.toArray(new String[vv.size()]);
		}

		int[] years = null;
		if (!timeRange.isEmpty()) {
			years = StringUtils.splitToIntegers(timeRange);
		}

		if (!bbox.isEmpty()) {

			double[] coords = StringUtils.splitToDoubles(bbox);
			if (coords.length != 4) {
				throw new KlabException("wrong bbox specification: " + bbox);
			}

			Shape shape = Shape.create(coords[0], coords[1], coords[2], coords[3], Projection.getLatLon());
			for (WeatherStation ws : WeatherFactory.INSTANCE.within(shape, source, vars)) {
				/*
				 * if we have asked for data and specified both variables and years, only
				 * include stations that provide data.
				 */
				Station station = new Station(ws, includeData != null && includeData, vars, years);
				boolean ok = true;
				if (includeData != null && includeData && (years != null || vars != null)) {
					ok = !station.getData().isEmpty();
				}
				if (ok) {
					ret.add(station);
				}
			}

		}

		return ret;
	}

	@RequestMapping(value = API.WEATHER.GET_STATION, method = RequestMethod.GET)
	public Station getStation(@RequestParam(value = "id") String id,
			@RequestParam(value = "include-data", required = false) Boolean includeData,
			@RequestParam(value = "variables", defaultValue = "") String variables,
			@RequestParam(value = "years", defaultValue = "") String timeRange) {

		String[] vars = null;
		if (!variables.isEmpty()) {
			List<String> vv = StringUtils.splitOnCommas(variables);
			vars = vv.toArray(new String[vv.size()]);
		}

		int[] years = null;
		if (!timeRange.isEmpty()) {
			years = StringUtils.splitToIntegers(timeRange);
		}

		WeatherStation ws = WeatherFactory.INSTANCE.getDatabase().retrieve(id);

		return ws == null ? null : new Station(ws, includeData == null ? false : includeData, vars, years);
	}

}
