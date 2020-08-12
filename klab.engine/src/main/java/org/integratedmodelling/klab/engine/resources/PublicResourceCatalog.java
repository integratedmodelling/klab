package org.integratedmodelling.klab.engine.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;

public class PublicResourceCatalog {

	class ResourceDescriptor {
		Set<String> nodes = new HashSet<>();
		boolean online;
		ResourceReference metadata;
	}

	Map<String, ResourceDescriptor> descriptors = Collections.synchronizedMap(new HashMap<>());

	public List<String> getOnlineUrns() {
		List<String> ret = new ArrayList<>();
		for (String urn : descriptors.keySet()) {
			if (isOnline(urn)) {
				ret.add(urn);
			}
		}
		return ret;
	}

	public void update(INodeIdentity node) {
		for (String urn : node.getResources()) {
			ResourceDescriptor descriptor = descriptors.get(urn);
			if (descriptor == null) {
				descriptor = new ResourceDescriptor();
				descriptors.put(urn, descriptor);
			}
			descriptor.nodes.add(node.getName());
			if (!descriptor.online && node.isOnline()) {
				descriptor.online = true;
			}
		}
	}

	public boolean isOnline(String urn) {
		ResourceDescriptor descriptor = descriptors.get(urn);
		if (descriptor == null || !descriptor.online) {
			return false;
		}
		return descriptor.nodes.size() > 0;
	}

	public synchronized IResource get(String urn) {
		ResourceDescriptor descriptor = descriptors.get(urn);
		if (descriptor == null) {
			Urn kurn = new Urn(urn);
			INodeIdentity node = Network.INSTANCE.getNode(kurn.getNodeName());
			if (node != null /* && node.isOnline() */) {
				try {
					ResourceReference res = node.getClient().get(API.url(API.NODE.RESOURCE.RESOLVE_URN, API.P_URN, urn),
							ResourceReference.class);
					if (res != null) {
						descriptor = new ResourceDescriptor();
						descriptor.metadata = res;
						descriptor.online = true;
						descriptor.nodes.add(node.getName());
						descriptors.put(urn, descriptor);
					}
				} catch (Exception e) {
					// move on with descriptor = null;
				}
			}
		}
		if (descriptor == null || !descriptor.online) {
			return null;
		}
		if (descriptor.metadata == null) {
			for (String id : descriptor.nodes) {
				INodeIdentity node = Network.INSTANCE.getNode(id);
				if (node.isOnline()) {
					try {
						descriptor.metadata = node.getClient()
								.get(API.url(API.NODE.RESOURCE.RESOLVE_URN, API.P_URN, urn), ResourceReference.class);
						break;
					} catch (Throwable t) {
						// move to the next
					}
				}
			}
		}
		return descriptor.metadata == null ? null : new Resource(descriptor.metadata);
	}

	public void remove(INodeIdentity node) {
		for (ResourceDescriptor descriptor : descriptors.values()) {
			if (descriptor.nodes.remove(node.getId())) {
				descriptor.online = descriptor.nodes.size() > 0;
			}
		}
	}

	public Collection<String> getNodes(String urn) {
		ResourceDescriptor descriptor = descriptors.get(urn);
		if (descriptor == null || !descriptor.online) {
			return new ArrayList<>();
		}
		return descriptor.nodes;
	}

}
