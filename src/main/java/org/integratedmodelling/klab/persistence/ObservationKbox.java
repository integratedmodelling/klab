///*******************************************************************************
// * Copyright (C) 2007, 2015:
// * 
// * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
// * other authors listed in @author annotations
// *
// * All rights reserved. This file is part of the k.LAB software suite, meant to enable
// * modular, collaborative, integrated development of interoperable data and model
// * components. For details, see http://integratedmodelling.org.
// * 
// * This program is free software; you can redistribute it and/or modify it under the terms
// * of the Affero General Public License Version 3 or any later version.
// *
// * This program is distributed in the hope that it will be useful, but without any
// * warranty; without even the implied warranty of merchantability or fitness for a
// * particular purpose. See the Affero General Public License for more details.
// * 
// * You should have received a copy of the Affero General Public License along with this
// * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
// * 330, Boston, MA 02111-1307, USA. The license is also available at:
// * https://www.gnu.org/licenses/agpl.html
// *******************************************************************************/
//package org.integratedmodelling.engine.modelling.kbox;
//
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.h2gis.utilities.SpatialResultSet;
//import org.integratedmodelling.api.auth.IUser;
//import org.integratedmodelling.api.modelling.IDirectObserver;
//import org.integratedmodelling.api.persistence.IKbox;
//import org.integratedmodelling.api.space.ISpatialExtent;
//import org.integratedmodelling.api.time.ITemporalExtent;
//import org.integratedmodelling.common.ConceptPair;
//import org.integratedmodelling.common.command.ServiceCall;
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.indexing.KnowledgeIndex;
//import org.integratedmodelling.common.space.IGeometricShape;
//import org.integratedmodelling.common.vocabulary.ObservationMetadata;
//import org.integratedmodelling.engine.kbox.sql.h2.schema.CompoundSchema;
//import org.integratedmodelling.engine.time.extents.RegularTemporalGrid;
//import org.integratedmodelling.klab.Klab;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IMetadata;
//import org.integratedmodelling.klab.api.knowledge.IProject;
//import org.integratedmodelling.klab.api.model.INamespace;
//import org.integratedmodelling.klab.api.observations.IScale;
//import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
//import org.integratedmodelling.klab.owl.Knowledge;
//import org.integratedmodelling.klab.persistence.ObservableKbox.NamespaceSchema;
//import org.integratedmodelling.klab.persistence.ObservableKbox.NamespaceSerializer;
//import org.integratedmodelling.klab.persistence.h2.H2Database;
//import org.integratedmodelling.klab.persistence.h2.H2Kbox;
//import org.integratedmodelling.klab.persistence.h2.H2Kbox.DirectDeserializer;
//import org.integratedmodelling.klab.persistence.h2.H2Kbox.Serializer;
//import org.integratedmodelling.klab.persistence.h2.SQL;
//import org.integratedmodelling.klab.utils.Escape;
//
//import com.google.common.util.concurrent.ServiceManager;
//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.io.WKBWriter;
//
///**
// * Kbox to hold finished observations - the ones specified with 'observe'. Supports free
// * text search (using Lucene on metadata) and automatically broadcasts the query to the
// * network. The ObservationKbox trades in ObservationMetadata, not in subject observers,
// * to prevent failures in retrieval due to projects not loaded at the engine side
// * (although only proper subject observers are stored as metadata).
// * 
// * @author ferdinando.villa
// *
// */
//public class ObservationKbox extends H2Kbox {
//
//    KnowledgeIndex             index;
//    boolean                    needsReindex = false;
//
//    /**
//     * The kbox version, which is used to create the filesystem storage. Change this when
//     * incompatible changes are made to force kbox reset.
//     */
//    public static final String KBOX_VERSION = "098v4";
//
//    static ObservationKbox     _this;
//
//    /**
//     * Return the single instance of the observation kbox.
//     * 
//     * @return the kbox
//     */
//    public static ObservationKbox get() {
//
//        if (_this == null) {
//            H2Kbox.set("observations_"
//                    + KBOX_VERSION, new ObservationKbox("observations_" + KBOX_VERSION, KLAB.ENGINE
//                            .getMonitor()));
//            _this = (ObservationKbox) H2Kbox.get("observations_" + KBOX_VERSION);
//            Klab.INSTANCE.info("observation kbox created: " + _this.count() + " observations are available");
//        }
//        return _this;
//    }
//
//    /*
//     * TODO - this and the one in ModelKbox
//     */
//    private String timeQuery(ITemporalExtent time) {
//        String ret = "";
//        // TODO Auto-generated method stub
//        return ret;
//    }
//
//    /*
//     * exposed to allow preallocating connections in big imports.
//     */
//    public H2Database getDatabase() {
//        return this.database;
//    }
//
//    private String spaceQuery(ISpatialExtent space) {
//        return "observation.space && '"
//                + ((IGeometricShape) (space.getExtent().getShape())).getStandardizedGeometry()
//                + "'";
//    }
//
//    private String observableQuery(Collection<IConcept> observables) {
//        return joinStringConditions("observation.type", observables, "OR");
//    }
//
//    public List<ObservationMetadata> query(Collection<IConcept> concepts, IScale scale, Collection<String> scenarios, boolean localOnly)
//            throws KlabException {
//
//        Set<ObservationMetadata> local = new HashSet<>();
//        List<ObservationMetadata> ret = new ArrayList<>();
//
//        /*
//         * only query locally if we've seen an observation before.
//         */
//        for (ObservationMetadata md : queryLocal(concepts, scale, scenarios)) {
//            if (local.add(md)) {
//                ret.add(md);
//            }
//        }
//
//        /*
//         * broadcast call for search function on network.
//         */
//        // if (!localOnly && KLAB.NETWORK.isPersonal() && ((EngineNetwork)
//        // KLAB.NETWORK).provides("im.search")) {
//
//        ServiceCall scl = null;
//        // ServiceManager
//        // .getServiceCall("thinklab.search-observations", "type", StringUtils
//        // .joinObjects(concepts, ','), "scenarios", StringUtils
//        // .join(scenarios, ','), "scale", Scale.asString(scale));
//
//        // if (scl != null) {
//        //
//        // scl.setMonitor(KLAB.ENGINE.getMonitor());
//        // Object mdd = ((EngineNetwork) KLAB.NETWORK).broadcast(scl.post(),
//        // scl.getMonitor());
//        //
//        // if (mdd instanceof Collection<?>) {
//        // for (Object md : ((Collection<?>) mdd)) {
//        // if (md instanceof ObservationMetadata) {
//        // if (local.add((ObservationMetadata) md)) {
//        // ret.add((ObservationMetadata) md);
//        // }
//        // // TODO should have ranks from search somewhere
//        // }
//        // }
//        // }
//        // }
//        // }
//
//        return ret;
//    }
//
//    public List<ObservationMetadata> queryLocal(Collection<IConcept> observable, IScale scale, Collection<String> scenarios)
//            throws KlabException {
//
//        List<ObservationMetadata> ret = new ArrayList<>();
//        if (!database.hasTable("observation")) {
//            return ret;
//        }
//        String query = "SELECT observation.oid FROM observation WHERE (";
//
//        // TODO query += "(" + scopeQuery(scenarios) + ")";
//        query += observableQuery(observable) + ")";
//
//        if (scale != null) {
//            if (scale.getSpace() != null) {
//                query += " AND (" + spaceQuery(scale.getSpace()) + ")";
//            }
//            String tquery = timeQuery(scale.getTime());
//            if (!tquery.isEmpty()) {
//                query += " AND (" + tquery + ");";
//            }
//        }
//
//        final List<Long> oids = database.queryIds(query);
//        for (long l : oids) {
//            try {
//                ret.add(deserialize(l));
//            } catch (KlabException e) {
//                // ontologies do not reflect content any more: concepts not found
//                KLAB.warn("kbox is out of sync with knowledge base: " + e.getMessage());
//            }
//        }
//
//        KLAB.info(ret.size() + " observations of " + observable + " retrieved");
//
//        return ret;
//    }
//
//    public List<ObservationMetadata> queryLocal(String queryString) throws KlabException {
//
//        List<ObservationMetadata> ret = new ArrayList<>();
//        Set<ObservationMetadata> local = new HashSet<>();
//
//        for (IMetadata m : index.search(queryString)) {
//            ObservationMetadata md = new ObservationMetadata();
//            md.name = m.getString("id");
//            md.id = m.getString("name");
//            md.namespaceId = m.getString("namespace");
//            md.observableName = m.getString("observable");
//            md.description = m.getString("description");
//            md.serverId = KLAB.NAME;
//            if (local.add(md)) {
//                ret.add(md);
//            }
//        }
//        return ret;
//    }
//
//    public List<ObservationMetadata> query(String freeText, boolean localOnly) throws KlabException {
//
//        Set<ObservationMetadata> local = new HashSet<>();
//        List<ObservationMetadata> ret = new ArrayList<>();
//
//        /**
//         * Quick and dirty: handle the * as a catch-all for anything stored locally;
//         * otherwise if the string is a concept, we just look for observations of that
//         * concept. Otherwise we move on to using the Lucene index.
//         */
//        if (freeText.equals("*")) {
//            return retrieveAll();
//        } else if (KLAB.KM.getConcept(freeText) != null) {
//            return query(Collections.singleton(KLAB.c(freeText)), null, null, localOnly);
//        }
//
//        for (ObservationMetadata md : queryLocal(freeText)) {
//            local.add(md);
//            ret.add(md);
//        }
//
//        /*
//         * broadcast call for search function on network.
//         */
//        // if (!localOnly && KLAB.NETWORK.isPersonal() && ((EngineNetwork)
//        // KLAB.NETWORK).provides("im.search")) {
//
//        // TODO all the rest if we have a context, too.
//        ServiceCall scl = ServiceManager
//                .getServiceCall("thinklab.search-observations", "query-string", freeText);
//
//        if (scl != null) {
//
//            // scl.setMonitor(KLAB.ENGINE.getMonitor());
//            // Object mdd = ((EngineNetwork) KLAB.NETWORK).broadcast(scl.post(),
//            // scl.getMonitor());
//            //
//            // if (mdd instanceof Collection<?>) {
//            // for (Object md : ((Collection<?>) mdd)) {
//            //
//            // /*
//            // * Only return those observations whose observable we recognize
//            // * finished objects should come out but they don't and it's a subtlety for
//            // now.
//            // * FIXME see why.
//            // */
//            // if (md instanceof Map && ((Map<?, ?>) md).containsKey("observable-name")) {
//            //// ObservationMetadata met = new ObservationMetadata((Map<?, ?>) md);
//            //// if (local.add(met)) {
//            //// ret.add(met);
//            //// }
//            // }
//            //
//            // }
//            // }
//        }
//        // }
//
//        return ret;
//    }
//
//    private String joinStringConditions(String field, Collection<?> stringValues, String operator) {
//
//        String ret = "";
//
//        for (Object o : stringValues) {
//            ret += (ret.isEmpty() ? "" : (" " + operator + " ")) + field + " = '" + o + "'";
//        }
//
//        return ret;
//    }
//
//    public boolean hasObservation(String name) throws KlabException {
//
//        if (!database.hasTable("observation")) {
//            return false;
//        }
//
//        return database.queryIds("SELECT oid FROM observation WHERE name = '" + Escape.forSQL(name) + "';")
//                .size() > 0;
//    }
//
//    @Override
//    public long store(Object o) throws KlabException {
//
//        if (o instanceof IDirectObserver) {
//
//            KLAB.info("storing observation " + o);
//
//            index.index(o);
//            needsReindex = true;
//            deleteNamed(((IDirectObserver) o).getName());
//        }
//
//        return super.store(o);
//    }
//
//    public String getUrnForStoredObservation(long id, IUser user) {
//        return KLAB.ENGINE.getName() + ":" + user.getUsername() + ":observations:" + id;
//    }
//
//    protected void deleteObservation(long oid) throws KlabException {
//        database.execute("DELETE FROM observation WHERE oid = " + oid);
//        database.execute("DELETE FROM observation WHERE fid = " + oid);
//        database.execute("DELETE FROM metadata WHERE fid = " + oid);
//        database.execute("DELETE FROM state WHERE fid = " + oid);
//        database.execute("DELETE FROM traitpairs WHERE fid = " + oid);
//    }
//
//    public int clearNamespace(String namespaceId) throws KlabException {
//
//        if (!database.hasTable("observation")) {
//            return 0;
//        }
//        int n = 0;
//        for (long oid : database.queryIds("SELECT oid FROM observation where namespaceid = '"
//                + Escape.forSQL(namespaceId) + "';")) {
//            deleteObservation(oid);
//            n++;
//        }
//
//        database.execute("DELETE FROM namespaces where id = '" + namespaceId + "';");
//
//        return n;
//    }
//
//    public void deleteNamed(String name) throws KlabException {
//
//        if (!database.hasTable("observation")) {
//            return;
//        }
//
//        List<Long> delenda = new ArrayList<>();
//        for (long oid : database.queryIds("SELECT oid FROM observation WHERE name ='" + Escape.forSQL(name)
//                + "';")) {
//            delenda.add(oid);
//        }
//        for (long oid : delenda) {
//            deleteObservation(oid);
//        }
//    }
//
//    public List<ObservationMetadata> retrieveAll() throws KlabException {
//
//        List<ObservationMetadata> ret = new ArrayList<>();
//
//        if (!database.hasTable("observation")) {
//            return ret;
//        }
//
//        for (long oid : database.queryIds("SELECT oid FROM observation;")) {
//            ret.add(deserialize(oid));
//        }
//        return ret;
//    }
//
//    /**
//     * Count the observations in the db.
//     * 
//     * @return number of observations
//     */
//    public long count() {
//
//        try {
//            if (!database.hasTable("observation")) {
//                return 0;
//            }
//            List<Long> ret = database.queryIds("SELECT COUNT(*) from observation;");
//            return ret.size() > 0 ? ret.get(0) : 0l;
//        } catch (KlabException e) {
//            throw new KlabRuntimeException(e);
//        }
//    }
//
//    class ObservationSerializer implements Serializer {
//
//        @Override
//        public String serialize(Object o, /* Schema schema, */ long primaryKey, long foreignKey) {
//
//            String ret = null;
//
//            if (o instanceof IDirectObserver) {
//
//                IDirectObserver md = (IDirectObserver) o;
//
//                if (((IDirectObserver) o).getObservable() == null
//                        || ((IDirectObserver) o).getObservable().getType() == null) {
//                    return null;
//                }
//
//                IScale scale = md.getCoverage(KLAB.ENGINE.getMonitor());
//                ISpatialExtent space = scale.getSpace();
//                IProject project = md.getNamespace().getProject();
//                ITemporalExtent time = scale.getTime();
//                ITemporalExtent timeExtent = null;
//                Long timestep = null;
//                if (time != null) {
//                    timeExtent = time.getExtent();
//                    if (time instanceof RegularTemporalGrid) {
//                        timestep = ((RegularTemporalGrid)time).getStep().getMilliseconds();
//                    }
//                }
//                // todo
//                ArrayList<ConceptPair> traits = new ArrayList<>();
//
//                ret = "INSERT INTO observation VALUES ("
//                        + primaryKey + ", "
//                        + 0 + ", " // TODO foreign key
//                        + "'" + KLAB.NAME + "', "// + "serverid VARCHAR(64), "
//                        + "'" + Escape.forSQL(md.getId()) + "', "// + "id VARCHAR(128), "
//                        + "'" + Escape.forSQL(md.getName()) + "', "// + "name
//                                                                   // VARCHAR(256), "
//                        + "'" + Escape.forSQL(md.getNamespace().getId()) + "', "// +
//                                                                                // "namespaceid
//                                                                                // VARCHAR(128),
//                                                                                // "
//                        + "'" + (project == null ? "" : project.getId()) + "', "// +
//                                                                                // "projectid
//                                                                                // VARCHAR(128),
//                                                                                // "
//                        + "'" + ((Knowledge) md.getObservable().getType()).asText() + "', "// +
//                                                                                           // "type
//                                                                                           // VARCHAR(256),
//                                                                                           // "
//                        + (md.getNamespace().isScenario() ? "TRUE" : "FALSE") + ", "// +
//                                                                                    // "inscenario
//                                                                                    // BOOLEAN,
//                                                                                    // "
//                        + (timeExtent == null ? 0l : timeExtent.getStart().getMillis()) + ", "// +
//                                                                                              // "timestart
//                                                                                              // LONG,
//                                                                                              // "
//                        + (timeExtent == null ? 0l : timeExtent.getEnd().getMillis()) + ", "// +
//                                                                                            // "timeend
//                                                                                            // LONG,
//                                                                                            // "
//                        + (timestep == null ? 0l : timestep) + ", "// +
//                                                                             // "timestep
//                                                                             // LONG, "
//                        + (space != null ? "TRUE" : "FALSE") + ", "// + "isspatial
//                                                                   // BOOLEAN, "
//                        + (time != null ? "TRUE" : "FALSE") + ", "// +
//                                                                  // "istemporal BOOLEAN,
//                                                                  // "
//                        + (time == null ? 0l : time.getMultiplicity()) + ", "// +
//                                                                             // "timemultiplicity
//                                                                             // LONG, "
//                        + (space == null ? 0l : space.getMultiplicity()) + ", "// +
//                                                                               // "spacemultiplicity
//                                                                               // LONG, "
//                        + scale.getMultiplicity() + ", "// + "scalemultiplicity LONG, "
//                        + "'" + (space == null ? "GEOMETRYCOLLECTION EMPTY" : ((IGeometricShape) space)
//                                .getStandardizedGeometry().toString())// + "space
//                                                                      // GEOMETRY"
//                        + "'); ";
//
//                if (traits != null) {
//                    for (ConceptPair cp : traits) {
//                        ret += "\nINSERT INTO traitpairs VALUES ("
//                                + primaryKey + ", "// + "fid LONG, "
//                                + "'" + cp.getFirst() + "', "// + "basetrait VARCHAR(256),
//                                                             // "
//                                + "'" + cp.getSecond() + "', "// + "traitvalue
//                                                              // VARCHAR(256)"
//                                + "); ";
//                    }
//                }
//
//                if (md.getMetadata() != null) {
//                    for (String s : md.getMetadata().getKeys()) {
//                        // TODO
//                    }
//                }
//
//            } else if (o instanceof INamespace) {
//
//                INamespace ns = (INamespace) o;
//                ret = "INSERT INTO namespaces VALUES ('" + Escape.forSQL(ns.getId()) + "', "
//                        + ns.getTimeStamp() + ", "
//                        + (ns.isScenario() ? "TRUE" : "FALSE") + ");";
//
//            } else {
//                throw new KlabRuntimeException("ObservationKbox can only store models or namespaces: "
//                        + o.getClass().getSimpleName());
//            }
//
//            return ret;
//        }
//
//        private String cn(Object o) {
//            return o == null ? "" : o.toString();
//        }
//    }
//
//    class ObservationDeserializer implements DirectDeserializer {
//
//        @Override
//        public void setKbox(IKbox h2Kbox) {
//        }
//
//        @Override
//        public ObservationMetadata deserialize(ResultSet rs) {
//
//            ObservationMetadata ret = null;
//            WKBWriter wkbWriter = new WKBWriter();
//
//            try {
//                SpatialResultSet srs = rs.unwrap(SpatialResultSet.class);
//
//                String name = rs.getString(5);
//                String concept = srs.getString(8);
//                Geometry geometry = srs.getGeometry(18); // "space GEOMETRY"
//                if (geometry.isEmpty()) {
//                    geometry = null;
//                }
//
//                ret = new ObservationMetadata();
//
//                ret.name = name;
//                ret.id = rs.getString(4);
//                ret.observableName = concept;
//                ret.serverId = KLAB.NAME;
//                ret.namespaceId = srs.getString(6);
//                if (geometry != null) {
//                    ret.geometryWKB = WKBWriter.toHex(wkbWriter.write(geometry));
//                }
//                ret.start = srs.getLong(10);
//                ret.end = srs.getLong(11);
//                ret.step = srs.getLong(12);
//
//                // 1 + "oid LONG, "
//                // 2 + "fid LONG, "
//                // 3 + "serverid VARCHAR(64), "
//                // 4 + "id VARCHAR(128), "
//                // 5 + "name VARCHAR(256), "
//                // 6 + "namespaceid VARCHAR(128), "
//                // 7 + "projectid VARCHAR(128), "
//                // 8 + "type VARCHAR(256), "
//                // 9 + "inscenario BOOLEAN, "
//                // 10 + "timestart LONG, "
//                // 11 + "timeend LONG, "
//                // 12 + "timestep LONG, "
//                // 13 + "isspatial BOOLEAN, "
//                // 14 + "istemporal BOOLEAN, "
//                // 15 + "timemultiplicity LONG, "
//                // 16 + "spacemultiplicity LONG, "
//                // 17 + "scalemultiplicity LONG, "
//                // 18 + "space GEOMETRY"
//                // TODO
//
//            } catch (Exception e) {
//                throw new KlabRuntimeException(e);
//            }
//            return ret;
//        }
//    }
//
//    class ObservationSchema extends CompoundSchema {
//
//        public ObservationSchema() {
//            super(IDirectObserver.class);
//        }
//
//        @Override
//        public String getCreateSQL() {
//            return "CREATE TABLE observation ("
//                    + "oid LONG PRIMARY KEY, "
//                    + "fid LONG, "
//                    + "serverid VARCHAR(64), "
//                    + "id VARCHAR(128), "
//                    + "name VARCHAR(256), "
//                    + "namespaceid VARCHAR(128), "
//                    + "projectid VARCHAR(128), "
//                    + "type VARCHAR(1024), "
//                    + "inscenario BOOLEAN, "
//                    + "timestart LONG, "
//                    + "timeend LONG, "
//                    + "timestep LONG, "
//                    + "isspatial BOOLEAN, "
//                    + "istemporal BOOLEAN, "
//                    + "timemultiplicity LONG, "
//                    + "spacemultiplicity LONG, "
//                    + "scalemultiplicity LONG, "
//                    + "space GEOMETRY"
//                    + "); "
//                    + "CREATE TABLE state ("
//                    + "fid LONG, "
//                    + "type VARCHAR(256), "
//                    + "ptype VARCHAR(256), "
//                    + "otype VARCHAR(256), "
//                    + "stype VARCHAR(256), "
//                    + "ttype VARCHAR(256), "
//                    + "value OTHER, "
//                    + "fname VARCHAR(256)"
//                    + "); "
//                    + "CREATE TABLE traitpairs ("
//                    + "fid LONG, "
//                    + "basetrait VARCHAR(256), "
//                    + "traitvalue VARCHAR(256)"
//                    + "); "
//                    + "CREATE TABLE metadata ("
//                    + "fid LONG, "
//                    + "key VARCHAR(256), "
//                    + "value OTHER"
//                    + ");"
//
//                    + "CREATE INDEX observation_oid_index ON observation(oid); "
//                    + "CREATE INDEX observation_fid_index ON observation(fid); "
//                    + "CREATE INDEX observation_name_index ON observation(name); "
//                    + "CREATE INDEX metadata_oid_index ON metadata(fid); "
//                    + "CREATE INDEX traitpairs_oid_index ON traitpairs(fid); "
//                    + "CREATE SPATIAL INDEX observation_space ON observation(space);";
//        }
//
//        @Override
//        public String getTableName() {
//            return "observation";
//        }
//    }
//
//    public ObservationKbox(String name, IMonitor monitor) {
//        super(name, monitor);
//        setSerializer(IDirectObserver.class, new ObservationSerializer());
//        setSerializer(INamespace.class, new NamespaceSerializer());
//        setDeserializer(ObservationMetadata.class, new ObservationDeserializer());
//        setSchema(IDirectObserver.class, new ObservationSchema());
//        setSchema(INamespace.class, new NamespaceSchema(INamespace.class));
//        try {
//            index = new KnowledgeIndex(name);
//        } catch (KlabException e) {
//            throw new KlabRuntimeException();
//        }
//    }
//
//    /**
//     * Pass the a namespace to check if its objects need to be stored. If the stored
//     * namespace record does not exist or has a timestamp older than the passed one,
//     * remove all objects that belong to it and return true. Does not store a new
//     * namespace record - this should be done when this has returned true and there were
//     * no errors.
//     * 
//     * Returns: 0 if no need to refresh, 1 if it must be entirely refreshed and every
//     * model and namespace record is removed from the kbox, and 2 if the models without
//     * errors need to be checked again (they may be in or not).
//     * 
//     * 
//     * @param namespace
//     * @return result code
//     */
//    public int removeIfOlder(INamespace namespace) throws KlabException {
//
//        if (!database.hasTable("namespaces")) {
//            return 1;
//        }
//
//        long dbTimestamp = getNamespaceTimestamp(namespace);
//        long timestamp = namespace.getTimeStamp();
//
//        /*
//         * if we have stored something and we are younger than the stored ns, remove all
//         * models coming from it so we can add our new ones.
//         */
//        if (timestamp > dbTimestamp) {
//
//            if (dbTimestamp > 0) {
//
//                monitor.debug("Removing all observations in namespace " + namespace.getId());
//                int removed = clearNamespace(namespace.getId());
//                monitor.debug("Removed " + removed + " observations.");
//            }
//
//            monitor.debug("Refreshing observations in " + namespace.getId() + ": stored  "
//                    + new Date(dbTimestamp)
//                    + " < "
//                    + new Date(timestamp));
//
//            return 1;
//        }
//
//        /*
//         * if we have not changed the source file but models had errors when stored,
//         * return the conservative mode so we can check model by model and only store
//         * those that are no longer in error due to external reasons.
//         */
//        if (namespace != null && namespace.hasErrors()) {
//            return 2;
//        }
//
//        return 0;
//    }
//
//    /**
//     * Return 0 if namespace is not in the kbox, or the (long) timestamp of the namespace
//     * if it is.
//     * 
//     * @return result code
//     * @throws KlabException
//     */
//    public long getNamespaceTimestamp(INamespace namespace) throws KlabException {
//
//        if (!database.hasTable("namespaces")) {
//            return 0l;
//        }
//        List<Long> ret = database.queryIds("SELECT timestamp FROM namespaces WHERE id = '"
//                + Escape.forSQL(namespace.getId())
//                + "';");
//        return ret.size() > 0 ? ret.get(0) : 0l;
//    }
//
//    public ObservationMetadata retrieveByName(String name) throws KlabException {
//
//        if (!database.hasTable("observation")) {
//            return null;
//        }
//
//        final ObservationDeserializer deserializer = new ObservationDeserializer();
//        final ArrayList<ObservationMetadata> ret = new ArrayList<>();
//        database.query("SELECT * FROM observation WHERE name = '" + Escape.forSQL(name)
//                + "';", new SQL.SimpleResultHandler() {
//                    @Override
//                    public void onRow(ResultSet rs) {
//                        ret.add(deserializer.deserialize(rs));
//                    }
//                });
//
//        return ret.size() > 0 ? ret.get(0) : null;
//    }
//
//    public ObservationMetadata deserialize(Long oid) throws KlabException {
//
//        final ObservationDeserializer deserializer = new ObservationDeserializer();
//        final ArrayList<ObservationMetadata> ret = new ArrayList<>();
//        database.query("SELECT * FROM observation WHERE oid = " + oid, new SQL.SimpleResultHandler() {
//            @Override
//            public void onRow(ResultSet rs) {
//                ret.add(deserializer.deserialize(rs));
//            }
//        });
//
//        return ret.size() > 0 ? ret.get(0) : null;
//    }
//
//    private static String nullify(String string) {
//        if (string == null || string.isEmpty())
//            return null;
//        return string;
//    }
//
//    public void reindexLocalObservations() throws KlabException {
//        if (needsReindex) {
//            needsReindex = false;
//            index.reindex();
//        }
//    }
//}
