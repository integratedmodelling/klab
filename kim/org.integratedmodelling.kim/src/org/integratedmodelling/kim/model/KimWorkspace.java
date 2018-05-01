package org.integratedmodelling.kim.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kim.KimStandaloneSetup;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimWorkspace;
import org.integratedmodelling.kim.model.Kim.UriResolver;
import org.integratedmodelling.kim.utils.ResourceSorter;
import org.integratedmodelling.klab.utils.Utils;

import com.google.inject.Injector;

public class KimWorkspace extends KimScope implements IKimWorkspace {

    private static final long serialVersionUID = -6601950097333987803L;

    private File root;
    private URL url;
    private String name;
    private boolean read;
    private File[] overridingProjects;
    private List<File> projectLocations = new ArrayList<>();
    private List<String> projectNames = new ArrayList<>();

    // projects are indexed by name and URI prefix
    private Map<String, KimProject> allProjects = new HashMap<>();
    private Map<String, KimProject> projectsByURI = new HashMap<>();

    private static Map<String, KimWorkspace> workspacesByURI = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<File> getProjectLocations() {
        return projectLocations;
    }

    public Collection<String> getProjectNames() {
        return projectNames;
    }

    public Collection<KimProject> getProjects() {
        return allProjects.values();
    }

    public void readProjects() throws IOException {

        if (!read) {

            this.name = Utils.getFileBaseName(this.root);

            try {
                this.url = normalize(root.toURI().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            if (root.exists()) {
                for (File dir : root.listFiles()) {
                    if (dir.isDirectory()
                            && new File(dir + File.separator + "META-INF" + File.separator + "klab.properties")
                                    .exists()) {
                        KimProject project = new KimProject(this, overrideIfPresent(dir, this.overridingProjects));
                        String pname = dir.toString().substring(dir.toString().lastIndexOf(File.separator) + 1);

                        allProjects.put(pname, project);
                        projectLocations.add(dir);
                        projectNames.add(pname);
                    }
                }
            }

            read = true;
        }
    }

    static Collection<KimWorkspace> getWorkspaces() {
        return workspacesByURI.values();
    }

    /**
     * Constructor for a file-based workspace. This one will be able to enumerate its projects and Kim
     * resources after construction. You can pass any number of project directories that will override the
     * ones in the library if they specify a project of the same name.
     * 
     * @param root
     * @param overridingProjects 
     */
    public KimWorkspace(File root, File... overridingProjects) {
        this.root = root;
        try {
            this.url = normalize(root.toURI().toURL());
        } catch (MalformedURLException e) {
            // aaaargh
            throw new RuntimeException(e);
        }
        this.overridingProjects = overridingProjects;
        workspacesByURI.put(this.url.toString(), this);
    }

    private URL normalize(URL url2) {
        String us = url2.toString();
        if (us.endsWith("/")) {
            us = us.substring(0, us.length() - 1);
            try {
                url2 = new URL(us);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return url2;
    }

    private File overrideIfPresent(File dir, File[] overridingProjects) {
        // TODO check if the project is overridden and if so, returning the overriding
        // path.
        return dir;
    }

    /**
     * Constructor for a URL-based workspace. Needs one resource so that it can infer the resource set and
     * compute the dependency tree. Does not know its projects until after validation, and the projects won't
     * know their resources until after validation.
     * 
     * @param workspaceUrl
     * @param root 
     */
    public KimWorkspace(URL workspaceUrl, File root) {
        this.url = normalize(workspaceUrl);
        this.name = Utils.getURLBaseName(workspaceUrl.toString());
        this.root = root;
        workspacesByURI.put(this.url.toString(), this);
    }

    @Override
    public File getRoot() {
        return root;
    }

    @Override
    public URL getURL() {
        return url;
    }

    public List<IKimNamespace> load(boolean incremental) throws IOException {

        List<IKimNamespace> ret = new ArrayList<>();

        //        new StandaloneSetup().setPlatformUri(root.toString());

        readProjects();

        Injector injector = new KimStandaloneSetup().createInjectorAndDoEMFRegistration();

        // obtain a resourceset from the injector
        XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
        resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);

        IResourceValidator validator = injector.getInstance(IResourceValidator.class);
        InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
        ResourceSorter sorter = new ResourceSorter(this);

        for (File file : getAllKimResources()) {
            sorter.add(resourceSet.getResource(URI.createFileURI(file.toString()), true));
        }
        List<Resource> sort = sorter.getResources();
        for (Resource resource : sort) {
            List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
            for (Issue issue : issues) {
                if (issue.getSeverity() == Severity.ERROR) {
                    Kim.INSTANCE.reportLibraryError(issue);
                }
            }
        }

        GeneratorDelegate generator = injector.getInstance(GeneratorDelegate.class);
        for (Resource resource : sort) {
            generator.doGenerate(resource, fsa);
        }

        for (String nsId : sorter.getSortedNamespaceIds()) {
            KimNamespace namespace = findNamespace(nsId);
            if (namespace != null) {
                ret.add(namespace);
            } else {
                Kim.INSTANCE.warn("namespace " + nsId + " was declared but not generated");
            }
        }

        return ret;
    }

    public KimNamespace findNamespace(String id) {

        for (KimProject project : allProjects.values()) {
            KimNamespace ret = project.getNamespace(id);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

    public List<File> getAllKimResources() {
        List<File> ret = new ArrayList<>();
        for (KimProject project : allProjects.values()) {
            ret.addAll(project.getSourceFiles());
        }
        return ret;
    }
    
    public static KimWorkspace getWorkspaceForResourceURI(URI resourceURI) {

        KimWorkspace ret = getWorkspaceForURI(resourceURI.toString());

        if (ret == null) {
            try {
                URL url = new URL(resourceURI.toString());
                String path = url.getPath();
                Properties properties = null;
                URL purl = null;
                while ((path = chopLastPathElement(path)) != null) {
                    purl = new URL(url.getProtocol(), url.getAuthority(), url.getPort(),
                            path + "/META-INF/klab.properties");
                    try (InputStream is = purl.openStream()) {
                        properties = new Properties();
                        properties.load(is);
                        break;
                    } catch (IOException exception) {
                        continue;
                    }
                }

                if (properties != null) {
                    String workspacePrefix = path.substring(0, path.lastIndexOf('/'));
                    int upos = url.toString().indexOf(workspacePrefix + "/") + workspacePrefix.length();
                    URL workspaceUrl = new URL(url.toString().substring(0, upos));
                    File dioFile = null;
                    if (workspaceUrl.toString().startsWith("file:")) {
                        dioFile = new File(workspaceUrl.getFile());
                    } else {
                        UriResolver resolver = Kim.INSTANCE.getUriResolver(resourceURI.scheme());
                        if (resolver != null) {
                            dioFile = resolver.resolveResourceUriToWorkspaceRootDirectory(resourceURI);
                        } else {
                            throw new RuntimeException("cannot resolve workspace location for resource URI "
                                    + resourceURI + "; please install a UriResolver for scheme "
                                    + resourceURI.scheme());
                        }
                    }
                    ret = new KimWorkspace(workspaceUrl, dioFile);
                }
            } catch (MalformedURLException e) {
                // just leave the workspace null
            }
        }
        return ret;
    }

    
    public static KimWorkspace getWorkspaceForResource(Resource resource) {
    	return getWorkspaceForResourceURI(resource.getURI());
    }

    public KimProject getProjectForResource(Resource resource) {

        KimProject ret = getProjectForURI(resource.getURI().toString());
        if (ret == null) {
            try {
                URL url = new URL(resource.getURI().toString());
                String path = url.getPath();
                Properties properties = null;
                URL purl = null;
                while ((path = chopLastPathElement(path)) != null) {
                    purl = new URL(url.getProtocol(), url.getAuthority(), url.getPort(),
                            path + "/META-INF/klab.properties");
                    try (InputStream is = purl.openStream()) {
                        properties = new Properties();
                        properties.load(is);
                        break;
                    } catch (IOException exception) {
                        continue;
                    }
                }

                if (properties != null) {
                    String projectName = path.substring(path.lastIndexOf('/') + 1);
                    KimWorkspace workspace = getWorkspaceForResource(resource);
                    ret = allProjects.get(projectName);
                    if (ret == null) {
                        ret = new KimProject(projectName, workspace, properties);
                        projectsByURI.put(workspace.getURL() + "/" + projectName, ret);
                        allProjects.put(url.getPath(), ret);
                    }
                }
            } catch (MalformedURLException e) {
                // just leave the project null
            }
        }
        return ret;
    }

    private static String chopLastPathElement(String path) {
        int idx = path.lastIndexOf('/');
        if (idx <= 0) {
            return null;
        }
        return path.substring(0, idx);
    }

    private KimProject getProjectForURI(String resUri) {

        for (String uri : projectsByURI.keySet()) {
            // platform:/resource/im == platform:/resource/im.aries/src/es/aesthetics.kim; check for '/' after subtracting URI
            if (resUri.startsWith(uri) && resUri.substring(uri.length()).startsWith("/")) {
                return projectsByURI.get(uri);
            }
        }
        return null;
    }

    private static KimWorkspace getWorkspaceForURI(String resUri) {
        for (String uri : workspacesByURI.keySet()) {
            if (resUri.startsWith(uri)) {
                return workspacesByURI.get(uri);
            }
        }
        return null;
    }

    @Override
    protected String getStringRepresentation(int offset) {
        return null;
    }

    @Override
    public String getLocationDescriptor() {
        return root.toString();
    }

    public void registerProject(KimProject project, URL projectUrl) {
        projectsByURI.put(projectUrl.toString(), project);
        allProjects.put(projectUrl.getPath(), project);
    }

}
