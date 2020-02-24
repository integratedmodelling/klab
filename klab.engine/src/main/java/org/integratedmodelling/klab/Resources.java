package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IFileResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.common.CompileInfo;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.SemanticType;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.data.encoding.DecodingDataBuilder;
import org.integratedmodelling.klab.data.encoding.LocalDataBuilder;
import org.integratedmodelling.klab.data.encoding.StandaloneResourceBuilder;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.data.storage.FutureResource;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.resources.ComponentsWorkspace;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.engine.resources.MonitorableFileWorkspace;
import org.integratedmodelling.klab.engine.resources.Project;
import org.integratedmodelling.klab.engine.resources.PublicResourceCatalog;
import org.integratedmodelling.klab.engine.resources.ServiceWorkspace;
import org.integratedmodelling.klab.engine.runtime.SimpleRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.LocalResourceReference;
import org.integratedmodelling.klab.rest.NamespaceCompilationResult;
import org.integratedmodelling.klab.rest.ProjectReference;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceDataRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.klab.utils.ZipUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Management and resolution of URNs. Also holds the URN metadata database for
 * local and public URNs.
 * 
 * @author ferdinando.villa
 *
 */
public enum Resources implements IResourceService {

	/**
	 * The global instance singleton.
	 */
	INSTANCE;

	class ResourceData {
		long timestamp;
		boolean online;
	}

	private Map<String, ResourceData> statusCache = Collections.synchronizedMap(new HashMap<>());
	private IKimLoader loader = null;

	/**
	 * Retry interval for resource online status refresh. TODO link to
	 * configuration.
	 */
	private long RETRY_INTERVAL_MINUTES = 15;

	Map<String, IResourceAdapter> resourceAdapters = Collections.synchronizedMap(new HashMap<>());
	Map<String, IUrnAdapter> urnAdapters = Collections.synchronizedMap(new HashMap<>());
	
	/**
	 * Cache for universal resources coming from nodes, to avoid continuous retrieval.
	 * TODO FIXME expire these regularly based on timestamp and store original node for
	 * a quick node check before use.
	 */
	Map<String, IResource> remoteCache = Collections.synchronizedMap(new HashMap<>());

	/**
	 * The local resource catalog is a map that can be set by the engine according
	 * to implementation. By default a persistent JSON database is used; tests may
	 * reset the resource catalog to a simple in-memory map using
	 * {@link #setResourceCatalog} before any request is made.
	 */
	IResourceCatalog localResourceCatalog;
	PublicResourceCatalog publicResourceCatalog = new PublicResourceCatalog();

	Map<String, Map<String, Project>> projectCatalog = new HashMap<>();

	Map<String, IWorkspace> workspaces = new HashMap<>();

	/**
	 * The core workspace, only containing the OWL knowledge distributed with the
	 * software, and no projects.
	 */
	private CoreOntology coreKnowledge;

	/**
	 * The worldview, synchronized at startup from Git repositories specified in or
	 * through the k.LAB certificate.
	 */
	private IWorldview worldview;

	/**
	 * The workspace containing components from the network (or local components if
	 * so configured), loaded on demand.
	 */
	private IWorkspace components;

	/**
	 * Workspace containing the k.LAB assets installed on the running instance. The
	 * files in this workspace are monitored and reloaded incrementally at each
	 * change.
	 */
	private IWorkspace local;

	/**
	 * The service workspace contains one project per session user where to define
	 * uploads, learned models and on-the-fly scenarios, plus one temporary project
	 * per user where extemporaneous resources are saved.
	 */
	private ServiceWorkspace service;

	private IProject localProject;

	private Resources() {
		Services.INSTANCE.registerService(this, IResourceService.class);
	}

	public IProject getLocalProject() {
		return this.localProject;
	}

	@Override
	public IWorkspace getLocalWorkspace() {
		return local;
	}

	@Override
	public ServiceWorkspace getServiceWorkspace() {
		return service;
	}

	@Override
	public CoreOntology getUpperOntology() {
		return coreKnowledge;
	}

	@Override
	public IWorldview getWorldview() {
		return worldview;
	}

	@Override
	public IWorkspace getComponentsWorkspace() {
		return components;
	}

	public Collection<IResourceAdapter> getResourceAdapters() {
		return resourceAdapters.values();
	}

	/*
	 * Extract and load the OWL core knowledge workspace.
	 */
	public boolean loadCoreKnowledge(IMonitor monitor) {
		try {
			coreKnowledge = new CoreOntology(Configuration.INSTANCE.getDataPath("knowledge"));
			coreKnowledge.load(monitor);
			workspaces.put(coreKnowledge.getName(), coreKnowledge);
			return true;
		} catch (Throwable e) {
			Logging.INSTANCE.error(e.getLocalizedMessage());
		}
		return false;
	}

	/*
	 * Create and load the components workspace. TODO needs the network to obtain
	 * components, then add/override any local ones.
	 */
	public boolean loadComponents(Collection<File> localComponentPaths, IMonitor monitor) {
		try {
			List<String> deployedComponents = new ArrayList<>();
			IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
			if (user != null) {
				for (Group group : user.getGroups()) {
					if (!group.isWorldview()) {
						deployedComponents.addAll(group.getProjectUrls());
					}
				}
			}
			components = new ComponentsWorkspace("components", Configuration.INSTANCE.getDataPath("workspace/deploy"),
					deployedComponents);
			workspaces.put(components.getName(), components);
			return true;
		} catch (Throwable e) {
			Logging.INSTANCE.error(e.getLocalizedMessage());
		}
		return false;
	}

	/*
	 * Create and load the worldview specified by the Git repositories pointed to by
	 * the certificate.
	 */
	public boolean loadWorldview(ICertificate certificate, IMonitor monitor) {
		try {
			worldview = certificate.getWorldview();
			this.loader = worldview.load(this.loader, monitor);
			workspaces.put(worldview.getName(), worldview);
			return true;
		} catch (Throwable e) {
			Logging.INSTANCE.error(e.getLocalizedMessage());
		}
		return false;
	}

	/*
	 * Initialize (index but do not load) the local workspace from the passed path.
	 */
	public void initializeLocalWorkspace(File workspaceRoot, IMonitor monitor) {
		if (local == null) {
			local = new MonitorableFileWorkspace("workspace", workspaceRoot);
			workspaces.put("workspace", local);
		}
	}

	/*
	 * Initialize (index but do not load) the service workspace from the passed
	 * path.
	 */
	public void initializeServiceWorkspace(File workspaceRoot, IMonitor monitor) {
		if (service == null) {
			service = new ServiceWorkspace(workspaceRoot);
			workspaces.put(service.getName(), service);
		}
	}

	/*
	 * Create and load the local workspace.
	 */
	public boolean loadLocalWorkspace(IMonitor monitor) {
		try {
			this.loader = getLocalWorkspace().load(this.loader, monitor);
			return true;
		} catch (Throwable e) {
			Logging.INSTANCE.error(e);
		}
		return false;
	}

	/*
	 * Create and load the local workspace.
	 */
	public boolean loadServiceWorkspace(IMonitor monitor) {
		try {
			this.loader = getServiceWorkspace().load(this.loader, monitor);
			return true;
		} catch (Throwable e) {
			Logging.INSTANCE.error(e);
		}
		return false;
	}

	public IWorkspace getWorkspace(String name) {
		return workspaces.get(name);
	}

	public IWorkspace getWorkspaceFor(File projectRoot) {
		for (IWorkspace workspace : workspaces.values()) {
			for (IProject project : workspace.getProjects()) {
				if (project.getRoot().equals(projectRoot)) {
					return workspace;
				}
			}
		}
		return null;
	}

	@Override
	public Project getProject(String name) {
		IKimProject project = Kim.INSTANCE.getProject(name);
		return project == null ? null : retrieveOrCreate(project);
	}

	public Collection<IProject> getProjects() {
		List<IProject> ret = new ArrayList<>();
		for (IKimProject project : Kim.INSTANCE.getProjects()) {
			ret.add(retrieveOrCreate(project));
		}
		return ret;
	}

	/**
	 * Return the IProject wrapper for a IKimProject, creating it if it does not
	 * exist. Project names are unique within a workspace.
	 * 
	 * @param project
	 * @return the IProject wrapper.
	 */
	public Project retrieveOrCreate(IKimProject project) {

		String workspace = project.getWorkspace().getName();

		if (projectCatalog.get(workspace) != null && projectCatalog.get(workspace).containsKey(project.getName())) {
			return projectCatalog.get(workspace).get(project.getName());
		}

		Project ret = new Project(project);
		if (projectCatalog.get(workspace) == null) {
			projectCatalog.put(workspace, new HashMap<>());
		}
		projectCatalog.get(workspace).put(ret.getName(), ret);

		return ret;
	}

	public static String getConceptPrefix() {
		return Urns.KLAB_URN_PREFIX + "knowledge:" + INSTANCE.getWorldview().getName() + ":";
	}

	public IResourceAdapter getResourceAdapter(String id) {
		return resourceAdapters.get(id);
	}

	public IUrnAdapter getUrnAdapter(String id) {
		return urnAdapters.get(id);
	}

	public List<IResourceAdapter> getResourceAdapter(File resource, IParameters<String> parameters) {
		List<IResourceAdapter> ret = new ArrayList<>();
		for (IResourceAdapter adapter : resourceAdapters.values()) {
			if (adapter.getValidator().canHandle(resource, parameters)) {
				ret.add(adapter);
			}
		}
		return ret;
	}

	/**
	 * Model URNs start with this followed either by 'local:' (for local models) or
	 * the server ID they came from.
	 */
	public final static String MODEL_URN_PREFIX = Urns.KLAB_URN_PREFIX + "models:";

	@Override
	public IResource resolveResource(String urn, IProject project) {

		boolean isLocalName = urn.indexOf(':') < 0;

		if (isLocalName && project == null) {
			throw new IllegalArgumentException("local resource name passed without a project");
		}

		if (project == null || !isLocalName) {
			return resolveResource(urn);
		}

		return project.getLocalResource(urn);
	}

	@Override
	public IResource resolveResource(String urns) {

		IResource ret = null;
		Urn urn = new Urn(urns);

		if (urn.isLocal()) {
			ret = getLocalResourceCatalog().get(urn.getUrn());
		} else if (urn.isUniversal()) {
				
			// TODO this should periodically expire resources whose timestamp is > x
			if (this.remoteCache.containsKey(urn.getUrn())) {
				ret = this.remoteCache.get(urn.getUrn());
			} else {
			
			IUrnAdapter adapter = getUrnAdapter(urn.getCatalog());
			if (adapter != null) {
				// locally available, use local
				return adapter.getResource(urns);
			} else {
				// if 1+ nodes provide the adapter, ask it for the
				// resource descriptor.
				INodeIdentity node = Network.INSTANCE.getNodeForResource(urn);
				if (node != null) {
					/*
					 * get the resource descriptor directly from the node
					 */
					ResourceReference reference = node.getClient().get(API.NODE.RESOURCE.RESOLVE_URN,
							ResourceReference.class, "urn", urn.getUrn());
					ret = new Resource(reference);
					this.remoteCache.put(urn.getUrn(), ret);
				}
			}
			}
		} else {
			ret = publicResourceCatalog.get(urn.getUrn());
		}

		/*
		 * apply any modification from parameters if any
		 */
		if (ret != null && !urn.getParameters().isEmpty()) {
			ret = ((Resource) ret).applyParameters(urn.getParameters());
		}

		return ret;

	}

	/**
	 * Create a resource URN from an ID and a project. Must be running with an
	 * authenticated user identity. Resource may or may not exist.
	 * 
	 * @param resourceId
	 * @param project
	 * @return
	 */
	public String createLocalResourceUrn(String resourceId, IProject project) {
		IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
		if (user == null) {
			throw new KlabAuthorizationException("cannot establish current user: resources cannot be created");
		}
		return "local:" + user.getUsername() + ":" + project.getName() + ":" + resourceId;
	}

	@Override
	public IResource createLocalResource(String resourceId, File file, IParameters<String> parameters, IProject project,
			String adapterType, boolean forceUpdate, boolean asynchronous, IMonitor monitor) {

		IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
		if (user == null) {
			throw new KlabAuthorizationException("cannot establish current user: resources cannot be created");
		}

		String urn = "local:" + user.getUsername() + ":" + project.getName() + ":" + resourceId;
		IResource ret = null;

		/**
		 * 2. see if it was processed before and timestamps match; if so, return
		 * existing resource unless we're forcing a revision
		 */
		ret = getLocalResourceCatalog().get(urn);

		if (!forceUpdate && ret != null && ret.getResourceTimestamp() >= file.lastModified()) {
			return ret;
		}

		/**
		 * Local resource versions are 0.0.build with the build starting at 1.
		 * Publishing them makes 0.1.build. Only their owners (or peer review?) can
		 * promote them to 1.0.0 or anything higher than the initial version.
		 */
		final Version version = ret == null ? Version.create("0.0.1")
				: ret.getVersion().withBuild(ret.getVersion().getBuild() + 1);

		// TODO define history items - add to previous if existing, date of creation
		// etc.
		List<IResource> history = new ArrayList<>();
		if (ret != null) {
			for (IResource resource : ret.getHistory()) {
				history.add(resource);
			}
			ret.getHistory().clear();
			history.add(ret);
		}

		return asynchronous
				? importResourceAsynchronously(urn, project, adapterType, file, parameters, version, history, monitor)
				: importResource(urn, project, adapterType, file, parameters, version, history, monitor);
	}

	/**
	 * Create a resource from a remote request, which is assumed valid and
	 * non-existing.
	 * 
	 * @param request
	 * @param monitor
	 * @return
	 */
	public IResource createLocalResource(ResourceCRUDRequest request, Monitor monitor) {

		monitor.info(
				"Start importing resource " + request.getResourceUrns().iterator().next() + ": this may take a while");

		String urn = request.getResourceUrns().iterator().next();
		IProject project = getProject(request.getDestinationProject());
		String adapterType = request.getAdapter();
		IResourceAdapter adapter = getResourceAdapter(adapterType);
		if (project == null || adapter == null) {
			throw new IllegalArgumentException("create resource: wrong request (adapter or project missing)");
		}

		/*
		 * translate the parameters to their actual types. Empty strings as parameters
		 * are possible.
		 */
		Parameters<String> parameters = Parameters.create();
		for (IPrototype.Argument argument : adapter.getResourceConfiguration().listArguments()) {
			if (request.getParameters().containsKey(argument.getName())) {
				String value = request.getParameters().get(argument.getName());
				if (value != null && !value.trim().isEmpty()) {
					parameters.put(argument.getName(), Utils.asPOD(value));
				}
			}
		}

		IResource ret = importResource(urn, project, adapterType, null, parameters, Version.create("0.0.1"),
				new ArrayList<>(), monitor);

		monitor.info("Import of resource " + request.getResourceUrns().iterator().next() + " finished");

		return ret;
	}

	private IResource importResource(String urn, IProject project, String adapterType, File file,
			IParameters<String> parameters, Version version, List<IResource> history, IMonitor monitor) {

		String id = Path.getLast(urn, ':');
		List<Throwable> errors = new ArrayList<>();
		IResource resource = null;
		File resourceDatapath = null;
		String resourceDataDir = id + ".v" + version;

		try {

			IResourceAdapter adapter = null;
			if (adapterType == null) {
				List<IResourceAdapter> adapters = getResourceAdapter(file, parameters);
				if (adapters.size() > 0) {
					/*
					 * TODO logics to pick the best for the input
					 */
					adapter = adapters.get(0);
					adapterType = adapter.getName();
				}
			} else {
				adapter = resourceAdapters.get(adapterType);
			}

			if (adapter != null) {

				IResourceValidator validator = adapter.getValidator();

				/*
				 * TODO use adapter metadata to validate the input before calling validate()
				 */
				Builder builder = validator.validate(file == null ? null : file.toURI().toURL(), parameters, monitor);

				// add all history items
				for (IResource his : history) {
					builder.addHistory(his);
				}

				/*
				 * if no errors, copy files and add notifications to resource; set relative
				 * paths in resource metadata
				 */
				if (!builder.hasErrors() && file != null) {
					resourceDatapath = new File(
							project.getRoot() + File.separator + "resources" + File.separator + resourceDataDir);
					resourceDatapath.mkdirs();

					for (File f : validator.getAllFilesForResource(file)) {
						FileUtils.copyFile(f,
								new File(resourceDatapath + File.separator + MiscUtilities.getFileName(f)));
						builder.addLocalResourcePath(project.getName() + "/resources/" + resourceDataDir + "/"
								+ MiscUtilities.getFileName(f));
					}
				}

				resource = builder.withResourceVersion(version).withProjectName(project.getName())
						.withParameters(parameters).withAdapterType(adapterType)
						.withLocalPath(project.getName() + "/resources/" + resourceDataDir).build(urn);

				resource.getExports().putAll(adapter.getImporter().getExportCapabilities(resource));

			} else {
				errors.add(new KlabValidationException("cannot find an adapter to process file " + file));
			}

		} catch (Exception e) {
			// FIXME only add KlabResourceException
			errors.add(e);
		}

		if (resource == null) {
			// FIXME not sure this is OK
			resource = Resource.error(urn, errors);
		}

		/*
		 * Resources with errors and files go in the catalog and become bright red
		 * eyesores in k.IM
		 * 
		 * FIXME these should only be errors that the user can do something about. Must
		 * properly encode those as KlabResourceException.
		 */
		if (file != null || !resource.hasErrors()) {
			localResourceCatalog.put(urn, resource);
		}

		if (resource.hasErrors()) {
			// TODO report errors but leave the resource so we can validate any use of it
			monitor.error("Resource " + urn + " has errors:");
		}

		return resource;
	}

	private FutureResource importResourceAsynchronously(String urn, IProject project, String adapterType, File file,
			IParameters<String> parameters, Version version, List<IResource> history, IMonitor monitor) {
		// TODO create future, send it for execution, return it. The future knows its
		// URN so it can be used for basic ops.
		return null;
	}

	/**
	 * Bulk import of several resources from a URL. Uses all the importers that
	 * declare they can handle the URL unless the adapter type is passed.
	 * 
	 * @param source      a URL compatible with one or more adapter importers
	 * @param project     the destination project for the local resources built
	 * @param adapterType optional, pass if needed to resolve ambiguities or prevent
	 *                    excessive calculations.
	 * @return
	 */
	public Collection<IResource> importResources(URL source, IProject project, @Nullable String adapterType) {

		List<IResourceAdapter> importers = new ArrayList<>();
		IParameters<String> parameters = new Parameters<String>();
		for (IResourceAdapter adapter : resourceAdapters.values()) {
			if ((adapterType == null || adapter.getName().equals(adapterType)) && adapter.getImporter() != null
					&& adapter.getImporter().canHandle(source.toString(), parameters)) {
				importers.add(adapter);
			}
		}

		List<IResource> ret = new ArrayList<>();

		for (IResourceAdapter adapter : importers) {

			IResourceImporter importer = adapter.getImporter();

			for (Builder builder : importer.importResources(source.toString(), parameters,
					Klab.INSTANCE.getRootMonitor())) {

				/*
				 * if no errors, copy files and add notifications to resource; set relative
				 * paths in resource metadata
				 */
				if (!builder.hasErrors()) {

					File resourceDatapath = new File(project.getRoot() + File.separator + "resources" + File.separator
							+ builder.getResourceId());
					resourceDatapath.mkdirs();

					for (File f : builder.getImportedFiles()) {
						try {
							FileUtils.copyFile(f,
									new File(resourceDatapath + File.separator + MiscUtilities.getFileName(f)));
						} catch (IOException e) {
							throw new KlabIOException(e);
						}
						builder.addLocalResourcePath(project.getName() + "/resources/" + builder.getResourceId() + "/"
								+ MiscUtilities.getFileName(f));
					}
				}

				// NB: should never be null but it is
				IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
				String owner = user == null ? "integratedmodelling.org" : user.getUsername();

				IResource resource = builder.withResourceVersion(Version.create("0.0.1"))
						.withProjectName(project.getName()).withParameters(parameters)
						.withAdapterType(adapter.getName())
						.withLocalPath(project.getName() + "/resources/" + builder.getResourceId())
						.build(Urns.INSTANCE.getLocalUrn(builder.getResourceId(), project, owner));

				if (resource != null && !resource.hasErrors()) {
					resource.getExports().putAll(adapter.getImporter().getExportCapabilities(resource));
					getLocalResourceCatalog().put(resource.getUrn(), resource);
					ret.add(resource);
				}

			}
		}

		return ret;
	}

	/**
	 * Easy programmatic access to resource importer.
	 * 
	 * @param resourceUrl
	 * @param project
	 * @param parameterKVPs
	 * @return the finished resource
	 */
	public IResource importResource(URL resourceUrl, IProject project, Object... parameterKVPs) {
		Importer importer = Resources.INSTANCE.createImporter(resourceUrl, project);
		if (parameterKVPs != null) {
			for (int i = 0; i < parameterKVPs.length; i++) {
				importer.with(parameterKVPs[i].toString(), parameterKVPs[i + 1]);
				i++;
			}
		}
		return importer.finish();
	}

	public boolean importIntoResource(URL importUrl, IResource resource, IMonitor monitor) {
		IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
		if (adapter != null) {
			return adapter.getImporter().importIntoResource(importUrl, resource, monitor);
		}
		return false;
	}

	/**
	 * Extract the OWL assets in the classpath (under /knowledge/**) to the
	 * specified filesystem directory.
	 * 
	 * @param destinationDirectory
	 * @throws IOException
	 */
	public void extractKnowledgeFromClasspath(File destinationDirectory) throws IOException {

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		org.springframework.core.io.Resource[] resources = resolver.getResources("/knowledge/**");
		for (org.springframework.core.io.Resource resource : resources) {

			String path = null;
			if (resource instanceof FileSystemResource) {
				path = ((FileSystemResource) resource).getPath();
			} else if (resource instanceof ClassPathResource) {
				path = ((ClassPathResource) resource).getPath();
			}
			if (path == null) {
				throw new IOException("internal: cannot establish path for resource " + resource);
			}

			if (!path.endsWith("owl")) {
				continue;
			}

			String filePath = path.substring(path.indexOf("knowledge/") + "knowledge/".length());

			int pind = filePath.lastIndexOf('/');
			if (pind >= 0) {
				String fileDir = filePath.substring(0, pind);
				File destDir = new File(destinationDirectory + File.separator + fileDir);
				destDir.mkdirs();
			}
			File dest = new File(destinationDirectory + File.separator + filePath);
			InputStream is = resource.getInputStream();
			FileUtils.copyInputStreamToFile(is, dest);
			is.close();
		}
	}

	// @Override
	public Pair<IArtifact, IArtifact> resolveResourceToArtifact(String urn, IMonitor monitor, boolean forceGrid,
			ISemantic observableSemantics, ISemantic contextObservableSemantics) {

		IObservable observable = observableSemantics instanceof IObservable ? (IObservable) observableSemantics
				: (observableSemantics == null ? null : Observable.promote(observableSemantics.getType()));
		IObservable contextObservable = contextObservableSemantics instanceof IObservable
				? (IObservable) contextObservableSemantics
				: (contextObservableSemantics == null ? null
						: Observable.promote(contextObservableSemantics.getType()));

		Pair<String, Map<String, String>> urnp = Urns.INSTANCE.resolveParameters(urn);
		IResource resource = resolveResource(urn);

		if (resource == null) {
			return null;
		}

		Scale scale = Scale.create(resource.getGeometry());
		if (forceGrid || !resource.getType().isCountable()) {
			scale = scale.adaptForExample();
		}

		if (contextObservable == null) {
			contextObservable = Observable.promote(OWL.INSTANCE.getNonsemanticPeer("Context", IArtifact.Type.OBJECT));
		}

		SimpleRuntimeScope context = new SimpleRuntimeScope((Observable) contextObservable, scale, monitor);
		IArtifact ctxArtifact = context.getTargetArtifact();

		if (observable == null) {
			observable = Observable.promote(OWL.INSTANCE.getNonsemanticPeer("Artifact", resource.getType()));
		}

		IKlabData data = getResourceData(resource, urnp.getSecond(), scale,
				context.getChild((Observable) observable, resource));

		return data == null ? null : new Pair<>(ctxArtifact, data.getArtifact());
	}

	@Override
	public Pair<IArtifact, IArtifact> resolveResourceToArtifact(String urn, IMonitor monitor) {
		return resolveResourceToArtifact(urn, monitor, false, null, null);
	}

	public IKlabData getResourceData(String urn, IKlabData.Builder builder, IMonitor monitor) {
		Pair<String, Map<String, String>> split = Urns.INSTANCE.resolveParameters(urn);
		IResource resource = resolveResource(urn);
		IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
		if (adapter == null) {
			throw new KlabUnsupportedFeatureException(
					"adapter for resource of type " + resource.getAdapterType() + " not available");
		}
		adapter.getEncoder().getEncodedData(resource, split.getSecond(), resource.getGeometry(), builder,
				Expression.emptyContext(monitor));
		return builder.build();
	}

	@Override
	public IKlabData getResourceData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			IContextualizationScope context) {

		boolean local = Urns.INSTANCE.isLocal(resource.getUrn());
		Urn urn = new Urn(resource.getUrn(), urnParameters);
		if (urn.isUniversal()) {
			// use it locally only if we have the adapter.
			local = getResourceAdapter(urn.getCatalog()) != null;
		}

		if (local) {

			if (urn.isUniversal()) {

				IUrnAdapter adapter = getUrnAdapter(urn.getCatalog());
				if (adapter == null) {
					throw new KlabUnsupportedFeatureException(
							"adapter for resource of type " + resource.getAdapterType() + " not available");
				}

				IKlabData.Builder builder = new LocalDataBuilder((IRuntimeScope) context);
				try {
					adapter.getEncodedData(urn, builder, geometry, context);
					return builder.build();
				} catch (Throwable e) {
					// just return null later
				}

			} else {

				IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
				if (adapter == null) {
					throw new KlabUnsupportedFeatureException(
							"adapter for resource of type " + resource.getAdapterType() + " not available");
				}

				IKlabData.Builder builder = new LocalDataBuilder((IRuntimeScope) context);
				try {
					adapter.getEncoder().getEncodedData(resource, urnParameters, geometry, builder, context);
					return builder.build();
				} catch (Throwable e) {
					// just return null later
				}
			}
		} else {

			INodeIdentity node = Network.INSTANCE.getNodeForResource(urn);
			if (node != null) {
				ResourceDataRequest request = new ResourceDataRequest();
				// send toString() with all parameters!
				request.setUrn(urn.toString());
				request.setGeometry(geometry.encode());
				DecodingDataBuilder builder = new DecodingDataBuilder(
						node.getClient().post(API.NODE.RESOURCE.CONTEXTUALIZE, request, Map.class), context);
				return builder.build();
			}
		}

		return null;
	}

	@Override
	public IKimObject getModelObject(String urn) {

		String serverId = null;
		IKimObject ret = null;

		if (urn.startsWith(Urns.KLAB_URN_PREFIX)) {
			/*
			 * remove all needed pieces until we are left with a server ID and a normal
			 * path, or an observable
			 */
		}

		if (Urns.INSTANCE.isUrn(urn)) {

			Urn kurn = new Urn(urn);
			if (kurn.isUniversal()) {

				/*
				 * TODO support
				 */

				IUrnAdapter adapter = urnAdapters.get(kurn.getCatalog());
				if (adapter == null) {
					return null;
				}

				VisitingDataBuilder builder = new VisitingDataBuilder();
				adapter.getEncodedData(kurn, builder, null, null);

				// resource specifies one object
				if (builder.getObjectCount() == 1) {

					if (builder.getObjectScale(0).getSpace() != null) {
						/*
						 * build an observer from the data and return it
						 */
						return Observations.INSTANCE.makeROIObserver(builder.getObjectName(0),
								builder.getObjectScale(0).getSpace().getShape(), builder.getObjectMetadata(0));
					}
				}
			}

		}

		if (serverId == null) {

			String ns = Path.getLeading(urn, '.');
			String ob = Path.getLast(urn, '.');
			if (ns == null && SemanticType.validate(urn)) {
				SemanticType st = new SemanticType(urn);
				ns = st.getNamespace();
				ob = st.getName();
			}

			INamespace namespace = Namespaces.INSTANCE.getNamespace(ns);
			if (namespace == null) {
				return null;
			}

			ret = namespace.getObject(ob);

		} else {

			/*
			 * TODO logics to synchronize the project and its requirements from the server.
			 */

		}

		return ret;
	}

	@Override
	public IResolvable getResolvableResource(String urn, IScale scale) {

		IKimObject obj = getModelObject(urn);
		if (obj instanceof IResolvable) {
			return (IResolvable) obj;
		}
		if (obj instanceof IConceptDefinition) {
			return Observable.promote((IConceptDefinition) obj);
		}
		// if it has spaces or parentheses it may be a declaration; try that one last
		// time before giving up
		if (urn.contains(" ") || urn.contains("(")) {
			IObservable obs = Observables.INSTANCE.declare(urn);
			if (obs != null) {
				// for debug only
				((Observable) obs).setOriginatingModelId("QUERY: " + urn);
				return ((Observable) obs);
			}
		}
		return null;
	}

	public void registerUrnAdapter(String type, IUrnAdapter adapter) {
		urnAdapters.put(type, adapter);
	}

	public void registerResourceAdapter(String type, IResourceAdapter adapter) {
		resourceAdapters.put(type, adapter);
	}

	/**
	 * Only call this just after {@link Engine#start()} if overriding the default,
	 * persistent resource catalog is desired.
	 * 
	 * @param catalog
	 */
	public void setLocalResourceCatalog(IResourceCatalog catalog) {
		this.localResourceCatalog = catalog;
	}

	@Override
	public IResourceCatalog getLocalResourceCatalog() {
		if (localResourceCatalog == null) {
			localResourceCatalog = new ResourceCatalog("localresources");
		}
		return localResourceCatalog;
	}

	public PublicResourceCatalog getPublicResourceCatalog() {
		return publicResourceCatalog;
	}

	// @Override
	public Builder createResourceBuilder() {
		return new ResourceBuilder();
	}

//	public ExecutorService getResourceTaskExecutor() {
//		if (resourceTaskExecutor == null) {
//			// TODO condition both the type and the parameters of the executor to options
//			resourceTaskExecutor = Executors.newFixedThreadPool(Configuration.INSTANCE.getResourceThreadCount());
//		}
//		return resourceTaskExecutor;
//	}

	@Override
	public boolean isResourceOnline(String urn) {
		if (!Urns.INSTANCE.isLocal(urn) && !Urns.INSTANCE.isUniversal(urn)) {
			return publicResourceCatalog.isOnline(urn);
		}
		IResource resource = resolveResource(urn);
		return resource == null ? false : isResourceOnline(resource);
	}

	public boolean isResourceOnline(IResource resource) {
		return isResourceOnline(resource, false);
	}

	public boolean isResourceOnline(IResource resource, boolean forceUpdate) {

		if (resource instanceof MergedResource) {
			return ((MergedResource) resource).isOnline();
		}

		if (Configuration.INSTANCE.forceResourcesOnline()) {
			return true;
		}

		if (!forceUpdate) {
			ResourceData cached = statusCache.get(resource.getUrn());
			if (cached != null
					&& (System.currentTimeMillis() - cached.timestamp) < (RETRY_INTERVAL_MINUTES * 60 * 1000)) {
				return cached.online;
			}
		}

		if (Urns.INSTANCE.isLocal(resource.getUrn())) {
			IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
			if (adapter != null) {
				boolean ret = adapter.getEncoder().isOnline(resource);
				ResourceData cached = statusCache.get(resource.getUrn());
				if (cached == null) {
					cached = new ResourceData();
					cached.online = ret;
					cached.timestamp = System.currentTimeMillis();
					statusCache.put(resource.getUrn(), cached);
				}
				return ret;
			}
		} else if (Urns.INSTANCE.isUniversal(resource.getUrn())) {
			Urn urn = new Urn(resource.getUrn());
			if (getUrnAdapter(urn.getCatalog()) != null) {
				return getUrnAdapter(urn.getCatalog()).isOnline(urn);
			} else {
				// can only have come from a remote node, assumed online
				return true;
			}
		} else {
			return publicResourceCatalog.isOnline(resource.getUrn());
		}

		return false;
	}

	/**
	 * Create an importer for the resource specified by the passed URL into the
	 * passed project.
	 * 
	 * @param url
	 * @param project
	 */
	public Importer createImporter(URL url, IProject project) {
		return new ImporterImpl(url, project);
	}

	class ImporterImpl implements Importer {

		URL url;
		File file;
		IProject project;
		IParameters<String> parameters = new Parameters<>();
		String adapter;
		String id;

		ImporterImpl(URL url, IProject project) {
			this.url = url;
			this.project = project;
			if (url.getProtocol().equals("file")) {
				this.file = new File(url.getFile());
				this.id = MiscUtilities.getFileBaseName(this.file);
			} else {
				this.id = MiscUtilities.getURLBaseName(this.url.toString());
			}
		}

		@Override
		public Importer withAdapter(String adapter) {
			this.adapter = adapter;
			return this;
		}

		@Override
		public Importer with(String parameter, Object value) {
			this.parameters.put(parameter, value);
			return this;
		}

		@Override
		public Importer withId(String id) {
			this.id = id;
			return this;
		}

		@Override
		public IResource finish() {
			return createLocalResource(this.id, this.file, this.parameters, this.project, this.adapter, true, false,
					Klab.INSTANCE.getRootMonitor());
		}

	}

	public ResourceReference synchronize(File rdir) {
		File rdef = new File(rdir + File.separator + "resource.json");
		if (rdef.exists()) {
			ResourceReference rref = JsonUtils.load(rdef, ResourceReference.class);
			if (!getLocalResourceCatalog().containsKey(rref.getUrn())) {
				// Logging.INSTANCE.info("synchronizing project resource " + rref.getUrn());
				getLocalResourceCatalog().put(rref.getUrn(), new Resource(rref));
			}
			return rref;
		}
		return null;
	}

	public ProjectReference createProjectDescriptor(IProject project) {

		ProjectReference ret = new ProjectReference();
		ret.setName(project.getName());
		ret.setRootPath(project.getRoot());
		for (INamespace namespace : project.getNamespaces()) {
			CompileInfo compileInfo = Kim.INSTANCE.getCompileInfo(namespace.getId());
			NamespaceCompilationResult compilationResult = new NamespaceCompilationResult();
			compilationResult.setNamespaceId(namespace.getId());
			if (compileInfo != null) {
				compilationResult.getNotifications().addAll(compileInfo.getErrors());
				compilationResult.getNotifications().addAll(compileInfo.getWarnings());
				compilationResult.getNotifications().addAll(compileInfo.getInfo());
			}
			compilationResult.setPublishable(namespace.isPublishable());
			ret.getCompilationReports().add(compilationResult);
		}
		for (String urn : project.getLocalResourceUrns()) {
			// TODO should also include notifications from namespace compilation in project
			LocalResourceReference rref = new LocalResourceReference();
			rref.setUrn(urn);
			IResource resource = Resources.INSTANCE.resolveResource(urn);
			if (resource != null) {
				rref.setOnline(Resources.INSTANCE.isResourceOnline(resource));
				rref.setError(resource.hasErrors());
				ret.getLocalResources().add(rref);
			}
		}
		return ret;
	}

	public void moveLocalResource(IResource resource, IProject destinationProject) {

		IProject sourceProject = getProject(resource.getLocalProjectName());
		if (sourceProject == null || sourceProject.getName().equals(destinationProject.getName())) {
			throw new IllegalArgumentException(
					"moving resource: source project is invalid or the same as the destination");
		}

		String originalUrn = resource.getUrn();
		String originalProject = resource.getLocalProjectName();

		/*
		 * set project move into resource history
		 */
		((Resource) resource).copyToHistory("Copied to new project " + destinationProject.getName());
		((Resource) resource).setLocalProject(destinationProject.getName());
		((Resource) resource).setUrn(Urns.INSTANCE.changeLocalProject(originalUrn, destinationProject.getName()));
		((Resource) resource).setLocalPath(
				destinationProject.getName() + resource.getLocalPath().substring(originalProject.length()));

		List<String> newLocalPaths = new ArrayList<>();
		for (String path : ((Resource) resource).getLocalPaths()) {
			newLocalPaths.add(destinationProject.getName() + path.substring(originalProject.length()));
		}
		resource.getLocalPaths().clear();
		resource.getLocalPaths().addAll(newLocalPaths);

		getLocalResourceCatalog().remove(originalUrn);
		getLocalResourceCatalog().put(resource.getUrn(), resource);

		Logging.INSTANCE.info("moved resource " + originalUrn + " to project " + destinationProject.getName()
				+ ": new URN is " + resource.getUrn());
	}

	/**
	 * Validate, register and notify a resource that has been completely defined
	 * outside of the service (not by a validator but most likely using the
	 * {@link StandaloneResourceBuilder}).
	 * 
	 * @param ret
	 * @return
	 */
	public IResource registerResource(IResource ret) {
		((Resource) ret).validate(this);
		IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(ret.getAdapterType());
		ret.getExports().putAll(adapter.getImporter().getExportCapabilities(ret));
		getLocalResourceCatalog().put(ret.getUrn(), ret);
		return getLocalResourceCatalog().get(ret.getUrn());
	}

	/**
	 * Return the loader used to load all the knowledge. Typically this contains
	 * knowledge from various workspaces, such as worldview, components and user.
	 * During code generation this may be null or an outer-level loader: use
	 * {@link } instead.
	 * 
	 * @return the loader. Can only be relied upon after loading, not during
	 *         validation or code generation.
	 */
	public IKimLoader getLoader() {
		return loader;
	}

	public Collection<ResourceAdapterReference> describeResourceAdapters() {
		List<ResourceAdapterReference> ret = new ArrayList<>();
		for (String adapter : resourceAdapters.keySet()) {
			ResourceAdapterReference ref = new ResourceAdapterReference();
			ref.setName(adapter);
			IPrototype configuration = resourceAdapters.get(adapter).getResourceConfiguration();
			ref.setLabel(configuration.getLabel());
			ref.setDescription(configuration.getDescription());
			ref.setParameters(Extensions.INSTANCE.describePrototype(configuration));
			ref.setFileBased(resourceAdapters.get(adapter) instanceof IFileResourceAdapter);
			ref.getExportCapabilities().putAll(Resources.INSTANCE.getResourceAdapter(adapter).getImporter()
					.getExportCapabilities((IResource) null));
			ref.setMultipleResources(resourceAdapters.get(adapter).getImporter().acceptsMultiple());
			ret.add(ref);
		}
		for (String adapter : urnAdapters.keySet()) {
			ResourceAdapterReference ref = new ResourceAdapterReference();
			ref.setName(adapter);
			IUrnAdapter urnAdapter = urnAdapters.get(adapter);
			ref.setLabel(adapter);
			ref.setDescription(urnAdapter.getDescription());
			ref.setUniversal(true);
			ref.setFileBased(false);
			ref.setMultipleResources(false);
			ret.add(ref);
		}
		return ret;
	}

	/**
	 * Return an object from a namespace's symbol table.
	 * 
	 * @param id
	 * @return
	 */
	public Object getSymbol(String id) {
		String nsId = Path.getLeading(id, '.');
		INamespace ns = Namespaces.INSTANCE.getNamespace(nsId);
		return ns == null ? null : ns.getSymbolTable().get(Path.getLast(id, '.'));
	}

	public void deleteProject(String projectId) {

		// physically delete project
		IProject project = getProject(projectId);
		if (project != null) {
			project.getWorkspace().deleteProject(project);
		}
		// reload
		getLoader().rescan(true);
	}

	public IGeometry getGeometry(IContextualizable resource) {
		switch (resource.getType()) {
		case RESOURCE:
			IResource res = resolveResource(resource.getUrn());
			if (res != null) {
				return res.getGeometry();
			}
			break;
		case SERVICE:
			Prototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
			if (prototype != null) {
				return prototype.getGeometry();
			}
			break;
		default:
			break;

		}
		return Geometry.scalar();
	}

	/**
	 * Return the type computed by the specified resource.
	 * 
	 * @param resource
	 * @return
	 */
	public Type getType(IContextualizable resource) {

		switch (resource.getType()) {
		case CLASSIFICATION:
			return Type.CONCEPT;
		case CONDITION:
			break;
		case CONVERSION:
			break;
		case EXPRESSION:
			break;
		case LITERAL:
			return Utils.getArtifactType(Utils.getPODClass(resource.getLiteral()));
		case LOOKUP_TABLE:
			return resource.getLookupTable().getLookupType();
		case RESOURCE:
			IResource res = resolveResource(resource.getUrn());
			if (res != null) {
				return res.getType();
			}
			break;
		case SERVICE:
			Prototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
			if (prototype != null) {
				return prototype.getType();
			}
			break;
		default:
			break;

		}
		return null;
	}

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
		return adapter.getCalculator(resource);
	}

	@Override
	public ITicket submitResource(IResource resource, String nodeId, Map<String, String> suggestions) {

		final INodeIdentity node = Network.INSTANCE.getNode(nodeId);

		if (resource.hasErrors() || !validateForPublication(resource)) {
			throw new KlabValidationException(
					"Resource " + resource.getUrn() + " cannot be published: " + resource.getStatusMessage());
		}
		if (node == null) {
			throw new KlabResourceNotFoundException("Resource " + resource.getUrn() + " cannot be published: node "
					+ nodeId + " unresponsive or offline");
		}

		IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
		String userId = user == null ? "anonymous" : user.getUsername();

		final ITicket ret = Klab.INSTANCE.getTicketManager().open("user", userId, ITicket.Type.ResourceSubmission,
				"node", nodeId, "resource", resource.getUrn());

		new Thread() {
			@Override
			public void run() {
				try {
					if (Urns.INSTANCE.isLocal(resource.getUrn())) {
						if (resource.getLocalPaths().isEmpty()) {
							ResourceReference reference = ((Resource) resource).getReference();
							reference.getMetadata().putAll(suggestions);
							TicketResponse.Ticket response = node.getClient().post(API.NODE.RESOURCE.SUBMIT_DESCRIPTOR,
									reference, TicketResponse.Ticket.class);
							ret.update("ticket", response.getId());
						} else {
							if (!suggestions.isEmpty()) {
								File pprop = new File(
										((Resource) resource).getPath() + File.separator + "publish.properties");
								Properties properties = new Properties();
								properties.putAll(suggestions);
								try (OutputStream out = new FileOutputStream(pprop)) {
									properties.store(out, null);
								}
							}
							File zipFile = new File(
									System.getProperty("java.io.tmpdir") + File.separator + ret.getId() + ".zip");
							ZipUtils.zip(zipFile, ((Resource) resource).getPath(), false, true);
							if (!suggestions.isEmpty()) {
								FileUtils.deleteQuietly(new File(
										((Resource) resource).getPath() + File.separator + "publish.properties"));
							}
							TicketResponse.Ticket response = node.getClient().postFile(API.NODE.RESOURCE.SUBMIT_FILES,
									zipFile, TicketResponse.Ticket.class);
							ret.update("ticket", response.getId());
						}
					} else {
						// TODO republish an update to a remote resource - should be just the updated
						// ResourceReference.
					}
				} catch (Throwable e) {
					ret.error("Error during publishing: " + e.getMessage());
				}

			}
		}.start();

		return ret;

	}

	@Override
	public boolean validateForPublication(IResource resource) {
		// TODO Auto-generated method stub
		return true;
	}

}
