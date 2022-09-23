package org.integratedmodelling.klab.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.h2gis.utilities.SpatialResultSet;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimStatement.Scope;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IEnumeratedExtent;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionConstraint;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.persistence.h2.SQL;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.ModelReference;
import org.integratedmodelling.klab.rest.ModelReference.Mediation;
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.scale.EnumeratedExtent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.Pair;
import org.locationtech.jts.geom.Geometry;

import com.google.common.collect.Sets;

public class ModelKbox extends ObservableKbox {

    private boolean workRemotely = !Configuration.INSTANCE.isOffline();
    private boolean initialized = false;

    /**
     * Create a kbox with the passed name. If the kbox exists, open it and return it.
     * 
     * @param name
     * @return a new kbox
     */
    public static ModelKbox create(String name) {
        return new ModelKbox(name);
    }

    private ModelKbox(String name) {
        super(name);
    }

    @Override
    protected void initialize(IMonitor monitor) {

        if (!initialized) {

            initialized = true;

            setSchema(ModelReference.class, new Schema(){

                @Override
                public String getTableName() {
                    return getMainTableId();
                }

                @Override
                public String getCreateSQL() {
                    String ret = "CREATE TABLE model (" + "oid LONG, " + "serverid VARCHAR(64), " + "id VARCHAR(256), "
                            + "name VARCHAR(256), " + "namespaceid VARCHAR(128), " + "projectid VARCHAR(128), " + "typeid LONG, "
                            + "otypeid LONG, " + "scope VARCHAR(16), " + "isresolved BOOLEAN, " + "isreification BOOLEAN, "
                            + "inscenario BOOLEAN, " + "hasdirectobjects BOOLEAN, " + "hasdirectdata BOOLEAN, "
                            + "timestart LONG, " + "timeend LONG, " + "isspatial BOOLEAN, " + "istemporal BOOLEAN, "
                            + "timemultiplicity LONG, " + "spacemultiplicity LONG, " + "scalemultiplicity LONG, "
                            + "dereifyingattribute VARCHAR(256), " + "minspatialscale INTEGER, " + "maxspatialscale INTEGER, "
                            + "mintimescale INTEGER, " + "maxtimescale INTEGER, " + "space GEOMETRY, "
                            + "observationtype VARCHAR(256), " + "enumeratedspacedomain VARCHAR(256), "
                            + "enumeratedspacelocation VARCHAR(1024), " + "specializedObservable BOOLEAN " + "); "
                            + "CREATE INDEX model_oid_index ON model(oid); "
                    // + "CREATE SPATIAL INDEX model_space ON model(space);"
                    ;

                    return ret;

                }
            });

            setSerializer(ModelReference.class, new Serializer<ModelReference>(){

                private String cn(Object o) {
                    return o == null ? "" : o.toString();
                }

                @Override
                public String serialize(ModelReference model, long primaryKey, long foreignKey) {

                    long tid = requireConceptId(model.getObservableConcept(), monitor);

                    String ret = "INSERT INTO model VALUES (" + primaryKey + ", " + "'" + cn(model.getServerId()) + "', " + "'"
                            + cn(model.getId()) + "', " + "'" + cn(model.getName()) + "', " + "'" + cn(model.getNamespaceId())
                            + "', " + "'" + cn(model.getProjectId()) + "', " + tid + ", "
                            + /* observation concept is obsolete oid */ 0 + ", '" + (model.getScope().name()) + "', "
                            + (model.isResolved() ? "TRUE" : "FALSE") + ", " + (model.isReification() ? "TRUE" : "FALSE") + ", "
                            + (model.isInScenario() ? "TRUE" : "FALSE") + ", " + (model.isHasDirectObjects() ? "TRUE" : "FALSE")
                            + ", " + (model.isHasDirectData() ? "TRUE" : "FALSE") + ", " + model.getTimeStart() + ", "
                            + model.getTimeEnd() + ", " + (model.isSpatial() ? "TRUE" : "FALSE") + ", "
                            + (model.isTemporal() ? "TRUE" : "FALSE") + ", " + model.getTimeMultiplicity() + ", "
                            + model.getSpaceMultiplicity() + ", " + model.getScaleMultiplicity() + ", " + "'"
                            + cn(model.getDereifyingAttribute()) + "', " + model.getMinSpatialScaleFactor() + ", "
                            + model.getMaxSpatialScaleFactor() + ", " + model.getMinTimeScaleFactor() + ", "
                            + model.getMaxTimeScaleFactor() + ", " + "'"
                            + (model.getShape() == null
                                    ? "GEOMETRYCOLLECTION EMPTY"
                                    : ((Shape) model.getShape()).getStandardizedGeometry().toString())
                            + "', '" + model.getObservationType() + "', '" + cn(model.getEnumeratedSpaceDomain()) + "', '"
                            + cn(model.getEnumeratedSpaceLocation()) + "', "
                            + (model.isSpecializedObservable() ? "TRUE" : "FALSE") + ");";

                    if (model.getMetadata() != null && model.getMetadata().size() > 0) {
                        storeMetadataFor(primaryKey, model.getMetadata());
                    }

                    return ret;
                }
            });

        }
    }

    /**
     * Pass the output of queryModelData to a contextual prioritizer and return the ranked list of
     * IModels. If we're a personal engine, also broadcast the query to the network and merge
     * results before returning.
     * 
     * @param observable
     * @param resolutionScope
     * @return models resulting from query, best first.
     * @throws KlabException
     */
    public List<IRankedModel> query(IObservable observable, ResolutionScope resolutionScope) throws KlabException {

        initialize(resolutionScope.getMonitor());

        // Contextualize the observable if needed. Don't do it if we're a predicate or
        // if
        // contextualization is deferred.
        if (resolutionScope.getContext() != null && !observable.getType().is(Type.PREDICATE)
                && ((Observable) observable).mustContextualizeAtResolution()) {
            observable = Observables.INSTANCE.contextualizeTo(observable, resolutionScope.getContext().getObservable().getType(),
                    true, resolutionScope.getMonitor());
        }

        Pair<Scale, Collection<IRankedModel>> preResolved = resolutionScope.isCaching()
                ? null
                : resolutionScope.getPreresolvedModels(observable);

        IPrioritizer<ModelReference> prioritizer = Resolver.getPrioritizer(resolutionScope);
        ModelQueryResult ret = new ModelQueryResult(prioritizer, resolutionScope.getMonitor());
        Set<ModelReference> local = new HashSet<>();

        /*
         * use previously resolved
         * 
         * TODO check use of contains(): overlaps() would be more correct but then we would need to
         * continue resolving, which misses the whole point of caching, and limit the resolution to
         * "other" models.
         * 
         * FIXME: MODELS FROM SCENARIOS MUST STILL TAKE OVER THESE!
         */
        if (preResolved != null && preResolved.getFirst().contains(resolutionScope.getCoverage())) {

            for (IRankedModel model : preResolved.getSecond()) {
                // rank them again in our scale
                ret.addCachedModel(model);
            }

            if (!Configuration.INSTANCE.resolveAllInstances()) {
                resolutionScope.getMonitor().debug("Model for " + observable + " was preset at resolution");
                return ret;
            }
        }

        /*
         * only query locally if we've seen a model before.
         */
        if (database.hasTable("model")) {
            for (ModelReference md : queryModels(observable, resolutionScope)) {
                if (Authentication.INSTANCE.canAccess(resolutionScope.getSession().getUser(), md.getProjectId())) {
                    local.add(md);
                    ret.addModel(md);
                }
            }
        }

        /**
         * If we're a modeling engine, dispatch the request to all nodes that allow it, which we do
         * simply by using the result list as a distributed operation.
         */
        if (Configuration.INSTANCE.isRemoteResolutionEnabled()) {
            // if (KLAB.ENGINE instanceof IModelingEngine && workRemotely) {
            //
            // ModelQuery mquery = new ModelQuery();
            // mquery.setObservable(KLAB.MFACTORY
            // .adapt(observable, org.integratedmodelling.common.beans.Observable.class));
            // mquery.setScope(KLAB.MFACTORY.adapt(context,
            // org.integratedmodelling.common.beans.Scope.class));
            // ret.setQuery(mquery);
            //
            // KLAB.ENGINE.getNetwork().broadcast(ret, ((ResolutionScope)
            // context).getMonitor());
        }

        /*
         * Warn and provide output if models were chosen but reported unavailability. Message is a
         * warning only if no other models were found.
         */
        if (ret.getOfflineModels().size() > 0) {

            String message = "warning: " + ret.getOfflineModels().size() + " model"
                    + (ret.getOfflineModels().size() < 2 ? " was" : "s were") + " chosen but found offline: ";

            for (ModelReference m : ret.getOfflineModels()) {
            	message += "\n   " + m.getName();
            }
            
            if (ret.size() > 0) {
                resolutionScope.getMonitor().info(message);
            } else {
                resolutionScope.getMonitor().warn(message);
            }

            for (ModelReference ref : ret.getOfflineModels()) {
                resolutionScope.getMonitor().debug("model " + ref.getName() + " is offline");
            }
        }

        return ret;
    }

    /**
     * Find and deserialize all modeldata matching the parameters. Do not rank or anything.
     * 
     * @param observable
     * @param context
     * @return all unranked model descriptors matching the query
     * @throws KlabException
     */
    public List<ModelReference> queryModels(IObservable observable, ResolutionScope context) throws KlabException {

        List<ModelReference> ret = new ArrayList<>();
        IUserIdentity user = context.getSession().getParentIdentity(IUserIdentity.class);
        Collection<IResolutionConstraint> constraints = context.getSession().getState().getResolutionConstraints();
        Set<String> userPermissions = new HashSet<>(user.getGroups().stream().map((g) -> g.getId()).collect(Collectors.toList()));

        if (!database.hasTable("model")) {
            return ret;
        }

        String query = "SELECT model.oid FROM model WHERE ";
        IConcept contextObservable = context.getContextObservable() == null ? null : context.getContextObservable().getType();
        String typequery = observableQuery(observable, contextObservable, context.getMode());

        if (typequery == null) {
            return ret;
        }

        query += "(" + scopeQuery(context, observable) + ")";
        query += " AND (" + typequery + ")";
        if (context.getCoverage().getSpace() != null) {
            String sq = spaceQuery(context.getCoverage().getSpace());
            if (!sq.isEmpty()) {
                query += " AND (" + sq + ")";
            }
        }

        String tquery = timeQuery(context.getCoverage().getTime());
        if (!tquery.isEmpty()) {
            query += " AND (" + tquery + ");";
        }

        // KLAB.info(query);

        final List<Long> oids = database.queryIds(query);

        for (long l : oids) {
            ModelReference model = retrieveModel(l, context.getMonitor());
            if (model != null) {
                if (isAuthorized(model, observable, userPermissions, constraints) && !context.isResolving(model.getName())) {
                    ret.add(model);
                }
            } else {
                Logging.INSTANCE.warn("kbox is out of sync with knowledge base");
            }
        }

        Logging.INSTANCE
                .info("model query for " + (context.getMode() == Mode.INSTANTIATION ? "instantiation of " : "explanation of ")
                        + observable + " found " + (ret.size() == 1 ? ret.get(0).getName() : (ret.size() + " models")));

        return ret;
    }

    private boolean isAuthorized(ModelReference model, IObservable observable, Set<String> userPermissions,
            Collection<IResolutionConstraint> constraints) {

        if (model.getProjectId() != null) {
            Set<String> permissions = Authentication.INSTANCE.getProjectPermissions(model.getProjectId());
            if (!permissions.isEmpty()) {
                if (Sets.intersection(permissions, userPermissions).size() == 0) {
                    return false;
                }
            }
        }

        if (constraints != null) {
            for (IResolutionConstraint c : constraints) {
                IKimObject m = Resources.INSTANCE.getModelObject(model.getUrn());
                if (m instanceof IModel) {
                    if (!c.accepts((IModel) m, observable)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private String observableQuery(IObservable observable, IConcept context, Mode mode) {

        Set<Long> ids = this.getCompatibleTypeIds(observable, context, mode);
        if (ids == null || ids.size() == 0) {
            return null;
        }
        String ret = "";
        for (long id : ids) {
            ret += (ret.isEmpty() ? "" : ", ") + id;
        }
        return "typeid IN (" + ret + ")";
    }

    /*
     * select models that are [instantiators if required] AND:] [private and in the home namespace
     * if not dummy OR] [project private and in the home project if not dummy OR] (non-private and
     * non-scenario) OR (in any of the scenarios in the context).
     */
    private String scopeQuery(IResolutionScope context, IObservable observable) {

        String ret = "";
        String projectId = null;
        String namespaceId = context.getResolutionNamespace() == null
                ? DUMMY_NAMESPACE_ID
                : context.getResolutionNamespace().getId();
        if (!namespaceId.equals(DUMMY_NAMESPACE_ID)) {
            ret += "(model.namespaceid = '" + namespaceId + "')";
            projectId = context.getResolutionNamespace().getProject() == null
                    ? null
                    : context.getResolutionNamespace().getProject().getName();
        }

        ret += (ret.isEmpty() ? "" : " OR ") + "((NOT model.scope = 'NAMESPACE') AND (NOT model.inscenario))";

        if (context.getScenarios() != null && context.getScenarios().size() > 0) {
            ret += " OR (" + joinStringConditions("model.namespaceid", context.getScenarios(), "OR") + ")";
        }

        if (observable.is(Type.COUNTABLE)) {
            if (context.getMode() == Mode.INSTANTIATION) {
                ret = "(" + ret + ") AND model.isreification";
            } else {
                ret = "(" + ret + ") AND (NOT model.isreification)";
            }
        }

        if (projectId != null) {
            ret += " AND (NOT (model.scope = 'PROJECT' AND model.projectid <> '" + projectId + "'))";
        }

        return ret;
    }

    /*
     * select models that intersect the given space or have no space at all. TODO must match
     * geometry when forced - if it has @intensive(space, time) it shouldn't match no space/time OR
     * non-distributed space/time. ALSO the dimensionality!
     */
    private String spaceQuery(ISpace space) {

        if (space instanceof IEnumeratedExtent) {
            // Accept anything that is from the same authority or baseconcept. If the
            // requesting
            // context needs specific values, these should be checked later in the
            // prioritizer.
            Pair<String, String> defs = ((EnumeratedExtent) space).getExtentDescriptors();
            return "model.enumeratedspacedomain = '" + defs.getFirst() + "'";
        }

        if (((ISpace) ((AbstractExtent) space).getExtent()).getShape().isEmpty()) {
            return "";
        }

        String scalequery = space.getScaleRank() + " BETWEEN model.minspatialscale AND model.maxspatialscale";

        String spacequery = "model.space && '" + ((Shape) space.getShape()).getStandardizedGeometry()
                + "' OR ST_IsEmpty(model.space)";

        return "(" + scalequery + ") AND (" + spacequery + ")";
    }

    /*
     * Entirely TODO. For initialization we should use time only to select for most current info -
     * either closer to the context or to today if time is null. For dynamic models we should either
     * not have a context or cover the context. Guess this is the job of the prioritizer, and we
     * should simply let anything through except when we look for T1(n>1) models.
     * 
     * TODO must match geometry when forced - if it has @intensive(space, time) it shouldn't match
     * no space/time OR non-distributed space/time. ALSO the dimensionality!
     */
    private String timeQuery(ITime time) {

        String ret = "";
        boolean checkBoundaries = false;
        if (time != null && checkBoundaries) {
            ret = "(timestart == -1 AND timeend == -1) OR (";
            long start = time.getStart() == null ? -1 : time.getStart().getMilliseconds();
            long end = time.getEnd() == null ? -1 : time.getEnd().getMilliseconds();
            if (start > 0 && end > 0) {
                ret += "timestart >= " + start + " AND timeend <= " + end;
            } else if (start > 0) {
                ret += "timestart >= " + start;
            } else if (end > 0) {
                ret += "timeend <= " + end;
            }
            ret += ")";
        }
        return ret;
    }

    public List<ModelReference> retrieveAll(IMonitor monitor) throws KlabException {

        initialize(monitor);

        List<ModelReference> ret = new ArrayList<>();
        if (!database.hasTable("model")) {
            return ret;
        }
        for (long oid : database.queryIds("SELECT oid FROM model;")) {
            ret.add(retrieveModel(oid, monitor));
        }
        return ret;
    }

    public ModelReference retrieve(String query, IMonitor monitor) {
        initialize(monitor);

        final ModelReference ret = new ModelReference();

        database.query(query, new SQL.SimpleResultHandler(){
            @Override
            public void onRow(ResultSet rs) {

                try {

                    SpatialResultSet srs = rs.unwrap(SpatialResultSet.class);

                    long tyid = srs.getLong(7);

                    ret.setName(srs.getString(4));

                    IConcept mtype = getType(tyid).getType();

                    ret.setObservableConcept(mtype);
                    ret.setObservable(getTypeDefinition(tyid));

                    ret.setServerId(nullify(srs.getString(2)));
                    ret.setId(srs.getString(3));

                    ret.setNamespaceId(srs.getString(5));
                    ret.setProjectId(nullify(srs.getString(6)));

                    ret.setScope(Scope.valueOf(srs.getString(9)));
                    ret.setResolved(srs.getBoolean(10));
                    ret.setReification(srs.getBoolean(11));
                    ret.setInScenario(srs.getBoolean(12));
                    ret.setHasDirectObjects(srs.getBoolean(13));
                    ret.setHasDirectData(srs.getBoolean(14));
                    ret.setTimeStart(srs.getLong(15));
                    ret.setTimeEnd(srs.getLong(16));
                    ret.setSpatial(srs.getBoolean(17));
                    ret.setTemporal(srs.getBoolean(18));
                    ret.setTimeMultiplicity(srs.getLong(19));
                    ret.setSpaceMultiplicity(srs.getLong(20));
                    ret.setScaleMultiplicity(srs.getLong(21));
                    ret.setDereifyingAttribute(nullify(srs.getString(22)));
                    ret.setMinSpatialScaleFactor(srs.getInt(23));
                    ret.setMaxSpatialScaleFactor(srs.getInt(24));
                    ret.setMinTimeScaleFactor(srs.getInt(25));
                    ret.setMaxTimeScaleFactor(srs.getInt(26));
                    Geometry geometry = srs.getGeometry(27);
                    if (!geometry.isEmpty()) {
                        ret.setShape(Shape.create(geometry, Projection.getLatLon())); // +
                    }
                } catch (SQLException e) {
                    throw new KlabStorageException(e);
                }
            }

        });

        return ret;
    }

    public ModelReference retrieveModel(long oid, IMonitor monitor) throws KlabException {

        ModelReference ret = retrieve("SELECT * FROM model WHERE oid = " + oid, monitor);
        ret.setMetadata(getMetadataFor(oid));
        return ret;
        //
        // initialize(monitor);
        //
        // final ModelReference ret = new ModelReference();
        //
        // database.query("SELECT * FROM model WHERE oid = " + oid, new
        // SQL.SimpleResultHandler() {
        // @Override
        // public void onRow(ResultSet rs) {
        //
        // try {
        //
        // SpatialResultSet srs = rs.unwrap(SpatialResultSet.class);
        //
        // long tyid = srs.getLong(7);
        //
        // ret.setName(srs.getString(4));
        //
        // IConcept mtype = getType(tyid);
        //
        // ret.setObservableConcept(mtype);
        // ret.setObservable(getTypeDefinition(tyid));
        //
        // ret.setServerId(nullify(srs.getString(2)));
        // ret.setId(srs.getString(3));
        //
        // ret.setNamespaceId(srs.getString(5));
        // ret.setProjectId(nullify(srs.getString(6)));
        //
        // ret.setPrivateModel(srs.getBoolean(9));
        // ret.setResolved(srs.getBoolean(10));
        // ret.setReification(srs.getBoolean(11));
        // ret.setInScenario(srs.getBoolean(12));
        // ret.setHasDirectObjects(srs.getBoolean(13));
        // ret.setHasDirectData(srs.getBoolean(14));
        // ret.setTimeStart(srs.getLong(15));
        // ret.setTimeEnd(srs.getLong(16));
        // ret.setSpatial(srs.getBoolean(17));
        // ret.setTemporal(srs.getBoolean(18));
        // ret.setTimeMultiplicity(srs.getLong(19));
        // ret.setSpaceMultiplicity(srs.getLong(20));
        // ret.setScaleMultiplicity(srs.getLong(21));
        // ret.setDereifyingAttribute(nullify(srs.getString(22)));
        // ret.setMinSpatialScaleFactor(srs.getInt(23));
        // ret.setMaxSpatialScaleFactor(srs.getInt(24));
        // ret.setMinTimeScaleFactor(srs.getInt(25));
        // ret.setMaxTimeScaleFactor(srs.getInt(26));
        // Geometry geometry = srs.getGeometry(27);
        // if (!geometry.isEmpty()) {
        // ret.setShape(Shape.create(geometry, Projection.getLatLon())); // +
        // }
        // } catch (SQLException e) {
        // throw new KlabStorageException(e);
        // }
        // }
        //
        // });
        //
        // ret.setMetadata(getMetadataFor(oid));
        //
        // return ret;
    }

    @Override
    protected String getMainTableId() {
        return "model";
    }

    /**
     * @param name
     * @return true if model with given id exists in database
     * @throws KlabException
     */
    public boolean hasModel(String name) throws KlabException {

        if (!database.hasTable("model")) {
            return false;
        }

        return database.queryIds("SELECT oid FROM model WHERE name = '" + name + "';").size() > 0;
    }

    @Override
    protected int deleteAllObjectsWithNamespace(String namespaceId, IMonitor monitor) throws KlabException {
        initialize(monitor);
        int n = 0;
        for (long oid : database.queryIds("SELECT oid FROM model where namespaceid = '" + Escape.forSQL(namespaceId) + "';")) {
            deleteObjectWithId(oid, monitor);
            n++;
        }
        return n;
    }

    @Override
    protected void deleteObjectWithId(long id, IMonitor monitor) throws KlabException {
        initialize(monitor);
        database.execute("DELETE FROM model WHERE oid = " + id);
        deleteMetadataFor(id);
    }

    @Override
    public long store(Object o, IMonitor monitor) throws KlabException {

        initialize(monitor);

        if (o instanceof INamespace && ((INamespace) o).isInternal()) {
            return 0;
        }

        ArrayList<Object> toStore = new ArrayList<>();

        if (o instanceof org.integratedmodelling.klab.model.Model) {

            Logging.INSTANCE.debug("storing model " + ((IModel) o).getName());

            for (ModelReference data : inferModels((org.integratedmodelling.klab.model.Model) o, monitor)) {
                toStore.add(data);
            }

        } else {
            toStore.add(o);
        }

        long ret = -1;
        for (Object obj : toStore) {
            long r = super.store(obj, monitor);
            if (ret < 0)
                ret = r;
        }

        return ret;
    }

    public static final String DUMMY_NAMESPACE_ID = "DUMMY_SEARCH_NS";

    /**
     * Return a collection of model beans that contains all the models implied by a model statement
     * (and the model itself, when appropriate).
     * 
     * @param model
     * @param monitor
     * @return the models implied by the statement
     */
    public static Collection<ModelReference> inferModels(Model model, IMonitor monitor) {

        List<ModelReference> ret = new ArrayList<>();

        // happens in error
        if (model.getObservables().size() == 0 || model.getObservables().get(0) == null) {
            return ret;
        }

        for (ModelReference m : getModelDescriptors(model, monitor)) {
            ret.add(m);
        }

        if (ret.size() > 0) {

            for (IObservable attr : model.getAttributeObservables().values()) {

                if (attr == null) {
                    // only in error
                    continue;
                }

                // attribute type must have inherent type added if it's an instantiated quality
                // (from an instantiator or as a secondary
                // observable of a resolver with explicit, specialized inherency)
                IConcept type = attr.getType();
                if (model.isInstantiator()) {
                    IConcept context = Observables.INSTANCE.getContextType(type);
                    if (context == null || !context.is(model.getObservables().get(0))) {
                        type = attr.getBuilder(monitor).of(model.getObservables().get(0).getType()).buildConcept();
                    }
                }
                ModelReference m = ret.get(0).copy();
                m.setObservable(type.getDefinition());
                m.setObservableConcept(type.getType());
                m.setObservationType(attr.getArtifactType().name());
                m.setDereifyingAttribute(attr.getName());
                m.setMediation(Mediation.DEREIFY_QUALITY);
                m.setPrimaryObservable(!model.isInstantiator());
                ret.add(m);
            }

            if (model.isInstantiator()) {
                // TODO add presence model for main observable type and
                // dereifying models for all mandatory attributes of observable in context
            }
        }

        return ret;
    }

    private static Collection<ModelReference> getModelDescriptors(org.integratedmodelling.klab.model.Model model,
            IMonitor monitor) {

        List<ModelReference> ret = new ArrayList<>();
        Scale scale = null;

        try {
            scale = model.getCoverage(monitor);
        } catch (Exception e) {
            return ret;
        }

        Shape spaceExtent = null;
        ITime timeExtent = null;
        long spaceMultiplicity = -1;
        long timeMultiplicity = -1;
        long scaleMultiplicity = 1;
        long timeStart = -1;
        long timeEnd = -1;
        boolean isSpatial = false;
        boolean isTemporal = false;
        String enumeratedSpaceDomain = null;
        String enumeratedSpaceLocation = null;
        IProject project = model.getNamespace().getProject();

        if (scale != null) {

            scaleMultiplicity = scale.size();
            if (scale.getSpace() instanceof IEnumeratedExtent) {
                Pair<String, String> defs = ((EnumeratedExtent) scale.getSpace()).getExtentDescriptors();
                enumeratedSpaceDomain = defs.getFirst();
                enumeratedSpaceLocation = defs.getSecond();
            } else if (scale.getSpace() != null) {
                spaceExtent = (Shape) scale.getSpace().getShape();
                // may be null when we just say 'over space'.
                if (spaceExtent != null) {
                    spaceExtent = spaceExtent.transform(Projection.getLatLon());
                    spaceMultiplicity = scale.getSpace().size();
                }
                isSpatial = true;
            }

            if (scale.getTime() != null) {
                timeExtent = (ITime) ((AbstractExtent) scale.getTime()).getExtent();
                if (timeExtent != null) {
                    if (timeExtent.getStart() != null) {
                        timeStart = timeExtent.getStart().getMilliseconds();
                    }
                    if (timeExtent.getEnd() != null) {
                        timeEnd = timeExtent.getEnd().getMilliseconds();
                    }
                    timeMultiplicity = scale.getTime().size();
                }
                isTemporal = true;
            }
        }

        boolean first = true;
        IObservable main = null;
        for (IObservable oobs : model.getObservables()) {

            if (first) {
                main = oobs;
            }

            for (IObservable obs : unpackObservables(oobs, main, first, monitor)) {

                ModelReference m = new ModelReference();

                m.setId(model.getId());
                m.setName(model.getName());
                m.setNamespaceId(model.getNamespace().getName());
                if (model.getNamespace().getProject() != null) {
                    m.setProjectId(model.getNamespace().getProject().getName());
                    if (model.getNamespace().getProject().isRemote()) {
                        m.setServerId(model.getNamespace().getProject().getOriginatingNodeId());
                    }
                }

                if (project != null) {
                    m.getPermissions().addAll(Authentication.INSTANCE.getProjectPermissions(project.getName()));
                }

                m.setTimeEnd(timeEnd);
                m.setTimeStart(timeStart);
                m.setTimeMultiplicity(timeMultiplicity);
                m.setSpaceMultiplicity(spaceMultiplicity);
                m.setScaleMultiplicity(scaleMultiplicity);
                m.setSpatial(isSpatial);
                m.setTemporal(isTemporal);
                m.setShape(spaceExtent);
                m.setEnumeratedSpaceDomain(enumeratedSpaceDomain);
                m.setEnumeratedSpaceLocation(enumeratedSpaceLocation);

                m.setObservable(obs.getType().getDefinition());
                m.setObservationType(obs.getDescriptionType().name());
                m.setObservableConcept(obs.getType());
                // m.setObservationConcept(obs.getObservationType());

                m.setScope(model.getScope());
                m.setInScenario(model.getNamespace().isScenario());
                m.setReification(model.isInstantiator());
                m.setResolved(model.isResolved());
                m.setHasDirectData(model.isResolved() && model.getObservables().get(0).is(Type.QUALITY));
                m.setHasDirectObjects(model.isResolved() && model.getObservables().get(0).is(Type.DIRECT_OBSERVABLE));

                m.setMinSpatialScaleFactor(model.getMetadata().get(IMetadata.IM_MIN_SPATIAL_SCALE, ISpace.MIN_SCALE_RANK));
                m.setMaxSpatialScaleFactor(model.getMetadata().get(IMetadata.IM_MAX_SPATIAL_SCALE, ISpace.MAX_SCALE_RANK));
                m.setMinTimeScaleFactor(model.getMetadata().get(IMetadata.IM_MIN_TEMPORAL_SCALE, ITime.MIN_SCALE_RANK));
                m.setMaxTimeScaleFactor(model.getMetadata().get(IMetadata.IM_MAX_TEMPORAL_SCALE, ITime.MAX_SCALE_RANK));

                m.setPrimaryObservable(first);

                if (first && obs.isSpecialized()) {
                    m.setSpecializedObservable(true);
                }

                first = false;

                m.setMetadata(translateMetadata(model.getMetadata()));

                ret.add(m);

            }

            /*
             * For now just disable additional observables in instantiators and use their attribute
             * observers upstream. We may do different things here:
             * 
             * 0. keep ignoring them 1. keep them all, contextualized to the instantiated
             * observable; 2. keep only the non-statically contextualized ones (w/o the value)
             * 
             */
            if (model.isInstantiator()) {
                break;
            }

        }
        return ret;
    }

    private static List<IObservable> unpackObservables(IObservable oobs, IObservable main, boolean first, IMonitor monitor) {

        List<IObservable> ret = new ArrayList<>();
        if (!first) {
            /**
             * Subsequent observables inherit any explicit specialization in the main observable of
             * a model
             */
            IConcept specialized = Observables.INSTANCE.getDirectContextType(main.getType());
            if (specialized != null && (oobs.getContext() == null || !oobs.getContext().is(specialized))) {
                oobs = oobs.getBuilder(monitor).within(specialized).buildObservable();
            }
        }
        ret.add(oobs);
        return ret;
    }

    private static Map<String, String> translateMetadata(IMetadata metadata) {
        Map<String, String> ret = new HashMap<>();
        for (String key : metadata.keySet()) {
            ret.put(key, metadata.get(key) == null ? "null" : metadata.get(key).toString());
        }
        return ret;
    }

    public ModelReference retrieveModel(String string, IMonitor monitor) {
        return retrieve("SELECT * FROM model WHERE name = '" + string + "'", monitor);
    }

}
