package org.integratedmodelling.kim.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kim.KimStandaloneSetup;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimNamespace.Role;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.kim.Model;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.model.Kim.Notifier;
import org.integratedmodelling.kim.utils.ResourceSorter;
import org.integratedmodelling.kim.utils.WorkspaceUtils;
import org.integratedmodelling.kim.utils.WorkspaceUtils.NamespaceLocation;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.common.CompileNotification;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.CollectionUtils;

import com.google.inject.Injector;

public class KimLoader implements IKimLoader {

    private Injector injector;

    class NsInfo {

        IKimNamespace namespace;
        List<ICompileNotification> issues = new ArrayList<>();
        String name;
        IKimProject project;
        boolean external;

        public NsInfo(IKimProject project) {
            this.project = project;
        }

        public NsInfo copyAsExternal() {
            NsInfo ret = new NsInfo(this.project);
            ret.issues.addAll(this.issues);
            ret.external = true;
            ret.name = this.name;
            ret.namespace = this.namespace;
            return ret;
        }
    }

    private Map<File, NsInfo> dependentResources = new HashMap<>();
    private Map<File, NsInfo> nonDependentResources = new HashMap<>();
    private Map<String, File> namespaceFiles = new HashMap<>();
    private List<String> sortedNames = new ArrayList<>();
    private IResourceValidator validator;
    private Graph<File, DefaultEdge> dependencyGraph;
    private Set<File> projectLocations = new HashSet<>();

    public KimLoader() {
    }

    public KimLoader(Injector injector) {
        setInjector(injector);
    }

    private Injector getInjector() {
        if (this.injector == null) {
            this.injector = new KimStandaloneSetup().createInjectorAndDoEMFRegistration();
        }
        return this.injector;
    }

    /**
     * Call this to use an appropriate injector if calling from a non-standalone
     * setup.
     */
    private void setInjector(Injector injector) {
        this.injector = injector;
    }

    public KimLoader(IKimLoader loader) {
        if (loader != null) {
            importLoader(loader);
        }
    }

    private void importLoader(IKimLoader loader) {
        for (File file : ((KimLoader) loader).dependentResources.keySet()) {
            NsInfo info = ((KimLoader) loader).dependentResources.get(file);
            this.dependentResources.put(file, info.copyAsExternal());
            namespaceFiles.put(info.name, file);
        }
        for (File file : ((KimLoader) loader).nonDependentResources.keySet()) {
            NsInfo info = ((KimLoader) loader).nonDependentResources.get(file);
            this.nonDependentResources.put(file, info.copyAsExternal());
            namespaceFiles.put(info.name, file);
        }
        projectLocations.addAll(((KimLoader) loader).projectLocations);
    }

    public KimLoader(Injector injector, IKimLoader loader) {
        this.injector = injector;
        importLoader(loader);
    }

    public KimLoader(Injector injector, Collection<File> projectRoots) {
        this.injector = injector;
        loadProjectFiles(projectRoots);
    }

    @Override
    public Collection<IKimProject> loadProjectFiles(Collection<File> projectRoots) {
        List<IKimProject> projects = new ArrayList<>();
        for (File root : projectRoots) {
            if (!projectLocations.contains(root)) {
                IKimProject project = Kim.INSTANCE.getProjectIn(root, true);
                projects.add(project);
            }
        }
        if (!projects.isEmpty()) {
            load(projects);
        }
        return projects;
    }

    @Override
    public void rescan(boolean clean) {
        // TODO remove anyth NsInfo that's not external AND whose file has time of
        // update > the namespace's.
        // TODO reload all projects loaded so far
    }

    @Override
    public void load(Collection<IKimProject> projects) {
        boolean trivial = true;
        for (IKimProject project : projects) {
            if (!projectLocations.contains(project.getRoot())) {
                projectLocations.add(project.getRoot());
                loadResources(project);
                trivial = false;
            }
        }
        if (!trivial) {
            doLoad(false);
        }
    }

    @Override
    public List<IKimNamespace> touch(Object namespaceProxy) {
        return touch(namespaceProxy, true);
    }

    public synchronized List<IKimNamespace> touch(Object namespaceProxy, boolean recurseDependencies) {

        File file = getFile(namespaceProxy);
        List<IKimNamespace> ret = new ArrayList<>();

        boolean loaded = false;
        IKimNamespace ours = getNamespace(file);
        if (ours != null) {
            IKimNamespace publ = Kim.INSTANCE.getNamespace(ours.getName());
            if (publ != null && publ.getTimestamp() > file.lastModified()) {
                setNamespace(file, publ);
                loaded = true;
            }
        }

        List<File> dependencies = new ArrayList<>();
        if (!loaded && ours != null) {
            // collect dependencies before we reload
            dependencies.add(file);
            if (recurseDependencies) {
                dependencies.addAll(collectDependencies(ours));
            }
            ret.addAll(loadFiles(dependencies, true));
        }

        return ret;
    }

    private Collection<IKimNamespace> loadFiles(Collection<File> files, boolean isReloading) {

        Map<URI, File> fileMap = new HashMap<>();
        XtextResourceSet resourceSet = getResourceSet();
        List<Resource> resources = new ArrayList<>();

        for (File file : files) {
            URI uri = URI.createFileURI(file.toString());
            Resource resource = resourceSet.getResource(uri, true);
            if (resource != null) {
                resources.add(resource);
                fileMap.put(uri, file);
            } else {
                System.out.println("PORCATA IN " + file);
            }
        }

        return loadResources(resources, fileMap, isReloading);
    }

    private XtextResourceSet getResourceSet() {
        // don't save this! It's an actual set and won't reload resources when called again
        XtextResourceSet ret = getInjector().getInstance(XtextResourceSet.class);
        ret.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
        return ret;
    }

    private List<File> collectDependencies(IKimNamespace namespace) {
        List<File> ret = new ArrayList<>();
        Set<File> visited = new HashSet<>();
        collectDependencies(namespace.getFile(), ret, visited);
        return ret;
    }

    private void collectDependencies(File file, List<File> ret, Set<File> visited) {
        if (dependencyGraph.containsVertex(file)) {
            // proceed breadth-first to ensure proper reloading
            List<File> added = new ArrayList<>();
            for (DefaultEdge edge : dependencyGraph.outgoingEdgesOf(file)) {
                File dependent = dependencyGraph.getEdgeTarget(edge);
                if (!visited.contains(dependent)) {
                    visited.add(dependent);
                    ret.add(dependent);
                    added.add(dependent);
                }
            }
            for (File f : added) {
                collectDependencies(f, ret, visited);
            }
        }
    }

    @Override
    public Collection<IKimNamespace> delete(Object namespaceProxy) {
        File file = getFile(namespaceProxy);
        if (file == null) {
            throw new IllegalArgumentException("can't delete a namespace that was not loaded: " + namespaceProxy);
        }
        NsInfo info = getNamespaceInfo(file);
        if (info == null || info.namespace == null) {
            throw new IllegalArgumentException("can't delete a namespace that was not loaded: " + namespaceProxy);
        }

        List<File> dependencies = collectDependencies(info.namespace);

        Kim.INSTANCE.removeNamespace((Namespace) ((KimNamespace) info.namespace).getEObject());
        Kim.INSTANCE.removeNamespaceConcepts((Namespace) ((KimNamespace) info.namespace).getEObject());

        dependencyGraph.removeVertex(file);
        dependentResources.remove(file);
        nonDependentResources.remove(file);
        sortedNames.remove(info.namespace.getName());
        namespaceFiles.remove(info.namespace.getName());

        return dependencies.isEmpty() ? new ArrayList<>() : loadFiles(dependencies, true);
    }

    @Override
    public IKimNamespace add(Object namespaceResource) {

        File file = getFile(namespaceResource);
        NamespaceLocation location = WorkspaceUtils.getNamespaceLocation(file);
        if (location == null) {
            throw new IllegalArgumentException(
                    "KimLoader: can't add " + namespaceResource + ": unable to determine role in project");
        }

        IKimProject project = Kim.INSTANCE.getProject(location.projectId);

        if (project == null) {
            throw new IllegalArgumentException("KimLoader: can't add " + namespaceResource
                    + ": unable to find source project " + location.projectId);
        }

        if (location.namespaceRole == Role.KNOWLEDGE) {
            dependentResources.put(file, new NsInfo(project));
        } else {
            nonDependentResources.put(file, new NsInfo(project));
        }

        Collection<IKimNamespace> namespaces = loadFiles(Collections.singleton(file), false);

        if (namespaces.size() > 0) {
            return namespaces.iterator().next();
        }

        return null;
    }

    @Override
    public IKimNamespace getNamespace(Object namespaceProxy) {
        NsInfo info = getNamespaceInfo(getFile(namespaceProxy));
        return info == null ? null : info.namespace;
    }

    private void setNamespace(Object namespaceProxy, IKimNamespace namespace) {
        NsInfo info = getNamespaceInfo(getFile(namespaceProxy));
        if (info != null) {
            info.namespace = namespace;
        }
    }

    private File getFile(Object namespaceProxy) {

        if (namespaceProxy instanceof String) {
            namespaceProxy = namespaceFiles.get((String) namespaceProxy);
        } else if (namespaceProxy instanceof IKimNamespace) {
            namespaceProxy = namespaceFiles.get(((IKimNamespace) namespaceProxy).getName());
        } else if (namespaceProxy instanceof Namespace) {
            namespaceProxy = namespaceFiles.get(Kim.getNamespaceId((Namespace) namespaceProxy));
        } else if (namespaceProxy instanceof INamespace) {
            namespaceProxy = namespaceFiles.get(((INamespace) namespaceProxy).getName());
        }

        if (!(namespaceProxy instanceof File)) {
            throw new IllegalArgumentException("cannot infer a namespace from " + namespaceProxy);
        }

        return (File) namespaceProxy;
    }

    @Override
    public Iterable<IKimNamespace> getNamespaces() {
        List<IKimNamespace> ret = new ArrayList<>();
        for (String name : sortedNames) {
            ret.add(getNamespaceInfo(namespaceFiles.get(name)).namespace);
        }
        return ret;
    }

    private void loadResources(IKimProject project) {
        for (File file : project.getSourceFiles()) {
            dependentResources.put(file, new NsInfo(project));
        }
        for (String subdir : new String[] { IKimProject.SCRIPT_FOLDER, IKimProject.TESTS_FOLDER }) {
            File pdir = new File(project.getRoot() + File.separator + subdir);
            if (pdir.isDirectory()) {
                collectNondependentResources(pdir, project);
            }
        }
    }

    private void collectNondependentResources(File pdir, IKimProject project) {
        for (File f : pdir.listFiles()) {
            if (f.isDirectory()) {
                collectNondependentResources(f, project);
            } else if (f.toString().endsWith(".kim")) {
                nonDependentResources.put(f, new NsInfo(project));
            }
        }
    }

    private synchronized List<IKimNamespace> doLoad(boolean forceUpdate) {

        this.sortedNames.clear();

        XtextResourceSet resourceSet = getResourceSet();
        ResourceSorter sorter = new ResourceSorter();
        Map<URI, File> fileMap = new HashMap<>();

        for (File file : dependentResources.keySet()) {

            if (!forceUpdate && isUpToDate(file)) {
                continue;
            }

            URI uri = URI.createFileURI(file.toString());
            Resource resource = resourceSet.getResource(uri, true);
            if (resource != null) {
                sorter.add(resource);
                fileMap.put(uri, file);
            } else {
                System.out.println("PORCATA IN " + file);
            }
        }

        List<Resource> nondep = new ArrayList<>();
        for (File file : nonDependentResources.keySet()) {

            if (!forceUpdate && isUpToDate(file)) {
                continue;
            }

            URI uri = URI.createFileURI(file.toString());
            Resource resource = resourceSet.getResource(uri, true);
            if (resource != null) {
                nondep.add(resource);
                fileMap.put(uri, file);
            } else {
                System.out.println("PORCATA IN " + file);
            }
        }

        List<Resource> sortedResources = CollectionUtils.join(sorter.getResources(), nondep);

        return loadResources(sortedResources, fileMap, false);
    }

    private boolean isUpToDate(File file) {
        NsInfo info = getNamespaceInfo(file);
        if (info != null) {
            if (info.external || (info.namespace != null && info.namespace.getTimestamp() > file.lastModified())) {
                return true;
            }
        }
        return false;
    }

    private List<IKimNamespace> loadResources(List<Resource> sortedResources, Map<URI, File> fileMap,
            boolean reloading) {

        List<IKimNamespace> ret = new ArrayList<>();
        IResourceValidator validator = getValidator();

        for (Resource resource : sortedResources) {
            try {

                Kim.INSTANCE.removeNamespace(((Model) resource.getContents().get(0)).getNamespace());
                List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
                String name = Kim.getNamespaceId(((Model) resource.getContents().get(0)).getNamespace());

                NsInfo info = getNamespaceInfo(fileMap.get(resource.getURI()));
                info.name = name;
                info.namespace = Kim.INSTANCE.getNamespace(name);
                info.issues.clear();

                for (Issue issue : issues) {
                    ICompileNotification notification = getNotification(info.namespace, issue);
                    if (notification != null) {
                        info.issues.add(notification);
                    }
                }

                if (info.namespace == null) {
                    throw new KlabInternalErrorException(
                            "namespace is null after validation. This should never happen.");
                }

                ((KimNamespace) info.namespace).setLoader(this);
                ((KimNamespace) info.namespace).setFile(fileMap.get(resource.getURI()));
                ((KimProject) info.project).addNamespace(info.namespace);

                if (!reloading) {
                    this.namespaceFiles.put(name, fileMap.get(resource.getURI()));
                    this.sortedNames.add(name);
                }

                ret.add(info.namespace);

            } catch (Throwable e) {
                System.out.println("ALTRE PORCATE: " + resource);
                e.printStackTrace();
            }
        }

        for (IKimNamespace namespace : ret) {
            // call notifiers for k.LAB model generation
            for (Notifier notifier : Kim.INSTANCE.getNotifiers()) {
                notifier.synchronizeNamespaceWithRuntime(namespace);
            }
        }

        computeDependencies();

        return ret;
    }

    private ICompileNotification getNotification(IKimNamespace namespace, Issue issue) {

        Level level = null;
        ICompileNotification ret = null;

        switch (issue.getSeverity()) {
        case ERROR:
            level = Level.SEVERE;
            break;
        case INFO:
            level = Level.INFO;
            break;
        case WARNING:
            level = Level.WARNING;
            break;
        default:
            break;
        }

        if (level != null) {
            ret = CompileNotification.create(level, issue.getMessage(), namespace.getName(),
                    KimStatement.createDummy(issue));
        }

        return ret;
    }

    private IResourceValidator getValidator() {
        if (this.validator == null) {
            this.validator = getInjector().getInstance(IResourceValidator.class);
        }
        return this.validator;
    }

    private NsInfo getNamespaceInfo(File file) {
        if (dependentResources.containsKey(file)) {
            return dependentResources.get(file);
        }
        return nonDependentResources.get(file);
    }

    @Override
    public Collection<ICompileNotification> getIssues(Object namespaceProxy) {
        NsInfo info = getNamespaceInfo(getFile(namespaceProxy));
        return info == null ? new ArrayList<>() : info.issues;
    }

    private void computeDependencies() {

        this.dependencyGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for (IKimNamespace namespace : getNamespaces()) {
            if (namespace != null && namespace.getFile() != null) {
                this.dependencyGraph.addVertex(namespace.getFile());
                for (String s : namespace.getImportedNamespaceIds(true)) {
                    if (namespaceFiles.containsKey(s)) {
                        File f = namespaceFiles.get(s);
                        if (!f.equals(namespace.getFile())) {
                            // we just trust that no file in catalog means the dependency is on a core ontology.
                            this.dependencyGraph.addVertex(f);
                            this.dependencyGraph.addEdge(f, namespace.getFile());
                        }
                    }
                }
            }
        }
    }

}
