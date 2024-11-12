package org.integratedmodelling.klab.ogc.vector.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.adapters.AbstractFilesetImporter;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.ogc.VectorAdapter;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.klab.utils.ZipUtils;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class VectorImporter extends AbstractFilesetImporter {

	VectorValidator validator = new VectorValidator();

	public VectorImporter() {
		super(VectorAdapter.fileExtensions.toArray(new String[VectorAdapter.fileExtensions.size()]));
	}

	@Override
	public IResourceImporter withOption(String option, Object value) {
		return this;
	}

	@Override
	protected Builder importFile(String urn, File file, IParameters<String> userData, IMonitor monitor) {
		try {

			Builder builder = validator.validate(urn, file.toURI().toURL(), userData, monitor);

			if (builder != null) {
				builder.setResourceId(MiscUtilities.getFileBaseName(file).toLowerCase());
				for (File f : validator.getAllFilesForResource(file)) {
					builder.addImportedFile(f);
				}
			}

			return builder;

		} catch (MalformedURLException e) {
			Logging.INSTANCE.error(e);
			return null;
		}
	}

	@Override
	public List<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
		List<Triple<String, String, String>> ret = new ArrayList<>();
		if (observation instanceof IObservationGroup) {
			observation = ((IObservationGroup) observation).groupSize() > 0
					? (IObservation) ((IObservationGroup) observation).iterator().next()
					: null;
		}
		if (observation instanceof IDirectObservation) {
			if (observation.getScale().getSpace() != null && 
			        (!observation.getScale().getSpace().isRegular()||
			        (observation.getScale().getSpace().getShape()!= null && !observation.getScale().getSpace().getShape().isRegular()))) {
				ret.add(new Triple<>("shp", "ESRI Shapefile", "zip"));
                ret.add(new Triple<>("json", "GeoJSON", "json"));
			}
		}
		return ret;
	}

	public void exportGeoJSON(IObservation observation, ILocator locator, Writer out, IMonitor monitor) {

		Pair<SimpleFeatureType, FeatureCollection<SimpleFeatureType, SimpleFeature>> collected = getFeatureCollection(
				observation, locator, monitor);
		try {
			 new FeatureJSON().writeFeatureCollection(collected.getSecond(), out);
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	public Pair<SimpleFeatureType, FeatureCollection<SimpleFeatureType, SimpleFeature>> getFeatureCollection(
			IObservation observation, ILocator locator, IMonitor monitor) {

		IObservation first = observation instanceof IObservationGroup
				? (IObservation) ((IObservationGroup) observation).iterator().next()
				: observation;

		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setCRS(((Projection) first.getScale().getSpace().getProjection()).getCoordinateReferenceSystem());

		builder.setName("the_geom");
		switch (first.getScale().getSpace().getDimensionality()) {
		case 0:
			builder.add("the_geom", Point.class,
					((Projection) first.getScale().getSpace().getProjection()).getCoordinateReferenceSystem());
			break;
		case 1:
			builder.add("the_geom", MultiLineString.class,
					((Projection) first.getScale().getSpace().getProjection()).getCoordinateReferenceSystem());
			break;
		case 2:
			builder.add("the_geom", MultiPolygon.class,
					((Projection) first.getScale().getSpace().getProjection()).getCoordinateReferenceSystem());
			break;
		default:
			throw new IllegalStateException(
					"trying to build shape attributes for a spatial feature with unsupported dimensionality");
		}

		builder.add("name", String.class);

		List<Pair<String, String>> metadataId = new ArrayList<>();
		for (String s : first.getMetadata().keySet()) {
			if (Utils.isPOD(first.getMetadata().get(s))) {
				String attr = s;
				if (s.length() > 10) {
					attr = attr.substring(0, 10);
				}
				attr = attr.replaceAll(":", "_");
				if (s.length() != attr.length()) {
					monitor.info("shapefile export: shortening metadata field name " + s + " to " + attr);
				}
				metadataId.add(new Pair<>(s, attr));
				builder.add(attr, Utils.getPODClass(first.getMetadata().get(s)));
			}
		}

		List<Pair<String, String>> stateId = new ArrayList<>();
		if (first instanceof IDirectObservation) {
			for (IState state : ((IDirectObservation) first).getStates()) {
				Class<?> type = Utils.getClassForType(state.getObservable().getArtifactType());
				if (!Utils.isPOD(type)) {
					type = String.class;
				}
				String attr = state.getObservable().getName();
				if (attr.length() > 10) {
					attr = attr.substring(0, 10);
					monitor.info("shapefile export: shortening attribute name " + state.getObservable().getName()
							+ " to " + attr);
				}
				stateId.add(new Pair<>(state.getObservable().getName(), attr));
				builder.add(attr, type);
			}
		}

		SimpleFeatureType type = builder.buildFeatureType();
		DefaultFeatureCollection collection = new DefaultFeatureCollection(null, null);
		// GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(type);

		Iterable<IArtifact> observations = null;
		if (observation instanceof IObservationGroup) {
			observations = ((IObservationGroup) observation);
		} else {
			observations = new ArrayList<IArtifact>();
			((ArrayList<IArtifact>) observations).add(observation);
		}

		for (IArtifact obs : observations) {
			if (obs instanceof IDirectObservation) {

				/*
				 * TODO filter for time span vs. locator
				 */

				featureBuilder
						.add(((Shape) ((IDirectObservation) obs).getScale().getSpace().getShape()).getJTSGeometry());

				featureBuilder.add(((IDirectObservation) obs).getName());

				for (Pair<String, String> s : metadataId) {
					featureBuilder.add(obs.getMetadata().get(s.getFirst()));
				}
				for (Pair<String, String> s : stateId) {
					Object value = null;
					boolean found = false;
					for (IState state : ((IDirectObservation) obs).getStates()) {
						if (state.getObservable().getName().equals(s.getFirst())) {
							value = state.aggregate(((IDirectObservation) obs).getScale().at(locator),
									Utils.getClassForType(state.getObservable().getArtifactType()));
							found = true;
							break;
						}
					}
					if (found) {
						featureBuilder.add(value);
					}
				}
				collection.add(featureBuilder.buildFeature(null));
			}
		}

		return new Pair<>(type, collection);
	}

	@Override
	public File exportObservation(File file, IObservation observation, ILocator locator, String format,
			IMonitor monitor) {

        Pair<SimpleFeatureType, FeatureCollection<SimpleFeatureType, SimpleFeature>> collected = getFeatureCollection(
                observation, locator, monitor);
        File dir = new File(MiscUtilities.changeExtension(file.toString(), "dir"));
        dir.mkdirs();

		if (format.equals("shp")) {
			File out = new File(dir + File.separator + MiscUtilities.getFileName(file));
			File zip = new File(MiscUtilities.changeExtension(file.toString(), "zip"));

			ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

			try {
				Map<String, Serializable> params = new HashMap<String, Serializable>();
				params.put("url", out.toURI().toURL());
				params.put("create spatial index", Boolean.TRUE);

				ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
				newDataStore.createSchema(collected.getFirst());

				Transaction transaction = new DefaultTransaction("create");
				// String typeName = newDataStore.getTypeNames()[0];
				SimpleFeatureSource featureSource = newDataStore.getFeatureSource(newDataStore.getTypeNames()[0]);
				((SimpleFeatureStore) featureSource).setTransaction(transaction);
				((SimpleFeatureStore) featureSource).addFeatures(collected.getSecond());
				try {
					transaction.commit();
				} finally {
					transaction.close();
				}
			} catch (Exception e) {
				throw new KlabIOException(e);
			}

			/*
			 * Zip the directory's contents and send back as the final output
			 */
			ZipUtils.zip(zip, dir, false, false);

			file = zip;
		} else if (format.equals("json")) {
            File json = new File(MiscUtilities.changeExtension(file.toString(), "json"));
            FeatureJSON fjson = new FeatureJSON();
            String geojsonString = null;
            try {
                geojsonString = fjson.toString(collected.getSecond());
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
            try (Writer writer = new FileWriter(json)) {
                writer.write(geojsonString);
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
            file = json;
		}

		return file;

	}

	@Override
	public Map<String, String> getExportCapabilities(IResource resource) {
		Map<String, String> ret = new HashMap<>();
		ret.put("shp", "ESRI shapefile");
		ret.put("json", "GeoJSON file");
		return ret;
	}

	@Override
	public boolean exportResource(File file, IResource resource, String format) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resourceCanHandle(IResource resource, String importLocation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean acceptsMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean write(Writer writer, IObservation observation, ILocator locator, IMonitor monitor) {
		try {
			exportGeoJSON(observation, locator, writer, monitor);
		} catch (Throwable t) {
			return false;
		}
		return true;
	}

}
