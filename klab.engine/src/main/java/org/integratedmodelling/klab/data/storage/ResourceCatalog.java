package org.integratedmodelling.klab.data.storage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.engine.indexing.ResourceIndexer;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Pair;

/**
 * The database storing resource data. Resource data are automatically
 * replicated to a local directory if that is passed in the constructor.
 * 
 * @author ferdinando.villa
 *
 */
public class ResourceCatalog implements IResourceCatalog {

	String name;

	/**
	 * TODO use MapDB as a persistent option (for the public catalog).
	 */
	private Map<String, ResourceReference> resources = Collections.synchronizedMap(new HashMap<>());
	private Map<String, IResource> inlineResources = Collections.synchronizedMap(new HashMap<>());

	/**
	 * Create a new resource catalog.
	 * 
	 * @param name
	 * @param localPath
	 */
	public ResourceCatalog(String name) {
		this.name = name;
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
		return resources.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return value instanceof IResource && containsKey(((IResource) value).getUrn());
	}

	@Override
	public IResource get(Object key) {
		if (inlineResources.containsKey(key)) {
			return inlineResources.get(key);
		}
		ResourceReference ref = resources.get(key);
		return ref == null ? null : new Resource(ref);
	}

	/**
	 * Return the resource's valid directory path in the filesystem, creating it if
	 * necessary. If this returns null, there must be an error in the resource.
	 * 
	 * @param resource
	 * @return
	 */
	private File getResourcePath(IResource resource) {

		File ret = null;

		if (resource.getLocalPath() != null) {
			String resPath = resource.getLocalPath();
			// ACHTUNG never call Resources.INSTANCE.getProject() here - it will recursively
			// sync resources, resulting in stack overflow.
			IKimProject project = Kim.INSTANCE.getProject(resource.getLocalProjectName());
			if (project == null) {
				throw new KlabIOException("resource " + resource.getUrn() + " belongs to invalid project "
						+ resource.getLocalProjectName());
			}
			resPath = resPath.substring(resource.getLocalProjectName().length() + 1);
			ret = new File(project.getRoot() + File.separator + resPath);
			ret.mkdir();
		}

		return ret;
	}

	/**
	 * Return the new path and new resource data for moving the passed resource to
	 * another project.
	 * 
	 * @param value
	 * @return
	 */
	private Pair<File, ResourceReference> getResourcePath(IResource value, IKimProject destinationProject) {

		if (value.getLocalPath() != null) {

			File ret = null;
			String resPath = value.getLocalPath();
			resPath = resPath.substring(value.getLocalProjectName().length() + 1);
			ret = new File(destinationProject.getRoot() + File.separator + resPath);
			ret.mkdir();
			ResourceReference ref = ((Resource) value).getReference();
			ref.setLocalPath(destinationProject.getName() + "/" + resPath);
			List<String> localfiles = new ArrayList<>();
			for (String localfile : ref.getLocalPaths()) {
				if (localfile.startsWith(value.getLocalProjectName())) {
					localfile = localfile.substring(value.getLocalProjectName().length() + 1);
					localfiles.add(destinationProject.getName() + File.separator + localfile);
				} else {
					localfiles.add(localfile);
				}
			}
			ref.setLocalPaths(localfiles);
			ref.setProjectName(destinationProject.getName());
			return new Pair<>(ret, ref);
		}

		return null;
	}

	public void addInlineResource(IResource resource) {
		this.inlineResources.put(resource.getUrn(), resource);
	}

	@Override
	public IResource put(String key, IResource value) {

		((Resource) value).validate(Resources.INSTANCE);

		IResource ret = get(value.getUrn());
		ResourceReference ref = ((Resource) value).getReference();
		
		if (resources.put(value.getUrn(), ref) != null) {
//			ResourceIndexer.INSTANCE.delete(value);
		}
//		ResourceIndexer.INSTANCE.index(value);
		
		File resourcePath = getResourcePath(value);
		if (resourcePath != null) {
			try {
				File resFile = new File(resourcePath + File.separator + "resource.json");
				if (!resFile.exists() || resFile.lastModified() < ref.getResourceTimestamp()) {
					FileUtils.writeStringToFile(resFile, JsonUtils.printAsJson(ref), StandardCharsets.UTF_8);
				}
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}
		return ret;
	}

	public IResource removeDefinition(String key) {
		IResource ret = get(key);
		resources.remove(key);
		return ret;
	}

	@Override
	public IResource remove(Object key) {
		IResource ret = get(key);
		// resources.remove(eq("urn", key));
		File resourcePath = getResourcePath(ret);
		IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(ret.getAdapterType());
		if (adapter != null) {
		    IResourcePublisher publisher = adapter.getPublisher();
		    if (publisher != null) {
		        publisher.unpublish(ret, Klab.INSTANCE.getRootMonitor());
		    }
		}
		if (resourcePath != null) {
			try {
				FileUtils.deleteDirectory(resourcePath);
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}
		resources.remove(key);
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
			File resourcePath = getResourcePath(resource);
	        IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
	        if (adapter != null) {
	            IResourcePublisher publisher = adapter.getPublisher();
	            if (publisher != null) {
	                publisher.unpublish(resource, Klab.INSTANCE.getRootMonitor());
	            }
	        }
			if (resourcePath.isDirectory()) {
				try {
					FileUtils.deleteDirectory(resourcePath);
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
			}
		}
		resources.clear();
	}

	@Override
	public Set<String> keySet() {
		return resources.keySet();
	}

	@Override
	public Collection<IResource> values() {
		List<IResource> ret = new ArrayList<>();
		for (ResourceReference r : resources.values()) {
			ret.add(new Resource(r));
		}
		return ret;
	}

	@Override
	public Set<Entry<String, IResource>> entrySet() {
		Set<Entry<String, IResource>> ret = new HashSet<>();
		for (final ResourceReference rr : resources.values()) {
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

	@Override
	public void clearOnly(Object... objects) {

		Set<String> urns = new HashSet<>();

		if (objects != null) {
			for (Object object : objects) {
				if (object instanceof IProject) {

					for (ResourceReference r : resources.values()) {
						if (r.getProjectName().equals(((IProject) object).getName())) {
							urns.add(r.getUrn());
						}
					}

					// resources.remove(eq("projectName", ((IProject) object).getName()));
				} else if (object instanceof IResource) {
					urns.add(((IResource) object).getUrn());
					// resources.remove(eq("urn", ((IResource) object).getUrn()));
				} else if (object instanceof String) {
					urns.add((String) object);
					// resources.remove(eq("urn", (String) object));
				} else {
					throw new IllegalArgumentException("cannot remove resources corresponding to selector " + object);
				}
			}
		}

		for (String s : urns) {
			remove(s);
		}
	}

	@Override
	public IResource move(IResource resource, IProject destinationProject) {

		File previousDir = getResourcePath(resource);
		Pair<File, ResourceReference> newData = getResourcePath(resource,
				Kim.INSTANCE.getProject(destinationProject.getName()));

		if (previousDir != null && newData != null) {
			try {
				FileUtils.copyDirectory(previousDir, newData.getFirst());
		        IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
		        if (adapter != null) {
		            IResourcePublisher publisher = adapter.getPublisher();
		            if (publisher != null) {
		                publisher.unpublish(resource, Klab.INSTANCE.getRootMonitor());
		            }
		        }
				FileUtils.writeStringToFile(new File(newData.getFirst() + File.separator + "resource.json"),
						JsonUtils.printAsJson(newData.getSecond()), StandardCharsets.UTF_8);
				FileUtils.deleteDirectory(previousDir);
				resources.remove(resource.getUrn());
				resources.put(resource.getUrn(), newData.getSecond());
				// db.commit();
			} catch (IOException e) {
				throw new KlabIOException(e);
			}

		}
		return get(resource.getUrn());
	}

	@Override
	public IResource copy(IResource resource, IProject destinationProject) {
		// define new name with _c and progressive number
		// copy resource files
		// overwrite resource.json with new project name
		// add new to catalog
		return null;
	}

	@Override
	public IResource rename(IResource resource, String newUrn, String updateMessage) {
		// TODO
		return null;
	}

	@Override
	public IResource update(IResource resource, String message) {

		((Resource) resource).validate(Resources.INSTANCE);

		ResourceReference ref = ((Resource) resource).getReference();
		ref.getNotifications().add(new Notification(message, Level.INFO.getName(), System.currentTimeMillis()));
		resources.put(resource.getUrn(), ref);
		
		File resourcePath = getResourcePath(resource);
		if (resourcePath != null) {
			try {
				File resFile = new File(resourcePath + File.separator + "resource.json");
				if (!resFile.exists() || resFile.lastModified() < ref.getResourceTimestamp()) {
					FileUtils.writeStringToFile(resFile, JsonUtils.printAsJson(ref), StandardCharsets.UTF_8);
				}
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}
		
		return get(resource.getUrn());
	}


}
