package org.integratedmodelling.klab;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator.Operation;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.ILocalWorkspace;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ISession;
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
import org.integratedmodelling.klab.data.resources.Codelist;
import org.integratedmodelling.klab.data.resources.ContextualizedResource;
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
import org.integratedmodelling.klab.engine.resources.Workspace;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.SimpleRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.CodelistReference;
import org.integratedmodelling.klab.rest.EngineEvent;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.LocalResourceReference;
import org.integratedmodelling.klab.rest.MappingReference;
import org.integratedmodelling.klab.rest.NamespaceCompilationResult;
import org.integratedmodelling.klab.rest.ProjectReference;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.integratedmodelling.klab.rest.ResourceAdapterReference.OperationReference;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceContextualizationRequest;
import org.integratedmodelling.klab.rest.ResourceDataRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.SessionActivity.ResourceActivity;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.GitUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.klab.utils.ZipUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Management and resolution of URNs. Also holds the URN metadata database for local and public
 * URNs.
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

    class ResourceAdapterData {
        IResourceAdapter adapter;
        boolean canCreateEmpty;
        boolean canDropFiles;
        IPrototype prototype;
    }

    private Map<String, ResourceData> statusCache = Collections.synchronizedMap(new HashMap<>());
    private IKimLoader loader = null;
    private Properties properties;

    /**
     * Retry interval for resource online status refresh. TODO link to configuration.
     */
    private long RETRY_INTERVAL_MINUTES = 15;

    Map<String, ResourceAdapterData> resourceAdapters = Collections.synchronizedMap(new HashMap<>());
    Map<String, IUrnAdapter> urnAdapters = Collections.synchronizedMap(new HashMap<>());

    /**
     * Cache for universal resources coming from nodes, to avoid continuous retrieval. TODO FIXME
     * expire these regularly based on timestamp and store original node for a quick node check
     * before use.
     */
    Map<String, IResource> remoteCache = Collections.synchronizedMap(new HashMap<>());

    /**
     * The local resource catalog is a map that can be set by the engine according to
     * implementation. By default a persistent JSON database is used; tests may reset the resource
     * catalog to a simple in-memory map using {@link #setResourceCatalog} before any request is
     * made.
     */
    IResourceCatalog localResourceCatalog;
    PublicResourceCatalog publicResourceCatalog = new PublicResourceCatalog();

    Map<String, Map<String, Project>> projectCatalog = new HashMap<>();
    Map<String, IWorkspace> workspaces = new HashMap<>();
    Map<File, ILocalWorkspace> workspacesByRoot = new HashMap<>();

    /**
     * The core workspace, only containing the OWL knowledge distributed with the software, and no
     * projects.
     */
    private CoreOntology coreKnowledge;

    /**
     * The worldview, synchronized at startup from Git repositories specified in or through the
     * k.LAB certificate.
     */
    private IWorldview worldview;

    /**
     * The workspace containing components from the network (or local components if so configured),
     * loaded on demand.
     */
    private ILocalWorkspace components;

    /**
     * Workspace containing the k.LAB assets installed on the running instance. The files in this
     * workspace are monitored and reloaded incrementally at each change.
     */
    private ILocalWorkspace local;

    /**
     * The service workspace contains one project per session user where to define uploads, learned
     * models and on-the-fly scenarios, plus one temporary project per user where extemporaneous
     * resources are saved.
     */
    private ServiceWorkspace service;

    private IProject localProject;

    public static String REGEX_ENTRY = "regex";

    private Resources() {
        Services.INSTANCE.registerService(this, IResourceService.class);
        File propfile = new File(Configuration.INSTANCE.getDataPath() + File.separator + "resources.properties");
        this.properties = new Properties();
        if (propfile.exists()) {
            try (InputStream input = new FileInputStream(propfile)) {
                properties.load(input);
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
        }
    }

    public IProject getLocalProject() {
        return this.localProject;
    }

    @Override
    public ILocalWorkspace getLocalWorkspace() {
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
    public ILocalWorkspace getComponentsWorkspace() {
        return components;
    }

    public Collection<IResourceAdapter> getResourceAdapters() {
        List<IResourceAdapter> ret = new ArrayList<>();
        for (ResourceAdapterData rd : resourceAdapters.values()) {
            ret.add(rd.adapter);
        }
        return ret;
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
     * Create and load the components workspace; record group permissions for all projects
     */
    public boolean loadComponents(Collection<File> localComponentPaths, String worldview, IMonitor monitor) {
        try {
            Map<String, Set<String>> deployedComponents = new HashMap<>();
            IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
            if (user != null && !user.isAnonymous()) {
                for (Group group : user.getGroups()) {
                    if (!group.isWorldview()) {
                        for (String projectUrl : group.getProjectUrls()) {
                            Set<String> groups = deployedComponents.get(projectUrl);
                            if (groups == null) {
                                groups = Collections.synchronizedSet(new HashSet<>());
                            }
                            groups.add(group.getId());
                            deployedComponents.put(projectUrl, groups);
                        }
                    }
                }
            }
            components = new ComponentsWorkspace("components", Configuration.INSTANCE.getDataPath("workspace/deploy"), worldview,
                    deployedComponents);
            workspaces.put(components.getName(), components);

            for (String url : deployedComponents.keySet()) {
                String project = StringUtils.removeEnd(MiscUtilities.getNameFromURL(url), ".git");
                Authentication.INSTANCE.setProjectPermissions(project, deployedComponents.get(url));
            }

            return true;
        } catch (Throwable e) {
            Logging.INSTANCE.error(e.getLocalizedMessage());
        }
        return false;
    }

    /*
     * Create and load the worldview specified by the Git repositories pointed to by the
     * certificate. By design there is no permission checking in the worldview, semantic assets are
     * always shared universally.
     */
    public boolean loadWorldview(ICertificate certificate, String worldviewId, IMonitor monitor) {
        try {
            worldview = new Worldview(worldviewId, Configuration.INSTANCE.getDataPath("worldview"),
                    certificate.getWorldviewRepositories(worldviewId));
            this.loader = ((ILocalWorkspace) worldview).load(this.loader, monitor);
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
    public void initializeLocalWorkspace(File workspaceRoot, String worldview, IMonitor monitor) {
        if (local == null) {
            local = new MonitorableFileWorkspace("workspace", worldview, workspaceRoot);
            workspaces.put("workspace", local);
        }
    }

    /*
     * Initialize (index but do not load) the service workspace from the passed path.
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
    public boolean loadLocalWorkspace(String worldview, IMonitor monitor) {
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
     * 
     * @param gitUrl
     * @return
     */
    public IProject retrieveAndLoadProject(String gitUrl) {
        String projectId = GitUtils.requireUpdatedRepository(gitUrl, Configuration.INSTANCE.getDataPath("temp/projects"));
        return projectId == null ? null : loadSingleton(projectId, Configuration.INSTANCE.getDataPath("temp/projects"));
    }

    public IProject loadSingleton(String projectId, File dataPath) {
        ILocalWorkspace workspace = workspacesByRoot.get(dataPath);
        if (workspace == null) {
            workspace = new Workspace(dataPath, null);
            workspacesByRoot.put(dataPath, workspace);
        }
        return workspace.loadProject(projectId, Klab.INSTANCE.getRootMonitor());
    }

    /**
     * Return the IProject wrapper for a IKimProject, creating it if it does not exist. Project
     * names are unique within a workspace.
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
        return resourceAdapters.containsKey(id) ? resourceAdapters.get(id).adapter : null;
    }

    public IUrnAdapter getUrnAdapter(String id) {
        return urnAdapters.get(id);
    }

    public List<IResourceAdapter> getResourceAdapter(File resource, IParameters<String> parameters) {
        List<IResourceAdapter> ret = new ArrayList<>();
        for (ResourceAdapterData adapter : resourceAdapters.values()) {
            if (adapter.canDropFiles && adapter.adapter.getValidator().canHandle(resource, parameters)) {
                ret.add(adapter.adapter);
            }
        }
        return ret;
    }

    /**
     * Model URNs start with this followed either by 'local:' (for local models) or the server ID
     * they came from.
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
                        ResourceReference reference = node.getClient().get(API.NODE.RESOURCE.RESOLVE_URN, ResourceReference.class,
                                "urn", urn.getUrn());
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
     * Create a resource URN from an ID and a project. Must be running with an authenticated user
     * identity. Resource may or may not exist.
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

    /**
     * Create or update a locally available resource from a specification or/and by examining a
     * local file. This is the beginning of a resource's life cycle. When a resource is successfully
     * created, its data will be stored in the project under the resources folder, and synchronized
     * with the local resource catalog. If the file has been seen already, the resource is updated
     * in the local catalog with full history records.
     * <p>
     * The local resource will have a {@code [urn:klab:]local:user:project:resourceid.version} URN
     * which is visible only within the project. All files and needed info are copied within the
     * resources project area.
     * <p>
     * The resource ID is created from the file name if an id field is not present in the
     * parameters. It is an error to pass a null file and no id.
     * <p>
     * The update parameter controls whether revisions are possible with files that don't have a
     * newer timestamp than the resource. It will normally be set to true only when the resource
     * creation is created explicitly. This function is also used when reading or updating a
     * resource for a file named in a k.IM model.
     * <p>
     * Local resource versions are in the form 0.0.build with the build starting at 1 and increasing
     * at each update. Publishing them modifies the minor version, starting at 0.1.build. Only their
     * owners' explicit action, or peer review in a reviewed repository, modifies the major version
     * to make them 1.x.b or anything higher than the initial version.
     * 
     * @param resourceId the ID for the resource, which will be part of the URN and must be unique
     *        within a project.
     * @param file a {@link java.io.File} object. May be null if userData contain all relevant info.
     *        The local path of the file (starting at the project folder, inclusive) is stored in
     *        metadata and checked in case of redefinition, so that the URN is versioned rather than
     *        recreated.
     * @param userData user data. May be empty (if all that's needed is the file). Must contain a
     *        suitable id if the file is null. These are used to define URN parameters at the
     *        discretion of the adapter.
     * @param project the project for the resource. Can't be null. All local resources are
     *        project-local; only public resources are visible globally.
     * @param adapterType pass null to interrogate all adapters and choose the first fitting
     *        adapter. Must be passed if file is null.
     * @param update if true, allow updating of the resource every time this is called. Otherwise
     *        just create if absent or update when the timestamp on the resource is older than that
     *        of the file.
     * @param asynchronous if true, spawn a validator thread and return a proxy for the resource
     *        without blocking.
     * @param monitor a {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor} object.
     * @return a {@link org.integratedmodelling.klab.api.data.IResource} object. with a local URN if
     *         successful.
     */
    public IResource createLocalResource(String resourceId, File file, IParameters<String> parameters, IProject project,
            String adapterType, boolean forceUpdate, boolean asynchronous, IMonitor monitor) {

        IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
        if (user == null) {
            throw new KlabAuthorizationException("cannot establish current user: resources cannot be created");
        }

        String urn = "local:" + user.getUsername() + ":" + project.getName() + ":" + resourceId;
        IResource ret = null;

        /**
         * 2. see if it was processed before and timestamps match; if so, return existing resource
         * unless we're forcing a revision
         */
        ret = getLocalResourceCatalog().get(urn);

        if (!forceUpdate && ret != null && ret.getResourceTimestamp() >= file.lastModified()) {
            return ret;
        }

        /**
         * Local resource versions are 0.0.build with the build starting at 1. Publishing them makes
         * 0.1.build. Only their owners (or peer review?) can promote them to 1.0.0 or anything
         * higher than the initial version.
         */
        final Version version = ret == null
                ? Version.create("0.0.1")
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

    @Override
    public IResource createLocalResource(IContextualizationScope scope, String resourceId, IProject project) {

        if (scope instanceof IRuntimeScope) {

            return null;
        }

        throw new KlabIllegalStateException("cannot create a dataflow resource from a non-resolution scope");
    }

    /**
     * Create a resource from a remote request, which is assumed valid and non-existing.
     * 
     * @param request
     * @param monitor
     * @return
     */
    public IResource createLocalResource(ResourceCRUDRequest request, Monitor monitor) {

        monitor.info("Start importing resource " + request.getResourceUrns().iterator().next() + ": this may take a while");

        String urn = request.getResourceUrns().iterator().next();
        IProject project = getProject(request.getDestinationProject());
        String adapterType = request.getAdapter();
        IResourceAdapter adapter = getResourceAdapter(adapterType);
        if (project == null || adapter == null) {
            throw new IllegalArgumentException("create resource: wrong request (adapter or project missing)");
        }

        /*
         * translate the parameters to their actual types. Empty strings as parameters are possible.
         */
        Parameters<String> parameters = Parameters.create();
        for (IPrototype prototype : adapter.getResourceConfiguration()) {
            if (adapterType.equals(prototype.getName())) {
                for (IPrototype.Argument argument : prototype.listArguments()) {
                    if (request.getParameters().containsKey(argument.getName())) {
                        String value = request.getParameters().get(argument.getName());
                        if (value != null && !value.trim().isEmpty()) {
                            parameters.put(argument.getName(), Utils.asPOD(value));
                        }
                    }
                }
                break;
            }
        }

        IResource ret = importResource(urn, project, adapterType, null, parameters, Version.create("0.0.1"), new ArrayList<>(),
                monitor);

        monitor.info("Import of resource " + request.getResourceUrns().iterator().next() + " finished");

        return ret;
    }

    private IResource importResource(String urn, IProject project, String adapterType, File file, IParameters<String> parameters,
            Version version, List<IResource> history, IMonitor monitor) {

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
                adapter = resourceAdapters.get(adapterType).adapter;
            }

            if (adapter != null) {

                IResourceValidator validator = adapter.getValidator();

                /*
                 * TODO use adapter metadata to validate the input before calling validate()
                 */
                Builder builder = validator.validate(urn, file == null ? null : file.toURI().toURL(), parameters, monitor);

                // add all history items
                for (IResource his : history) {
                    builder.addHistory(his);
                }

                /*
                 * if no errors, copy files and add notifications to resource; set relative paths in
                 * resource metadata
                 */
                if (!builder.hasErrors() && file != null) {
                    resourceDatapath = new File(
                            project.getRoot() + File.separator + "resources" + File.separator + resourceDataDir);
                    resourceDatapath.mkdirs();

                    for (File f : validator.getAllFilesForResource(file)) {
                        FileUtils.copyFile(f, new File(resourceDatapath + File.separator + MiscUtilities.getFileName(f)));
                        builder.addLocalResourcePath(
                                project.getName() + "/resources/" + resourceDataDir + "/" + MiscUtilities.getFileName(f));
                    }
                }

                resource = builder.withResourceVersion(version).withProjectName(project.getName()).withParameters(parameters)
                        .withAdapterType(adapterType).withLocalPath(project.getName() + "/resources/" + resourceDataDir).build();

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
         * Resources with errors and files go in the catalog and become bright red eyesores in the
         * k.IM editor
         * 
         * FIXME these should only be errors that the user can do something about. Must properly
         * encode those as KlabResourceException.
         */
        if (/* file != null || */!resource.hasErrors()) {
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
     * Bulk import of several resources from a URL. Uses all the importers that declare they can
     * handle the URL unless the adapter type is passed.
     * 
     * @param source a URL compatible with one or more adapter importers
     * @param project the destination project for the local resources built
     * @param adapterType optional, pass if needed to resolve ambiguities or prevent excessive
     *        calculations.
     * @param regex if not null, is added to parameters with key REGEX_ENTRY and used as filter by
     *        the importer
     * @return
     */
    public Collection<IResource> importResources(URL source, IProject project, @Nullable String adapterType, String regex) {

        List<IResourceAdapter> importers = new ArrayList<>();
        IParameters<String> parameters = new Parameters<String>();
        // TODO better way to pass the regex to the importer, if not removed from
        // userData after use,
        // is added to metadata
        if (regex != null && !regex.equals("")) {
            parameters.put(REGEX_ENTRY, regex);
        }
        for (ResourceAdapterData adapter : resourceAdapters.values()) {
            if ((adapterType == null || adapter.adapter.getName().equals(adapterType)) && adapter.adapter.getImporter() != null
                    && adapter.adapter.getImporter().canHandle(source.toString(), parameters)) {
                importers.add(adapter.adapter);
            }
        }

        List<IResource> ret = new ArrayList<>();

        for (IResourceAdapter adapter : importers) {

            IResourceImporter importer = adapter.getImporter();

            for (Builder builder : importer.importResources(source.toString(), project, parameters,
                    Klab.INSTANCE.getRootMonitor())) {

                /*
                 * if no errors, copy files and add notifications to resource; set relative paths in
                 * resource metadata
                 */
                if (!builder.hasErrors()) {

                    File resourceDatapath = new File(
                            project.getRoot() + File.separator + "resources" + File.separator + builder.getResourceId());
                    resourceDatapath.mkdirs();

                    for (File f : builder.getImportedFiles()) {
                        try {
                            FileUtils.copyFile(f, new File(resourceDatapath + File.separator + MiscUtilities.getFileName(f)));
                        } catch (IOException e) {
                            throw new KlabIOException(e);
                        }
                        builder.addLocalResourcePath(
                                project.getName() + "/resources/" + builder.getResourceId() + "/" + MiscUtilities.getFileName(f));
                    }
                } else {
                    Logging.INSTANCE.error("resource " + source + " was not imported due to errors in the import process");
                }

                // NB: should never be null but it is
                IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
                String owner = user == null ? "integratedmodelling.org" : user.getUsername();

                IResource resource = builder.withResourceVersion(Version.create("0.0.1")).withProjectName(project.getName())
                        .withParameters(parameters).withAdapterType(adapter.getName())
                        .withLocalPath(project.getName() + "/resources/" + builder.getResourceId()).build();

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
     * Extract the OWL assets in the classpath (under /knowledge/**) to the specified filesystem
     * directory.
     * 
     * @param destinationDirectory
     * @throws IOException
     */
    public void extractKnowledgeFromClasspath(File destinationDirectory) {
        try {
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
                    throw new KlabIOException("internal: cannot establish path for resource " + resource);
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
        } catch (IOException ex) {
            throw new KlabIOException(ex);
        }
    }

    /**
     * Only works for a flat hierarchy!
     * 
     * @param resourcePattern
     * @param destinationDirectory
     */
    public void extractResourcesFromClasspath(String resourcePattern, File destinationDirectory) {

        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            org.springframework.core.io.Resource[] resources = resolver.getResources(resourcePattern);
            for (org.springframework.core.io.Resource resource : resources) {

                String path = null;
                if (resource instanceof FileSystemResource) {
                    path = ((FileSystemResource) resource).getPath();
                } else if (resource instanceof ClassPathResource) {
                    path = ((ClassPathResource) resource).getPath();
                }
                if (path == null) {
                    throw new KlabIOException("internal: cannot establish path for resource " + resource);
                }
                String fileName = MiscUtilities.getFileName(path);
                File dest = new File(destinationDirectory + File.separator + fileName);
                InputStream is = resource.getInputStream();
                FileUtils.copyInputStreamToFile(is, dest);
                is.close();
            }
        } catch (IOException ex) {
            throw new KlabIOException(ex);
        }
    }

    // @Override
    public Pair<IArtifact, IArtifact> resolveResourceToArtifact(String urn, IMonitor monitor, boolean forceGrid,
            ISemantic observableSemantics, ISemantic contextObservableSemantics) {

        IObservable observable = observableSemantics instanceof IObservable
                ? (IObservable) observableSemantics
                : (observableSemantics == null ? null : Observable.promote(observableSemantics.getType()));
        IObservable contextObservable = contextObservableSemantics instanceof IObservable
                ? (IObservable) contextObservableSemantics
                : (contextObservableSemantics == null ? null : Observable.promote(contextObservableSemantics.getType()));

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

        IKlabData data = getResourceData(resource, urnp.getSecond(), scale, context.getChild((Observable) observable, resource));

        return data == null ? null : new Pair<>(ctxArtifact, data.getArtifact());
    }

    /**
     * Resolve a URN to data using default builder and context, using the full geometry of the
     * resource and a suitable scale (i.e. downscaling if the resulting artifact is too big to
     * handle). Return the resulting artifact, or null if things go wrong.
     * 
     * @param urn
     * @param monitor
     * @return a pair containing the context artifact and the artifact built by the resource
     *         (iterable if objects)
     */
    // @Override
    public Pair<IArtifact, IArtifact> resolveResourceToArtifact(String urn, IMonitor monitor) {
        return resolveResourceToArtifact(urn, monitor, false, null, null);
    }

    /**
     * Non-semantic version that will use the entire geometry of the resource.
     * 
     * @param urn
     * @param builder
     * @param monitor
     * @return
     */
    public IKlabData getResourceData(String urn, IKlabData.Builder builder, IMonitor monitor) {
        Urn kurn = new Urn(urn);
        if (kurn.isLocal()) {
            IResource resource = resolveResource(urn);
            IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
            if (adapter == null) {
                throw new KlabUnsupportedFeatureException(
                        "adapter for resource of type " + resource.getAdapterType() + " not available");
            }
            adapter.getEncoder().getEncodedData(resource, kurn.getParameters(), resource.getGeometry(), builder,
                    Expression.emptyContext(resource.getGeometry(), monitor));

        } else if (kurn.isUniversal() && getUrnAdapter(kurn.getCatalog()) != null) {

            IUrnAdapter adapter = getUrnAdapter(kurn.getCatalog());
            if (adapter == null) {
                throw new KlabUnsupportedFeatureException("adapter for resource of type " + kurn.getCatalog() + " not available");
            }

            IResource resource = resolveResource(urn);
            IContextualizationScope scope = Expression.emptyContext(resource.getGeometry(), monitor);
            try {
                adapter.encodeData(kurn, builder, resource.getGeometry(), scope);
            } catch (Throwable e) {
                // just return null later
                scope.getMonitor().error("could not extract data from " + urn + ": " + e.getMessage());
            }

        } else {
            throw new KlabUnimplementedException(
                    "getResourceData(): this call can only be used to access locally supported resources");
        }
        return builder.build();
    }

    /**
     * Non-semantic version that will use the passed geometry.
     * 
     * @param urn
     * @param builder
     * @param geometry
     * @param monitor
     * @return
     */
    public IKlabData getResourceData(String urn, IKlabData.Builder builder, IArtifact.Type requestType, String resultId,
            IGeometry geometry, IMonitor monitor) {

        Urn kurn = new Urn(urn);
        IResource resource = resolveResource(urn);
        ISession session = monitor.getIdentity().getParentIdentity(ISession.class);

        if (kurn.isLocal()) {
            if (resource == null) {
                throw new KlabResourceAccessException("Access to resource data failed for resource " + urn);
            }
            IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
            if (adapter == null) {
                throw new KlabUnsupportedFeatureException(
                        "adapter for resource of type " + resource.getAdapterType() + " not available");
            }
            adapter.getEncoder().getEncodedData(resource, kurn.getParameters(), geometry, builder,
                    Expression.emptyContext(geometry, monitor));

        } else if (kurn.isUniversal() && getUrnAdapter(kurn.getCatalog()) != null) {

            IUrnAdapter adapter = getUrnAdapter(kurn.getCatalog());
            if (adapter == null) {
                throw new KlabUnsupportedFeatureException("adapter for resource of type " + kurn.getCatalog() + " not available");
            }

            IContextualizationScope scope = Expression.emptyContext(geometry, monitor);
            try {
                adapter.encodeData(kurn, builder, geometry, scope);
            } catch (Throwable e) {
                // just return null later
                scope.getMonitor().error("could not extract data from " + urn + ": " + e.getMessage());
            }

        } else {
            INodeIdentity node = Network.INSTANCE.getNodeForResource(kurn);
            if (node != null) {

                ResourceDataRequest request = new ResourceDataRequest();
                request.setUrn(urn.toString());
                request.setGeometry(encodeScale(geometry, resource));
                request.setArtifactName(resultId);
                request.setArtifactType(requestType);

                builder = new DecodingDataBuilder(
                        node.getClient().onBehalfOf(session.getUser()).post(API.NODE.RESOURCE.GET_DATA, request, Map.class),
                        request, Expression.emptyContext(geometry, monitor));
            }
        }
        return builder.build();
    }

    /**
     * Resolve a resource to data in a passed geometry. This involves retrieval of the adapter,
     * decoding of the resource (remotely or locally according to the resource itself) and building
     * of the data object through the passed scope. If no exceptions are thrown, the result is
     * guaranteed consistent with the geometry and free of errors.
     * <p>
     * If this is used and a runtime scope is passed, this <b>will build</b> observations in the
     * context! If the resource is being tried out for later building, use one of the getters that
     * use a builder.
     * 
     * @param resource
     * @param urnParameters
     * @param geometry
     * @param scope
     * @return KlabException if anything goes wrong
     */
    public IKlabData getResourceData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
            IContextualizationScope scope) {
        return getResourceData(resource, urnParameters, geometry, scope, null);
    }

    /**
     * Like {@link #getResourceData(IResource, Map, IGeometry, IContextualizationScope)} but using a
     * passed artifact instead of simply finding the target artifact in the scope. This is used when
     * layers or other wrappers must be passed from the upstream contextualizer.
     * 
     * @param resource
     * @param urnParameters
     * @param geometry
     * @param scope
     * @param targetArtifact
     * @return
     */
    public IKlabData getResourceData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
            IContextualizationScope scope, IArtifact targetArtifact) {

        Urn urn = new Urn(resource.getUrn(), urnParameters);
        boolean local = Urns.INSTANCE.isLocal(resource.getUrn());
        RuntimeException error = null;

        SessionActivity.ResourceActivity descriptor = null;
        AbstractTask<?> task = scope.getMonitor().getIdentity().getParentIdentity(AbstractTask.class);
        if (task != null && task.getActivity().getActivityDescriptor() != null) {
            descriptor = task.getActivity().getActivityDescriptor().getResourceActivities().get(resource.getUrn());
            if (descriptor == null) {
                descriptor = new ResourceActivity();
                descriptor.setUrn(urn.toString());
                task.getActivity().getActivityDescriptor().getResourceActivities().put(resource.getUrn(), descriptor);
            }
        }

        long start = System.currentTimeMillis();
        if (descriptor != null) {
            descriptor.setnCalls(descriptor.getnCalls() + 1);
        }

        if (urn.isUniversal()) {
            // use it locally only if we have the adapter.
            local = getUrnAdapter(urn.getCatalog()) != null;
        }

        if (local) {

            if (urn.isUniversal()) {

                IUrnAdapter adapter = getUrnAdapter(urn.getCatalog());
                if (adapter == null) {

                    error = new KlabUnsupportedFeatureException(
                            "adapter for resource of type " + resource.getAdapterType() + " not available");

                    if (descriptor != null) {
                        descriptor.setnErrors(descriptor.getnErrors() + 1);
                        descriptor.getErrors().add(ExceptionUtils.getStackTrace(error));
                    }

                    throw error;
                }

                IKlabData.Builder builder = new LocalDataBuilder((IRuntimeScope) scope, targetArtifact);
                try {
                    adapter.encodeData(urn, builder, geometry, scope);
                    IKlabData ret = builder.build();
                    if (descriptor != null) {
                        long elapsed = System.currentTimeMillis() - start;
                        if (descriptor.getMinTimeMs() == 0 || descriptor.getMinTimeMs() > elapsed) {
                            descriptor.setMinTimeMs(elapsed);
                        }
                        if (descriptor.getMaxTimeMs() < elapsed) {
                            descriptor.setMaxTimeMs(elapsed);
                        }
                        descriptor.setTotalTimeMs(descriptor.getTotalTimeMs() + elapsed);
                    }
                    return ret;
                } catch (Throwable e) {
                    // just return null later
                    scope.getMonitor().error("could not extract data from " + resource.getUrn() + ": " + e.getMessage());

                    if (descriptor != null) {
                        descriptor.setnErrors(descriptor.getnErrors() + 1);
                        descriptor.getErrors().add(ExceptionUtils.getStackTrace(e));
                    }

                }

            } else {

                IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
                if (adapter == null) {

                    error = new KlabUnsupportedFeatureException(
                            "adapter for resource of type " + resource.getAdapterType() + " not available");

                    if (descriptor != null) {
                        descriptor.setnErrors(descriptor.getnErrors() + 1);
                        descriptor.getErrors().add(ExceptionUtils.getStackTrace(error));
                    }

                    throw error;
                }

                IKlabData.Builder builder = new LocalDataBuilder(scope, targetArtifact);
                try {
                    adapter.getEncoder().getEncodedData(resource, urnParameters, geometry, builder, scope);
                    IKlabData ret = builder.build();

                    if (descriptor != null) {
                        long elapsed = System.currentTimeMillis() - start;
                        if (descriptor.getMinTimeMs() == 0 || descriptor.getMinTimeMs() > elapsed) {
                            descriptor.setMinTimeMs(elapsed);
                        }
                        if (descriptor.getMaxTimeMs() < elapsed) {
                            descriptor.setMaxTimeMs(elapsed);
                        }
                        descriptor.setTotalTimeMs(descriptor.getTotalTimeMs() + elapsed);
                    }

                    return ret;

                } catch (Throwable e) {

                    if (descriptor != null) {
                        descriptor.setnErrors(descriptor.getnErrors() + 1);
                        descriptor.getErrors().add(ExceptionUtils.getStackTrace(e));
                    }
                    Logging.INSTANCE.error(e);
                    // just return null later
                }
            }
        } else {

            INodeIdentity node = Network.INSTANCE.getNodeForResource(urn);
            if (node != null) {
                ResourceDataRequest request = new ResourceDataRequest();
                // send toString() with all parameters!
                request.setUrn(urn.toString());
                request.setGeometry(encodeScale(geometry, resource));
                request.setArtifactType(scope.getTargetArtifact() == null ? Type.VALUE : scope.getTargetArtifact().getType());
                request.setArtifactName(scope.getTargetArtifact() == null ? "result" : scope.getTargetName());

                DecodingDataBuilder builder = new DecodingDataBuilder(node.getClient().onBehalfOf(scope.getSession().getUser())
                        .post(API.NODE.RESOURCE.GET_DATA, request, Map.class), request, scope);
                IKlabData ret = builder.build();

                if (descriptor != null) {
                    descriptor.getNodes().add(node.getName());
                    long elapsed = System.currentTimeMillis() - start;
                    if (descriptor.getMinTimeMs() == 0 || descriptor.getMinTimeMs() > elapsed) {
                        descriptor.setMinTimeMs(elapsed);
                    }
                    if (descriptor.getMaxTimeMs() < elapsed) {
                        descriptor.setMaxTimeMs(elapsed);
                    }
                    descriptor.setTotalTimeMs(descriptor.getTotalTimeMs() + elapsed);
                }

                return ret;

            } else {
                error = new KlabResourceAccessException("cannot find a node to resolve URN " + urn);
                descriptor.setnErrors(descriptor.getnErrors() + 1);
                descriptor.getErrors().add(ExceptionUtils.getStackTrace(error));
                Logging.INSTANCE.error(error);
            }
        }

        return null;
    }

    /**
     * Return an object defined by a URN within a namespace's symbol table
     * 
     * @return
     */
    public Object getNamespaceObject(String urn) {
        String namespace = Path.getLeading(urn, '.');
        INamespace ns = Namespaces.INSTANCE.getNamespace(namespace);
        return ns == null ? null : ns.getSymbolTable().get(Path.getLast(urn, '.'));
    }

    @Override
    public IKimObject getModelObject(String urn) {

        String serverId = null;
        IKimObject ret = null;

        if (urn.startsWith(Urns.KLAB_URN_PREFIX)) {
            /*
             * remove all needed pieces until we are left with a server ID and a normal path, or an
             * observable
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
                adapter.encodeData(kurn, builder, null, null);
                IKlabData data = builder.build();

                // resource specifies one object
                if (data.getObjectCount() == 1) {

                    if (data.getObjectScale(0).getSpace() != null) {
                        /*
                         * build an observer from the data and return it
                         */
                        return Observations.INSTANCE.makeROIObserver(data.getObjectName(0),
                                data.getObjectScale(0).getSpace().getShape(),
                                org.integratedmodelling.klab.Time.INSTANCE.getGenericCurrentExtent(Resolution.Type.YEAR),
                                data.getObjectMetadata(0));
                    }
                }
            }

        }

        if (serverId == null) {

            String ob = null;
            INamespace namespace = null;
            if (StringUtil.countMatches(urn, ":") == 1 && SemanticType.validate(urn)) {
                SemanticType st = new SemanticType(urn);
                ob = st.getName();
                namespace = Namespaces.INSTANCE.getNamespace(st.getNamespace());
            } else {
                ob = Path.getLast(urn, '.');
                String ns = Path.getLeading(urn, '.');
                namespace = Namespaces.INSTANCE.getNamespace(ns);
            }

            if (namespace == null || ob == null) {
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

    public void registerResourceAdapter(String type, IResourceAdapter adapter, boolean canDropFiles, boolean canCreateEmpty) {
        ResourceAdapterData data = new ResourceAdapterData();
        data.adapter = adapter;
        data.canCreateEmpty = canCreateEmpty;
        data.canDropFiles = canDropFiles;
        resourceAdapters.put(type, data);
    }

    /**
     * Only call this just after {@link Engine#start()} if overriding the default, persistent
     * resource catalog is desired.
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

    @Override
    public PublicResourceCatalog getPublicResourceCatalog() {
        return publicResourceCatalog;
    }

    public IResourceCatalog getCatalog(IResource resource) {
        /*
         * TODO/CHECK this must also work on nodes
         */
        if (Urns.INSTANCE.isLocal(resource.getUrn())) {
            return getLocalResourceCatalog();
        }
        throw new KlabResourceAccessException("unimplemented alignment of public and local resource catalogs");
    }

    // @Override
    public Builder createResourceBuilder(String urn) {
        return new ResourceBuilder(urn);
    }

    @Override
    public boolean isResourceOnline(String urn) {
        IResource resource = resolveResource(urn);
        if (resource == null) {
            Klab.INSTANCE.getRootMonitor().error("non-existent resource referenced: " + urn);
        }
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
            if (cached != null && (System.currentTimeMillis() - cached.timestamp) < (RETRY_INTERVAL_MINUTES * 60 * 1000)) {
                return cached.online;
            }
        }

        if (Urns.INSTANCE.isLocal(resource.getUrn())) {
            IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
            if (adapter != null) {
                boolean ret = adapter.getEncoder().isOnline(resource, Klab.INSTANCE.getRootMonitor());
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
     * Create an importer for the resource specified by the passed URL into the passed project.
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
                Resource resource = new Resource(rref);
                getLocalResourceCatalog().put(rref.getUrn(), resource);
                for (String codelistId : resource.getCodelists()) {
                    ICodelist codelist = getCodelist(resource, codelistId, Klab.INSTANCE.getRootMonitor());
                    if (codelist == null) {
                        Logging.INSTANCE
                                .error("non-existent codelist " + codelistId + " referenced in resource " + resource.getUrn());
                    } else if (codelist.isAuthority()) {
                        Authorities.INSTANCE.registerResourceAuthority(codelist, resource);
                    }
                }
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

        long event = -1;
        try {

            /*
             * inform any listeners of the potentially blocking operation
             */
            event = Klab.INSTANCE.notifyEventStart(EngineEvent.Type.ResourceValidation);

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
        } catch (Throwable t) {
            throw t;
        } finally {
            Klab.INSTANCE.notifyEventEnd(event);
        }

        return ret;
    }

    public void moveLocalResource(IResource resource, IProject destinationProject) {

        IProject sourceProject = getProject(resource.getLocalProjectName());
        if (sourceProject == null || sourceProject.getName().equals(destinationProject.getName())) {
            throw new IllegalArgumentException("moving resource: source project is invalid or the same as the destination");
        }

        String originalUrn = resource.getUrn();
        String originalProject = resource.getLocalProjectName();

        /*
         * set project move into resource history
         */
        ((Resource) resource).copyToHistory("Copied to new project " + destinationProject.getName());
        ((Resource) resource).setLocalProject(destinationProject.getName());
        ((Resource) resource).setUrn(Urns.INSTANCE.changeLocalProject(originalUrn, destinationProject.getName()));
        ((Resource) resource)
                .setLocalPath(destinationProject.getName() + resource.getLocalPath().substring(originalProject.length()));

        List<String> newLocalPaths = new ArrayList<>();
        for (String path : ((Resource) resource).getLocalPaths()) {
            newLocalPaths.add(destinationProject.getName() + path.substring(originalProject.length()));
        }
        resource.getLocalPaths().clear();
        resource.getLocalPaths().addAll(newLocalPaths);

        getLocalResourceCatalog().remove(originalUrn);
        getLocalResourceCatalog().put(resource.getUrn(), resource);

        Logging.INSTANCE.info("moved resource " + originalUrn + " to project " + destinationProject.getName() + ": new URN is "
                + resource.getUrn());
    }

    /**
     * Validate, register and notify a resource that has been completely defined outside of the
     * service (not by a validator but most likely using the {@link StandaloneResourceBuilder}).
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
     * Return the loader used to load all the knowledge. Typically this contains knowledge from
     * various workspaces, such as worldview, components and user. During code generation this may
     * be null or an outer-level loader: use {@link } instead.
     * 
     * @return the loader. Can only be relied upon after loading, not during validation or code
     *         generation.
     */
    public IKimLoader getLoader() {
        return loader;
    }

    public Collection<ResourceAdapterReference> describeResourceAdapters() {
        List<ResourceAdapterReference> ret = new ArrayList<>();
        for (String adapter : resourceAdapters.keySet()) {

            ResourceAdapterData ad = resourceAdapters.get(adapter);

            for (IPrototype configuration : ad.adapter.getResourceConfiguration()) {
                ResourceAdapterReference ref = new ResourceAdapterReference();
                ref.setName(configuration.getName());
                ref.setLabel(configuration.getLabel());
                ref.setDescription(configuration.getDescription());
                ref.setParameters(Extensions.INSTANCE.describePrototype(configuration));
                ref.setCanCreateEmpty(ad.canCreateEmpty);
                ref.setAcceptsDrops(ad.canDropFiles);
                ref.getExportCapabilities().putAll(ad.adapter.getImporter().getExportCapabilities((IResource) null));
                ref.setMultipleResources(ad.adapter.getImporter().acceptsMultiple());

                for (Operation operation : ad.adapter.getValidator().getAllowedOperations(null)) {
                    OperationReference op = new OperationReference();
                    op.setDescription(operation.getDescription());
                    op.setName(operation.getName());
                    op.setRequiresConfirmation(operation.isShouldConfirm());
                    ref.getOperations().add(op);
                }

                ret.add(ref);
            }
        }
        for (String adapter : urnAdapters.keySet()) {
            ResourceAdapterReference ref = new ResourceAdapterReference();
            ref.setName(adapter);
            IUrnAdapter urnAdapter = urnAdapters.get(adapter);
            ref.setLabel(adapter);
            ref.setDescription(urnAdapter.getDescription());
            ref.setUniversal(true);
            ref.setCanCreateEmpty(false);
            ref.setAcceptsDrops(false);
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
            ((ILocalWorkspace) project.getWorkspace()).deleteProject(project);
        }
        // reload
        getLoader().rescan(true);
    }

    public IGeometry getGeometry(IContextualizable resource) {
        switch(resource.getType()) {
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

        switch(resource.getType()) {
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
            throw new KlabResourceNotFoundException(
                    "Resource " + resource.getUrn() + " cannot be published: node " + nodeId + " unresponsive or offline");
        }

        IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
        String userId = user == null ? "anonymous" : user.getUsername();

        final ITicket ret = Klab.INSTANCE.getTicketManager().open("user", userId, ITicket.Type.ResourceSubmission, "node", nodeId,
                "resource", resource.getUrn());

        new Thread(){
            @Override
            public void run() {
                try {
                    if (Urns.INSTANCE.isLocal(resource.getUrn())) {
                        if (!hasFileContent(resource)) {
                            ResourceReference reference = ((Resource) resource).getReference();
                            reference.getMetadata().putAll(suggestions);
                            TicketResponse.Ticket response = node.getClient().post(API.NODE.RESOURCE.SUBMIT_DESCRIPTOR, reference,
                                    TicketResponse.Ticket.class);
                            ret.update("ticket", response.getId());
                        } else {
                            if (!suggestions.isEmpty()) {
                                File pprop = new File(((Resource) resource).getPath() + File.separator + "publish.properties");
                                Properties properties = new Properties();
                                properties.putAll(suggestions);
                                try (OutputStream out = new FileOutputStream(pprop)) {
                                    properties.store(out, null);
                                }
                            }
                            File zipFile = new File(System.getProperty("java.io.tmpdir") + File.separator + ret.getId() + ".zip");
                            ZipUtils.zip(zipFile, ((Resource) resource).getPath(), false, true);
                            if (!suggestions.isEmpty()) {
                                FileUtils.deleteQuietly(
                                        new File(((Resource) resource).getPath() + File.separator + "publish.properties"));
                            }
                            TicketResponse.Ticket response = node.getClient().postFile(API.NODE.RESOURCE.SUBMIT_FILES, zipFile,
                                    TicketResponse.Ticket.class);
                            ret.update("ticket", response.getId());
                        }
                    } else {
                        // TODO republish an update to a remote resource - should be just the
                        // updated
                        // ResourceReference.
                    }
                } catch (Throwable e) {
                    ret.error("Error during publishing: " + e.getMessage());
                }

            }
        }.start();

        return ret;

    }

    protected boolean hasFileContent(IResource resource) {

        if (!resource.getLocalPaths().isEmpty()) {
            return true;
        }
        return ((Resource) resource).getPath().listFiles().length > 1;
    }

    @Override
    public boolean validateForPublication(IResource resource) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public IKlabData getResourceData(String urn, IGeometry geometry, IMonitor monitor) {
        IResource resource = resolveResource(urn);
        if (resource == null) {
            return null;
        }
        Urn kurn = new Urn(urn);
        return getResourceData(resource, kurn.getParameters(), geometry, Expression.emptyContext(geometry, monitor));
    }

    /**
     * Detailed re-validation done on demand.
     * 
     * @param resource
     * @param rmonitor
     */
    public void revalidate(IResource resource, IMonitor monitor) {

        monitor.info("Revalidating resource " + resource.getUrn());

        /*
         * first validate the offline status
         */
        boolean online = false;
        if (Urns.INSTANCE.isLocal(resource.getUrn())) {
            IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
            if (adapter != null) {
                boolean ret = adapter.getEncoder().isOnline(resource, monitor);
                ResourceData cached = statusCache.get(resource.getUrn());
                if (cached == null) {
                    cached = new ResourceData();
                    cached.online = ret;
                    cached.timestamp = System.currentTimeMillis();
                    statusCache.put(resource.getUrn(), cached);
                }
                online = ret;
            }
        } else if (Urns.INSTANCE.isUniversal(resource.getUrn())) {
            Urn urn = new Urn(resource.getUrn());
            if (getUrnAdapter(urn.getCatalog()) != null) {
                online = getUrnAdapter(urn.getCatalog()).isOnline(urn);
            } else {
                // can only have come from a remote node, assumed online
                online = true;
            }
        } else {
            online = publicResourceCatalog.isOnline(resource.getUrn());
        }

        monitor.info("Initial status is " + (online ? "online" : "OFFLINE"));

        // TODO Auto-generated method stub
        IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());

    }

    @Override
    public File getFilesystemLocation(IResource resource) {
        File rootPath = null;
        Urn urn = new Urn(resource.getUrn());
        if (urn.isLocal()) {
            IProject project = Resources.INSTANCE.getProject(((Resource) resource).getLocalProjectName());
            rootPath = project.getRoot().getParentFile();
        } else if (!urn.isUniversal()) {
            rootPath = new File(resource.getLocalPath());
        }
        return rootPath;
    }

    public IResource updateResource(String urn, ResourceCRUDRequest request) {

        IResource previous = getLocalResourceCatalog().get(urn);
        IResource resource = getResourceAdapter(previous.getAdapterType()).getValidator().update(previous, request);
        Resources.INSTANCE.getLocalResourceCatalog().put(urn, resource);
        return resource;
    }

    /**
     * Create an image for the spatial context of the resource, if any is specified.
     * 
     * @param urn
     * @return
     */
    public BufferedImage getResourceSpatialContextImage(String urn) {

        IResource resource = resolveResource(urn);
        if (resource != null) {

            ResourceReference ref = ((Resource) resource).getReference();
            SpatialExtent geometry = ref.getSpatialExtent();
            if (geometry == null) {
                return null;
            }
            try {
                BufferedImage image = ImageIO.read(getClass().getResource("/icons/worldscaled.png"));
                image.getGraphics().setColor(Color.RED);
                int x = (int) (geometry.getWest() + 180);
                int y = (int) (geometry.getSouth() + 90);
                int width = (int) (geometry.getEast() - geometry.getWest());
                int height = (int) (geometry.getNorth() - geometry.getSouth());
                if (width < 2 && height < 2) {
                    image.getGraphics().drawImage(ImageIO.read(getClass().getResource("/icons/target_red.png")), x - 8,
                            180 - y - 8, null);
                } else {
                    image.getGraphics().drawRect(x, 180 - y - height, width, height);
                }
                return image;
            } catch (Exception e) {
                // just return null
            }
        }
        return null;
    }

    @Override
    public ContextualizedResource contextualizeResource(IResource resource, Map<String, String> urnParameters, IScale scale,
            IArtifact observation, IContextualizationScope scope) {

        if (resource instanceof MergedResource) {
            return ((MergedResource) resource).contextualize(scale, observation, scope);
        }

        Urn urn = new Urn(resource.getUrn());

        if (Urns.INSTANCE.isLocal(resource.getUrn())) {
            IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
            if (adapter != null) {
                IResourceEncoder encoder = adapter.getEncoder();
                if (encoder != null) {
                    IResource ret = encoder.contextualize(resource, scale, observation, urnParameters, scope);
                    return ret instanceof ContextualizedResource
                            ? (ContextualizedResource) ret
                            : new ContextualizedResource((Resource) ret);
                }
            }
        } else {

            /*
             * if it's universal and we have the adapter, handle locally
             */
            if (Urns.INSTANCE.isUniversal(resource.getUrn())) {
                IUrnAdapter adapter = getUrnAdapter(urn.getCatalog());
                if (adapter != null) {
                    IResource ret = adapter.contextualize(resource, scale, ((IObservation) observation).getScale(),
                            ((IObservation) observation).getObservable());
                    return ret instanceof ContextualizedResource
                            ? (ContextualizedResource) ret
                            : new ContextualizedResource((Resource) ret);
                }
            }

            INodeIdentity node = Network.INSTANCE.getNodeForResource(urn);
            if (node != null) {
                ResourceContextualizationRequest request = new ResourceContextualizationRequest();
                request.setResource(((Resource) resource).getReference());
                request.setGeometry(encodeScale(scale, resource));
                request.setOverallGeometry(encodeScale(((IObservation) observation).getScale(), resource));
                try {
                    ResourceReference reference = node.getClient().onBehalfOf(scope.getSession().getUser())
                            .post(API.NODE.RESOURCE.CONTEXTUALIZE, request, ResourceReference.class);
                    return new ContextualizedResource(reference);
                } catch (Throwable e) {
                    scope.getMonitor().warn("Remote resource contextualization call failed. Availability info missing.");
                }
            }
        }

        return resource instanceof ContextualizedResource
                ? (ContextualizedResource) resource
                : new ContextualizedResource((Resource) resource);
    }

    private String encodeScale(IGeometry scale, IResource resource) {

        Dimension space = resource.getGeometry().getDimension(Dimension.Type.SPACE);
        IGeometry.Encoding[] options = space != null && space.isRegular()
                ? new IGeometry.Encoding[]{IGeometry.Encoding.SKIP_GRID_SHAPE, IGeometry.Encoding.CONCRETE_TIME_INTERVALS}
                : new IGeometry.Encoding[]{IGeometry.Encoding.CONCRETE_TIME_INTERVALS};

        IGeometry geometry = scale instanceof Scale ? ((Scale) scale).asGeometry(options) : (IGeometry) scale;

        return geometry.encode(options);
    }

    public ICodelist createCodelist(IResource resource, String codelistId, IMonitor monitor) {

        ICodelist codelist = getCodelist(resource, codelistId, monitor);
        if (codelist != null) {
            throw new KlabIllegalArgumentException("codelist " + codelistId + " already exists in resource " + resource.getUrn());
        }

        IResourceAdapter adapter = getResourceAdapter(resource.getAdapterType());
        if (adapter != null) {
            return adapter.getEncoder().categorize(resource, codelistId, monitor);
        }

        return null;
    }

    public void updateCodelist(IResource resource, CodelistReference codelist, IMonitor monitor) {

        if (Urns.INSTANCE.isLocal(resource.getUrn())) {

            String prefix = ((Resource) resource).getPath() + File.separator + "code_" + codelist.getId();
            if (new File(prefix + ".json").exists()) {
                try {
                    FileUtils.copyFile(new File(prefix + ".json"), new File(prefix + ".json.bak"));
                    if (codelist.isAuthority()) {
                        Authorities.INSTANCE.registerResourceAuthority(getCodelist(resource, codelist.getId(), monitor),
                                resource);
                    }
                } catch (IOException e) {
                    monitor.error("Error creating codelist backup for " + codelist.getId());
                }
            }
            JsonUtils.printAsJson(codelist, new File(prefix + ".json"));

            if (codelist.getAuthorityId() != null) {
                // update codelist in remote resource
                Codelist cl = new Codelist(codelist);
                Authorities.INSTANCE.registerResourceAuthority(cl, resource);
            }

        } else {

            if (Urns.INSTANCE.isUniversal(resource.getUrn())) {
                throw new KlabUnsupportedFeatureException("universal resources do not support codelists");
            }
            INodeIdentity node = Network.INSTANCE.getNodeForResource(new Urn(resource.getUrn()));
            if (node != null) {
                // TODO update codelist in remote resource? Or throw exception - these should be
                // published atomically
            }
        }
    }

    public void deleteCodelist(IResource resource, CodelistReference codelist, IMonitor monitor) {

        if (Urns.INSTANCE.isLocal(resource.getUrn())) {

            // CodelistReference reference = null;
            // String prefix = ((Resource)resource).getPath() + File.separator + "code_" +
            // attribute;
            // if (new File(prefix + ".json").exists()) {
            // reference = JsonUtils.load(new File(prefix + ".json"),
            // CodelistReference.class);
            // } else if (new File(prefix + ".properties").exists()) {
            // reference = upgradeCodelist(attribute, new File(prefix + ".properties"));
            // }
            //
            // if (reference != null) {
            // return new Codelist(reference);
            // }

        } else {

            if (Urns.INSTANCE.isUniversal(resource.getUrn())) {
                throw new KlabUnsupportedFeatureException("universal resources do not support codelists");
            }
            INodeIdentity node = Network.INSTANCE.getNodeForResource(new Urn(resource.getUrn()));
            if (node != null) {
                // TODO update codelist in remote resource
            }
        }
    }

    public ICodelist getCodelist(IResource resource, String attribute, IMonitor monitor) {

        if (Urns.INSTANCE.isLocal(resource.getUrn())) {

            CodelistReference reference = null;
            String prefix = ((Resource) resource).getPath() + File.separator + "code_" + attribute;
            if (new File(prefix + ".json").exists()) {
                reference = JsonUtils.load(new File(prefix + ".json"), CodelistReference.class);
            } else if (new File(prefix + ".properties").exists()) {
                reference = upgradeCodelist(attribute, new File(prefix + ".properties"));
            }

            if (reference != null) {
                return new Codelist(reference);
            }

        } else {

            if (Urns.INSTANCE.isUniversal(resource.getUrn())) {
                throw new KlabUnsupportedFeatureException("universal resources do not support codelists");
            }
            INodeIdentity node = Network.INSTANCE.getNodeForResource(new Urn(resource.getUrn()));
            if (node != null) {
                // TODO retrieve codelist from remote resource
            }
        }

        return null;
    }

    private CodelistReference upgradeCodelist(String name, File file) {

        CodelistReference ret = new CodelistReference();
        Properties properties = new Properties();

        ret.setName(name);
        ret.setId(name);

        try (InputStream input = new FileInputStream(file)) {
            properties.load(input);
            ret.setWorldview(properties.getProperty("worldview"));
            ret.setAuthorityId(properties.getProperty("authority"));
            String t = properties.getProperty("type");
            if (t != null) {
                ret.setType(IArtifact.Type.valueOf(t.toUpperCase()));
            }
            String rc = properties.getProperty("root.concept");
            ret.setRootConceptId(rc);
            ret.setDirectMapping(new MappingReference());
            ret.setInverseMapping(new MappingReference());
            String pattern = Pattern.quote("->");
            ret.getDirectMapping().setKeyType(IArtifact.Type.TEXT);
            ret.getDirectMapping().setValueType(ret.getType());
            ret.getDirectMapping().setValueType(ret.getType());
            ret.getDirectMapping().setKeyType(IArtifact.Type.TEXT);
            Set<String> keys = new HashSet<>();
            for (Object key : properties.keySet()) {
                if (key.toString().startsWith("category.")) {
                    String[] pp = properties.getProperty(key.toString()).split(pattern);
                    if (pp.length > 1 && !pp[1].isEmpty()) {
                        ret.getDirectMapping().getMappings().add(new Pair<>(pp[0], pp[1]));
                        ret.getInverseMapping().getMappings().add(new Pair<>(pp[1], pp[0]));
                    }
                    if (keys.add(pp[0])) {
                        ret.getCodeDescriptions().put(pp[0], "");
                    }
                }
            }

            File outfile = MiscUtilities.changeExtension(file, "json");
            JsonUtils.save(ret, outfile);

        } catch (Exception e) {
            throw new KlabIOException(e);
        }

        return ret;
    }

    public synchronized void persistProperties() {
        File propfile = new File(Configuration.INSTANCE.getDataPath() + File.separator + "resources.properties");
        try (OutputStream input = new FileOutputStream(propfile)) {
            properties.store(input, "");
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    public synchronized String getProperty(String property, String string) {
        return this.properties.getProperty(property, string);
    }

    public synchronized void setProperty(String property, String string) {
        this.properties.setProperty(property, string);
    }

    public void unregisterProjectResources(IProject project) {
        for (String urn : project.getLocalResourceUrns()) {
            localResourceCatalog.remove(urn);
        }
    }
}
