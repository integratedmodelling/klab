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
import org.integratedmodelling.klab.api.auth.KlabPermissions;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.EncodingDataBuilder;
import org.integratedmodelling.klab.node.NodeApplication;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.auth.Role;
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
				// six minutes default
				Configuration.INSTANCE.getProperty(NodeApplication.RESOURCE_CHECKING_INTERVAL_SECONDS, "360")));

	}

	protected synchronized void checkResources() {

		List<String> urns = new ArrayList<>(this.catalog.keySet());
		List<String> online = new ArrayList<>();
		List<String> offline = new ArrayList<>();
		for (String urn : urns) {
			boolean ok = false;
			IResource resource = catalog.get(urn);
			if (resource != null) {
				IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
				ok = adapter != null && adapter.getEncoder().isOnline(resource);
			}
			if (ok) {
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

			IUrnAdapter adapter = Resources.INSTANCE.getUrnAdapter(kurn.getCatalog());
			if (adapter == null) {
				return null;
			}

			EncodingDataBuilder builder = new EncodingDataBuilder();
			adapter.getEncodedData(kurn, builder, geometry, null);
			return builder.buildEncoded();

		}

		Urn kUrn = new Urn(urn);
		IResource resource = catalog.get(kUrn.getUrn());
		if (resource == null) {
			throw new IllegalArgumentException("URN " + kUrn + " cannot be resolved");
		}

		EncodingDataBuilder builder = new EncodingDataBuilder();
		Resources.INSTANCE.getResourceData(resource, kUrn.getParameters(), geometry, null);
		return builder.buildEncoded();
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
			if (user.getRoles().contains(Role.ROLE_ADMINISTRATOR)) {
				return true;
			}
			if (resource.getMetadata().containsKey(IMetadata.DC_CONTRIBUTOR)
					&& user.getUsername().equals(resource.getMetadata().get(IMetadata.DC_CONTRIBUTOR))) {
				return true;
			}
			KlabPermissions permissions = KlabPermissions.empty();
			if (resource.getMetadata().containsKey(IMetadata.IM_PERMISSIONS)) {
				permissions = KlabPermissions.create(resource.getMetadata().get(IMetadata.IM_PERMISSIONS).toString());
			}
			List<String> groups = new ArrayList<>();
			for (Group group : user.getGroups()) {
				groups.add(group.getId());
			}
			return permissions.isAuthorized(user.getUsername(), groups);
		}
		return false;
	}

}
