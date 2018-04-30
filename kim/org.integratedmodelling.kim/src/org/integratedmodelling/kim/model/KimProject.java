package org.integratedmodelling.kim.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.model.Kim.UriResolver;
import org.integratedmodelling.klab.utils.Utils;

public class KimProject implements IKimProject {

    public String name;
    public KimWorkspace workspace;
    File root;
    URL url;
    String pathPrefix;
    Properties properties;

    /**
     * Namespaces by URI of corresponding statement
     */
    private Map<URI, KimNamespace> namespaces = new HashMap<>();

    /**
     * Namespaces by namespace ID
     */
    private Map<String, KimNamespace> namespacesbyId = new HashMap<>();

    public KimProject(KimWorkspace workspace, File dir) {
        this.workspace = workspace;
        this.root = dir;
        this.name = Utils.getFileBaseName(dir);
        this.properties = new Properties();
        File pfile = new File(this.root + File.separator + "META-INF" + File.separator + "klab.properties");
        if (pfile.exists()) {
            try (InputStream inp = new FileInputStream(pfile)) {
                properties.load(inp);
            } catch (IOException e) {
                // TODO log error
            }
        }
    }

    public KimProject(String projectName, KimWorkspace workspace, Properties properties) {
        this.name = projectName;
        this.workspace = workspace;
        this.properties = properties;
        this.root = new File(workspace.getRoot() + File.separator + projectName);
    }

    @Override
    public List<File> getSourceFiles() {
        return getSourceFiles(root);
    }

    @Override
    public KimNamespace getNamespace(String id) {
        return namespacesbyId.get(id);
    }

    public void removeNamespace(Namespace namespace) {
        namespaces.remove(EcoreUtil.getURI(namespace));
        namespacesbyId.remove(getNamespaceId(namespace));
    }

    /**
     * Return the stated name, adding "|worldview" if it's anonymous/sidecar file, counting on the
     * fact that the latter are always parsed one at a time and should never provide storeable knowledge.
     * 
     * @param namespace
     * @return the namespace ID
     */
    public static String getNamespaceId(Namespace namespace) {
        return namespace.getName() + (namespace.isWorldviewBound() ? "|worldview" : "");
    }

    //  @Override
    public String getNamespaceIdFor(EObject o) {

        final String PLATFORM_URI_PREFIX = "platform:/resource/";
        String ret = null;
        String sourceDir = properties.getProperty(KLAB_CONFIGURATION_SOURCE_FOLDER, "src");
        String kuri = workspace.getURL() + "/" + name + "/META-INF/knowledge.kim";
        String wuri = o.eResource().getURI().toString(); // THIS GETS platform for workspace files even
                                                         // if they are the same.
        if (wuri.startsWith(PLATFORM_URI_PREFIX) && kuri.startsWith("file:")) {
            // substitute actual file location of workspace
            UriResolver resolver = Kim.INSTANCE.getUriResolver("platform");
            if (resolver != null) {
                String ws = resolver.resolveResourceUriToWorkspaceRootDirectory(o.eResource().getURI()).toURI()
                        .toString();
                wuri = ws + (ws.endsWith("/") ? "" : "/") + wuri.substring(PLATFORM_URI_PREFIX.length());
            }
        }
        if (wuri.startsWith(kuri)) {
            return this.name;
        } else {
            kuri = workspace.getURL() + "/" + name
                    + (sourceDir == null || sourceDir.isEmpty() ? "" : ("/" + sourceDir));
            if (wuri.startsWith(kuri)) {
                ret = wuri.substring(kuri.length() + 1);
                if (ret.endsWith(".kim")) {
                    ret = ret.substring(0, ret.length() - 4);
                }
                ret = ret.replaceAll("\\/", "\\.");
            }
        }
        // no correspondence: resource is outside the beaten path
        return ret;
    }

    @Override
    public KimWorkspace getWorkspace() {
        return workspace;
    }

    public List<File> getSourceFiles(File folder) {
        List<File> result = new ArrayList<>();
        for (File f : folder.listFiles()) {
            if (isModelFile(f)) {
                result.add(f);
            } else if (f.isDirectory()) {
                result.addAll(getSourceFiles(f));
            }
        }
        return result;
    }

    private boolean isModelFile(File f) {
        return f.isFile() && f.getName().endsWith(".kim");
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Get the worldview we define, if any, or null
     * 
     * @return the worldview ID
     */
    @Override
    public String getDefinedWorldview() {
        return properties.getProperty(KLAB_CONFIGURATION_DEFINED_WORLDVIEW_ID);
    }

    /**
     * Get the worldview we refer to
     * 
     * @return the worldview used
     */
    @Override
    public String getWorldview() {
        String ret = properties.getProperty(KLAB_CONFIGURATION_WORLDVIEW_ID);
        return ret == null ? properties.getProperty(KLAB_CONFIGURATION_DEFINED_WORLDVIEW_ID) : ret;

    }

    @Override
    public File getRoot() {
        return root;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    public KimNamespace getNamespace(URI uri, Namespace namespace, boolean createIfAbsent) {
        KimNamespace ret = namespaces.get(uri);
        if (ret == null && createIfAbsent) {
            ret = new KimNamespace(namespace, this);
            namespaces.put(EcoreUtil.getURI(namespace), ret);
            namespacesbyId.put(getNamespaceId(namespace), ret);
            Kim.INSTANCE.initializeNamespaceRegisters(getNamespaceId(namespace));
        }
        return ret;
    }

    @Override
    public List<IKimNamespace> getNamespaces() {
        List<IKimNamespace> ret = new ArrayList<>();
        ret.addAll(namespaces.values());
        Collections.sort(ret, new Comparator<IKimNamespace>() {
            @Override
            public int compare(IKimNamespace o1, IKimNamespace o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return ret;
    }

}
