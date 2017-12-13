//package org.integratedmodelling.engine.modelling.kbox;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.h2gis.utilities.SpatialResultSet;
//import org.integratedmodelling.api.auth.IUser;
//import org.integratedmodelling.api.engine.IModelingEngine;
//import org.integratedmodelling.api.knowledge.IConcept;
//import org.integratedmodelling.api.knowledge.IObservation;
//import org.integratedmodelling.api.modelling.IClassifier;
//import org.integratedmodelling.api.modelling.IExtent;
//import org.integratedmodelling.api.modelling.IScale;
//import org.integratedmodelling.api.modelling.resolution.IResolutionScope;
//import org.integratedmodelling.api.monitoring.IMonitor;
//import org.integratedmodelling.api.space.ISpatialExtent;
//import org.integratedmodelling.api.time.ITemporalExtent;
//import org.integratedmodelling.common.beans.DirectObservation;
//import org.integratedmodelling.common.beans.Event;
//import org.integratedmodelling.common.beans.Observation;
//import org.integratedmodelling.common.beans.Relationship;
//import org.integratedmodelling.common.beans.State;
//import org.integratedmodelling.common.beans.Subject;
//import org.integratedmodelling.common.beans.requests.ObservationQuery;
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.owl.Knowledge;
//import org.integratedmodelling.common.space.IGeometricShape;
//import org.integratedmodelling.common.utils.Escape;
//import org.integratedmodelling.engine.kbox.sql.SQL;
//import org.integratedmodelling.engine.kbox.sql.h2.H2Kbox;
//import org.integratedmodelling.engine.kbox.sql.h2.schema.CompoundSchema;
//import org.integratedmodelling.engine.modelling.resolver.ResolutionScope;
//import org.integratedmodelling.engine.modelling.runtime.Scale;
//import org.integratedmodelling.exceptions.KlabException;
//import org.integratedmodelling.exceptions.KlabRuntimeException;
//import org.integratedmodelling.exceptions.KlabValidationException;
//
//import com.vividsolutions.jts.geom.Geometry;
//
//public class ObservationKbox2 extends ObservableKbox {
//
//    private static ObservationKbox2 _this;
//    private boolean                 workRemotely = false;
//
//    @Override
//    public String getUrnForStoredObservation(long id, IUser user) {
//        return KLAB.ENGINE.getName() + ":" + user.getUsername() + ":observations:" + id;
//    }
//
//    public String storeObservation(Observation o, long primaryKey, long foreignKey) {
//        return storeObservation(o, primaryKey, foreignKey, -1l, -1l);
//    }
//
//    public String storeObservation(Observation o, long primaryKey, long foreignKey, long sourceId, long targetId) {
//
//        long typeId = -1;
//        String conceptDef = null;
//        if (foreignKey == 0) {
//            // store searchable typeid only for top-level observation
//            typeId = this.requireConceptId(Knowledge.parse(o.getObservable().getType()));
//            // TODO geometry
//        } else {
//            conceptDef = o.getObservable().getType();
//        }
//
//        IScale scale = o.getObservation() == null ? KLAB.MFACTORY.adapt(o.getScale(), Scale.class)
//                : o.getObservation().getScale();
//
//        boolean isconstant = o instanceof State ? ((State) o).isConstant() : false;
//        boolean isscalar = o instanceof State ? ((State) o).getConstantData() != null : false;
//        boolean isprivate = o.isNamespacePrivate();
//        boolean isinscenario = o.isNamespaceScenario();
//        boolean isspatial = scale.getSpace() != null;
//        boolean istemporal = scale.getTime() != null;
//        boolean hasdata = o instanceof State && ((State) o).getEncodedData() != null;
//        boolean hasdataset = o instanceof State && ((State) o).getDatasetHandle() != null;
//        long timestart = scale.getTime() != null ? scale.getTime().getStart().getMillis() : 0l;
//        long timeend = scale.getTime() != null ? scale.getTime().getEnd().getMillis() : 0l;
//        long timestep = scale.getTime() != null ? scale.getTime().getStep().getMilliseconds() : 0l;
//        Geometry shape = scale.getSpace() == null ? null
//                : ((IGeometricShape) scale.getSpace().getShape()).getGeometry();
//        long spacemult = scale.getSpace() == null ? -1 : scale.getSpace().getMultiplicity();
//        long timemult = scale.getTime() == null ? -1 : scale.getTime().getMultiplicity();
//        long scalemult = scale.getMultiplicity();
//
//        double doublevalue = 0.0;
//        boolean booleanvalue = false;
//        String conceptValue = null;
//        String scalartype = "X";
//        
//        if (o instanceof State && ((State) o).getConstantData() != null) {
//            if (((State) o).getConstantData() instanceof Number) {
//                doublevalue = ((Number) ((State) o).getConstantData()).doubleValue();
//                scalartype = "N";
//            } else if (((State) o).getConstantData() instanceof Boolean) {
//                booleanvalue = (Boolean) ((State) o).getConstantData();
//                scalartype = "B";
//            } else if (((State) o).getConstantData() instanceof String) {
//                conceptValue = (String) ((State) o).getConstantData();
//                scalartype = "C";
//            } else if (((State) o).getConstantData() instanceof IConcept) {
//                conceptValue = ((IConcept) ((State) o).getConstantData()).getDefinition();
//                scalartype = "C";
//            }
//
//        }
//
//        String ret = "INSERT INTO observation VALUES ("
//                + primaryKey + ", "// "oid LONG, "
//                + foreignKey + ", "// "parentid LONG, "
//                + "'" + KLAB.NAME + "', "// "serverid VARCHAR(64), "
//                + "'" + o.getId() + "', " // "id VARCHAR(256), "
//                + "'" + cn(getObservationCode(o)) + "', " // "obstype CHAR(1)
//                + "'" + cn(o instanceof org.integratedmodelling.common.beans.DirectObservation
//                        ? ((org.integratedmodelling.common.beans.DirectObservation) o).getName() : null)
//                + "', "// "name VARCHAR(256), "
//                + "'" + cn(o.getNamespace()) + "', "// "namespaceid VARCHAR(128), "
//                + "'" + cn(o.getProjectId()) + "', "// "projectid VARCHAR(128), "
//                + typeId + ", "// "typeid LONG, "
//                + sourceId + ", "// "sourceid LONG, "
//                + targetId + ", "// "targetid LONG, "
//                + "'" + cn(conceptDef) + "', "// "conceptdef VARCHAR(512), "
//                + (isconstant ? "TRUE, " : "FALSE, ")// "isconstant BOOLEAN, " // for
//                                                     // states
//                + (isscalar ? "TRUE, " : "FALSE, ")// "isscalar BOOLEAN, " // for states
//                + "'" + scalartype + "', "  // "scalartype CHAR(1), " // for states
//                + doublevalue + ", "// "doublevalue
//                                                                                                         // DOUBLE,
//                                                                                                         // "
//                                                                                                         // //
//                                                                                                         // for
//                                                                                                         // states
//                + "'" + cn(conceptValue) + "', "// "conceptvalue VARCHAR(512), " // for
//                                                // states
//                + (booleanvalue ? "TRUE, " : "FALSE, ")// "booleanvalue BOOLEAN, " // for
//                                                       // states
//                + (isprivate ? "TRUE, " : "FALSE, ")// "isprivate BOOLEAN, "
//                + (isinscenario ? "TRUE, " : "FALSE, ")// "inscenario BOOLEAN, "
//                + timestart + ", "// "timestart LONG, "
//                + timeend + ", "// "timeend LONG, "
//                + timestep + ", "// "timestep LONG, "
//                + (isspatial ? "TRUE, " : "FALSE, ")// "isspatial BOOLEAN, "
//                + (istemporal ? "TRUE, " : "FALSE, ")// "istemporal BOOLEAN, "
//                + (hasdata ? "TRUE, " : "FALSE, ")// "hasdata BOOLEAN, " // blob of
//                                                  // encoded data in database
//                + (hasdataset ? "TRUE, " : "FALSE, ")// "hasdataset BOOLEAN, " // file
//                                                     // $id.nc
//                + timemult + ", "// "timemultiplicity LONG, "
//                + spacemult + ", "// "spacemultiplicity LONG, "
//                + scalemult + ", "// "scalemultiplicity LONG, "
//                + "'"
//                + (shape == null
//                        ? "GEOMETRYCOLLECTION EMPTY"
//                        : shape.toString())
//                + "'"// "space GEOMETRY "
//                + "); ";
//
//        Map<String, Long> subjectOids = new HashMap<>();
//
//        if (o instanceof DirectObservation) {
//            for (Subject child : ((DirectObservation) o).getChildSubjects()) {
//                long oid = database.getNextId();
//                String sql = storeObservation(child, oid, primaryKey);
//                ret += "\n" + sql;
//                subjectOids.put(child.getId(), oid);
//            }
//            for (Event child : ((DirectObservation) o).getChildEvents()) {
//                long oid = database.getNextId();
//                String sql = storeObservation(child, oid, primaryKey);
//                ret += "\n" + sql;
//            }
//            for (org.integratedmodelling.common.beans.Process child : ((DirectObservation) o)
//                    .getChildProcesses()) {
//                long oid = database.getNextId();
//                String sql = storeObservation(child, oid, primaryKey);
//                ret += "\n" + sql;
//            }
//            for (State child : ((DirectObservation) o).getStates()) {
//                long oid = database.getNextId();
//                String sql = storeObservation(child, oid, primaryKey);
//                ret += "\n" + sql;
//            }
//        }
//
//        if (o instanceof State && o.getObservation() != null) {
//
//            /*
//             * TODO store observer
//             */
//
//            /*
//             * TODO add inline data from associated observation unless the fields are
//             * already set.
//             */
//        }
//
//        if (o instanceof Subject && ((Subject) o).getStructure() != null) {
//            /*
//             * store all relationships using subjectOids for ID reference
//             */
//            for (Relationship rel : ((Subject) o).getStructure()) {
//                long oid = database.getNextId();
//                long sId = subjectOids.get(rel.getSource());
//                long tId = subjectOids.get(rel.getTarget());
//                String sql = storeObservation(rel, oid, primaryKey, sId, tId);
//                ret += "\n" + sql;
//            }
//        }
//
//        return ret;
//    }
//
//    class ObservationSerializer implements Serializer<Observation> {
//
//        @Override
//        public String serialize(Observation o, long primaryKey, long foreignKey) {
//            return storeObservation(o, primaryKey, foreignKey);
//        }
//    }
//
//    public static ObservationKbox2 get() {
//
//        if (_this == null) {
//            H2Kbox.set("observations2_"
//                    + KBOX_VERSION, new ObservationKbox2("observations2_" + KBOX_VERSION, KLAB.ENGINE
//                            .getMonitor()));
//            _this = (ObservationKbox2) H2Kbox.get("observations2_" + KBOX_VERSION);
//        }
//        return _this;
//    }
//
//    public static String getObservationCode(Observation obs) {
//        if (obs instanceof Subject) {
//            return "T";
//        }
//        if (obs instanceof Event) {
//            return "E";
//        }
//        if (obs instanceof Relationship) {
//            return "R";
//        }
//        if (obs instanceof org.integratedmodelling.common.beans.Process) {
//            return "P";
//        }
//        if (obs instanceof State) {
//            return "S";
//        }
//        return null;
//    }
//
//    public static String getClassifierCode(IClassifier obs) {
//        return null;
//    }
//
//    public static String getExtentCode(IExtent obs) {
//        return null;
//    }
//
//    private ObservationKbox2(String name, IMonitor monitor) {
//
//        super(name, monitor);
//
//        setSchema(Observation.class, new CompoundSchema(Observation.class) {
//
//            @Override
//            public String getTableName() {
//                return getMainTableId();
//            }
//
//            @Override
//            public String getCreateSQL() {
//
//                String ret = "CREATE TABLE observation ("
//                        + "oid LONG, "
//                        + "parentid LONG, "
//                        + "serverid VARCHAR(64), "
//                        + "id VARCHAR(256), "
//                        + "obstype CHAR(1), "   // E, T (subject), S, P, R
//                        + "name VARCHAR(256), "
//                        + "namespaceid VARCHAR(128), " // may be null
//                        + "projectid VARCHAR(128), "
//                        + "typeid LONG, " // primary objects store type
//                        + "sourceid LONG, " // relationships store ID of source...
//                        + "targetid LONG, " // ... and target subjects.
//                        + "conceptdef VARCHAR(512), " // secondary objects store concept
//                        + "isconstant BOOLEAN, " // for states
//                        + "isscalar BOOLEAN, " // for states
//                        + "scalartype CHAR(1), " // for states
//                        + "doublevalue DOUBLE, " // for states
//                        + "conceptvalue VARCHAR(512), " // for states
//                        + "booleanvalue BOOLEAN, " // for states
//                        + "isprivate BOOLEAN, "
//                        + "inscenario BOOLEAN, "
//                        + "timestart LONG, "
//                        + "timeend LONG, "
//                        + "timestep LONG, "
//                        + "isspatial BOOLEAN, "
//                        + "istemporal BOOLEAN, "
//                        + "hasdata BOOLEAN, " // file $id.dat with non-temporal data or
//                                              // init slice
//                        + "hasdataset BOOLEAN, " // file $id.nc
//                        + "timemultiplicity LONG, "
//                        + "spacemultiplicity LONG, "
//                        + "scalemultiplicity LONG, "
//                        + "space GEOMETRY "
//                        + "); "
//                        + "CREATE INDEX observation_oid_index ON observation(oid); "
//                        + "CREATE INDEX observation_id_index ON observation(id); "
//                        + "CREATE SPATIAL INDEX observation_space ON observation(space);\n";
//
//                ret += "CREATE TABLE observer ("
//                        + "id LONG, "
//                        + "observationid LONG, "
//                        + "typeid LONG, "
//                        + "otypeid LONG, "
//                        + "ctypeid LONG, "
//                        + "classificationid LONG, "
//                        + "minimumval DOUBLE, "
//                        + "maximumval DOUBLE, "
//                        + "isinteger BOOLEAN, "
//                        + "isindirect BOOLEAN, "
//                        + "isproportion BOOLEAN, "
//                        + "isdiscretization BOOLEAN, "
//                        + "isintegerscale BOOLEAN, "
//                        + "isscaleleftbounded BOOLEAN, "
//                        + "isscalerightbounded BOOLEAN, "
//                        + "unitorcurrency VARCHAR(256), "
//                        + "metadataproperty VARCHAR(256), "
//                        + "observerclass VARCHAR(256) "
//                        + ");\n";
//
//                ret += "CREATE TABLE classification ("
//                        + "id LONG, "
//                        + "observerid LONG, "
//                        + "typeid LONG "
//                        + ");\n";
//
//                ret += "CREATE TABLE relationships ("
//                        + "id LONG, "
//                        + "subjectid LONG, "
//                        + "conceptdef VARCHAR(512), "
//                        + "sourceid LONG, "
//                        + "targetid LONG "
//                        + ");\n";
//
//                ret += "CREATE TABLE classifier ("
//                        + "id LONG, "
//                        + "classifierid LONG, " // for child classifiers
//                        + "classifiertype CHAR(3), "
//                        + "numbermatch DOUBLE, "
//                        + "intervalmatchlow DOUBLE, "
//                        + "intervalmatchhigh DOUBLE, "
//                        + "conceptmatch VARCHAR(512), "
//                        + "traitmatch VARCHAR(512), "
//                        + "booleanmatch INTEGER, "
//                        + "stringmatch VARCHAR(512), "
//                        + "isnull BOOLEAN, "
//                        + "classificationid LONG, "
//                        + "iscatchall BOOLEAN, "
//                        + "isnegated BOOLEAN, "
//                        + "ismultiple BOOLEAN, " // made up by child classifiers
//                        + "concept VARCHAR(256) " // the resulting concept, by literal
//                                                  // definition
//                        + ");\n";
//
//                ret += "CREATE TABLE data ("
//                        + "observationid LONG, "
//                        + "hasdataset BOOLEAN, "
//                        + "inlinedata BLOB"
//                        + ");\n";
//
//                ret += "CREATE TABLE scale ("
//                        + "id LONG, "
//                        + "observationid LONG, "
//                        + "observertype CHAR(1) "
//                        + ");\n";
//
//                ret += "CREATE TABLE extent ("
//                        + "id LONG, "
//                        + "scaleid LONG, "
//                        + "x1 DOUBLE, "
//                        + "x2 DOUBLE, "
//                        + "y1 DOUBLE, "
//                        + "y2 DOUBLE, "
//                        + "s1 INTEGER, "
//                        + "s2 INTEGER, "
//                        + "shape GEOMETRY, "
//                        + "extenttype CHAR(1) "
//                        + "); ";
//
//                return ret;
//
//            }
//        });
//
//        setSerializer(Observation.class, new ObservationSerializer());
//    }
//
//    @Override
//    public long store(Object o) throws KlabException {
//
//        String clx = o == null ? "null" : o.getClass().getCanonicalName();
//        if (!(o instanceof Observation)) {
//            o = KLAB.MFACTORY.adapt(o, Observation.class);
//        }
//        if (o == null) {
//            throw new KlabValidationException("cannot turn object of class " + clx
//                    + " into an Observation bean for storage");
//        }
//        return super.store(o);
//    }
//
//    /**
//     * Pass the output of queryModelData to a contextual prioritizer and return the ranked
//     * list of IModels. If we're a personal engine, also broadcast the query to the
//     * network and merge results before returning.
//     * 
//     * @param observable
//     * @param context
//     * @return models resulting from query, best first.
//     * @throws KlabException
//     */
//    public List<IObservation> query(IConcept observable, IResolutionScope context)
//            throws KlabException {
//        //
//        // IModelPrioritizer<IModelMetadata> prioritizer = context.getPrioritizer2();
//        // ModelQueryResult ret = new ModelQueryResult(prioritizer, ((ResolutionScope)
//        // context).getMonitor());
//        Set<Observation> local = new HashSet<>();
//
//        ObservationQueryResult ret = new ObservationQueryResult(((ResolutionScope) context).getMonitor());
//
//        /*
//         * only query locally if we've seen a model before.
//         */
//        if (database.hasTable("observation")) {
//            for (Observation md : queryObservations(observable, context)) {
//                local.add(md);
//                ret.addObservationBean(md);
//            }
//        }
//
//        /**
//         * If we're a modeling engine, dispatch the request to all nodes that allow it,
//         * which we do simply by using the result list as a distributed operation.
//         */
//        if (KLAB.ENGINE instanceof IModelingEngine && workRemotely) {
//
//            ObservationQuery mquery = new ObservationQuery();
//            mquery.setObservable(KLAB.MFACTORY
//                    .adapt(observable, org.integratedmodelling.common.beans.Observable.class));
//            mquery.setContext(KLAB.MFACTORY.adapt(context, org.integratedmodelling.common.beans.Scope.class));
//
//            KLAB.ENGINE.getNetwork().broadcast(ret, ((ResolutionScope) context).getMonitor());
//        }
//
//        return ret;
//    }
//
//    /**
//     * Find and deserialize all modeldata matching the parameters. Do not rank or
//     * anything.
//     * 
//     * @param observable
//     * @param context
//     * @throws KlabException
//     */
//    public List<Observation> queryObservations(IConcept observable, IResolutionScope context)
//            throws KlabException {
//
//        List<Observation> ret = new ArrayList<>();
//
//        if (!database.hasTable("observation")) {
//            return ret;
//        }
//
//        String query = "SELECT observation.oid FROM observation WHERE ";
//        String typequery = observableQuery(observable, context);
//
//        if (typequery == null) {
//            return ret;
//        }
//
//        query += "(" + scopeQuery(context, observable) + ")";
//        query += " AND (" + typequery + ")";
//        if (context.getScale().getSpace() != null) {
//            String sq = spaceQuery(context.getScale().getSpace());
//            if (!sq.isEmpty()) {
//                query += " AND (" + sq + ")";
//            }
//        }
//
//        String tquery = timeQuery(context.getScale().getTime());
//        if (!tquery.isEmpty()) {
//            query += " AND (" + tquery + ");";
//        }
//
//        KLAB.info(query);
//
//        final List<Long> oids = database.queryIds(query);
//
//        for (long l : oids) {
//            Observation model = retrieveModel(l);
//            if (model != null) {
//                ret.add(model);
//            } else {
//                KLAB.warn("kbox is out of sync with knowledge base");
//            }
//        }
//
//        // KLAB.info(KLAB.ENGINE.getName() + ": model query for "
//        // + (context.isForInstantiation() ? "instantiation of " : "explanation of ") +
//        // observable
//        // + " found "
//        // + (ret.size() == 1 ? ret.get(0).getName() : (ret.size() + " models")));
//
//        return ret;
//    }
//
//    private String observableQuery(IConcept observable, IResolutionScope context) {
//        Set<Long> ids = this.getCompatibleTypeIds(observable, context);
//        if (ids == null || ids.size() == 0) {
//            return null;
//        }
//        String ret = "";
//        for (long id : ids) {
//            ret += (ret.isEmpty() ? "" : ", ") + id;
//        }
//        return "typeid IN (" + ret + ")";
//    }
//
//    /*
//     * select models that are [instantiators if required] AND:] [private and in the home
//     * namespace if not dummy OR] (non-private and non-scenario) OR (in any of the
//     * scenarios in the context).
//     */
//    private String scopeQuery(IResolutionScope context, IConcept observable) {
//
//        String ret = "";
//
//        String namespaceId = context.getResolutionNamespace() == null ? DUMMY_NAMESPACE_ID
//                : context.getResolutionNamespace().getId();
//        if (!namespaceId.equals(DUMMY_NAMESPACE_ID)) {
//            // ret += "(observation.isprivate AND observation.namespaceid = '" +
//            // namespaceId
//            // + "')";
//            ret += "(observation.namespaceid = '" + namespaceId + "')";
//        }
//
//        ret += (ret.isEmpty() ? "" : " OR ")
//                + "((NOT observation.isprivate) AND (NOT observation.inscenario))";
//
//        if (context.getScenarios() != null && context.getScenarios().size() > 0) {
//            ret += " OR (" + joinStringConditions("observation.namespaceid", context.getScenarios(), "OR")
//                    + ")";
//        }
//
//        return ret;
//    }
//
//    /*
//     * select models that intersect the given space or have no space at all.
//     */
//    private String spaceQuery(ISpatialExtent space) {
//        if (space.getExtent().getShape().isEmpty()) {
//            return "";
//        }
//        return "observation.space && '"
//                + ((IGeometricShape) (space.getExtent().getShape())).getStandardizedGeometry()
//                + "' OR ST_IsEmpty(observation.space)";
//    }
//
//    /*
//     * Entirely TODO. For initialization we should use time only to select for most
//     * current info - either closer to the context or to today if time is null. For
//     * dynamic models we should either not have a context or cover the context. Guess this
//     * is the job of the prioritizer, and we should simply let anything through except
//     * when we look for process models.
//     */
//    private String timeQuery(ITemporalExtent time) {
//
//        String ret = "";
//        boolean checkBoundaries = false;
//        if (time != null && checkBoundaries) {
//            ret = "(timestart == -1 AND timeend == -1) OR (";
//            long start = time.getStart() == null ? -1 : time.getStart().getMillis();
//            long end = time.getEnd() == null ? -1 : time.getEnd().getMillis();
//            if (start > 0 && end > 0) {
//                ret += "timestart >= " + start + " AND timeend <= " + end;
//            } else if (start > 0) {
//                ret += "timestart >= " + start;
//            } else if (end > 0) {
//                ret += "timeend <= " + end;
//            }
//            ret += ")";
//        }
//        return ret;
//    }
//
//    public List<Observation> retrieveAll() throws KlabException {
//
//        List<Observation> ret = new ArrayList<>();
//        if (!database.hasTable("observation")) {
//            return ret;
//        }
//        for (long oid : database.queryIds("SELECT oid FROM observation;")) {
//            ret.add(retrieveModel(oid));
//        }
//        return ret;
//    }
//
//    public Observation retrieveModel(long oid) throws KlabException {
//
//        final Observation ret = new Observation();
//
//        database.query("SELECT * FROM observation WHERE oid = " + oid, new SQL.SimpleResultHandler() {
//            @Override
//            public void onRow(ResultSet rs) {
//
//                try {
//
//                    SpatialResultSet srs = rs.unwrap(SpatialResultSet.class);
//
//                    long tyid = srs.getLong(7);
//                    long obid = srs.getLong(8);
//
//                    IConcept mtype = getType(tyid);
//                    IConcept otype = getType(obid);
//
//                    if (mtype == null || otype == null) {
//                        return;
//                    }
//
//                    // ret.setObservableConcept(mtype);
//                    // ret.setObservationConcept(otype);
//                    // ret.setObservable(getTypeDefinition(tyid));
//                    // ret.setObservationType(getTypeDefinition(obid));
//                    //
//                    // ret.setServerId(nullify(srs.getString(2)));
//                    // ret.setId(srs.getString(3));
//                    // ret.setName(srs.getString(4));
//                    // ret.setNamespaceId(srs.getString(5));
//                    // ret.setProjectId(nullify(srs.getString(6)));
//                    //
//                    // ret.setPrivateModel(srs.getBoolean(9));
//                    // ret.setResolved(srs.getBoolean(10));
//                    // ret.setReification(srs.getBoolean(11));
//                    // ret.setInScenario(srs.getBoolean(12));
//                    // ret.setHasDirectObjects(srs.getBoolean(13));
//                    // ret.setHasDirectData(srs.getBoolean(14));
//                    // ret.setTimeStart(srs.getLong(15));
//                    // ret.setTimeEnd(srs.getLong(16));
//                    // ret.setSpatial(srs.getBoolean(17));
//                    // ret.setTemporal(srs.getBoolean(18));
//                    // ret.setTimeMultiplicity(srs.getLong(19));
//                    // ret.setSpaceMultiplicity(srs.getLong(20));
//                    // ret.setScaleMultiplicity(srs.getLong(21));
//                    // ret.setDereifyingAttribute(nullify(srs.getString(22)));
//
//                    Geometry geometry = srs.getGeometry(23);
//                    if (!geometry.isEmpty()) {
//                        // ret.setShape(new ShapeValue(geometry,
//                        // Geospace.get().getDefaultCRS())); // +
//                    }
//                } catch (SQLException e) {
//                    throw new KlabRuntimeException(e);
//                }
//            }
//
//        });
//
//        ret.setMetadata(getMetadataFor(oid));
//
//        return ret;
//    }
//
//    @Override
//    protected String getMainTableId() {
//        return "observation";
//    }
//
//    /**
//     * @param name
//     * @return true if model with given id exists in database
//     * @throws KlabException
//     */
//    public boolean hasModel(String name) throws KlabException {
//
//        if (!database.hasTable("observation")) {
//            return false;
//        }
//
//        return database.queryIds("SELECT oid FROM observation WHERE name = '" + name + "';").size() > 0;
//    }
//
//    @Override
//    protected int deleteAllObjectsWithNamespace(String namespaceId) throws KlabException {
//        int n = 0;
//        for (long oid : database
//                .queryIds("SELECT oid FROM observation where namespaceid = '" + Escape.forSQL(namespaceId)
//                        + "';")) {
//            deleteObjectWithId(oid);
//            n++;
//        }
//        return n;
//    }
//
//    @Override
//    protected void deleteObjectWithId(long id) throws KlabException {
//        database.execute("DELETE FROM observation WHERE oid = " + id);
//        deleteMetadataFor(id);
//    }
//
//    // @Override
//    // public long store(Object o) throws KlabException {
//    //
//    // ArrayList<Object> toStore = new ArrayList<>();
//    //
//    // if (o instanceof IObservation) {
//    //
//    //
//    // } else {
//    // toStore.add(o);
//    // }
//    //
//    // long ret = -1;
//    // for (Object obj : toStore) {
//    // long r = super.store(obj);
//    // if (ret < 0)
//    // ret = r;
//    // }
//    //
//    // return ret;
//    // }
//
//    public static final String DUMMY_NAMESPACE_ID = "DUMMY_SEARCH_NS";
//
//}
