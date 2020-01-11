package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.EncodingDataBuilder;
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
	private ResourceCatalog catalog;

	public ResourceManager() {
		this.catalog = new ResourceCatalog();
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
					IResource resource = null;
					if (uploadFolder != null) {
						resource = catalog.importResource(uploadFolder, user);
					} else {
						resource = catalog.importResource(resourceReference, user);
					}
					ret.resolve("urn", resource.getUrn());
				} catch (Throwable t) {
					ret.error("Publishing failed with exception: " + t.getMessage());
				}
			}
		}.start();

		return ret;
	}

	public IResource getResource(String urn, Set<Group> groups) {
		// TODO permission check
		return catalog.get(urn);
	}

	public Collection<String> getCatalogs() {
		return catalog.getCatalogs();
	}

	public Collection<String> getNamespaces() {
		return catalog.getNamespaces();
	}

}
