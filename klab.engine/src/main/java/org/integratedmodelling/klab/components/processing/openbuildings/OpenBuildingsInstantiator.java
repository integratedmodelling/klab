package org.integratedmodelling.klab.components.processing.openbuildings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

public class OpenBuildingsInstantiator extends AbstractContextualizer implements IInstantiator, IExpression {
    private IDirectObservation contextSubject = null;
    private List<String> downloadableFiles = new Vector<>();

    private static int LATITUDE_INDEX = 0;
    private static int LONGITUDE_INDEX = 1;
    private static int AREA_INDEX = 2;
    private static int CONFIDENCE_INDEX = 3;
    private static int GEOMETRY_INDEX = 4;

    private double threshold = 90.0;

    public OpenBuildingsInstantiator(Parameters<Object> params, IContextualizationScope scope) {
        this.contextSubject = scope.getContextObservation();

        if (params.containsKey("threshold")) {
            this.threshold = params.get("threshold", Double.class);
        }
    }

    public OpenBuildingsInstantiator() {
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    @Override
    public Object eval(IContextualizationScope scope, Object... parameters) {
        return new OpenBuildingsInstantiator(Parameters.create(parameters), scope);
    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope scope) throws KlabException {
        List<IObjectArtifact> ret = new ArrayList<>();
        String tilesfile = "../../klab.engine/src/main/resources/components/org.integratedmodelling.geospace/openbuildings/tiles.geojson";

        Geometry contextGeometry = ((Shape) contextSubject.getScale().getSpace().getShape()).getJTSGeometry();

        try (InputStream inputStream = new FileInputStream(tilesfile)) {
            FeatureIterator<SimpleFeature> featureIterator = new FeatureJSON().streamFeatureCollection(inputStream);
            int featureCount = 0;
            while (featureIterator.hasNext()) {
                SimpleFeature feature = featureIterator.next();
                featureCount++;

                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                // We validate if it intersects with our context
                if (geometry == null || geometry.isEmpty()) {
                    continue;
                }

                if (!geometry.intersects(contextGeometry)) {
                    continue;
                }

                for (Property property : feature.getProperties()) {
                    if (property.getName().toString().equals("tile_url")) {
                        downloadableFiles.add(property.getValue().toString());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new KlabException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new KlabException();
        }

        for (String fileUrl : downloadableFiles) {
            try {
                URL url = new URL(fileUrl);
                InputStream inputStream = url.openStream();
                GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
                InputStreamReader reader = new InputStreamReader(gzipInputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(reader);

                CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());
                int n = 1;
                for (CSVRecord record : csvParser) {
                    double recordConfidence = Double.parseDouble(record.get(CONFIDENCE_INDEX));
                    if (recordConfidence < threshold) {
                        continue;
                    }

                    double lat = Double.parseDouble(record.get(LATITUDE_INDEX));
                    double lon = Double.parseDouble(record.get(LONGITUDE_INDEX));

                    if (!contextGeometry.contains(Shape.makePoint(lon, lat))) {
                        continue;
                    }
                    IMetadata metadata = new Metadata();
                    metadata.put("confidence", recordConfidence);
                    metadata.put("area_in_meters", Double.parseDouble(record.get(AREA_INDEX)));
                    String geometryWkt = record.get(GEOMETRY_INDEX);

                    Shape buildingShape = Shape.create(geometryWkt, Projection.create(Projection.DEFAULT_PROJECTION_CODE));
                    String id = CamelCase.toLowerCase(Observables.INSTANCE.getDisplayName(semantics), '-') + "_" + n++;
                    ISubject subject = (ISubject) scope.newObservation(semantics, id, getScale(buildingShape, contextSubject), metadata);

                    ret.add(subject);
                }
            } catch (IOException e) {
                throw new KlabResourceAccessException("Error processing csv.gz file: " + e.getMessage());
            }
        }
        return ret;
    }

    // TODO from OSMSubjectInstantiator. Remove one of them to avoid dupes.
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

}
