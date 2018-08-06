//package org.integratedmodelling.klab.components.geospace.processing.osm;
//
//import java.io.InputStream;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.geotools.geometry.jts.JTS;
//import org.geotools.geometry.jts.ReferencedEnvelope;
//import org.integratedmodelling.api.data.IList;
//import org.integratedmodelling.api.knowledge.IConcept;
//import org.integratedmodelling.api.knowledge.IObservation;
//import org.integratedmodelling.api.modelling.IActiveSubject;
//import org.integratedmodelling.api.modelling.IExtent;
//import org.integratedmodelling.api.modelling.IModel;
//import org.integratedmodelling.api.modelling.IObservableSemantics;
//import org.integratedmodelling.api.modelling.IScale;
//import org.integratedmodelling.api.modelling.IState;
//import org.integratedmodelling.api.modelling.ISubject;
//import org.integratedmodelling.api.modelling.contextualization.ISubjectInstantiator;
//import org.integratedmodelling.api.modelling.resolution.IResolutionScope;
//import org.integratedmodelling.api.modelling.scheduling.ITransition;
//import org.integratedmodelling.api.monitoring.IMonitor;
//import org.integratedmodelling.api.monitoring.Messages;
//import org.integratedmodelling.api.project.IProject;
//import org.integratedmodelling.api.provenance.IProvenance;
//import org.integratedmodelling.api.services.annotations.Prototype;
//import org.integratedmodelling.api.space.ISpatialExtent;
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.utils.CamelCase;
//import org.integratedmodelling.common.utils.Escape;
//import org.integratedmodelling.common.vocabulary.NS;
//import org.integratedmodelling.common.vocabulary.ObservableSemantics;
//import org.integratedmodelling.engine.geospace.Geospace;
//import org.integratedmodelling.engine.geospace.literals.ShapeValue;
//import org.integratedmodelling.engine.modelling.runtime.Scale;
//import org.integratedmodelling.exceptions.KlabException;
//import org.integratedmodelling.exceptions.KlabIOException;
//import org.integratedmodelling.exceptions.KlabValidationException;
//
//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.geom.LineString;
//import com.vividsolutions.jts.geom.MultiPolygon;
//import com.vividsolutions.jts.geom.Polygon;
//import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;
//
//import de.topobyte.osm4j.core.access.OsmIterator;
//import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
//import de.topobyte.osm4j.core.dataset.MapDataSetLoader;
//import de.topobyte.osm4j.core.model.iface.OsmEntity;
//import de.topobyte.osm4j.core.model.iface.OsmNode;
//import de.topobyte.osm4j.core.model.iface.OsmRelation;
//import de.topobyte.osm4j.core.model.iface.OsmWay;
//import de.topobyte.osm4j.core.model.util.OsmModelUtil;
//import de.topobyte.osm4j.core.resolve.EntityFinder;
//import de.topobyte.osm4j.core.resolve.EntityFinders;
//import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
//import de.topobyte.osm4j.core.resolve.EntityNotFoundStrategy;
//import de.topobyte.osm4j.geometry.GeometryBuilder;
//import de.topobyte.osm4j.geometry.RegionBuilder;
//import de.topobyte.osm4j.geometry.RegionBuilderResult;
//import de.topobyte.osm4j.geometry.WayBuilder;
//import de.topobyte.osm4j.geometry.WayBuilderResult;
//import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;
//
///**
// * Subject instantiator to extract features from OSM. Either pass an Overpass query
// * directly, or build it using the parameters. Tries to comply with the geometric
// * requests. If temporal = true, uses timestamps to create objects and events at the time
// * of their existence. Otherwise simply builds objects at initialization regardless of
// * timestamps.
// * 
// * Should also return a single ID by identifier, to annotate as we please. This should
// * become usable within observe statements (using ...) so that we can construct contexts
// * from OSM through Nominatim queries.
// * 
// * TODO pass a zoom level from metadata (call it an extent metric or something, with 0 =
// * total for the worldview) and void the correspondent model if the current context has a
// * zoom level below that.
// * 
// * @author ferdinando.villa
// *
// */
//@Prototype(
//        id = "osm.query-features",
//        published = true,
//        args = {
//                "# id",
//                Prototype.INT,
//                "# filters", // more complex queries already in overpass format, one per
//                             // type
//                Prototype.LIST,
//                "# feature-type",
//                Prototype.TEXT, // "node|way|relation|polygon|area|line|point" or
//                                // comma-separated combinations
//                "# max-objects",
//                Prototype.INT,
//                "# timeout",
//                Prototype.INT,
//                "# equal",
//                Prototype.LIST,
//                "# temporal",
//                Prototype.BOOLEAN,
//                "# not-equal",
//                Prototype.LIST,
//                "# matching",
//                Prototype.LIST,
//                "# not-matching",
//                Prototype.LIST,
//                "# use-cache",
//                Prototype.BOOLEAN,
//                "# simplify-polygons",
//                Prototype.BOOLEAN, },
//        returnTypes = { NS.SUBJECT_INSTANTIATOR })
//public class OSMSubjectInstantiator implements ISubjectInstantiator {
//
//    // TODO this may be configurable or contain a set of alternative URLs to try.
//    public static final String OVERPASS_URL   = "http://overpass-api.de/api/interpreter";
//
//    IList                      matching       = null;
//    IList                      notmatching    = null;
//    IList                      equal          = null;
//    IList                      notequal       = null;
//
//    IList                      filters        = null;
//
//    String                     type           = null;
//
//    boolean                    isTemporal     = false;
//    boolean                    fixGeometries  = false;
//
//    // String outputType = null;
//    String                     query          = null;
//
//    boolean                    canDispose     = false;
//    boolean                    useCache       = true;
//
//    IActiveSubject             context        = null;
//    IResolutionScope           scope          = null;
//
//    // forced types
//    Set<String>                geometryTypes  = new HashSet<>();
//
//    private IConcept           observable;
//    private int                nsubjs         = 0;
//    private IMonitor           monitor;
//    int                        maxObjects     = 0;
//
//    private WayBuilder         wayBuilder     = new WayBuilder();
//    private RegionBuilder      regionBuilder  = new RegionBuilder();
//
//    private int                timeout        = 600;
//    private boolean            simplifyShapes = false;
//    double                     bufferDistance = Double.NaN;
//
//    @Override
//    public void initialize(IActiveSubject contextSubject, IResolutionScope context, IModel callingModel, Map<String, IObservableSemantics> expectedInputs, Map<String, IObservableSemantics> expectedOutputs, IMonitor monitor)
//            throws KlabException {
//
//        this.observable = callingModel.getObservable().getType();
//        this.monitor = monitor;
//
//        canDispose = !contextSubject.getScale().isTemporallyDistributed();
//        this.context = contextSubject;
//        this.scope = context;
//    }
//
//    private String buildQuery() throws KlabValidationException {
//
//        if (context.getScale().getSpace() == null) {
//            return null;
//        }
//
//        Collection<String> targets = getQueryTargets();
//        ShapeValue shape = (ShapeValue) context.getScale().getSpace().getShape();
//
//        if (Double.isNaN(bufferDistance)) {
//            ReferencedEnvelope bbox = shape.getEnvelope();
//            double mdist = bbox.getWidth() + bbox.getHeight() / 2.0;
//            bufferDistance = mdist / 100.0;
//        } else {
//            // TODO must pass as parameters in meters or so, translate to projection
//        }
//
//        OSMQuery query = new OSMQuery(shape, targets.toArray(new String[targets.size()]));
//
//        query.setTimeout(timeout);
//
//        if (this.equal != null) {
//            query.filterEqual(this.equal.toArray());
//        }
//        if (this.notequal != null) {
//            query.filterNotEqual(this.notequal.toArray());
//        }
//        if (this.matching != null) {
//            query.filterMatch(this.matching.toArray());
//        }
//        if (this.notmatching != null) {
//            query.filterNotMatch(this.notmatching.toArray());
//        }
//
//        return query.toString();
//    }
//
//    private Collection<String> getQueryTargets() throws KlabValidationException {
//        Set<String> ret = new HashSet<>();
//        String[] types = this.type.split(",");
//        for (String t : types) {
//            if (t.equals("area")) {
//                t = "polygon";
//            }
//            if (t.equals("point") || t.equals("polygon") || t.equals("line")) {
//                this.geometryTypes.add(t);
//            } else if (!(t.equals("node") || t.equals("way") || t.equals("relation"))) {
//                throw new KlabValidationException(t
//                        + ": wrong query type: OSM query can refer to node, way or relation, or use area, polygon, point or line");
//            }
//            ret.add(t);
//        }
//        return ret;
//    }
//
//    @Override
//    public boolean canDispose() {
//        return canDispose;
//    }
//
//    @Override
//    public void setContext(Map<String, Object> parameters, IModel model, IProject project, IProvenance.Artifact provenance)
//            throws KlabValidationException {
//
//        this.filters = (IList) parameters.get("filters");
//        this.equal = (IList) parameters.get("equal");
//        this.matching = (IList) parameters.get("matching");
//        this.notequal = (IList) parameters.get("not-equal");
//        this.notmatching = (IList) parameters.get("not-matching");
//
//        if (parameters.containsKey("feature-type")) {
//            this.type = parameters.get("feature-type").toString();
//            if (!this.type.contains(",")) {
//                this.fixGeometries = this.type.equals("point") || this.type.equals("polygon")
//                        || this.type.equals("line") || this.type.equals("area");
//            }
//        }
//        if (parameters.containsKey("max-objects")) {
//            this.maxObjects = ((Number) parameters.get("max-objects")).intValue();
//        }
//        if (parameters.containsKey("timeout")) {
//            this.timeout = ((Number) parameters.get("timeout")).intValue();
//        }
//        if (parameters.containsKey("temporal")) {
//            this.isTemporal = (Boolean) parameters.get("temporal");
//        }
//        if (parameters.containsKey("use-cache")) {
//            this.useCache = (Boolean) parameters.get("use-cache");
//        }
//        if (parameters.containsKey("simplify-polygons")) {
//            this.simplifyShapes = (Boolean) parameters.get("simplify-polygons");
//        }
//    }
//
//    @Override
//    public Map<String, IObservation> createSubjects(IActiveSubject context, ITransition transition, Map<String, IState> inputs)
//            throws KlabException {
//
//        if (transition != null) {
//            canDispose = transition.isLast();
//            if (!isTemporal) {
//                return null;
//            }
//        }
//
//        if (query == null) {
//            query = buildQuery();
//            KLAB.info("Overpass query " + query);
//        }
//
//        Map<String, IObservation> ret = new HashMap<>();
//
//        if (query /* still */ == null) {
//            return ret;
//        }
//
//        /*
//         * TODO use cache if available, unless caching is disabled in parameters.
//         */
//        if (useCache) {
//
//        }
//
//        String url = OVERPASS_URL + "?data=" + Escape.forURL(query);
//        try (InputStream input = new URL(url).openStream()) {
//
//            OsmIterator iterator = new OsmXmlIterator(input, true);
//            InMemoryMapDataSet data = MapDataSetLoader.read(iterator, true, true, true);
//
//            if ((type == null || type.contains("node") || type.contains("point"))
//                    && !data.getNodes().isEmpty()) {
//                for (OsmNode node : data.getNodes().valueCollection()) {
//                    Geometry point = new GeometryBuilder().build(node);
//                    if (fixGeometries && (this.type.equals("polygon") || this.type.equals("area"))) {
//                        point = point.buffer(bufferDistance);
//                    }
//                    ISubject subject = makeSubject(point, node);
//                    if (subject != null) {
//                        ret.put(subject.getName(), subject);
//                    }
//                }
//            }
//
//            // relations first, so later we can exclude ways that compose them
//            Set<OsmWay> waysInRelations = new HashSet<>();
//            EntityFinder wayFinder = EntityFinders.create(data, EntityNotFoundStrategy.IGNORE);
//
//            if ((type == null || type.contains("relation") || type.contains("area")
//                    || type.contains("polygon"))
//                    && !data.getRelations().isEmpty()) {
//                for (OsmRelation rel : data.getRelations().valueCollection()) {
//                    Geometry polygon = getPolygon(rel, data);
//                    try {
//                        wayFinder.findMemberWays(rel, waysInRelations);
//                    } catch (EntityNotFoundException e) {
//                        // won't happen
//                    }
//                    if (this.fixGeometries && this.type.equals("point")) {
//                        polygon = polygon.getCentroid();
//                    }
//                    ISubject subject = makeSubject(polygon, rel);
//                    if (subject != null) {
//                        ret.put(subject.getName(), subject);
//                    }
//                }
//            }
//
//            if ((type == null || type.contains("way") || type.contains("area") || type.contains("polygon")
//                    || type.contains("line"))
//                    && !data.getWays().isEmpty()) {
//                for (OsmWay way : data.getWays().valueCollection()) {
//
//                    // bele vist
//                    if (waysInRelations.contains(way)) {
//                        continue;
//                    }
//                    Geometry line = (fixGeometries
//                            && (this.type.equals("polygon") || this.type.equals("area")))
//                                    ? getPolygon(way, data) : new GeometryBuilder().build(way, data);
//                    ISubject subject = makeSubject(line, way);
//                    if (subject != null) {
//                        ret.put(subject.getName(), subject);
//                    }
//                }
//            }
//        } catch (Throwable e) {
//            throw new KlabIOException(e);
//        }
//
//        /*
//         * TODO cache query and results in session data if OK, unless caching is disabled
//         * in parameters.
//         */
//
//        monitor.info(this.nsubjs + " objects retrieved from OpenStreetMap", Messages.INFOCLASS_DOWNLOAD);
//
//        return ret;
//    }
//
//    private ISubject makeSubject(Geometry geometry, OsmEntity node) throws KlabException {
//
//        this.nsubjs++;
//
//        if (this.simplifyShapes && geometry instanceof Polygon) {
//            /*
//             * use 1% of the longest dimension as a default
//             * TODO add option to modify
//             */
//            ReferencedEnvelope envelope = JTS.toEnvelope(geometry);
//            double dist = Math.max(envelope.getWidth(), envelope.getHeight()) / 100;
//            geometry = TopologyPreservingSimplifier.simplify(geometry, dist);
//        }
//
//        ShapeValue shape = new ShapeValue(geometry, Geospace.get().getDefaultCRS());
//        if (context.getScale().getSpace() != null) {
//            shape = shape.transform(Geospace.getCRSFromID(context.getScale().getSpace().getCRSCode()));
//        }
//
//        /*
//         * TODO other actions on the shape if requested. TODO maybe also check for
//         * validity - we don't know where it's been.
//         */
//
//        Map<String, String> tags = OsmModelUtil.getTagsAsMap(node);
//        String id = (tags.containsKey("name") ? tags.get("name")
//                : CamelCase.toLowerCase(NS.getDisplayName(observable), '-') + "_" + this.nsubjs);
//
//        /*
//         * TODO check for more metadata from OSM
//         */
//
//        ISubject ret = context
//                .newSubject(new ObservableSemantics(this.observable), getScale(shape
//                        .asExtent(), context), id, KLAB
//                                .p(NS.PART_OF));
//
//        /*
//         * add the unused metadata
//         */
//        for (String tag : tags.keySet()) {
//            // weird shit happens
//            String tgv = tags.get(tag).replaceAll("\\P{Print}", "");
//            ret.getMetadata().put(tag, tgv);
//        }
//
//        return ret;
//    }
//
//    private IScale getScale(ISpatialExtent extent, ISubject context) throws KlabException {
//
//        List<IExtent> exts = new ArrayList<>();
//        for (IExtent e : context.getScale()) {
//            if (e instanceof ISpatialExtent) {
//                exts.add(extent);
//            } else {
//                exts.add(e);
//            }
//        }
//        return new Scale(exts.toArray(new IExtent[exts.size()]));
//    }
//
//    private Collection<LineString> getLine(OsmWay way, InMemoryMapDataSet data) {
//        List<LineString> results = new ArrayList<>();
//        try {
//            WayBuilderResult lines = wayBuilder.build(way, data);
//            results.addAll(lines.getLineStrings());
//            if (lines.getLinearRing() != null) {
//                results.add(lines.getLinearRing());
//            }
//        } catch (EntityNotFoundException e) {
//            // ignore
//        }
//        return results;
//    }
//
//    private MultiPolygon getPolygon(OsmWay way, InMemoryMapDataSet data) {
//        try {
//            RegionBuilderResult region = regionBuilder.build(way, data);
//            return region.getMultiPolygon();
//        } catch (EntityNotFoundException e) {
//            return null;
//        }
//    }
//
//    private MultiPolygon getPolygon(OsmRelation relation, InMemoryMapDataSet data) {
//        try {
//            RegionBuilderResult region = regionBuilder.build(relation, data);
//            return region.getMultiPolygon();
//        } catch (EntityNotFoundException e) {
//            return null;
//        }
//    }
//
//}
