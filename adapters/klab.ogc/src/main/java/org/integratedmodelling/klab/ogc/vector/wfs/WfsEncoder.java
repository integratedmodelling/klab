/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.ogc.vector.wfs;

import java.io.IOException;
import java.util.Map;

import org.geotools.data.FeatureSource;
import org.geotools.data.simple.SimpleFeatureCollection;º   
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.hortonmachine.gears.io.wfs.Wfs;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.ogc.WfsAdapter;
import org.integratedmodelling.klab.ogc.vector.files.VectorEncoder;
import org.integratedmodelling.klab.scale.Scale;
import org.locationtech.jts.geom.Envelope;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * The WFS encoder simply redefines the getFeatureSource method within a vector
 * encoder.
 */
public class WfsEncoder extends VectorEncoder {

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope scope) {
        //String wfsUrl = "https://visualizador.ideam.gov.co/gisserver/services/Vulnerabilidad_Susceptibilidad_Ambiental/MapServer/WFSServer?service=WFS&request=GetCapabilities";
        String wfsUrl = resource.getParameters().get("serviceUrl", String.class) + "?service=WFS&request=GetCapabilities";
        String desiredLayerName = resource.getParameters().get("wfsIdentifier", String.class);
        //String desiredLayerName = "Categorizacion_de_SZH_por_Evaluacion_Integrada_ENA_2014";

        Wfs wfs = new Wfs(wfsUrl, desiredLayerName);
        wfs.forceNormalizeGeometryName();
        wfs.forceArcgisCompatibility();

        // TODO read swap from parameters
        this.forceXYSwap = false;
        if (forceXYSwap) {
            wfs.forceCoordinateSwapping();
        }
        try {
            wfs.connect();
            System.out.println("Version: " + wfs.getVersion());

            Space space = (Space) geometry.getDimensions().stream().filter(d -> d instanceof Space).findFirst().orElseThrow();
            IEnvelope geomAsEnv = space.getEnvelope();
            Envelope env = new Envelope(geomAsEnv.getMinX(), geomAsEnv.getMaxX(), geomAsEnv.getMinY(), geomAsEnv.getMaxY());
            Scale requestScale = geometry instanceof Scale ? (Scale) geometry : Scale.create(geometry);

            SimpleFeatureCollection fc = wfs.getFeatureCollection(env);
            this.defaultTypeName = fc.getSchema().getTypeName() + "_";
            CoordinateReferenceSystem crs = fc.getSchema().getCoordinateReferenceSystem();
            crs = GeotoolsUtils.INSTANCE.checkCrs(crs);
            this.originalProjection = Projection.create(crs);
            IEnvelope envelopeInOriginalProjection = requestScale.getSpace().getEnvelope().transform(originalProjection, true);
            ReferencedEnvelope bboxRefEnv = ((org.integratedmodelling.klab.components.geospace.extents.Envelope) envelopeInOriginalProjection).getJTSEnvelope();

            int n = 1;
            FeatureIterator<SimpleFeature> it = fc.features();
            this.intersect = urnParameters.containsKey("intersect") ? Boolean.parseBoolean(urnParameters.get("intersect")) : true;
            this.presence = urnParameters.containsKey("presence") ? Boolean.parseBoolean(urnParameters.get("presence")) : false;
            this.idRequested = urnParameters.containsKey(Urn.SINGLE_PARAMETER_KEY) && urnParameters.size() == 1
                    ? urnParameters.get(Urn.SINGLE_PARAMETER_KEY)
                    : null;

            parseFeatures(it, resource, urnParameters, geometry, builder, scope, bboxRefEnv);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                wfs.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    
    }

	@Override
	protected FeatureSource<SimpleFeatureType, SimpleFeature> getFeatureSource(IResource resource, IGeometry geometry) {
	    
		WFSDataStore dataStore = WfsAdapter.getDatastore(resource.getParameters().get("serviceUrl", String.class),
				Version.create(resource.getParameters().get("wfsVersion", "1.0.0")));
		if (dataStore == null) {
            throw new KlabIOException("datastore " + resource.getParameters().get("serviceUrl", String.class) + " is unreachable");
		}
		try {
			return dataStore.getFeatureSource(resource.getParameters().get("wfsIdentifier", String.class));
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		return true; // NetUtilities.urlResponds(resource.getParameters().get("serviceUrl", String.class));
	}

}
