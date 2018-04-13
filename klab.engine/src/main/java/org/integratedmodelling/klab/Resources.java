package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimWorkspace;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.SemanticType;
import org.integratedmodelling.kim.model.Urns;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.data.storage.FutureResource;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.MonitorableFileWorkspace;
import org.integratedmodelling.klab.engine.resources.Project;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.Path;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Static functions related to the management and resolution of URNs. Also holds the URN metadata
 * database for local URNs, synchronized with the META-INF/data.kim file and maintained
 * automatically.
 * 
 * @author ferdinando.villa
 *
 */
public enum Resources implements IResourceService {

  INSTANCE;

  Map<String, IResourceAdapter> resourceAdapters = Collections.synchronizedMap(new HashMap<>());

  /**
   * The local resource catalog is a map that can be set by the engine according to implementation.
   * By default a persistent JSON database is used; tests may reset the resource catalog to a simple
   * in-memory map using {@link #setResourceCatalog} before any request is made.
   */
  IResourceCatalog              localResourceCatalog;
  IResourceCatalog              publicResourceCatalog;

  Map<String, Project>          projects         = new HashMap<>();

  Map<String, IWorkspace>       workspaces       = new HashMap<>();

  /**
   * The core workspace, only containing the OWL knowledge distributed with the software, and no
   * projects.
   */
  private CoreOntology          coreKnowledge;

  /**
   * The worldview, synchronized at startup from Git repositories specified in or through the k.LAB
   * certificate.
   */
  private IWorldview            worldview;

  /**
   * The workspace containing components from the network (or local components if so configured),
   * loaded on demand.
   */
  private IWorkspace            components;

  /**
   * Workspace containing the k.LAB assets installed on the running instance. The files in this
   * workspace are monitored and reloaded incrementally at each change.
   */
  private IWorkspace            local;

  /**
   * Temporary, ephemeral workspace only meant to host the common project for on-demand namespaces.
   * 
   */
  private IWorkspace            common;

  @Override
  public IWorkspace getLocalWorkspace() {
    return local;
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

  /*
   * Extract and load the OWL core knowledge workspace.
   */
  public boolean loadCoreKnowledge(IMonitor monitor) {
    try {
      coreKnowledge = new CoreOntology(Configuration.INSTANCE.getDataPath("knowledge"));
      coreKnowledge.load(false, monitor);
      workspaces.put(coreKnowledge.getName(), coreKnowledge);
      return true;
    } catch (Throwable e) {
      Logging.INSTANCE.error(e.getLocalizedMessage());
    }
    return false;
  }

  /*
   * Create and load the components workspace. TODO needs the network to obtain components, then
   * add/override any local ones.
   */
  public boolean loadComponents(File[] localComponentPaths, IMonitor monitor) {
    try {
      components = new MonitorableFileWorkspace(Configuration.INSTANCE.getDataPath("components"),
          localComponentPaths);
      components.load(false, monitor);
      workspaces.put(components.getName(), components);
      return true;
    } catch (Throwable e) {
      Logging.INSTANCE.error(e.getLocalizedMessage());
    }
    return false;
  }

  /*
   * Create and load the worldview specified by the Git repositories pointed to by the certificate.
   */
  public boolean loadWorldview(ICertificate certificate, IMonitor monitor) {
    try {
      worldview = certificate.getWorldview();
      worldview.load(false, monitor);
      workspaces.put(worldview.getName(), worldview);

      return true;
    } catch (Throwable e) {
      Logging.INSTANCE.error(e.getLocalizedMessage());
    }
    return false;
  }

  public IWorkspace getCommonWorkspace() {
    if (common == null) {
      IKimWorkspace cw = Kim.INSTANCE.getCommonWorkspace();
      common = new MonitorableFileWorkspace(cw.getRoot());
    }
    return common;
  }

  /*
   * Initialize (index but do not load) the local workspace from the passed path.
   */
  public void initializeLocalWorkspace(File workspaceRoot, IMonitor monitor) {
    if (local == null) {
      local = new MonitorableFileWorkspace(workspaceRoot);
    }
  }

  /*
   * Create and load the local workspace.
   */
  public boolean loadLocalWorkspace(IMonitor monitor) {
    try {
      getLocalWorkspace().load(false, monitor);
      return true;
    } catch (Throwable e) {
      Logging.INSTANCE.error(e.getLocalizedMessage());
    }
    return false;
  }

  public IWorkspace getWorkspace(String name) {
    return workspaces.get(name);
  }

  @Override
  public Project getProject(String projectId) {
    return projects.get(projectId);
  }

  public IProject retrieveOrCreate(IKimProject project) {
    if (projects.containsKey(project.getName())) {
      return projects.get(project.getName());
    }

    Project ret = new Project(project);
    projects.put(ret.getName(), ret);
    return ret;
  }


  public static String getConceptPrefix() {
    return Urns.KLAB_URN_PREFIX + "knowledge:" + INSTANCE.getWorldview().getName() + ":";
  }

  // @Override
  public IResourceAdapter getResourceAdapter(String id) {
    return resourceAdapters.get(id);
  }


  // @Override
  public List<IResourceAdapter> getResourceAdapter(File resource) {
    List<IResourceAdapter> ret = new ArrayList<>();
    return ret;
  }

  /**
   * Model URNs start with this followed either by 'local:' (for local models) or the server ID they
   * came from.
   */
  public final static String MODEL_URN_PREFIX = Urns.KLAB_URN_PREFIX + "models:";

  @Override
  public IResource resolveResource(final String urn)
      throws KlabUnknownUrnException, KlabUnauthorizedUrnException {

    IResource ret = getLocalResourceCatalog().get(urn);

    if (ret != null && !Urns.INSTANCE.isLocal(urn)) {
      /*
       * TODO check if we need to refresh the URN from the network; if so, set ret to null again.
       */
    }

    // TODO use network services; ensure any checks are done

    // TODO cache data with appropriate expiration time

    return null;

  }

  @Override
  public IResource getLocalFileResource(File file, IMonitor monitor) {

    // get URN from k.IM service, unique per file
    String urn = Urns.INSTANCE.getFileUrn(file);
    IResource ret = null;


    /**
     * 2. see if it was processed before and timestamps match; if so, return existing resource
     */
    ret = getLocalResourceCatalog().get(urn);

    if (ret != null && ret.getResourceTimestamp() >= file.lastModified()) {
      return ret;
    }

    /**
     * 3. else, spawn resource processing thread for local publishing and return temporary resource
     * record
     */
    new Thread() {

      @Override
      public void run() {

        List<Throwable> errors = new ArrayList<>();
        IResource resource = null;
        try {

          List<IResourceAdapter> adapters = getResourceAdapter(file);
          if (adapters.size() > 0) {
            /*
             * TODO logics to pick the best
             */
            IResourceAdapter adapter = adapters.get(0);
            Builder builder =
                adapter.getValidator().validate(file.toURI().toURL(), new Parameters(), monitor);
            resource = builder.build(urn);

          } else {
            errors
                .add(new KlabValidationException("cannot find an adapter to process file " + file));
          }

        } catch (Exception e) {
          errors.add(e);
        }

        if (resource == null) {
          resource = Resource.error(urn, errors);
        }

        localResourceCatalog.put(urn, resource);
      }

    }.start();

    return new FutureResource(urn);
  }

  /**
   * Extract the OWL assets in the classpath (under /knowledge/**) to the specified filesystem
   * directory.
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
  public IResolvable getResolvableResource(String urn) {
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
        return obs;
      }
    }
    return null;
  }

  public void registerResourceAdapter(String type, IResourceAdapter adapter) {
    resourceAdapters.put(type, adapter);
  }

  /**
   * Only call this just after {@link Engine#start()} if overriding the default, persistent resource
   * catalog is desired.
   * 
   * @param catalog
   */
  public void setLocalResourceCatalog(IResourceCatalog catalog) {
    this.localResourceCatalog = catalog;
  }

  @Override
  public IResourceCatalog getLocalResourceCatalog() {
    if (localResourceCatalog == null) {
      localResourceCatalog = new ResourceCatalog();
    }
    return localResourceCatalog;
  }

  @Override
  public IResourceCatalog getPublicResourceCatalog() {
    if (publicResourceCatalog == null) {
      publicResourceCatalog = new ResourceCatalog();
    }
    return publicResourceCatalog;
  }

  @Override
  public Builder createResourceBuilder() {
    return new ResourceBuilder();
  }

}
