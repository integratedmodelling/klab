//package org.integratedmodelling.klab.persistence;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.h2gis.utilities.SpatialResultSet;
//import org.integratedmodelling.api.engine.IModelingEngine;
//import org.integratedmodelling.api.metadata.IModelMetadata;
//import org.integratedmodelling.api.modelling.IObservableSemantics;
//import org.integratedmodelling.api.modelling.resolution.IModelPrioritizer;
//import org.integratedmodelling.api.modelling.resolution.IResolutionScope;
//import org.integratedmodelling.api.space.ISpatialExtent;
//import org.integratedmodelling.api.time.ITemporalExtent;
//import org.integratedmodelling.common.beans.Model;
//import org.integratedmodelling.common.beans.requests.ModelQuery;
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.space.IGeometricShape;
//import org.integratedmodelling.engine.geospace.Geospace;
//import org.integratedmodelling.engine.geospace.literals.ShapeValue;
//import org.integratedmodelling.engine.kbox.sql.h2.schema.CompoundSchema;
//import org.integratedmodelling.engine.modelling.resolver.ResolutionScope;
//import org.integratedmodelling.klab.Configuration;
//import org.integratedmodelling.klab.Models;
//import org.integratedmodelling.klab.Observables;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.model.IModel;
//import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
//import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
//import org.integratedmodelling.klab.persistence.h2.H2Kbox;
//import org.integratedmodelling.klab.persistence.h2.H2Kbox.Serializer;
//import org.integratedmodelling.klab.persistence.h2.SQL;
//import org.integratedmodelling.klab.utils.Escape;
//
//import com.vividsolutions.jts.geom.Geometry;
//
//public class ModelKbox extends ObservableKbox {
//
//    private static ModelKbox _this;
//    private boolean workRemotely = !Configuration.INSTANCE.isOffline();
//
//    public static ModelKbox get() {
//
//        if (_this == null) {
//            H2Kbox.set("models2_"
//                    + KBOX_VERSION, new ModelKbox("models2_" + KBOX_VERSION, KLAB.ENGINE.getMonitor()));
//            _this = (ModelKbox) H2Kbox.get("models2_" + KBOX_VERSION);
//        }
//        return _this;
//    }
//
//    private ModelKbox(String name, IMonitor monitor) {
//
//        super(name, monitor);
//
//        setSchema(Model.class, new CompoundSchema(Model.class) {
//
//            @Override
//            public String getTableName() {
//                return getMainTableId();
//            }
//
//            @Override
//            public String getCreateSQL() {
//                String ret = "CREATE TABLE model ("
//                        + "oid LONG, "
//                        + "serverid VARCHAR(64), "
//                        + "id VARCHAR(256), "
//                        + "name VARCHAR(256), "
//                        + "namespaceid VARCHAR(128), "
//                        + "projectid VARCHAR(128), "
//                        + "typeid LONG, "
//                        + "otypeid LONG, "
//                        + "isprivate BOOLEAN, "
//                        + "isresolved BOOLEAN, "
//                        + "isreification BOOLEAN, "
//                        + "inscenario BOOLEAN, "
//                        + "hasdirectobjects BOOLEAN, "
//                        + "hasdirectdata BOOLEAN, "
//                        + "timestart LONG, "
//                        + "timeend LONG, "
//                        + "isspatial BOOLEAN, "
//                        + "istemporal BOOLEAN, "
//                        + "timemultiplicity LONG, "
//                        + "spacemultiplicity LONG, "
//                        + "scalemultiplicity LONG, "
//                        + "dereifyingattribute VARCHAR(256), "
//                        + "minspatialscale INTEGER, "
//                        + "maxspatialscale INTEGER, "
//                        + "mintimescale INTEGER, "
//                        + "maxtimescale INTEGER, "
//                        + "space GEOMETRY, "
//                        + "); "
//                        + "CREATE INDEX model_oid_index ON model(oid); "
//                        + "CREATE SPATIAL INDEX model_space ON model(space);";
//                
//                return ret;
//
//            }
//        });
//
//        setSerializer(Model.class, new Serializer<Model>() {
//
//            private String cn(Object o) {
//                return o == null ? "" : o.toString();
//            }
//
//            @Override
//            public String serialize(Model model, /* Schema schema, */ long primaryKey, long foreignKey) {
//
//                long tid = requireConceptId(model.getObservableConcept());
//                long oid = requireConceptId(model.getObservationConcept());
//
//                String ret = "INSERT INTO model VALUES ("
//                        + primaryKey + ", "
//                        + "'" + cn(model.getServerId()) + "', "
//                        + "'" + cn(model.getId()) + "', "
//                        + "'" + cn(model.getName()) + "', "
//                        + "'" + cn(model.getNamespaceId()) + "', "
//                        + "'" + cn(model.getProjectId()) + "', "
//                        + tid + ", "
//                        + oid + ", "
//                        + (model.isPrivateModel() ? "TRUE" : "FALSE") + ", "
//                        + (model.isResolved() ? "TRUE" : "FALSE") + ", "
//                        + (model.isReification() ? "TRUE" : "FALSE") + ", "
//                        + (model.isInScenario() ? "TRUE" : "FALSE") + ", "
//                        + (model.isHasDirectObjects() ? "TRUE" : "FALSE") + ", "
//                        + (model.isHasDirectData() ? "TRUE" : "FALSE") + ", "
//                        + model.getTimeStart() + ", "
//                        + model.getTimeEnd() + ", "
//                        + (model.isSpatial() ? "TRUE" : "FALSE") + ", "
//                        + (model.isTemporal() ? "TRUE" : "FALSE") + ", "
//                        + model.getTimeMultiplicity() + ", "
//                        + model.getSpaceMultiplicity() + ", "
//                        + model.getScaleMultiplicity() + ", "
//                        + "'" + cn(model.getDereifyingAttribute()) + "', "
//                        + model.getMinSpatialScaleFactor() + ", "
//                        + model.getMaxSpatialScaleFactor() + ", "
//                        + model.getMinTimeScaleFactor() + ", "
//                        + model.getMaxTimeScaleFactor() + ", "
//                        + "'"
//                        + (model.getShape() == null
//                                ? "GEOMETRYCOLLECTION EMPTY"
//                                : model.getShape().getStandardizedGeometry().toString())
//                        + "'"
//                        + ");";
//
//                if (model.getMetadata() != null && model.getMetadata().getData().size() > 0) {
//                    storeMetadataFor(primaryKey, model.getMetadata());
//                }
//                
//                return ret;
//            }
//        });
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
//    public List<IModel> query(IObservableSemantics observable, IResolutionScope context)
//            throws KlabException {
//
//        IModelPrioritizer<IModelMetadata> prioritizer = context.getPrioritizer();
//        ModelQueryResult ret = new ModelQueryResult(prioritizer, ((ResolutionScope) context).getMonitor());
//        Set<Model> local = new HashSet<>();
//
//        /*
//         * only query locally if we've seen a model before.
//         */
//        if (database.hasTable("model")) {
//            for (Model md : queryModels(observable, context)) {
//                local.add(md);
//                ret.addModel(md);
//            }
//        }
//
//        /**
//         * If we're a modeling engine, dispatch the request to all nodes that allow it,
//         * which we do simply by using the result list as a distributed operation.
//         */
//        if (KLAB.ENGINE instanceof IModelingEngine && workRemotely) {
//
//            ModelQuery mquery = new ModelQuery();
//            mquery.setObservable(KLAB.MFACTORY
//                    .adapt(observable, org.integratedmodelling.common.beans.Observable.class));
//            mquery.setScope(KLAB.MFACTORY.adapt(context, org.integratedmodelling.common.beans.Scope.class));
//            ret.setQuery(mquery);
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
//    public List<Model> queryModels(IObservableSemantics observable, IResolutionScope context)
//            throws KlabException {
//
//        List<Model> ret = new ArrayList<>();
//
//        if (!database.hasTable("model")) {
//            return ret;
//        }
//
//        String query = "SELECT model.oid FROM model WHERE ";
//        String typequery = observableQuery(observable.getType(), context);
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
////         KLAB.info(query);
//
//        final List<Long> oids = database.queryIds(query);
//
//        for (long l : oids) {
//            Model model = retrieveModel(l);
//            if (model != null) {
//                ret.add(model);
//            } else {
//                KLAB.warn("kbox is out of sync with knowledge base");
//            }
//        }
//
//        KLAB.info(KLAB.ENGINE.getName() + ": model query for "
//                + (context.isForInstantiation() ? "instantiation of " : "explanation of ") + observable
//                + " found "
//                + (ret.size() == 1 ? ret.get(0).getName() : (ret.size() + " models")));
//
//        return ret;
//    }
//
//    private String observableQuery(IConcept observable, IResolutionScope context) {
//        
//        /*
//         * remove any transformations before querying
//         */
//        observable = Observables.getUntransformedObservable(observable);
//        
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
//    private String scopeQuery(IResolutionScope context, IObservableSemantics observable) {
//
//        String ret = "";
//
//        String namespaceId = context.getResolutionNamespace() == null ? DUMMY_NAMESPACE_ID
//                : context.getResolutionNamespace().getId();
//        if (!namespaceId.equals(DUMMY_NAMESPACE_ID)) {
//            // ret += "(model.isprivate AND model.namespaceid = '" + namespaceId
//            // + "')";
//            ret += "(model.namespaceid = '" + namespaceId + "')";
//        }
//
//        ret += (ret.isEmpty() ? "" : " OR ") + "((NOT model.isprivate) AND (NOT model.inscenario))";
//
//        if (context.getScenarios() != null && context.getScenarios().size() > 0) {
//            ret += " OR (" + joinStringConditions("model.namespaceid", context.getScenarios(), "OR") + ")";
//        }
//
//        if (NS.isCountable(observable)) {
//            if (context.isForInstantiation()
//                    || ((org.integratedmodelling.common.vocabulary.ObservableSemantics) observable)
//                            .isInstantiator()) {
//                ret = "(" + ret + ") AND model.isreification";
//            } else {
//                ret = "(" + ret + ") AND (NOT model.isreification)";
//            }
//        }
//
//        return ret;
//    }
//
//    /*
//     * select models that intersect the given space or have no space at all.
//     */
//    private String spaceQuery(ISpatialExtent space) {
//
//        if (space.getExtent().getShape().isEmpty()) {
//            return "";
//        }
//
//        String scalequery = space.getScaleRank() + " BETWEEN model.minspatialscale AND model.maxspatialscale";
//
//        String spacequery = "model.space && '"
//                + ((IGeometricShape) (space.getExtent().getShape())).getStandardizedGeometry()
//                + "' OR ST_IsEmpty(model.space)";
//        
//        return "(" + scalequery + ") AND (" + spacequery + ")";
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
//    public List<Model> retrieveAll() throws KlabException {
//
//        List<Model> ret = new ArrayList<>();
//        if (!database.hasTable("model")) {
//            return ret;
//        }
//        for (long oid : database.queryIds("SELECT oid FROM model;")) {
//            ret.add(retrieveModel(oid));
//        }
//        return ret;
//    }
//
//    public Model retrieveModel(long oid) throws KlabException {
//
//        final Model ret = new Model();
//
//        database.query("SELECT * FROM model WHERE oid = " + oid, new SQL.SimpleResultHandler() {
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
//                    ret.setName(srs.getString(4));
//                    
//                    IConcept mtype = getType(tyid);
//                    IConcept otype = getType(obid);
//
////                    if (mtype == null || otype == null) {
////                        return;
////                    }
//
//                    ret.setObservableConcept(mtype);
//                    ret.setObservationConcept(otype);
//                    ret.setObservable(getTypeDefinition(tyid));
//                    ret.setObservationType(getTypeDefinition(obid));
//
//                    ret.setServerId(nullify(srs.getString(2)));
//                    ret.setId(srs.getString(3));
//
//                    ret.setNamespaceId(srs.getString(5));
//                    ret.setProjectId(nullify(srs.getString(6)));
//
//                    ret.setPrivateModel(srs.getBoolean(9));
//                    ret.setResolved(srs.getBoolean(10));
//                    ret.setReification(srs.getBoolean(11));
//                    ret.setInScenario(srs.getBoolean(12));
//                    ret.setHasDirectObjects(srs.getBoolean(13));
//                    ret.setHasDirectData(srs.getBoolean(14));
//                    ret.setTimeStart(srs.getLong(15));
//                    ret.setTimeEnd(srs.getLong(16));
//                    ret.setSpatial(srs.getBoolean(17));
//                    ret.setTemporal(srs.getBoolean(18));
//                    ret.setTimeMultiplicity(srs.getLong(19));
//                    ret.setSpaceMultiplicity(srs.getLong(20));
//                    ret.setScaleMultiplicity(srs.getLong(21));
//                    ret.setDereifyingAttribute(nullify(srs.getString(22)));
//                    ret.setMinSpatialScaleFactor(srs.getInt(23));
//                    ret.setMaxSpatialScaleFactor(srs.getInt(24));
//                    ret.setMinTimeScaleFactor(srs.getInt(25));
//                    ret.setMaxTimeScaleFactor(srs.getInt(26));
//                    Geometry geometry = srs.getGeometry(27);
//                    if (!geometry.isEmpty()) {
//                        ret.setShape(new ShapeValue(geometry, Geospace.get().getDefaultCRS())); // +
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
//        return "model";
//    }
//
//    /**
//     * @param name
//     * @return true if model with given id exists in database
//     * @throws KlabException
//     */
//    public boolean hasModel(String name) throws KlabException {
//
//        if (!database.hasTable("model")) {
//            return false;
//        }
//
//        return database.queryIds("SELECT oid FROM model WHERE name = '" + name + "';").size() > 0;
//    }
//    
//    @Override
//    protected int deleteAllObjectsWithNamespace(String namespaceId) throws KlabException {
//        int n = 0;
//        for (long oid : database
//                .queryIds("SELECT oid FROM model where namespaceid = '" + Escape.forSQL(namespaceId)
//                        + "';")) {
//            deleteObjectWithId(oid);
//            n++;
//        }
//        return n;
//    }
//
//    @Override
//    protected void deleteObjectWithId(long id) throws KlabException {
//        database.execute("DELETE FROM model WHERE oid = " + id);
//        deleteMetadataFor(id);
//    }
//
//    @Override
//    public long store(Object o) throws KlabException {
//
//        ArrayList<Object> toStore = new ArrayList<>();
//
//        if (o instanceof IModel) {
//
//            KLAB.info("storing model " + ((IModel) o).getName());
//
//            for (Model data : Models.inferModels((IModel) o, monitor)) {
//                toStore.add(data);
//            }
//
//        } else {
//            toStore.add(o);
//        }
//
//        long ret = -1;
//        for (Object obj : toStore) {
//            long r = super.store(obj);
//            if (ret < 0)
//                ret = r;
//        }
//
//        return ret;
//    }
//
//    public static final String DUMMY_NAMESPACE_ID = "DUMMY_SEARCH_NS";
//
//}
