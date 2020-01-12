package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.EncodingDataBuilder;
import org.integratedmodelling.klab.node.NodeApplication;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceManager {

	@Autowired
	TicketService ticketService;

	private Timer resourceChecker;

	/*
	 * The public resource catalog. This is not persisted at the moment, but simply
	 * uses the physical resources in ~/.klab/resources. Only this class must be
	 * able to access it as insertion and deletion need to keep the resource paths
	 * updated, which doesn't happen automatically at put().
	 */
	private ResourceCatalog catalog;

	private Set<String> onlineResourceUrns = Collections.synchronizedSet(new LinkedHashSet<>());
	private Set<String> offlineResourceUrns = Collections.synchronizedSet(new LinkedHashSet<>());
	
	public ResourceManager() {
		this.catalog = new ResourceCatalog();
		this.resourceChecker = new Timer(true);
		this.resourceChecker.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				checkResources();
			}
		}, 0, Integer.parseInt(
				Configuration.INSTANCE.getProperty(NodeApplication.RESOURCE_CHECKING_INTERVAL_SECONDS, "120")));

	}
	
	protected synchronized void checkResources() {

		List<String> urns = new ArrayList<>(this.catalog.keySet());
		List<String> online = new ArrayList<>();
		List<String> offline = new ArrayList<>();
		for (String urn : urns) {
			if (Resources.INSTANCE.isResourceOnline(catalog.get(urn))) {
				online.add(urn);
			} else {
				offline.add(urn);
			}
		}
		
		this.offlineResourceUrns.clear();
		this.offlineResourceUrns.addAll(offline);
		this.onlineResourceUrns.clear();
		this.onlineResourceUrns.addAll(online);
		
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

	public ITicket publishResource(ResourceReference resourceReference, File uploadArchive, EngineAuthorization user,
			IMonitor monitor) {

		String originalUrn = null;
		File resourcePath = null;
		if (uploadArchive != null) {
			Pair<File, String> unpacked = catalog.unpackArchive(uploadArchive);
			resourcePath = unpacked.getFirst();
			originalUrn = unpacked.getSecond();
		} else {
			originalUrn = resourceReference.getUrn();
		}

		final ITicket ret = ticketService.open(ITicket.Type.ResourcePublication, "resource", originalUrn, "user",
				user.getUsername());
		final File uploadDirectory = resourcePath;

		/*
		 * spawn thread that will publish and resolve the ticket with the "urn"
		 * parameter set to the public URN.
		 */
		new Thread() {
			@Override
			public void run() {
				try {
					IResource resource = null;
					if (uploadDirectory != null) {
						resource = catalog.importResource(uploadDirectory, user);
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

	public Collection<String> getOnlineResources() {
		return onlineResourceUrns;
	}
	
	public IResource getResource(String urn, Set<Group> groups) {
		// TODO permission check
		return catalog.get(urn);
	}

	public Collection<String> getCatalogs() {
		// TODO maybe some catalogs could be reserved to specific groups/users
		return catalog.getCatalogs();
	}

	public Collection<String> getNamespaces() {
		// TODO maybe some namespaces could be reserved to specific groups/users
		return catalog.getNamespaces();
	}

	public String getDefaultCatalog() {
		return catalog.getDefaultCatalog();
	}

	public String getDefaultNamespace() {
		return catalog.getDefaultNamespace();
	}

	public boolean canAccess(String urn, EngineAuthorization user) {
		IResource resource = catalog.get(urn);
		if (resource != null) {
			// TODO
			return true;
		}
		return false;
	}

}
