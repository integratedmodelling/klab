package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.integratedmodelling.kim.model.SemanticType;
import org.integratedmodelling.kim.model.Urns;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.Path;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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

  Map<String, IResourceAdapter> resourceAdapters = new HashMap<>();

  /**
   * The local resource catalog is a map that can be set by the engine according to implementation.
   * By default a persistent JSON database is used; tests may reset the resource catalog to a simple
   * in-memory map using {@link #setResourceCatalog} before any request is made.
   */
  Map<String, IResource>        resourceCatalog;

  public static String getConceptPrefix() {
    return Urns.KLAB_URN_PREFIX + "knowledge:" + Workspaces.INSTANCE.getWorldview().getName() + ":";
  }

  @Override
  public IResourceAdapter getResourceAdapter(String id) {
    return resourceAdapters.get(id);
  }


  @Override
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
  public IResource getResource(final String urn)
      throws KlabUnknownUrnException, KlabUnauthorizedUrnException {

    String id = urn;
    IResource ret = null;

    if (id.startsWith(Urns.LOCAL_URN_PREFIX)) {
      id = id.substring(Urns.LOCAL_URN_PREFIX.length());
    } else if (id.startsWith(Urns.KLAB_URN_PREFIX)) {
      id = id.substring(Urns.KLAB_URN_PREFIX.length());
    }

    if (id.startsWith(Urns.LOCAL_FILE_PREFIX)) {

      String text = StringEscapeUtils.unescapeHtml(id.substring(Urns.LOCAL_FILE_PREFIX.length()));
      ret = getLocalFileResource(new File(text));

    }

    return ret;
  }

  @Override
  public IResource getLocalFileResource(File file) {

    IResource ret = null;

    /**
     * 1. create URN
     */

    /**
     * 2. see if it was processed before and timestamps match; if so, return existing resource
     */

    /**
     * 3. else, spawn resource processing thread and return temporary resource record
     */

    return ret;
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
    Resource[] resources = resolver.getResources("/knowledge/**");
    for (Resource resource : resources) {

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

  public void setResourceCatalog(Map<String, IResource> catalog) {
    this.resourceCatalog = catalog;
  }

  public Map<String, IResource>  getResourceCatalog() {
      if (resourceCatalog == null) {
        // TODO create the persistent catalog
      }
      return resourceCatalog;
  }

}
