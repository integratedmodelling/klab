/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.persistence.h2;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import javax.sql.PooledConnection;

import org.h2.jdbcx.JdbcDataSource;
import org.h2gis.h2spatial.CreateSpatialExtension;
import org.h2gis.utilities.SFSUtilities;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Structure;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.persistence.h2.H2Kbox.Schema;
import org.integratedmodelling.klab.persistence.h2.H2Kbox.Serializer;
import org.integratedmodelling.klab.utils.Pair;

/**
 * A wrapper to simplify the use of a H2 database. Can be used with formally
 * specified schemata for multiple tables (rather obsoleted in design) or with
 * newer {@link ITable} interface when the structure is simple. The kboxes use
 * the old structure, so that will remain until we reimplement them.
 * 
 * @author Ferd
 *
 */
public class H2Database {

	private static Map<String, H2Database> datastores = new HashMap<>();

	JdbcDataSource ds;
	String url;
	boolean isNew = false;
	AtomicLong oid = new AtomicLong(1);
	// directory containing the database files.
//    File directory;
	String name;
	// cache for still unknown trait-assembled concepts that were saved before.
	Map<String, String> derivedConcepts = new HashMap<>();
	PooledConnection pooledConnection = null;
	// we leave it to the user to decide whether to reuse a connection (for
	// single-user
	// repetitive operations) or get one from the pool at every use (default).
	Connection connection = null;

	// these for the old-style schema functions
	Set<String> tables = null;
	private Map<Class<?>, Schema> schemata = new HashMap<>();
	private Set<Class<?>> initializedSchemata = new HashSet<>();

	// these support the more modern table functions
	private List<Pair<Class<?>, Function<?, String>>> serializers = new ArrayList<>();
	private List<Pair<Class<?>, Function<Map<String, Object>, ?>>> deserializers = new ArrayList<>();

	public static class Builder {

		private List<Structure> tables = new ArrayList<>();
		private String name;
		private boolean persistent;
		private List<Pair<Class<?>, Function<?, String>>> serializers = new ArrayList<>();
		private List<Pair<Class<?>, Function<Map<String, Object>, ?>>> deserializers = new ArrayList<>();

		public Builder(String databaseName, boolean persistent) {
			this.name = databaseName;
			this.persistent = persistent;
		}

		public Builder table(Structure structure) {
			tables.add(structure);
			return this;
		}

		public <T> Builder serialize(Function<T, String> serializer, Class<? extends T> cls) {
			return this;
		}

		public <T> Builder deserialize(Function<Map<String, Object>, T> deserializer, Class<? extends T> cls) {
			return this;
		}

		public H2Database build() {
			return null;
		}

	}

	public static Builder builder(String databaseName, boolean persistent) {
		return new Builder(databaseName, persistent);
	}

	public List<Map<String, String>> dump(String table) throws KlabStorageException {
		List<Map<String, String>> ret = new ArrayList<>();

		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM " + table + ";");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			while (rs.next()) {
				Map<String, String> mp = new HashMap<>();
				for (int i = 1; i <= columnsNumber; i++) {
					mp.put(rsmd.getColumnName(i), rs.getString(i));
				}
				ret.add(mp);
			}

		} catch (SQLException e) {
			throw new KlabStorageException(e);
		} finally {
			try {
				if (connection != null) {
					// connection.close();
				}
			} catch (Exception e) {
				throw new KlabStorageException(e);
			}
		}

		return ret;
	}

	private H2Database(String kboxName) {
		this(kboxName, Configuration.INSTANCE.useInMemoryDatabase());
	}

	private H2Database(String kboxName, boolean inMemory) {

		this.name = kboxName;

		this.ds = new JdbcDataSource();

		if (inMemory) {

			this.url = "jdbc:h2:mem:" + kboxName;
			this.isNew = true;

		} else {

			File directory = Configuration.INSTANCE.getDataPath("kbox/" + kboxName);
			directory.mkdirs();
			File f1 = new File(directory + File.separator + kboxName + ".mv.db");
			File f2 = new File(directory + File.separator + kboxName + ".h2.db");
			/*
			 * FIXME weak check. On Win, only the .mv.db get created anyway; on Linux, the
			 * h2 is created and the mv only exists after the db contains anything.
			 */
			this.isNew = !f1.exists() && !f2.exists();
			try {
				String fileUrl = directory.toURI().toURL().toString();
				this.url = "jdbc:h2:" + fileUrl + kboxName + ";AUTO_SERVER=true;MVCC=true";
			} catch (MalformedURLException e1) {
				throw new KlabValidationException(e1);
			}

		}

		this.ds.setURL(url);
		this.ds.setUser("sa");
		this.ds.setPassword("sa");
		try {
			pooledConnection = this.ds.getPooledConnection();
		} catch (SQLException e) {
			throw new KlabStorageException(e);
		}

		if (isNew) {
			try {
				CreateSpatialExtension.initSpatialExtension(this.ds.getConnection());
				execute("CREATE TABLE hids (id LONG)");
				execute("INSERT INTO hids VALUES (1)");
				execute("CREATE TABLE knowledge_structure (knowledge VARCHAR(256) PRIMARY KEY, structure VARCHAR(4096))");
			} catch (Exception e) {
				throw new KlabStorageException(e);
			}
		} else {
			try {
				oid.set(queryIds("SELECT id FROM hids").get(0));
				/*
				 * TODO fill derivedConcept cache
				 */
			} catch (KlabException e) {
				throw new KlabStorageException(e);
			}
		}
		datastores.put(kboxName, this);
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				deallocateConnection();
			}
		});
		sanityCheck();
	}

	public void preallocateConnection() {
		if (connection == null) {
			try {
				connection = SFSUtilities.wrapConnection(pooledConnection.getConnection());
			} catch (SQLException e) {
				// just leave null
			}
		}
	}

	public void deallocateConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				Logging.INSTANCE.warn(e);
			} finally {
				connection = null;
			}
		}
	}

	private void sanityCheck() {

		Logging.INSTANCE.info("sanity check started on kbox " + url);

		/*
		 * TODO 2. Run sanity checks: 1. check that installed schemata are in sync with
		 * API 2. compute current statistics 3. if new, install default schemata
		 */
	}

//    protected void recover() throws KlabException {
//        try {
//            Recover.execute(directory.toString(), name);
//        } catch (SQLException e) {
//            throw new KlabStorageException(e);
//        }
//    }

	public synchronized long getNextId() {
		long ret = oid.getAndIncrement();
		try {
			execute("UPDATE hids SET id = " + (ret + 1));
		} catch (KlabException e) {
			throw new KlabStorageException(e);
		}
		return ret;
	}

	public boolean hasTable(String tableName) throws KlabException {

		if (tables == null) {

			tables = new HashSet<>();

			class RH implements SQL.ResultHandler {

				@Override
				public void onRow(ResultSet rs) {
					try {
						tables.add(rs.getString(1));
					} catch (SQLException e) {
						throw new KlabStorageException(e);
					}
				}

				@Override
				public void nResults(int nres) {
				}
			}

			RH rh = new RH();
			this.query("select table_name from information_schema.tables;", rh);

		}
		return tables.contains(tableName) || tables.contains(tableName.toUpperCase());
	}

	/**
	 * Get a connection, turning any exception into a Thinklab one.
	 * 
	 * @return connection
	 * @throws KlabStorageException
	 */
	public Connection getConnection() throws KlabStorageException {

		if (connection != null) {
			return connection;
		}

		try {
			// FIXME must close the pooledconnection, not the wrapped connection
			return SFSUtilities.wrapConnection(pooledConnection.getConnection());
		} catch (SQLException e) {
			throw new KlabStorageException(e);
		}
	}

	public void execute(String sql) throws KlabException {

		if (sql == null) {
			// FIXME check if an exception should be thrown or if this shouldn't be
			// checked at all.
			return;
		}

		// System.out.println(sql);

		Connection connection = null;
		try {
			connection = getConnection();
			connection.createStatement().execute(sql);
			// connection.close();
		} catch (SQLException e) {
			throw new KlabStorageException(e);
		} finally {
			try {
				if (connection != null) {
					// connection.close();
				}
			} catch (Exception e) {
				throw new KlabStorageException(e);
			}
		}
	}

	public void query(String sql, SQL.ResultHandler handler) throws KlabException {

		Connection connection = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			result = stmt.executeQuery(sql);
			int res = 0;
			while (result.next()) {
				res++;
				handler.onRow(result);
			}
			handler.nResults(res);
		} catch (SQLException e) {
			throw new KlabStorageException(e);
		} finally {
			// jesus christ
			try {
				if (result != null) {
					result.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					// connection.close();
				}
			} catch (Exception e) {
				throw new KlabStorageException(e);
			}
		}
	}
	
	public DBIterator query(String sql) {

		Connection connection = null;
		Statement stmt = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			return new DBIterator(stmt.executeQuery(sql), stmt, connection);
		} catch (SQLException e) {
			throw new KlabStorageException(e);
		}
	}
	
	public class DBIterator {

		public ResultSet result;
		public boolean finished;
		private Statement stmt;
		private Connection connection;
		
		DBIterator(ResultSet res, Statement stmt, Connection connection) {
			this.result = res;
			this.stmt = stmt;
			this.connection = connection;
			try {
				this.finished = !res.first();
			} catch (SQLException e) {
				finish();
			}
		}
				
		public void finish() {

			try {
				if (result != null) {
					result.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					// connection.close();
				}
			} catch (Exception e) {
				throw new KlabStorageException(e);
			}
		}

		public boolean hasNext() {
			return finished;
		}
		
		public void advance() {
			try {
				this.finished = !result.next();
			} catch (SQLException e) {
				finish();
			}
		}
		
	}
	

	public static H2Database create(String kboxName) {
		if (datastores.get(kboxName) != null) {
			return datastores.get(kboxName);
		}
		return new H2Database(kboxName);
	}

	public static H2Database createPersistent(String kboxName) {
		if (datastores.get(kboxName) != null) {
			return datastores.get(kboxName);
		}
		return new H2Database(kboxName, false);
	}

	public <T> long storeObject(T o, long foreignKey, Serializer<T> serializer, IMonitor monitor) throws KlabException {

		Pair<Class<?>, Schema> schema = getSchema(o.getClass());
		if (schema != null && !initializedSchemata.contains(schema.getFirst())) {
			if (schema.getSecond().getTableName() != null) {
				if (!hasTable(schema.getSecond().getTableName())) {
					execute(schema.getSecond().getCreateSQL());
					tables = null;
				}
			}
			initializedSchemata.add(schema.getFirst());
		}
		long id = getNextId();
		String sql = serializer.serialize(o, /* schema.getSecond(), */ id, foreignKey);
		if (sql != null && !sql.isEmpty()) {
			execute(sql);
		}
		return id;
	}

	private Pair<Class<?>, Schema> getSchema(Class<? extends Object> cls) {
		for (Class<?> cl : schemata.keySet()) {
			if (cl.isAssignableFrom(cls)) {
				return new Pair<>(cl, schemata.get(cl));
			}
		}
		return null;
	}

	/**
	 * Old stuff that should be deprecated, but I don't see myself changing this any
	 * time soon.
	 * 
	 * @param cls
	 * @param schema
	 */
	public void setSchema(Class<?> cls, Schema schema) {
		schemata.put(cls, schema);
	}

	/**
	 * Returns a list of IDs as a result of running a query, subject to the
	 * assumption that the id is field 1 of the result.
	 * 
	 * @param query
	 * @return the list of IDs resulting, or empty
	 * @throws KlabException
	 */
	public List<Long> queryIds(String query) throws KlabException {

		final List<Long> ret = new ArrayList<>();
		query(query, new SQL.SimpleResultHandler() {

			@Override
			public void onRow(ResultSet rs) {
				try {
					ret.add(rs.getLong(1));
				} catch (SQLException e) {
				}
			}
		});
		return ret;
	}

	public <T> List<T> query(String query, Class<? extends T> cls) {
		// TODO Auto-generated method stub
		return null;
	}
}
