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

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.PooledConnection;

import org.h2.engine.Constants;
import org.h2.jdbcx.JdbcDataSource;
import org.h2gis.functions.factory.H2GISFunctions;
import org.h2gis.utilities.JDBCUtilities;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.persistence.h2.H2Kbox.Schema;
import org.integratedmodelling.klab.persistence.h2.H2Kbox.Serializer;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.Pair;

/**
 * A wrapper to simplify the use of a H2 database. Can be used with formally
 * specified schemata for multiple tables (rather obsoleted in design) or with
 * newer {@link IStructuredTable} interface when the structure is simple. The
 * kboxes use the old structure, so that will remain until we reimplement them.
 * 
 * DEBUG: H2 URL for kbox "local_ferdinando.villa_un.seea.cf_seea.cf.supply" will be 
 * jdbc:h2:~/.klab/kbox/local_ferdinando.villa_un.seea.cf_seea.cf.supply/local_ferdinando.villa_un.seea.cf_seea.cf.supply
 * with username=sa, password=sa
 * 
 * Use with org.h2.tools.Console
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
	// File directory;
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
	
	static public Map<String, H2Database> getDatastores() {
	    return datastores;
	}

	private H2Database(String kboxName) {
		this(kboxName, Configuration.INSTANCE.useInMemoryDatabase());
	}

	public String toString() {
	    return "H2: " + url;
	}
	
	private H2Database(String kboxName, boolean inMemory) {

		this.name = kboxName;

		this.ds = new JdbcDataSource();

		if (inMemory) {

			this.url = "jdbc:h2:mem:" + kboxName;
			this.isNew = true;

		} else {

			checkVersions(kboxName);

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
				this.url = "jdbc:h2:" + fileUrl + kboxName + ";AUTO_SERVER=true;NON_KEYWORDS=KEY,VALUE,SECOND,MINUTE,HOUR,DAY,MONTH,YEAR"; //;MVCC=true"; - Not supported from h2 1.4.200
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
				H2GISFunctions.load(this.ds.getConnection());
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
				connection = JDBCUtilities.wrapConnection(pooledConnection.getConnection());
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

	public synchronized long getNextId() {
		long ret = oid.getAndIncrement();
		try {
			execute("UPDATE hids SET id = " + (ret + 1));
		} catch (KlabException e) {
			throw new KlabStorageException(e);
		}
		return ret;
	}

	public long countRows(String tableName) {
		long ret = -1;
		if (hasTable(tableName)) {
			DBIterator result = query("SELECT COUNT(*) FROM " + tableName + ";");
			try {
				ret = result.result.getLong(1);
			} catch (SQLException e) {
				// return -1
			}
		}
		return ret;
	}

	public boolean hasTable(String tableName) throws KlabException {

		try {
			execute("SELECT COUNT(*) FROM " + tableName + ";");
		} catch (Throwable t) {
			return false;
		}

		return true;
		// /*
		// * TODO doesn't work, for mysterious reasons, all tables are there except the
		// one we need,
		// which
		// * exists.
		// */
		// if (tables == null) {
		//
		// tables = new HashSet<>();
		//
		// class RH implements SQL.ResultHandler {
		//
		// @Override
		// public void onRow(ResultSet rs) {
		// try {
		// tables.add(rs.getString(1));
		// } catch (SQLException e) {
		// throw new KlabStorageException(e);
		// }
		// }
		//
		// @Override
		// public void nResults(int nres) {
		// }
		// }
		//
		// RH rh = new RH();
		// this.query("select table_name from information_schema.tables;", rh);
		//
		// }
		// return tables.contains(tableName) ||
		// tables.contains(tableName.toUpperCase());
	}

	/**
	 * Get a connection, turning any exception into a k.LAB one.
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
			return JDBCUtilities.wrapConnection(pooledConnection.getConnection());
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

//		System.out.println(sql);

		Connection connection = null;
		try {
			connection = getConnection();
			connection.createStatement().execute(sql);
			connection.commit();
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

	public void execute(String sql, Connection connection) throws KlabException {

		if (sql == null) {
			// FIXME check if an exception should be thrown or if this shouldn't be
			// checked at all.
			return;
		}

//		System.out.println(sql);

		try {
			connection.createStatement().execute(sql);
		} catch (SQLException e) {
			throw new KlabStorageException(e);
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

	public class DBIterator implements Closeable {

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
				close();
			}
		}

		public void close() {

			try {
				if (result != null) {
					result.close();
					result = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (connection != null) {
					// connection.close();
				}
			} catch (Exception e) {
				throw new KlabStorageException(e);
			}
		}

		public boolean hasNext() {
			return !finished;
		}

		public void advance() {
			try {
				this.finished = !result.next();
			} catch (SQLException e) {
				close();
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

	/**
	 * Check which version of H2 the specified kbox was made with. If unknown, or
	 * not the same, move the previous kbox to a different directory and create a
	 * new one.
	 */
	private static synchronized void checkVersions(String kboxName) {

		boolean refresh = false;
		File directory = Configuration.INSTANCE.getDataPath("kbox/" + kboxName);
		if (directory.isDirectory()) {
			File propfile = new File(Configuration.INSTANCE.getDataPath("kbox") + File.separator + "kbox.properties");
			String knownVersion = null;
			Properties properties = new Properties();
			if (propfile.exists()) {
				try (InputStream input = new FileInputStream(propfile)) {
					properties.load(input);
					knownVersion = properties.getProperty(kboxName + ".h2.version");
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
			}
			refresh = knownVersion == null;
			if (!refresh) {
				refresh = !knownVersion.equals(Constants.FULL_VERSION);
			}

			if (refresh) {
				properties.setProperty(kboxName + ".h2.version", Constants.FULL_VERSION);
				try (OutputStream output = new FileOutputStream(propfile)) {
					properties.store(output, null);
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
			}
		}

		if (refresh) {
			int i = 1;
			File backupDir = null;
			do {
				backupDir = new File(directory + ".backup." + (i++));
			} while (backupDir.exists());
			try {
				Logging.INSTANCE.warn("Database for kbox " + kboxName
						+ " was written with a different version: moving to backup directory " + backupDir);
				FileUtils.copyDirectory(directory, backupDir);
				FileUtils.deleteDirectory(directory);
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}

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

    public String getUrl() {
        return this.url;
    }
}
