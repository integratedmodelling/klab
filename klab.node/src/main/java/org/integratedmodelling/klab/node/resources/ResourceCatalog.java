package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.auth.NodeAuthenticationManager;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.klab.utils.ZipUtils;

/**
 * The node resource catalog is very similar to the local resource catalog,
 * except it uses the datadir/resource path for all resources, removes project
 * information, supports versioning and has an import() method that will take
 * the metadata from a local resource (from an engine) and adapt it for use as a
 * public one.
 * <p>
 * In addition to the extended import functionalities, the catalog manages
 * permissions and metadata.
 * 
 * @author ferdinando.villa
 *
 */
public class ResourceCatalog implements IResourceCatalog {

	/**
	 * TODO use MapDB as a persistent option.
	 */
	private Map<String, ResourceReference> resources = Collections.synchronizedMap(new HashMap<>());
	private Map<String, File> resourcePaths = Collections.synchronizedMap(new HashMap<>());
	private File resourcePath;
	private Set<String> namespaces = new HashSet<>();
	private Set<String> catalogs = new HashSet<>();
	private static Set<String> commonExtensions;

	static {
		commonExtensions = Collections.synchronizedSet(new HashSet<>());
		commonExtensions.add(".shp");
		commonExtensions.add(".tif");
		commonExtensions.add(".tiff");
		commonExtensions.add(".csv");
		commonExtensions.add(".xsl");
	}

	public ResourceReference getDescriptor(String urn) {
		return resources.get(urn);
	}

	private String sanitizeResourceId(String id) {
		if (id == null) {
			return null;
		}
		id = id.toLowerCase();
		for (String ext : commonExtensions) {
			if (id.endsWith(ext)) {
				id = id.substring(0, id.length() - ext.length());
			}
		}
		// all spaces become dots
		id = id.replaceAll("\\s+", ".");
		// all .N become _N
		id = id.replaceAll("\\.([0-9])", "_$1");
		
		String ret = Utils.sanitizeUrl(id);
		return ret;
	}

	/**
	 * Create a new resource catalog.
	 * 
	 * @param name
	 * @param localPath
	 */
	public ResourceCatalog() {
		this.resourcePath = Configuration.INSTANCE.getDataPath("resources");
		initialize();
	}

	private void initialize() {
		for (File resPath : this.resourcePath.listFiles()) {
			File metadata = new File(resPath + File.separator + "resource.json");
			if (metadata.exists()) {
				Resource resource = new Resource(JsonUtils.load(metadata, ResourceReference.class));
				put(resource.getUrn(), resource);
				resourcePaths.put(resource.getUrn(), resPath);
			}
		}
	}

	public IResource importResource(ResourceReference reference, EngineAuthorization user) {

		IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(reference.getAdapterType());
		IResourcePublisher publisher = adapter == null ? null : adapter.getPublisher();

		if (publisher == null) {
			throw new IllegalStateException(
					"cannot import resource " + reference.getUrn() + ": adapter is not recognized or cannot publish");
		}

		IResource ret = null;
		File resourcePath = new File(this.resourcePath + File.separator + NameGenerator.shortUUID());
		Resource resource = importResourceData(resourcePath, reference, user);
		File metadata = new File(resourcePath + File.separator + "resource.json");
		try {
			FileUtils.writeStringToFile(metadata, JsonUtils.printAsJson(resource.getReference()),
					StandardCharsets.UTF_8);

			// publish, validate and insert
			ret = publisher.publish(resource, this, Klab.INSTANCE.getRootMonitor());
			((Resource) ret).validate(Resources.INSTANCE);
			put(ret.getUrn(), ret);
			resourcePaths.put(ret.getUrn(), resourcePath);

		} catch (IOException e) {
			throw new KlabIOException(e);
		}

		return ret;
	}

	/**
	 * Fix local resource metadata so they behave properly in a public context.
	 * Fixing involves:
	 * <ul>
	 * <li>Determine a normalized, clean local ID and urn that is unique (user may
	 * change it later)</li>
	 * <li>Reset the version to 1.0.0</li>
	 * <li>Set localName and URN</li>
	 * <li>Reset localPath and localPaths to remove the project/resources
	 * prefix</li>
	 * <li>Void the projectName</li>
	 * <li>Add publication date and username to metadata</li>
	 * </ul>
	 * 
	 * @param reference
	 * @return the correspondent resource
	 */
	private Resource importResourceData(File resourcePath, ResourceReference reference, EngineAuthorization user) {

		Properties importSettings = new Properties();
		File importProperties = new File(resourcePath + File.separator + "publish.properties");
		if (importProperties.exists()) {
			try (InputStream in = new FileInputStream(importProperties)) {
				importSettings.load(in);
				Logging.INSTANCE.info("resource metadata imported from publish.properties:\n"
						+ Utils.propertiesToString(importSettings));

			} catch (Throwable t) {
				throw new KlabIOException(t);
			}
		}

		String id = reference.getMetadata().containsKey(Resource.PREFERRED_LOCALNAME_METADATA_KEY)
				? reference.getMetadata().get(Resource.PREFERRED_LOCALNAME_METADATA_KEY)
				: reference.getLocalName();

		if (!importSettings.isEmpty()) {
			id = importSettings.getProperty(IResourcePublisher.SUGGESTED_RESOURCE_ID_PROPERTY, id);
		}

		/*
		 * fix the resource reference
		 */
		id = sanitizeResourceId(id);

		reference.setLocalName(id);
		reference.setProjectName(null);
		reference.setLocalPath(resourcePath.toString());
		List<String> localpaths = new ArrayList<>();
		for (String s : reference.getLocalPaths()) {
			localpaths.add(removeProject(s));
		}
		reference.getLocalPaths().clear();
		reference.getLocalPaths().addAll(localpaths);

		reference.setVersion("1.0.0");
		reference.getMetadata().put(IMetadata.DC_DATE_AVAILABLE, "" + new Date().getTime());
		reference.getMetadata().put(IMetadata.DC_CONTRIBUTOR, user.getUsername());
		for (Object key : importSettings.keySet()) {
			reference.getMetadata().put(key.toString(), importSettings.get(key).toString());
		}

		String catalog = getCatalogName(reference, importSettings);
		String namespace = getNamespace(reference, importSettings);
		String urn = NodeAuthenticationManager.INSTANCE.getNodeName() + ":" + catalog + ":" + namespace + ":" + id;

		String present = urn;
		int i = 0;
		while (get(present) != null) {
			present = urn + "." + getIdModifier(i++);
		}
		reference.setUrn(present);

		return new Resource(reference);
	}

	private String getIdModifier(int i) {
		String prefix = "";
		if (i > 27) {
			prefix += (char) ('a' + (i / 25));
		}
		return prefix + (char) ('a' + (i % 25));
	}

	private String getCatalogName(ResourceReference reference, Properties preferred) {
		String ret = reference.getMetadata().get(Resource.PREFERRED_CATALOG_METADATA_KEY);
		if (ret == null && !preferred.isEmpty()) {
			ret = preferred.getProperty(IResourcePublisher.SUGGESTED_CATALOG_PROPERTY);
		}
		if (ret == null) {
			/*
			 * use thematic area if any
			 */
			ret = reference.getMetadata().get(IMetadata.IM_THEMATIC_AREA);
		}
		if (ret /* still */ == null) {
			ret = getDefaultCatalog();
		}
		return sanitizeResourceId(ret);
	}

	private String getNamespace(ResourceReference reference, Properties preferred) {
		String ret = reference.getMetadata().get(Resource.PREFERRED_NAMESPACE_METADATA_KEY);
		if (ret == null && !preferred.isEmpty()) {
			ret = preferred.getProperty(IResourcePublisher.SUGGESTED_NAMESPACE_PROPERTY);
		}
		if (ret == null) {
			/*
			 * use region if any
			 */
			ret = reference.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA);
		}
		if (ret == null) {
			ret = getDefaultNamespace();
		}
		return sanitizeResourceId(ret);
	}

	private String removeProject(String localPath) {
		// remove project/resources/folder/, leaving only the local file paths from the
		// data directory
		String ret = Path.getRemainder(localPath, "/");
		ret = Path.getRemainder(ret, "/");
		return Path.getRemainder(ret, "/");
	}

	public IResource importResource(File resourcePath, EngineAuthorization user) {

		File metadata = new File(resourcePath + File.separator + "resource.json");
		ResourceReference reference = JsonUtils.load(metadata, ResourceReference.class);
		IResource ret = importResourceData(resourcePath, reference, user);

		IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(ret.getAdapterType());
		IResourcePublisher publisher = adapter == null ? null : adapter.getPublisher();

		if (publisher == null) {
			try {
				FileUtils.deleteDirectory(resourcePath);
			} catch (IOException e) {
				Logging.INSTANCE.error(e);
			}
			throw new KlabIllegalStateException(
					"cannot import resource " + reference.getUrn() + ": adapter is not recognized or cannot publish");
		}

		try {
			// write out the modified resource, overwriting the original
			FileUtils.writeStringToFile(metadata, JsonUtils.printAsJson(((Resource) ret).getReference()),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new KlabIOException(e);
		}

		/*
		 * validate and load into catalog
		 */
		((Resource) ret).validate(Resources.INSTANCE);
		put(ret.getUrn(), ret);
		resourcePaths.put(ret.getUrn(), resourcePath);

		/**
		 * Call the publisher after the resource has been inserted, passing the catalog
		 * for updates or modifications.
		 */
		IResource published = publisher.publish(ret, this, Klab.INSTANCE.getRootMonitor());

		if (published == null) {
			remove(ret.getUrn());
		}

		return published;
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
		Urn urn = new Urn(key.toString());
		ResourceReference ref = resources.get(urn.getUrn());
		return ref == null ? null : new Resource(ref);
	}

	@Override
	public IResource put(String key, IResource value) {

		Urn urn = new Urn(key);
		this.namespaces.add(urn.getNamespace());
		this.catalogs.add(urn.getCatalog());
		IResource ret = get(value.getUrn());
		ResourceReference ref = ((Resource) value).getReference();
		resources.put(value.getUrn(), ref);

		return ret;
	}

	/**
	 * All known namespaces in alphabetical order.
	 * 
	 * @return
	 */
	public List<String> getNamespaces() {
		List<String> ret = new ArrayList<>(namespaces);
		Collections.sort(ret);
		return ret;
	}

	/**
	 * All known catalogs in alphabetical order.
	 * 
	 * @return
	 */
	public List<String> getCatalogs() {
		List<String> ret = new ArrayList<>(catalogs);
		Collections.sort(ret);
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
		File resourcePath = resourcePaths.remove(key);
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
		throw new IllegalStateException(
				"putAll cannot be called on a public resource catalog: all insertions must be atomic");
	}

	@Override
	public void clear() {
		for (IResource resource : values()) {
			File resourcePath = resourcePaths.remove(resource.getUrn());
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
		throw new IllegalStateException("Public resources cannot be moved");
	}

	@Override
	public IResource copy(IResource resource, IProject destinationProject) {
		throw new IllegalStateException("Public resources cannot be copied");
	}

	/**
	 * Update the resource currently identified by an urn to the contents of the
	 * metadata passed. Catch renamings and behave accordingly.
	 * 
	 * @param urn
	 * @param reference
	 * @return
	 */
	public IResource update(String urn, ResourceReference reference, String message) {

		File path = resourcePaths.get(urn);
		ResourceReference oldResource = getDescriptor(urn);

		if (path == null || oldResource == null) {
			throw new KlabResourceAccessException("cannot update non-existing resource " + reference.getUrn());
		}

		boolean renaming = urn.equals(reference.getUrn());
		ResourceReference newResource = reference;
		newResource.getHistory().add(oldResource);
		newResource.getNotifications().add(new Notification(message, Level.INFO.getName(), System.currentTimeMillis()));
		Version version = Version.create(oldResource.getVersion());
		newResource.setVersion(version.withMajor(version.getMajor() + 1).toString());
		newResource.setResourceTimestamp(System.currentTimeMillis());

		if (renaming) {
			this.resourcePaths.remove(urn);
			this.resources.remove(urn);
		}

		File metadata = new File(path + File.separator + "resource.json");
		JsonUtils.save(newResource, metadata);
		this.resourcePaths.put(newResource.getUrn(), path);
		this.resources.put(newResource.getUrn(), newResource);

		return get(reference.getUrn());
	}

	public String getDefaultNamespace() {
		return Configuration.INSTANCE.getProperty(Resource.DEFAULT_NAMESPACE_PROPERTY, "public");
	}

	public String getDefaultCatalog() {
		return Configuration.INSTANCE.getProperty(Resource.DEFAULT_CATALOG_PROPERTY, "generic");
	}

	public Pair<File, String> unpackArchive(File uploadArchive) {
		File temporary = new File(this.resourcePath + File.separator + NameGenerator.shortUUID());
		ZipUtils.unzip(uploadArchive, temporary);
		File metadata = new File(temporary + File.separator + "resource.json");
		ResourceReference reference = JsonUtils.load(metadata, ResourceReference.class);
		return new Pair<>(temporary, reference.getUrn());
	}

	@Override
	public IResource rename(IResource resource, String newUrn, String message) {
		ResourceReference reference = getDescriptor(resource.getUrn());
		reference.setUrn(newUrn);
		return update(resource.getUrn(), reference, message);
	}

	@Override
	public IResource update(IResource resource, String updateMessage) {
		return update(resource.getUrn(), ((Resource) resource).getReference(), updateMessage);
	}

}
