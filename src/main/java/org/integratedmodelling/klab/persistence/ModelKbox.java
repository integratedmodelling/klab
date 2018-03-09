package org.integratedmodelling.klab.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.h2gis.utilities.SpatialResultSet;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.rest.resources.Model;
import org.integratedmodelling.klab.data.rest.resources.Model.Mediation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.persistence.h2.SQL;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.utils.Escape;
import com.vividsolutions.jts.geom.Geometry;

public class ModelKbox extends ObservableKbox {

  private boolean workRemotely = !Configuration.INSTANCE.isOffline();
  private boolean initialized = false;

  /**
   * Create a kbox with the passed name. If the kbox exists, open it and return it.
   * 
   * @param name
   * @return
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

      setSchema(Model.class, new Schema() {

        @Override
        public String getTableName() {
          return getMainTableId();
        }

        @Override
        public String getCreateSQL() {
          String ret = "CREATE TABLE model (" + "oid LONG, " + "serverid VARCHAR(64), "
              + "id VARCHAR(256), " + "name VARCHAR(256), " + "namespaceid VARCHAR(128), "
              + "projectid VARCHAR(128), " + "typeid LONG, " + "otypeid LONG, "
              + "isprivate BOOLEAN, " + "isresolved BOOLEAN, " + "isreification BOOLEAN, "
              + "inscenario BOOLEAN, " + "hasdirectobjects BOOLEAN, " + "hasdirectdata BOOLEAN, "
              + "timestart LONG, " + "timeend LONG, " + "isspatial BOOLEAN, "
              + "istemporal BOOLEAN, " + "timemultiplicity LONG, " + "spacemultiplicity LONG, "
              + "scalemultiplicity LONG, " + "dereifyingattribute VARCHAR(256), "
              + "minspatialscale INTEGER, " + "maxspatialscale INTEGER, " + "mintimescale INTEGER, "
              + "maxtimescale INTEGER, " + "space GEOMETRY, " + "); "
              + "CREATE INDEX model_oid_index ON model(oid); "
              + "CREATE SPATIAL INDEX model_space ON model(space);";

          return ret;

        }
      });

      setSerializer(Model.class, new Serializer<Model>() {

        private String cn(Object o) {
          return o == null ? "" : o.toString();
        }

        @Override
        public String serialize(Model model, long primaryKey, long foreignKey) {

          long tid = requireConceptId(model.getObservableConcept(), monitor);

          String ret =
              "INSERT INTO model VALUES (" + primaryKey + ", " + "'" + cn(model.getServerId())
                  + "', " + "'" + cn(model.getId()) + "', " + "'" + cn(model.getName()) + "', "
                  + "'" + cn(model.getNamespaceId()) + "', " + "'" + cn(model.getProjectId())
                  + "', " + tid + ", " + /* observation concept is obsolete oid */ 0 + ", "
                  + (model.isPrivateModel() ? "TRUE" : "FALSE") + ", "
                  + (model.isResolved() ? "TRUE" : "FALSE") + ", "
                  + (model.isReification() ? "TRUE" : "FALSE") + ", "
                  + (model.isInScenario() ? "TRUE" : "FALSE") + ", "
                  + (model.isHasDirectObjects() ? "TRUE" : "FALSE") + ", "
                  + (model.isHasDirectData() ? "TRUE" : "FALSE") + ", " + model.getTimeStart()
                  + ", " + model.getTimeEnd() + ", " + (model.isSpatial() ? "TRUE" : "FALSE") + ", "
                  + (model.isTemporal() ? "TRUE" : "FALSE") + ", " + model.getTimeMultiplicity()
                  + ", " + model.getSpaceMultiplicity() + ", " + model.getScaleMultiplicity() + ", "
                  + "'" + cn(model.getDereifyingAttribute()) + "', "
                  + model.getMinSpatialScaleFactor() + ", " + model.getMaxSpatialScaleFactor()
                  + ", " + model.getMinTimeScaleFactor() + ", " + model.getMaxTimeScaleFactor()
                  + ", " + "'" + (model.getShape() == null ? "GEOMETRYCOLLECTION EMPTY"
                      : model.getShape().getStandardizedGeometry().toString())
                  + "'" + ");";

          if (model.getMetadata() != null && model.getMetadata().getDataAsMap().size() > 0) {
            storeMetadataFor(primaryKey, model.getMetadata());
          }

          return ret;
        }
      });

    }
  }

  /**
   * Pass the output of queryModelData to a contextual prioritizer and return the ranked list of
   * IModels. If we're a personal engine, also broadcast the query to the network and merge results
   * before returning.
   * 
   * @param observable
   * @param context
   * @return models resulting from query, best first.
   * @throws KlabException
   */
  public List<IRankedModel> query(IObservable observable, ResolutionScope context) throws KlabException {

    initialize(context.getMonitor());

    IPrioritizer<Model> prioritizer = Resolver.INSTANCE.getPrioritizer(context);
    ModelQueryResult ret = new ModelQueryResult(prioritizer, context.getMonitor());
    Set<Model> local = new HashSet<>();

    /*
     * only query locally if we've seen a model before.
     */
    if (database.hasTable("model")) {
      for (Model md : queryModels(observable, context)) {
        local.add(md);
        ret.addModel(md);
      }
    }

    /**
     * If we're a modeling engine, dispatch the request to all nodes that allow it, which we do
     * simply by using the result list as a distributed operation.
     */
    // if (KLAB.ENGINE instanceof IModelingEngine && workRemotely) {
    //
    // ModelQuery mquery = new ModelQuery();
    // mquery.setObservable(KLAB.MFACTORY
    // .adapt(observable, org.integratedmodelling.common.beans.Observable.class));
    // mquery.setScope(KLAB.MFACTORY.adapt(context,
    // org.integratedmodelling.common.beans.Scope.class));
    // ret.setQuery(mquery);
    //
    // KLAB.ENGINE.getNetwork().broadcast(ret, ((ResolutionScope) context).getMonitor());
    // }

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
  public List<Model> queryModels(IObservable observable, ResolutionScope context)
      throws KlabException {

    List<Model> ret = new ArrayList<>();

    if (!database.hasTable("model")) {
      return ret;
    }

    String query = "SELECT model.oid FROM model WHERE ";
    String typequery = observableQuery(observable);

    if (typequery == null) {
      return ret;
    }

    query += "(" + scopeQuery(context, observable) + ")";
    query += " AND (" + typequery + ")";
    if (context.getScale().getSpace() != null) {
      String sq = spaceQuery(context.getScale().getSpace());
      if (!sq.isEmpty()) {
        query += " AND (" + sq + ")";
      }
    }

    String tquery = timeQuery(context.getScale().getTime());
    if (!tquery.isEmpty()) {
      query += " AND (" + tquery + ");";
    }

    // KLAB.info(query);

    final List<Long> oids = database.queryIds(query);

    for (long l : oids) {
      Model model = retrieveModel(l, context.getMonitor());
      if (model != null) {
        ret.add(model);
      } else {
        Klab.INSTANCE.warn("kbox is out of sync with knowledge base");
      }
    }

    Klab.INSTANCE.info("model query for "
        + (context.getMode() == Mode.INSTANTIATION ? "instantiation of " : "explanation of ")
        + observable + " found "
        + (ret.size() == 1 ? ret.get(0).getName() : (ret.size() + " models")));

    return ret;
  }

  private String observableQuery(IObservable observable) {

    // /*
    // * remove any transformations before querying
    // */
    // IConcept concept = observable.getMain();

    Set<Long> ids = this.getCompatibleTypeIds(observable);
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
   * select models that are [instantiators if required] AND:] [private and in the home namespace if
   * not dummy OR] (non-private and non-scenario) OR (in any of the scenarios in the context).
   */
  private String scopeQuery(IResolutionScope context, IObservable observable) {

    String ret = "";

    String namespaceId = context.getResolutionNamespace() == null ? DUMMY_NAMESPACE_ID
        : context.getResolutionNamespace().getId();
    if (!namespaceId.equals(DUMMY_NAMESPACE_ID)) {
      // ret += "(model.isprivate AND model.namespaceid = '" + namespaceId
      // + "')";
      ret += "(model.namespaceid = '" + namespaceId + "')";
    }

    ret += (ret.isEmpty() ? "" : " OR ") + "((NOT model.isprivate) AND (NOT model.inscenario))";

    if (context.getScenarios() != null && context.getScenarios().size() > 0) {
      ret +=
          " OR (" + joinStringConditions("model.namespaceid", context.getScenarios(), "OR") + ")";
    }

    if (observable.is(Type.COUNTABLE)) {
      if (context.getMode() == Mode.INSTANTIATION) {
        ret = "(" + ret + ") AND model.isreification";
      } else {
        ret = "(" + ret + ") AND (NOT model.isreification)";
      }
    }

    return ret;
  }

  /*
   * select models that intersect the given space or have no space at all.
   */
  private String spaceQuery(Space space) {

    if (space.getExtent().getShape().isEmpty()) {
      return "";
    }

    String scalequery =
        space.getScaleRank() + " BETWEEN model.minspatialscale AND model.maxspatialscale";

    String spacequery = "model.space && '" + space.getShape().getStandardizedGeometry()
        + "' OR ST_IsEmpty(model.space)";

    return "(" + scalequery + ") AND (" + spacequery + ")";
  }

  /*
   * Entirely TODO. For initialization we should use time only to select for most current info -
   * either closer to the context or to today if time is null. For dynamic models we should either
   * not have a context or cover the context. Guess this is the job of the prioritizer, and we
   * should simply let anything through except when we look for process models.
   */
  private String timeQuery(ITime time) {

    String ret = "";
    boolean checkBoundaries = false;
    if (time != null && checkBoundaries) {
      ret = "(timestart == -1 AND timeend == -1) OR (";
      long start = time.getStart() == null ? -1 : time.getStart().getMillis();
      long end = time.getEnd() == null ? -1 : time.getEnd().getMillis();
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

  public List<Model> retrieveAll(IMonitor monitor) throws KlabException {

    initialize(monitor);

    List<Model> ret = new ArrayList<>();
    if (!database.hasTable("model")) {
      return ret;
    }
    for (long oid : database.queryIds("SELECT oid FROM model;")) {
      ret.add(retrieveModel(oid, monitor));
    }
    return ret;
  }

  public Model retrieveModel(long oid, IMonitor monitor) throws KlabException {

    initialize(monitor);

    final Model ret = new Model();

    database.query("SELECT * FROM model WHERE oid = " + oid, new SQL.SimpleResultHandler() {
      @Override
      public void onRow(ResultSet rs) {

        try {

          SpatialResultSet srs = rs.unwrap(SpatialResultSet.class);

          long tyid = srs.getLong(7);

          ret.setName(srs.getString(4));

          IConcept mtype = getType(tyid);

          ret.setObservableConcept(mtype);
          ret.setObservable(getTypeDefinition(tyid));

          ret.setServerId(nullify(srs.getString(2)));
          ret.setId(srs.getString(3));

          ret.setNamespaceId(srs.getString(5));
          ret.setProjectId(nullify(srs.getString(6)));

          ret.setPrivateModel(srs.getBoolean(9));
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
          throw new KlabRuntimeException(e);
        }
      }

    });

    ret.setMetadata(getMetadataFor(oid));

    return ret;
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
  protected int deleteAllObjectsWithNamespace(String namespaceId, IMonitor monitor)
      throws KlabException {
    initialize(monitor);
    int n = 0;
    for (long oid : database.queryIds(
        "SELECT oid FROM model where namespaceid = '" + Escape.forSQL(namespaceId) + "';")) {
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

    ArrayList<Object> toStore = new ArrayList<>();

    if (o instanceof org.integratedmodelling.klab.model.Model) {

      Klab.INSTANCE.info("storing model " + ((IModel) o).getName());

      for (Model data : inferModels((org.integratedmodelling.klab.model.Model) o, monitor)) {
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
   * @return
   */
  public static Collection<Model> inferModels(org.integratedmodelling.klab.model.Model model,
      IMonitor monitor) {
    List<Model> ret = new ArrayList<>();

    for (Model m : getModelDescriptors(model, monitor)) {
      ret.add(m);
    }
    if (ret.size() > 0) {
      /*
       * the observer come out of getAttributeObservers() with their inherent type already set
       */
      for (IObservable attr : model.getAttributeObservables().values()) {
        Model m = ret.get(0).copy();
        m.setObservable(attr.getType().getDefinition());
        m.setObservableConcept(attr.getType());
        m.setObservationType(attr.getObservationType().name());
        // m.setObservationConcept(attr.getObservationType());
        m.setDereifyingAttribute(attr.getLocalName());
        m.setMediation(Mediation.DEREIFY_QUALITY);
        ret.add(m);
      }

      if (model.isInstantiator()) {
        // TODO add presence model for main observable type and
        // dereifying models for all mandatory attributes of observable in context
      }
    }

    if (model.getObservables().get(0).is(Type.CLASS)) {

      Collection<IConcept> trs =
          Types.INSTANCE.getExposedTraits(model.getObservables().get(0).getType());
      IConcept context =
          Observables.INSTANCE.getContextType(model.getObservables().get(0).getType());
      IConcept inherent =
          Observables.INSTANCE.getInherentType(model.getObservables().get(0).getType());

      if (trs != null) {
        for (IConcept tr : trs) {
          /**
           * TODO add model for type of given trait with context and inherency from class
           */
          IConcept tinherent = Observables.INSTANCE.getInherentType(tr);
        }
      }

    }

    return ret;
  }

  private static Collection<Model> getModelDescriptors(
      org.integratedmodelling.klab.model.Model model, IMonitor monitor) {

    List<Model> ret = new ArrayList<>();
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

    if (scale != null) {

      scaleMultiplicity = scale.size();
      if (scale.getSpace() != null) {
        spaceExtent = scale.getSpace().getShape();
        // may be null when we just say 'over space'.
        if (spaceExtent != null) {
          spaceMultiplicity = scale.getSpace().size();
        }
        isSpatial = true;
      }
      if (scale.getTime() != null) {
        timeExtent = scale.getTime().getExtent();
        if (timeExtent != null) {
          if (timeExtent.getStart() != null) {
            timeStart = timeExtent.getStart().getMillis();
          }
          if (timeExtent.getEnd() != null) {
            timeEnd = timeExtent.getEnd().getMillis();
          }
          timeMultiplicity = scale.getTime().size();
        }
        isTemporal = true;
      }
    }

    boolean first = true;
    for (IObservable obs : model.getObservables()) {

      Model m = new Model();

      m.setId(model.getId());
      m.setName(model.getName());
      m.setNamespaceId(model.getNamespace().getId());
      m.setProjectId(model.getNamespace().getProject().getName());
      if (model.getNamespace().getProject().isRemote()) {
        m.setServerId(model.getNamespace().getProject().getOriginatingNodeId());
      }

      m.setTimeEnd(timeEnd);
      m.setTimeStart(timeStart);
      m.setTimeMultiplicity(timeMultiplicity);
      m.setSpaceMultiplicity(spaceMultiplicity);
      m.setScaleMultiplicity(scaleMultiplicity);
      m.setSpatial(isSpatial);
      m.setTemporal(isTemporal);
      m.setShape(spaceExtent);

      m.setObservable(obs.getType().getDefinition());
      m.setObservationType(obs.getObservationType().name());
      m.setObservableConcept(obs.getType());
      // m.setObservationConcept(obs.getObservationType());

      m.setPrivateModel(model.isPrivate());
      m.setInScenario(model.getNamespace().isScenario());
      m.setReification(model.isInstantiator());
      m.setResolved(model.isResolved());
      m.setHasDirectData(model.isResolved() && model.getObservables().get(0).is(Type.QUALITY));
      m.setHasDirectObjects(
          model.isResolved() && model.getObservables().get(0).is(Type.DIRECT_OBSERVABLE));

      m.setMinSpatialScaleFactor(
          model.getMetadata().getInt(IMetadata.IM_MIN_SPATIAL_SCALE, ISpace.MIN_SCALE_RANK));
      m.setMaxSpatialScaleFactor(
          model.getMetadata().getInt(IMetadata.IM_MAX_SPATIAL_SCALE, ISpace.MAX_SCALE_RANK));
      m.setMinTimeScaleFactor(
          model.getMetadata().getInt(IMetadata.IM_MIN_TEMPORAL_SCALE, ITime.MIN_SCALE_RANK));
      m.setMaxTimeScaleFactor(
          model.getMetadata().getInt(IMetadata.IM_MAX_TEMPORAL_SCALE, ITime.MAX_SCALE_RANK));

      m.setPrimaryObservable(first);
      first = false;

      m.setMetadata((Metadata) model.getMetadata());

      ret.add(m);
    }
    return ret;
  }

  public static String generateObjectModelSource(IConcept observable, String objectSource,
      Map<String, IObservable> attributes, String nameAttribute) {

    String ret = "model each " + objectSource + " as " + observable.getDefinition();

    if (nameAttribute != null || (attributes != null && attributes.size() > 0)) {
      ret += "\n   interpret\n";
      if (nameAttribute != null) {
        ret += "      " + nameAttribute.toUpperCase() + " as im:name"
            + (attributes != null && attributes.size() > 0 ? "," : "") + "\n";
      }
      if (attributes != null) {
        int n = 0;
        for (String s : attributes.keySet()) {
          ret += "      " + s.toUpperCase() + " as " + attributes.get(s).getDefinition()
              + (n == attributes.size() - 1 ? "" : ",") + "\n";
          n++;
        }
      }
    }

    ret += ";";

    return ret;
  }


}
