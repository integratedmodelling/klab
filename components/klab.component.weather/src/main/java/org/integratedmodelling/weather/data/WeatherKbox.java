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
package org.integratedmodelling.weather.data;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.persistence.h2.H2Database;
import org.integratedmodelling.klab.persistence.h2.SQL;

// it's just a DBTable - finish that
public enum WeatherKbox {

	INSTANCE;
	
	private H2Database database;

	private WeatherKbox(/* String name, IMonitor monitor */) {
//		setSerializer(WeatherStation.class, new WeatherSerializer());
//		setDeserializer(WeatherStation.class, new WeatherDeserializer());
//		setSchema(WeatherStation.class, new WeatherSchema(WeatherStation.class));
//		setQueryBuilder(new WeatherQueryBuilder());
	}

	
	/*
	 * exposed to allow preallocating connections in big imports.
	 */
	public H2Database getDatabase() {
		return this.database;
	}

	/**
	 * Get weather station in the spatial extent provided. Expand the bounding box
	 * by the spatial extent provided (if 0, leave it alone and use the actual
	 * geometry; if >0, use the bounding box and look as many times around in lenght
	 * and width). If variables are passed, return only stations that provide them.
	 *
	 * @param space
	 * @param expandFactor
	 * @param variables
	 * @return
	 * @throws ThinklabException
	 */
	public List<WeatherStation> getStationsAround(ISpace space, double expandFactor, String source, String... variables)
			throws KlabException {

		Shape shape = (Shape) space.getShape();
		if (expandFactor > 0) {
			/*
			 * TODO change shape to extended shape
			 */
		}
		return within(shape, source, variables);
	}

	public List<WeatherStation> around(double lat, double lon, String source, String... variables)
			throws KlabException {

		Shape shape = Shape.create(lon, lat, Projection.getLatLon());
		// NO! FIXME! use isWithinDistance(p1, p2, distance (back in degrees?))
		return within(shape, source, variables);
	}

	/**
	 * Return all weather stations in the passed geometry (using the intersect
	 * operator), optionally restricting to those providing the variables passed.
	 * 
	 * @param context
	 * @param source    pass "ALL" or null for everything or a specific source type
	 *                  (currently GHCND, CRU, USER).
	 * @param variables
	 * @return
	 * @throws ThinklabException
	 */
	public List<WeatherStation> within(IShape context, String source, String... variables) throws KlabException {

		final List<WeatherStation> ret = new ArrayList<>();

		if (!database.hasTable("weatherstations")) {
			return ret;
		}

		String query = "SELECT * FROM weatherstations WHERE location && '" + ((Shape) context).getStandardizedGeometry()
				+ "'";
		if (source != null && !source.equals("ALL")) {
			switch (source) {
			case "CRU":
				query += " AND id LIKE 'CRU_%'";
				break;
			case "USER":
				query += " AND id LIKE 'USER_%'";
				break;
			default:
				query += " AND id NOT LIKE 'USER_%' AND id NOT LIKE 'CRU_%'";
				break;
			}
		}
		if (variables != null) {
			for (String v : variables) {
				query += " AND provided_vars LIKE '%" + v + "%'";
			}
		}
		query += ";";

		for (WeatherStation ws : database.query(query, WeatherStation.class)) {
			ret.add(ws);
		}

		return ret;
	}

	public WeatherStation retrieve(String id) throws KlabException {

		if (!database.hasTable("weatherstations")) {
			return null;
		}

		WeatherStation ret = null;

		String query = "SELECT * FROM weatherstations WHERE id = '" + id + "';";

		for (WeatherStation ws : database.query(query, WeatherStation.class)) {
			ret = ws;
		}

		return ret;
	}

	public void store(WeatherStation station) {
		// TODO
	}
	
	/**
	 * Count the weather stations in the db.
	 * 
	 * @return
	 * @throws ThinklabException
	 */
	public long count() throws KlabException {

		if (!database.hasTable("weatherstations")) {
			return 0;
		}

		/*
		 * ch'agg'a fa'.
		 */
		final List<Long> ret = new ArrayList<>();

		database.query("SELECT COUNT(*) from weatherstations;", new SQL.SimpleResultHandler() {
			@Override
			public void onRow(ResultSet rs) {
				try {
					ret.add(rs.getLong(1));
				} catch (SQLException e) {
				}
			}
		});

		return ret.size() > 0 ? ret.get(0) : 0l;
	}

	class WeatherSerializer {

		public String serialize(Object o, /* Schema schema, */ long primaryKey, long foreignKey) {

			if (!(o instanceof WeatherStation) /* || !(schema instanceof WeatherSchema) */) {
				throw new KlabException("WeatherKbox can only store weather stations: " + o.getClass().getSimpleName());
			}

			WeatherStation ws = (WeatherStation) o;
			String[] provided = ws.getProvidedVarsDescriptors();

			return "INSERT INTO weatherstations VALUES (" + primaryKey + ", " + "'" + ws.getId() + "', " + ws._altitude
					+ ", " + ws._latitude + ", " + ws._longitude + ", " + "'" + provided[0] + "', " + "'" + provided[1]
					+ "', " + "'" + provided[2] + "', " + "'" + ws._location.getGeometry() + "'" + ");";
		}
	}

	class WeatherDeserializer {

		public Object deserialize(ResultSet rs) {

			WeatherStation ret = null;

			try {
				// long oid = rs.getLong(1);
				String id = rs.getString(2);
				double elevation = rs.getDouble(3);
				double latitude = rs.getDouble(4);
				double longitude = rs.getDouble(5);
				String pvar = rs.getString(6);
				String psta = rs.getString(7);
				String pend = rs.getString(8);

				ret = new WeatherStation(WeatherFactory.baseURL, id, latitude, longitude, elevation);
				ret.parseVarsDescriptors(pvar, psta, pend);

				if (id.startsWith("CRU_")) {
					ret._type = "INTERPOLATED";
					ret._source = "CRU";
				}

			} catch (SQLException e) {
				throw new KlabException(e);
			}

			return ret;
		}
	}

	class WeatherSchema {

		public String getCreateSQL() {
			return "CREATE TABLE weatherstations (" + "oid LONG, " + "id VARCHAR(32) PRIMARY KEY, "
					+ "elevation DOUBLE, " + "latitude DOUBLE, " + "longitude DOUBLE, "
					+ "provided_vars VARCHAR(1024), " + "provided_start VARCHAR(1024), "
					+ "provided_end VARCHAR(1024), " + "location GEOMETRY" + "); "
					+ "CREATE INDEX oid_index ON weatherstations(oid); "
					+ "CREATE SPATIAL INDEX weather_location ON weatherstations(location);";
		}

		public String getTableName() {
			return "weatherstations";
		}

	}

//	public WeatherKbox(String name, File directory, IMonitor monitor) {
//		setSerializer(WeatherStation.class, new WeatherSerializer());
//		setDeserializer(WeatherStation.class, new WeatherDeserializer());
//		setSchema(WeatherStation.class, new WeatherSchema(WeatherStation.class));
//		setQueryBuilder(new WeatherQueryBuilder());
//	}

	public void remove(String id) throws KlabException {
		database.execute("DELETE FROM weatherstations WHERE id = '" + id + "'");
	}

}
