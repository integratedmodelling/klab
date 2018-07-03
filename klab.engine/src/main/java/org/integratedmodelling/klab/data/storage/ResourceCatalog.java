package org.integratedmodelling.klab.data.storage;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JsonUtils;

/**
 * The Nitrite database storing resource data. Resource data are automatically
 * replicated to a local directory if that is passed in the constructor.
 * 
 * @author ferdinando.villa
 *
 */
public class ResourceCatalog implements IResourceCatalog {

	Nitrite db;
	ObjectRepository<ResourceReference> resources;

	/**
	 * Create a new resource catalog.
	 * 
	 * @param name
	 * @param localPath
	 */
	public ResourceCatalog(String name) {
		this.db = Nitrite.builder()// .compressed() TODO may reintegrate in production
				.filePath(Configuration.INSTANCE.getDataPath() + File.separator + name + ".db")
				.openOrCreate("user", "password");
		this.resources = db.getRepository(ResourceReference.class);
	}

	@Override
	public int size() {
		return (int) resources.size();
	}

	@Override
	public boolean isEmpty() {
		return resources.size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return resources.find(eq("urn", key)).hasMore();
	}

	@Override
	public boolean containsValue(Object value) {
		return value instanceof IResource && containsKey(((IResource) value).getUrn());
	}

	@Override
	public IResource get(Object key) {
		ResourceReference ref = resources.find(eq("urn", key)).firstOrDefault();
		return ref == null ? null : new Resource(ref);
	}

	@Override
	public IResource put(String key, IResource value) {

		((Resource) value).validate(Resources.INSTANCE);
		
		IResource ret = get(value.getUrn());
		if (ret != null) {
			removeDefinition(value.getUrn());
		}
		
		ResourceReference ref = ((Resource) value).getReference();
		resources.insert(ref);

		if (value.getLocalPath() != null) {
			/*
			 * Save resource data as JSON in resource path
			 */
			File resourcePath = new File(Resources.INSTANCE.getLocalWorkspace().getRoot() + File.separator + value.getLocalPath());
			resourcePath.mkdir();
			try {
				FileUtils.writeStringToFile(new File(resourcePath + File.separator + "resource.json"),
						JsonUtils.printAsJson(ref));
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}

		return ret;
	}

	public IResource removeDefinition(Object key) {
		IResource ret = get(key);
		resources.remove(eq("urn", key));
		return ret;
	}
	
	@Override
	public IResource remove(Object key) {
		IResource ret = get(key);
		resources.remove(eq("urn", key));
		if (ret != null && ret.getLocalPath() != null) {
			File resourcePath = new File(Resources.INSTANCE.getLocalWorkspace().getRoot() + File.separator + ret.getLocalPath());
			if (resourcePath.exists()) {
				try {
					FileUtils.deleteDirectory(resourcePath);
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
			}
		}
		return ret;
	}

	@Override
	public void putAll(Map<? extends String, ? extends IResource> m) {
		for (String key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	@Override
	public void clear() {
		for (IResource resource : values()) {
			if (resource.getLocalPath() != null) {
				File respath = new File(Resources.INSTANCE.getLocalWorkspace().getRoot() + File.separator + resource.getLocalPath());
				if (respath.isDirectory()) {
					try {
						FileUtils.deleteDirectory(respath);
					} catch (IOException e) {
						throw new KlabIOException(e);
					}
				}
			}
		}
		resources.remove((ObjectFilter) null);
	}

	@Override
	public Set<String> keySet() {
		Set<String> ret = new HashSet<>();
		for (Iterator<ResourceReference> r = resources.find().iterator(); r.hasNext();) {
			ret.add(r.next().getUrn());
		}
		return ret;
	}

	@Override
	public Collection<IResource> values() {
		List<IResource> ret = new ArrayList<>();
		for (Iterator<ResourceReference> r = resources.find().iterator(); r.hasNext();) {
			ret.add(new Resource(r.next()));
		}
		return ret;
	}

	@Override
	public Set<Entry<String, IResource>> entrySet() {
		Set<Entry<String, IResource>> ret = new HashSet<>();
		for (Iterator<ResourceReference> r = resources.find().iterator(); r.hasNext();) {
			final ResourceReference rr = r.next();
			ret.add(new Entry<String, IResource>() {
				@Override
				public String getKey() {
					return rr.getUrn();
				}

				@Override
				public IResource getValue() {
					return new Resource(rr);
				}

				@Override
				public IResource setValue(IResource value) {
					put(value.getUrn(), value);
					return new Resource(rr);
				}
			});
		}
		return ret;
	}

	public static void main(String args[]) {

		ResourceCatalog catalog = new ResourceCatalog("test");
		IResource resource = new ResourceBuilder().withResourceVersion(Version.getCurrent()).withAdapterType("wcs")
				.withGeometry(Geometry.empty()).build("zio:cane:test:hostia");

		catalog.put(resource.getUrn(), resource);
		IResource retrieved = catalog.get(resource.getUrn());
		System.out.println("Retrieved: " + retrieved);
	}

}
