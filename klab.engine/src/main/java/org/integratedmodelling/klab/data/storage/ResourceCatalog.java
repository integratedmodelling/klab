package org.integratedmodelling.klab.data.storage;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import java.io.File;
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

/**
 * The Nitrite database storing resource data.
 * 
 * @author ferdinando.villa
 *
 */
public class ResourceCatalog implements IResourceCatalog {

	Nitrite db;
	ObjectRepository<Resource> resources;

	public ResourceCatalog(String name) {
		db = Nitrite.builder()// .compressed() TODO may reintegrate in production
				.filePath(Configuration.INSTANCE.getDataPath() + File.separator + name + ".db")
				.openOrCreate("user", "password");
		resources = db.getRepository(Resource.class);
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
		return resources.find(eq("urn", key)).firstOrDefault();
	}

	@Override
	public IResource put(String key, IResource value) {

		((Resource) value).validate(Resources.INSTANCE);

		IResource ret = get(value.getUrn());
		if (ret != null) {
			remove(value.getUrn());
		}
		resources.insert((Resource) value);
		return ret;
	}

	@Override
	public IResource remove(Object key) {
		IResource ret = get(key);
		resources.remove(eq("urn", key));
		return ret;
	}

	@Override
	public void putAll(Map<? extends String, ? extends IResource> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		resources.remove((ObjectFilter) null);
	}

	@Override
	public Set<String> keySet() {
		Set<String> ret = new HashSet<>();
		for (Iterator<Resource> r = resources.find().iterator(); r.hasNext();) {
			ret.add(r.next().getUrn());
		}
		return ret;
	}

	@Override
	public Collection<IResource> values() {
		List<IResource> ret = new ArrayList<>();
		for (Iterator<Resource> r = resources.find().iterator(); r.hasNext();) {
			ret.add(r.next());
		}
		return ret;
	}

	@Override
	public Set<Entry<String, IResource>> entrySet() {
		Set<Entry<String, IResource>> ret = new HashSet<>();
		for (Iterator<Resource> r = resources.find().iterator(); r.hasNext();) {
			final Resource rr = r.next();
			ret.add(new Entry<String, IResource>() {
				@Override
				public String getKey() {
					return rr.getUrn();
				}
				@Override
				public IResource getValue() {
					return rr;
				}
				@Override
				public IResource setValue(IResource value) {
					put(value.getUrn(), value);
					return rr;
				}
			});
		}
		return ret;
	}

	public static void main(String args[]) {

		ResourceCatalog catalog = new ResourceCatalog("test");
		IResource resource = new ResourceBuilder().setResourceVersion(Version.getCurrent()).setAdapterType("wcs")
				.setGeometry(Geometry.empty()).build("zio:cane:test:hostia");

		catalog.put(resource.getUrn(), resource);
		IResource retrieved = catalog.get(resource.getUrn());
		System.out.println("Retrieved: " + retrieved);
	}

}
