package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.util.Set;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.springframework.stereotype.Service;

@Service
public class ResourceManager {

	/*
	 * The public resource catalog. This is not persisted at the moment, but simply
	 * uses the physical resources in ~/.klab/resources.
	 */
	ResourceCatalog catalog;

	public ResourceManager() {
		this.catalog = new ResourceCatalog("publicresources");
	}

	public IResource getResource(String urn, Set<Group> groups) {
		
		Urn kurn = new Urn(urn);
		if (kurn.isUniversal()) {

			/*
			 * TODO support
			 */

			IUrnAdapter adapter = Resources.INSTANCE.getUrnAdapter(kurn.getCatalog());
			if (adapter == null) {
				return null;
			}

			VisitingDataBuilder builder = new VisitingDataBuilder();
//			adapter.getEncodedData(kurn, builder, null, null);
//
//			// resource specifies one object
//			if (builder.getObjectCount() == 1) {

//				if (builder.getObjectScale(0).getSpace() != null) {
//					/*
//					 * build an observer from the data and return it
//					 */
//					return Observations.INSTANCE.makeROIObserver(builder.getObjectName(0),
//							builder.getObjectScale(0).getSpace().getShape(), builder.getObjectMetadata(0));
//				}
//			}
		}
		return null;
	}

	public IResource publishResource(ResourceReference resourceReference, File uploadFolder, EngineAuthorization user,
			IMonitor monitor) {

		IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resourceReference.getAdapterType());
		if (adapter == null) {
			throw new KlabUnsupportedFeatureException("resource adapter " + resourceReference.getAdapterType()
					+ " not installed: cannot publish resource");
		}

		IResourcePublisher publisher = adapter.getPublisher();
		if (publisher == null) {
			throw new KlabUnsupportedFeatureException("resource publisher " + resourceReference.getAdapterType()
					+ " not implemented: cannot publish resource");
		}

		IResource ret = publisher.publish(new Resource(resourceReference).in(uploadFolder), monitor);

		// TODO establish public URN

		if (ret != null && !ret.hasErrors()) {
			this.catalog.put(ret.getUrn(), ret);
		}

		return ret;
	}

}
