package org.integratedmodelling.klab.components.geospace.processing.osm;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.scale.Scale;

@UrnAdapter(type = "osm", version = Version.CURRENT)
public class OSMUrnAdapter implements IUrnAdapter {

	@Override
	public String getName() {
		return "osm";
	}

	@Override
	public boolean isOnline(Urn urn) {
		return Geocoder.INSTANCE.isRetrievalAccessible();
	}

	@Override
	public void getEncodedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {

		IParameters<String> data = Geocoder.INSTANCE.getData(urn.getNamespace(), urn.getResourceId());

		if (data != null) {

			IScale scale = null;

			if (data.containsKey(Geocoder.GEOMETRY_FIELD)) {
				IShape shape = Shape.create(
						data.get(Geocoder.GEOMETRY_FIELD, com.vividsolutions.jts.geom.Geometry.class),
						Projection.getLatLon());
				scale = Scale.create(shape);
			} else {
				scale = Scale.empty();
			}

			String id = urn.getNamespace() + "_" + urn.getResourceId();
			String name = id;

			if (data.containsKey("name")) {
				name = data.get("name").toString();
			}

			builder = builder.startObject(id, name, scale);

			for (String key : data.keySet()) {
				if (Geocoder.GEOMETRY_FIELD.equals(key) || "name".equals(key)) {
					continue;
				}
				builder.withMetadata(key, data.get(key));
			}

			builder.finishObject();

			return;
		}

		throw new KlabIOException("cannot retrieve OSM data to resolve URN " + urn);
	}

	@Override
	public Type getType(Urn urn) {
		return Type.OBJECT;
	}

	@Override
	public IGeometry getGeometry(Urn urn) {

		switch (urn.getNamespace()) {
		case "relation":
			return Geometry.create("#s2");
		case "way":
			return Geometry.create("#s1");
		case "node":
			return Geometry.create("#s0");
		}

		throw new KlabIOException("wrong OSM resource namespace in " + urn);
	}

}
