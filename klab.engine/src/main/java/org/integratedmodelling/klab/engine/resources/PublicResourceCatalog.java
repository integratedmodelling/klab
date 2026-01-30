package org.integratedmodelling.klab.engine.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.data.IPublicResourceCatalog;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class PublicResourceCatalog implements IPublicResourceCatalog {

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

	@Override
	public void updateNode(INodeIdentity node) {
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
		Urn kurn = new Urn(urn);
		if (descriptor == null) {

			INodeIdentity node = Network.INSTANCE.getNode(kurn.getNodeName());

			if (node == null) {
				// check for 1.0 resource
				var klabService = Network.INSTANCE.getKlabService(IIdentity.Type.RESOURCES, kurn.getNodeName());
				if (klabService != null) {
					var resource = resolveKlab10Resource(klabService, urn);
					if (resource != null) {
						return resource;
					}
				}
			} else {
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

	private Resource resolveKlab10Resource(INodeIdentity klabService, String urn) {
		// TODO Auto-generated method stub

		var client = klabService.getClient();
		try {
			var definition = client.get(API.KLAB_1.RESOURCES.RETRIEVE, Map.class, "urn", urn, "knowledgeClass",
					API.KLAB_1.KnowledgeClass.RESOURCE.name());

			if (definition instanceof Map map) {
				var descriptor = new ResourceReference();

				descriptor.setUrn(map.get("urn").toString());
				descriptor.setVersion(Version.create((Map<?, ?>) map.get("version")).toString());
				descriptor.setAdapterType(map.get("adapterType").toString());
				descriptor.setLocalName(map.get("localName").toString());
				descriptor.setType(Type.valueOf(map.get("type").toString()));
				descriptor.setResourceTimestamp((Long) map.get("timestamp"));
				var metadata = API.getParameterMap(map.get("metadata"));
				for (var key : metadata.keySet()) {
					var value = metadata.get(key);
					if (value instanceof String string)
						descriptor.getMetadata().put(key.toString(), string);
				}
				descriptor.setGeometry(Geometry.create((Map<?, ?>) map.get("geometry")).encode());

				var parameters = API.getParameterMap(map.get("parameters"));
				for (var parameter : parameters.keySet()) {
					descriptor.getParameters().put(parameter, parameters.get(parameter).toString());
				}

				for (var attribute : (Collection<?>) map.get("attributes")) {
					var amap = (Map<?,?>)attribute;
					var attr = new AttributeReference();
					attr.setIndex(((Number)amap.get("index")).intValue());
					attr.setName(amap.get("name").toString());
					attr.setOptional((Boolean)amap.get("optional"));
					attr.setKey((Boolean)amap.get("key"));
					attr.setType(Type.valueOf(amap.get("type").toString()));
					descriptor.getAttributes().add(attr);
				}
				for (var input : (Collection<?>) map.get("inputs")) {
					var amap = (Map<?,?>)input;
					var attr = new AttributeReference();
					attr.setIndex(((Number)amap.get("index")).intValue());
					attr.setName(amap.get("name").toString());
					attr.setOptional((Boolean)amap.get("optional"));
					attr.setKey((Boolean)amap.get("key"));
					attr.setType(Type.valueOf(amap.get("type").toString()));
					descriptor.getDependencies().add(attr);
				}
				for (var output : (Collection<?>) map.get("outputs")) {
					var amap = (Map<?,?>)output;
					var attr = new AttributeReference();
					attr.setIndex(((Number)amap.get("index")).intValue());
					attr.setName(amap.get("name").toString());
					attr.setOptional((Boolean)amap.get("optional"));
					attr.setKey((Boolean)amap.get("key"));
					attr.setType(Type.valueOf(amap.get("type").toString()));
					descriptor.getOutputs().add(attr);
				}

				return new Resource(descriptor);
			}

		} catch (Throwable t) {
			// just return null
			Logging.INSTANCE.error(t);
		}
		return null;
	}

	@Override
	public void removeNode(INodeIdentity node) {
		for (ResourceDescriptor descriptor : descriptors.values()) {
			if (descriptor.nodes.remove(node.getId())) {
				descriptor.online = descriptor.nodes.size() > 0;
			}
		}
	}

	@Override
	public Collection<String> getNodes(String urn) {
		ResourceDescriptor descriptor = descriptors.get(urn);
		if (descriptor == null) {
			// no resource was reported, see if the original node serving it is online and
			// if so, use that
			Urn kurn = new Urn(urn);
			INodeIdentity node = Network.INSTANCE.getNode(kurn.getNodeName());
			if (node != null && node.isOnline()) {
				return Collections.singleton(node.getName());
			}
		}
		if (descriptor == null || !descriptor.online) {
			return new ArrayList<>();
		}
		return descriptor.nodes;
	}

}
