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
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
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
import org.locationtech.jts.simplify.TopologyPreservingSimplifier;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import com.graphbuilder.curve.Point;

public class OpenBuildingsInstantiator extends AbstractContextualizer implements IInstantiator, IExpression {
    private IDirectObservation contextSubject = null;
    private List<String> downloadableFiles = new Vector<>();

    public OpenBuildingsInstantiator(Parameters<Object> create, IContextualizationScope scope) {
        this.contextSubject = scope.getContextObservation();

        // TODO manage parameters
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
        String tilesfile = "klab.engine/src/main/resources/components/org.integratedmodelling.geospace/openbuildings/tiles.geojson";

        Geometry contextGeometry = ((Shape) contextSubject.getScale().getSpace().getShape()).getJTSGeometry();

        try (InputStream inputStream = new FileInputStream(tilesfile)) {
            FeatureIterator<SimpleFeature> featureIterator = new FeatureJSON().streamFeatureCollection(inputStream);
            int featureCount = 0;
            while (featureIterator.hasNext()) {
                SimpleFeature feature = featureIterator.next();
                featureCount++;

                System.out.println("\n--- Feature " + featureCount + " ---");
                System.out.println("ID: " + feature.getID());

                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                
                // We validate if it intersects with our context
                if (geometry == null || geometry.isEmpty()) {
                    continue;
                }
                System.out.println("Geometry Type: " + geometry.getGeometryType());
                System.out.println("Geometry WKT: " + geometry.toText());

                if (!geometry.intersects(contextGeometry)) {
                    continue;
                }

                // Get properties (attributes)
                System.out.println("Properties:");
                for (Property property : feature.getProperties()) {
                    if (property.getName().equals("tile_url")) {
                        downloadableFiles.add(tilesfile);
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
                // TODO if it works, optimize
                int n = 1;
                for (CSVRecord record : csvParser) {
                    double lat = Double.parseDouble(record.get(0));
                    double lon = Double.parseDouble(record.get(1));

                    if (!contextGeometry.contains(Shape.makePoint(lat, lon))) { // TODO check coordinates
                        continue;
                    }
                    IMetadata metadata = new Metadata();
                    metadata.put("confidence", Double.parseDouble(record.get(3)));
                    metadata.put("area_in_meters", Double.parseDouble(record.get(2)));
                    String geometryWkt = record.get(4);

                    Shape buildingShape = Shape.create(geometryWkt, Projection.create("EPSG:3857"));
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
