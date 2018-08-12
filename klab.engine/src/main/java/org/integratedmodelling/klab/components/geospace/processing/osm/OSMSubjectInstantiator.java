package org.integratedmodelling.klab.components.geospace.processing.osm;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Escape;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.dataset.MapDataSetLoader;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.core.resolve.EntityFinder;
import de.topobyte.osm4j.core.resolve.EntityFinders;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.EntityNotFoundStrategy;
import de.topobyte.osm4j.geometry.GeometryBuilder;
import de.topobyte.osm4j.geometry.RegionBuilder;
import de.topobyte.osm4j.geometry.RegionBuilderResult;
import de.topobyte.osm4j.geometry.WayBuilder;
import de.topobyte.osm4j.geometry.WayBuilderResult;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

/**
 * Subject instantiator to extract features from OSM. Either pass an Overpass query
 * directly, or build it using the parameters. Tries to comply with the geometric
 * requests. If temporal = true, uses timestamps to create objects and events at the time
 * of their existence. Otherwise simply builds objects at initialization regardless of
 * timestamps.
 *  
 * @author ferdinando.villa
 *
 */
public class OSMSubjectInstantiator implements IInstantiator, IExpression {

    List<Object>               matching       = null;
    List<Object>               notmatching    = null;
    List<Object>               equal          = null;
    List<Object>               notequal       = null;

    List<Object>               filters        = null;

    String                     type           = null;

    boolean                    isTemporal     = false;
    boolean                    fixGeometries  = false;

    // String outputType = null;
    String                     query          = null;

    boolean                    canDispose     = false;
    boolean                    useCache       = true;

    IDirectObservation         contextSubject = null;

    // forced types
    Set<String>                geometryTypes  = new HashSet<>();

    private int                nsubjs         = 0;
    int                        maxObjects     = 0;

    private WayBuilder         wayBuilder     = new WayBuilder();
    private RegionBuilder      regionBuilder  = new RegionBuilder();

    private int                timeout        = 600;
    private boolean            simplifyShapes = false;
    double                     bufferDistance = Double.NaN;

    // TODO this should be configurable. Also we must provide all this machinery as a remote resource using
    // its own OSM mirror.
    public static final String OVERPASS_URL   = "http://overpass-api.de/api/interpreter";

    public OSMSubjectInstantiator() {
    }

    @SuppressWarnings("unchecked")
    public OSMSubjectInstantiator(IParameters<String> parameters, IComputationContext context) {

        this.contextSubject = context.getContextObservation();

        this.filters = parameters.get("filters", List.class);
        this.equal = parameters.get("equal", List.class);
        this.matching = parameters.get("matching", List.class);
        this.notequal = parameters.get("not-equal", List.class);
        this.notmatching = parameters.get("not-matching", List.class);

        if (parameters.containsKey("feature-type")) {
            this.type = parameters.get("feature-type", String.class);
            if (!this.type.contains(",")) {
                this.fixGeometries = this.type.equals("point") || this.type.equals("polygon")
                        || this.type.equals("line") || this.type.equals("area");
            }
        }
        if (parameters.containsKey("max-objects")) {
            this.maxObjects = parameters.get("max-objects", Number.class).intValue();
        }
        if (parameters.containsKey("timeout")) {
            this.timeout = parameters.get("timeout", Number.class).intValue();
        }
        if (parameters.containsKey("temporal")) {
            this.isTemporal = parameters.get("temporal", Boolean.class);
        }
        if (parameters.containsKey("use-cache")) {
            this.useCache = parameters.get("use-cache", Boolean.class);
        }
        if (parameters.containsKey("simplify-polygons")) {
            this.simplifyShapes = parameters.get("simplify-polygons", Boolean.class);
        }
    }

    @Override
    public IGeometry getGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context)
            throws KlabException {

        if (query == null) {
            query = buildQuery();
            Logging.INSTANCE.info("Overpass query " + query);
        }

        List<IObjectArtifact> ret = new ArrayList<>();

        if (query /* still */ == null) {
            return ret;
        }

        /*
        * TODO use cache if available, unless caching is disabled in parameters.
        */
        if (useCache) {

        }

        String url = OVERPASS_URL + "?data=" + Escape.forURL(query);
        try (InputStream input = new URL(url).openStream()) {

            OsmIterator iterator = new OsmXmlIterator(input, true);
            InMemoryMapDataSet data = MapDataSetLoader.read(iterator, true, true, true);

            if ((type == null || type.contains("node") || type.contains("point"))
                    && !data.getNodes().isEmpty()) {
                for (OsmNode node : data.getNodes().valueCollection()) {
                    Geometry point = new GeometryBuilder().build(node);
                    if (fixGeometries && (this.type.equals("polygon") || this.type.equals("area"))) {
                        point = point.buffer(bufferDistance);
                    }
                    ISubject subject = makeSubject(semantics, point, node, context);
                    if (subject != null) {
                        ret.add(subject);
                    }
                }
            }

            // relations first, so later we can exclude ways that compose them
            Set<OsmWay> waysInRelations = new HashSet<>();
            EntityFinder wayFinder = EntityFinders.create(data, EntityNotFoundStrategy.IGNORE);

            if ((type == null || type.contains("relation") || type.contains("area")
                    || type.contains("polygon"))
                    && !data.getRelations().isEmpty()) {
                for (OsmRelation rel : data.getRelations().valueCollection()) {
                    Geometry polygon = getPolygon(rel, data);
                    try {
                        wayFinder.findMemberWays(rel, waysInRelations);
                    } catch (EntityNotFoundException e) {
                        // won't happen
                    }
                    if (this.fixGeometries && this.type.equals("point")) {
                        polygon = polygon.getCentroid();
                    }
                    ISubject subject = makeSubject(semantics, polygon, rel, context);
                    if (subject != null) {
                        ret.add(subject);
                    }
                }
            }

            if ((type == null || type.contains("way") || type.contains("area") || type.contains("polygon")
                    || type.contains("line"))
                    && !data.getWays().isEmpty()) {
                for (OsmWay way : data.getWays().valueCollection()) {

                    // bele vist
                    if (waysInRelations.contains(way)) {
                        continue;
                    }
                    Geometry line = (fixGeometries
                            && (this.type.equals("polygon") || this.type.equals("area")))
                                    ? getPolygon(way, data)
                                    : new GeometryBuilder().build(way, data);
                    ISubject subject = makeSubject(semantics, line, way, context);
                    if (subject != null) {
                        ret.add(subject);
                    }
                }
            }
        } catch (Throwable e) {
            throw new KlabIOException(e);
        }

        /*
        * TODO cache query and results in session data if OK, unless caching is disabled
        * in parameters.
        */
        context.getMonitor().info(this.nsubjs + " objects retrieved from OpenStreetMap");

        return ret;
    }

    @Override
    public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
        return new OSMSubjectInstantiator(parameters, context);
    }

    private String buildQuery() throws KlabValidationException {

        if (contextSubject.getScale().getSpace() == null) {
            return null;
        }

        Collection<String> targets = getQueryTargets();
        Shape shape = (Shape) contextSubject.getScale().getSpace().getShape();

        if (Double.isNaN(bufferDistance)) {
            ReferencedEnvelope bbox = shape.getEnvelope().getJTSEnvelope();
            double mdist = bbox.getWidth() + bbox.getHeight() / 2.0;
            bufferDistance = mdist / 100.0;
        } else {
            // TODO must pass as parameters in meters or so, translate to projection
        }

        OSMQuery query = new OSMQuery(shape, targets.toArray(new String[targets.size()]));

        query.setTimeout(timeout);

        if (this.equal != null) {
            query.filterEqual(this.equal.toArray());
        }
        if (this.notequal != null) {
            query.filterNotEqual(this.notequal.toArray());
        }
        if (this.matching != null) {
            query.filterMatch(this.matching.toArray());
        }
        if (this.notmatching != null) {
            query.filterNotMatch(this.notmatching.toArray());
        }

        return query.toString();
    }

    private Collection<String> getQueryTargets() {
        Set<String> ret = new HashSet<>();
        String[] types = this.type.split(",");
        for (String t : types) {
            if (t.equals("area")) {
                t = "polygon";
            }
            if (t.equals("point") || t.equals("polygon") || t.equals("line")) {
                this.geometryTypes.add(t);
            } else if (!(t.equals("node") || t.equals("way") || t.equals("relation"))) {
                throw new KlabValidationException(t
                        + ": wrong query type: OSM query can refer to node, way or relation, or use area, polygon, point or line");
            }
            ret.add(t);
        }
        return ret;
    }

    private ISubject makeSubject(IObservable observable, Geometry geometry, OsmEntity node, IComputationContext context)
            throws KlabException {

        this.nsubjs++;

        if (this.simplifyShapes && geometry instanceof Polygon) {
            /*
            * use 1% of the longest dimension as a default
            * TODO add option to modify
            */
            ReferencedEnvelope envelope = JTS.toEnvelope(geometry);
            double dist = Math.max(envelope.getWidth(), envelope.getHeight()) / 100;
            geometry = TopologyPreservingSimplifier.simplify(geometry, dist);
        }

        Shape shape = Shape.create(geometry, Projection.getDefault());
        if (context.getScale().getSpace() != null) {
            shape = shape.transform(context.getScale().getSpace().getProjection());
        }

        /*
        * TODO other actions on the shape if requested. TODO maybe also check for
        * validity - we don't know where it's been.
        */

        Map<String, String> tags = OsmModelUtil.getTagsAsMap(node);
        String id = (tags.containsKey("name") ? tags.get("name")
                : CamelCase.toLowerCase(Concepts.INSTANCE.getDisplayName(observable), '-') + "_"
                        + this.nsubjs);

        ISubject ret = (ISubject) context.newObservation(observable, id, getScale(shape, contextSubject));

        /*
        * add the unused metadata from OSM
        */
        for (String tag : tags.keySet()) {
            // weird shit happens
            String tgv = tags.get(tag).replaceAll("\\P{Print}", "");
            ret.getMetadata().put(tag, tgv);
        }

        return ret;
    }

    private IScale getScale(ISpace extent, IDirectObservation context) throws KlabException {

        List<IExtent> exts = new ArrayList<>();
        for (IExtent e : context.getScale().getExtents()) {
            if (e.getType() == Dimension.Type.SPACE) {
                exts.add(extent);
            } else {
                exts.add(e);
            }
        }
        return Scale.create(exts);
    }

    private Collection<LineString> getLine(OsmWay way, InMemoryMapDataSet data) {
        List<LineString> results = new ArrayList<>();
        try {
            WayBuilderResult lines = wayBuilder.build(way, data);
            results.addAll(lines.getLineStrings());
            if (lines.getLinearRing() != null) {
                results.add(lines.getLinearRing());
            }
        } catch (EntityNotFoundException e) {
            // ignore
        }
        return results;
    }

    private MultiPolygon getPolygon(OsmWay way, InMemoryMapDataSet data) {
        try {
            RegionBuilderResult region = regionBuilder.build(way, data);
            return region.getMultiPolygon();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    private MultiPolygon getPolygon(OsmRelation relation, InMemoryMapDataSet data) {
        try {
            RegionBuilderResult region = regionBuilder.build(relation, data);
            return region.getMultiPolygon();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

}
