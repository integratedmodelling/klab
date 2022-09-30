package org.integratedmodelling.geoprocessing.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureIterator;
import org.geotools.grid.Grids;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.opengis.feature.simple.SimpleFeature;

@UrnAdapter(type = GridAdapter.NAME, version = Version.CURRENT)
public class GridAdapter implements IUrnAdapter {

    public static final String NAME = "grid";

    public static final String NAMESPACE_TILES = "tiles";
    public static final String NAMESPACE_ROWS = "rows";
    public static final String NAMESPACE_COLS = "columns";
    public static final String NAMESPACE_INDEX = "index";

    public GridAdapter() {
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isOnline(Urn urn) {
        return true;
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
        String[] unam = urn.getNamespace().split("\\.");
        switch(unam[0]) {
        case NAMESPACE_TILES:
            makeTiles(urn, builder, geometry, context);
            break;
        }
        // return builder.build();
    }

    private void makeTiles(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {

        /*
         * Tiles should be CONFORMANT, i.e. the number of divisions should fit exactly within the
         * bounding box using projection units. Common degree and m subdivisions should be
         * specifiable as resourceId in the URN. Same should apply for "strips" corresponding to
         * columns and rows. We just send out a warning along with the FU'd shapes in case.
         */

        SimpleFeatureSource source = null;
        IScale scale = (geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry));
        String nameAttribute = "tile";

        if (scale.getSpace() == null) {
            throw new KlabIllegalStateException("cannot use the grid adapter on a non-spatial geometry");
        }

        IEnvelope envelope = scale.getSpace().getEnvelope();

        double width = 1;
        String[] resPath = urn.getResourceId().split("\\.");
        if (resPath.length > 1 && NumberUtils.encodesInteger(resPath[1])) {
            String nn = resPath[1];
            if (nn.charAt(0) == '0') {
                // ignore the first zero
                nn = "0." + nn.substring(1);
            }
            width = Double.parseDouble(nn);
        }

        String[] unam = urn.getNamespace().split("\\.");
        if (unam.length > 1) {
            if ("overlap".equals(unam[1])) {
                envelope = Grid.snapOutside(envelope, width);
            }
        }

        switch(resPath[0]) {
        case "square":
            source = Grids.createSquareGrid(((Envelope) envelope).getJTSEnvelope(), width);
            break;
        case "hexagonal":
            source = Grids.createHexagonalGrid(((Envelope) envelope).getJTSEnvelope(), width);
            break;
        case "oval":
            source = Grids.createOvalGrid(((Envelope) envelope).getJTSEnvelope(), width);
            break;
        }

        if (source == null) {
            return;
        }
        
        IEnvelope within = null;
        double[] containing = null;
        
        if (urn.getParameters().containsKey("within")) {
            double[] coords = NumberUtils.doubleArrayFromString(urn.getParameters().get("within"), ",");
            within = Envelope.create(coords[0], coords[1], coords[2], coords[3], Projection.getLatLon());
        }
        if (urn.getParameters().containsKey("containing")) {
            containing = NumberUtils.doubleArrayFromString(urn.getParameters().get("containing"), ",");
        }

        int n = 1;
        try {
            FeatureIterator<SimpleFeature> it = source.getFeatures().features();
            while(it.hasNext()) {

                SimpleFeature feature = it.next();
                Object shape = feature.getDefaultGeometryProperty().getValue();
                if (shape instanceof org.locationtech.jts.geom.Geometry) {

                    if (((org.locationtech.jts.geom.Geometry) shape).isEmpty()) {
                        continue;
                    }
                    
                    IShape objectShape = Shape.create((org.locationtech.jts.geom.Geometry) shape, envelope.getProjection());

                    if (within != null && !objectShape.getEnvelope().overlaps(within)) {
                        continue;
                    }
                    
                    if (containing != null && !objectShape.contains(containing)) {
                        continue;
                    }
                    
                    IScale objectScale = Scale.createLike(scale, objectShape);
                    builder = builder.startObject(nameAttribute + "_" + n, nameAttribute + "_" + n, objectScale).finishObject();
                }

                n++;
            }

            it.close();

        } catch (IOException e) {
            throw new KlabIOException("cannot create features from grid adapter URN parameters");
        }

    }

    @Override
    public Type getType(Urn urn) {
        String[] unam = urn.getNamespace().split("\\.");
        switch(unam[0]) {
        case NAMESPACE_TILES:
            return Type.OBJECT;
        }
        throw new KlabUnimplementedException("bgrid: can't handle URN namespace " + urn.getNamespace());
    }

    @Override
    public IGeometry getGeometry(Urn urn) {
        String[] unam = urn.getNamespace().split("\\.");
        switch(unam[0]) {
        case NAMESPACE_TILES:
        	// bounding box is the planet - this determines coverage and results when used as a source
            return Geometry.create("#S2{" + Geometry.WORLD_BBOX_PARAMETERS + "}");
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "Square, rectangular or hexagonal grids.";
    }

    @Override
    public Collection<String> getResourceUrns() {
        List<String> ret = new ArrayList<>();
        // TODO
        return ret;
    }

    @Override
    public IResource getResource(String urn) {

        Urn kurn = new Urn(urn);
        ResourceReference ref = new ResourceReference();
        ref.setUrn(urn.toString());
        ref.setAdapterType(getName());
        ref.setLocalName(kurn.getResourceId());
        ref.setGeometry(getGeometry(kurn).encode());

        /**
         * any parameters not understood become attributes
         */
        // if (!DATA.equals(kurn.getNamespace())) {
        // for (String attribute : kurn.getParameters().keySet()) {
        // if (Arrays.binarySearch(object_attribute_ids, attribute) < 0) {
        // Object value = getAttributeValue(kurn.getParameters().get(attribute));
        // if (value != null) {
        // Type type = Utils.getArtifactType(value.getClass());
        // AttributeReference aref = new AttributeReference();
        // aref.setName(attribute);
        // aref.setExampleValue(value.toString());
        // aref.setType(type);
        // ref.getAttributes().add(aref);
        // }
        // }
        // }
        // }

        ref.setVersion(Version.CURRENT);
        ref.setType(getType(kurn));
        return new Resource(ref);
    }

    @Override
    public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {
        return resource;
    }

}
