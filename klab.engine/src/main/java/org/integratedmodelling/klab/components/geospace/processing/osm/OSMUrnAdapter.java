package org.integratedmodelling.klab.components.geospace.processing.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.ResourceReference;
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
	public IKlabData getEncodedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {

		IParameters<String> data = Geocoder.INSTANCE.getData(urn.getNamespace(), urn.getResourceId());

		if (data != null) {

			IScale scale = null;

			if (data.containsKey(Geocoder.GEOMETRY_FIELD)) {
				Shape shape = Shape.create(
						data.get(Geocoder.GEOMETRY_FIELD, com.vividsolutions.jts.geom.Geometry.class),
						Projection.getLatLon());
				// TODO adjust, parameterize for power users
				shape.simplifyIfNecessary(5000, 8000);
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

			return builder.build();
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

	@Override
	public String getDescription() {
		return "Simple, URN-based OpenStreetMap access for k.LAB";
	}

	@Override
	public Collection<String> getResourceUrns() {
		List<String> ret = new ArrayList<>();
		return ret;
	}

	@Override
	public IResource getResource(String urn) {
		// TODO Auto-generated method stub
		Urn kurn = new Urn(urn);
		ResourceReference ref = new ResourceReference();
		ref.setUrn(urn.toString());
		ref.setAdapterType(getName());
		ref.setLocalName(kurn.getResourceId());
		ref.setGeometry("#");
		ref.setVersion(Version.CURRENT);
		ref.setType(Type.VALUE); // for now
		return new Resource(ref);
	}

}
