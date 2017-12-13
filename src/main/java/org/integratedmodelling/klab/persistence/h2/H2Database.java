/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
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

import javax.sql.PooledConnection;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Recover;
import org.h2gis.h2spatial.CreateSpatialExtension;
import org.h2gis.utilities.SFSUtilities;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.persistence.h2.H2Kbox.Schema;
import org.integratedmodelling.klab.persistence.h2.H2Kbox.Serializer;
import org.integratedmodelling.klab.utils.Pair;

public class H2Database {

    private static Map<String, H2Database> datastores          = new HashMap<>();

    JdbcDataSource                         ds;
    String                                 url;
    boolean                                isNew               = false;
    AtomicLong                             oid                 = new AtomicLong(1);
    // directory containing the database files.
    File                                   directory;
    String                                 name;
    // cache for still unknown trait-assembled concepts that were saved before.
    Map<String, String>                    derivedConcepts     = new HashMap<>();
    PooledConnection                       pooledConnection    = null;
    // we leave it to the user to decide whether to reuse a connection (for single-user
    // repetitive operations) or get one from the pool at every use (default).
    Connection                             connection          = null;
    Set<String>                            tables              = null;
    protected Map<Class<?>, Schema>        schemata            = new HashMap<>();

    private Set<Class<?>>                  initializedSchemata = new HashSet<>();

    private H2Database(String kboxName) {
        this(kboxName, Configuration.INSTANCE.getDataPath("kbox"));
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
                Map<String,String> mp = new HashMap<>();
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
                throw new KlabRuntimeException(e);
            }
        }
        
        return ret;
    }

    private H2Database(String kboxName, File directory) {

        this.directory = directory;
        this.name = kboxName;

        directory.mkdirs();
        JdbcDataSource dds = new JdbcDataSource();
        /*
         * FIXME weak check. On Win, only the .mv.db get created anyway; on Linux, the h2
         * is created and the mv only exists after the db contains anything.
         */
        File f1 = new File(directory + File.separator + kboxName + ".mv.db");
        File f2 = new File(directory + File.separator + kboxName + ".h2.db");
        this.isNew = !f1.exists() && !f2.exists();
        try {
            String fileUrl = directory.toURI().toURL().toString();
            this.url = "jdbc:h2:" + fileUrl + kboxName + ";AUTO_SERVER=TRUE";
            /*
             * TODO This creates an in-memory database We could use it for models, and
             * that would likely help with corruption issues and speed, at the cost of RAM
             * and startup time when many models are available.
             * 
             * Can use an option to set this from user preferences.
             */
            // this.url = "jdbc:h2:mem:" + kboxName /* + ";AUTO_SERVER=TRUE" */;
        } catch (MalformedURLException e1) {
            throw new KlabRuntimeException(e1);
        }
        // this.url = "jdbc:h2:file:" + directory.toString().replaceAll("\\\\", "/") + "/"
        // + kboxName
        // + ";AUTO_SERVER=TRUE";

        dds.setURL(url);
        dds.setUser("sa");
        dds.setPassword("sa");
        // Context ctx;
        // try {
        // ctx = new InitialContext();
        // ctx.bind("jdbc/" + name, dds);
        // } catch (NamingException e1) {
        // throw new ThinklabRuntimeException("error during kbox initialization: " +
        // e1.getMessage());
        // }
        this.ds = /* SFSUtilities.wrapSpatialDataSource( */dds; // );
        // this.connections = JdbcConnectionPool.create(url, "sa", "sa");
        try {
            pooledConnection = dds.getPooledConnection();
        } catch (SQLException e) {
            throw new KlabRuntimeException(e);
        }

        if (isNew) {
            try {
                CreateSpatialExtension.initSpatialExtension(dds.getConnection());
                execute("CREATE TABLE hids (id LONG)");
                execute("INSERT INTO hids VALUES (1)");
                execute("CREATE TABLE knowledge_structure (knowledge VARCHAR(256) PRIMARY KEY, structure VARCHAR(4096))");
            } catch (Exception e) {
                throw new KlabRuntimeException(e);
            }
        } else {
            try {
                oid.set(queryIds("SELECT id FROM hids").get(0));
                /*
                 * TODO fill derivedConcept cache
                 */
            } catch (KlabException e) {
                throw new KlabRuntimeException(e);
            }
        }
        datastores.put(kboxName, this);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    deallocateConnection();
                    pooledConnection.close();
                } catch (SQLException e) {
                    // poh
                }
            }
        });
        sanityCheck();
        // serializer = new H2Serializer();
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

            } finally {
                connection = null;
            }
        }
    }

    private void sanityCheck() {

        Klab.INSTANCE.info("sanity check started on kbox " + url);

        /*
         * TODO 2. Run sanity checks: 1. check that installed schemata are in sync with
         * API 2. compute current statistics 3. if new, install default schemata
         */
    }

    protected void recover() throws KlabException {
        try {
            Recover.execute(directory.toString(), name);
        } catch (SQLException e) {
            throw new KlabStorageException(e);
        }
    }

    public synchronized long getNextId() {
        long ret = oid.getAndIncrement();
        try {
            execute("UPDATE hids SET id = " + (ret + 1));
        } catch (KlabException e) {
            throw new KlabRuntimeException(e);
        }
        return ret;
    }

    public boolean hasTable(String tableName) throws KlabException {

        if (tables == null) {

            tables = new HashSet<>();

            class RH implements SQL.ResultHandler {

                int n = 0;

                @Override
                public void onRow(ResultSet rs) {
                    try {
                        tables.add(rs.getString(1));
                    } catch (SQLException e) {
                        throw new KlabRuntimeException(e);
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
                throw new KlabRuntimeException(e);
            }
        }
    }

    public void query(String sql, SQL.ResultHandler handler) throws KlabException {

        if (sql.contains("POINT EMPTY")) {
            System.out.println("WHAT?");
        }

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
                throw new KlabRuntimeException(e);
            }
        }
    }

    public static H2Database get(String kboxName) {
        if (datastores.get(kboxName) != null) {
            return datastores.get(kboxName);
        }
        return new H2Database(kboxName);
    }

    public static H2Database get(File directory, String kboxName) {
        if (datastores.get(kboxName) != null) {
            return datastores.get(kboxName);
        }
        return new H2Database(kboxName, directory);
    }

    public <T> long storeObject(T o, long foreignKey, Serializer<T> serializer, IMonitor monitor)
            throws KlabException {

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

    public void setSchema(Class<?> cls, Schema schema) {
        schemata.put(cls, schema);
    }

    /**
     * Returns a list of IDs as a result of running a query, subject to the assumption
     * that the id is field 1 of the result.
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

    /**
     * TODO store all concepts that depend on complex trait composition as their signature
     * from base concepts, so they can be reconstructed at initialization to avoid
     * surprises when models are searched for that are based on concepts that haven't been
     * loaded.
     * 
     * @param observable
     */
    public void updateKnowledge(IObservable observable) {

        if (observable == null) {
            return;
        }

        storeTraits(observable.getType());
    }

    private void storeTraits(IConcept c) {

        if (c == null) {
            return;
        }

        String k = c.toString();
        String s = c.getDefinition();

        if (!k.equals(s)) {
            /*
             * search for k; if not there, insert k,s into knowledge_structure.
             */
        }
    }

    /**
     * Call after component initialization and each project loading: assembles any concept
     * that was decomposed into traits before and is still unknown, ensuring that all
     * knowledge is as synchronized as possible.
     */
    public void synchronizeKnowledge() {

    }
}
