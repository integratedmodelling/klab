package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.util.Set;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.EncodingDataBuilder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceManager {

	@Autowired
	TicketService ticketService;

	/*
	 * The public resource catalog. This is not persisted at the moment, but simply
	 * uses the physical resources in ~/.klab/resources.
	 */
	ResourceCatalog catalog;

	public ResourceManager() {
		this.catalog = new ResourceCatalog("publicresources");
	}

	public KlabData getResourceData(String urn, IGeometry geometry, Set<Group> groups) {

		Urn kurn = new Urn(urn);
		if (kurn.isUniversal()) {

			/*
			 * TODO support
			 */

			IUrnAdapter adapter = Resources.INSTANCE.getUrnAdapter(kurn.getCatalog());
			if (adapter == null) {
				return null;
			}

			EncodingDataBuilder builder = new EncodingDataBuilder();
			adapter.getEncodedData(kurn, builder, geometry, null);
			
			return builder.buildEncoded();
			
		} else {
			
		}
		return null;
	}

	public ITicket publishResource(ResourceReference resourceReference, File uploadFolder, EngineAuthorization user,
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

		final ITicket ret = ticketService.open(ITicket.Type.ResourcePublication, "resource", resourceReference.getUrn(),
				"user", user.getUsername());

		/*
		 * spawn thread that will publish and resolve the ticket with the "urn"
		 * parameter set to the public URN.
		 */
		new Thread() {
			@Override
			public void run() {

				try {
					IResource resource = publisher.publish(new Resource(resourceReference).in(uploadFolder), monitor);
					if (resource != null && !resource.hasErrors()) {
						ret.resolve("urn", resource.getUrn());
//					this.catalog.put(ret.getUrn(), ret);
					} else {
						ret.error("Publishing failed: " + resource == null ? "no resource returned by publisher"
								: resource.getStatusMessage());
					}
				} catch (Throwable t) {
					ret.error("Publishing failed with exception: " + t.getMessage());
				}

			}
		}.start();

		return ret;
	}

	public IResource getResource(String urn, Set<Group> groups) {
		// TODO Auto-generated method stub
		return null;
	}

}
