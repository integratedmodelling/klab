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
import java.util.function.Consumer;
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
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kim.KimStandaloneSetup;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimNamespace.Role;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.kim.Model;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.model.Kim.Notifier;
import org.integratedmodelling.kim.utils.DependencyGraph;
import org.integratedmodelling.kim.utils.ResourceSorter;
import org.integratedmodelling.kim.utils.WorkspaceUtils;
import org.integratedmodelling.kim.utils.WorkspaceUtils.NamespaceLocation;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.common.CompileNotification;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;

import com.google.inject.Injector;

public class KimLoader implements IKimLoader {

    private Injector injector;

    /**
     * Sent to project consumers that want to parse directly from the ECore beans, which are
     * guaranteed serializable.
     * 
     * @author Ferd
     *
     */
    public interface NamespaceDescriptor {

        enum Role {
            NAMESPACE, TESTCASE, SCRIPT
        }

        /**
         * The parsed bean. This isn't necessarily serializable so should be adapted before
         * transferring.
         * 
         * @return
         */
        IKimNamespace getNamespace();

        /**
         * 
         * @return
         */
        Model getNamespaceModel();

        /**
         * 
         * @return
         */
        String getProjectName();

        /**
         * 
         * @return
         */
        Role getNamespaceRole();

        /**
         * 
         * @return
         */
        List<ICompileNotification> getIssues();

    }

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
    private List<File> behaviorFiles = new ArrayList<>();
    private List<String> sortedNames = new ArrayList<>();
    private IResourceValidator validator;
    private Graph<File, DefaultEdge> dependencyGraph;
    private Graph<File, DefaultEdge> behaviorGraph;
    private Set<File> projectLocations = new HashSet<>();
    private Consumer<List<NamespaceDescriptor>> reactor = null;

    public KimLoader() {
    }

    /**
     * Add a reactor to receive the list of namespace descriptors after each
     * {@link #loadProjectFiles(Collection)}.
     * 
     * @param reactor
     */
    public KimLoader(Consumer<List<NamespaceDescriptor>> reactor) {
        this.reactor = reactor;
    }

    private Injector getInjector() {
        if (this.injector == null) {
            this.injector = new KimStandaloneSetup().createInjectorAndDoEMFRegistration();
        }
        return this.injector;
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
        if (injector != null) {
            injector.injectMembers(this);
        }
        importLoader(loader);
    }

    public KimLoader(Injector injector, Collection<File> projectRoots) {
        this.injector = injector;
        if (injector != null) {
            injector.injectMembers(this);
        }
        loadProjectFiles(projectRoots);
    }

    // @Override
    public IKimProject loadProject(File root) {

        IKimProject project = Kim.INSTANCE.getProject(MiscUtilities.getFileName(root));
        if (project != null && !project.getRoot().equals(root)) {
            // TODO override in same workspace
            KimWorkspace workspace = Kim.INSTANCE.getWorkspaceForProject(project.getName());
            project = workspace.overrideProject(project.getName(), root);
        }

        // if (!projectLocations.contains(root)) {
        project = Kim.INSTANCE.getProjectIn(root);
        if (project == null) {
            Kim.INSTANCE.userWorkspace.addProjectPath(root);
            Kim.INSTANCE.userWorkspace.readProjects();
            Kim.INSTANCE.registerWorkspace(Kim.INSTANCE.userWorkspace);
            project = Kim.INSTANCE.getProjectIn(root);
        }
        if (project == null) {
            System.err.println("internal: project path " + root + " caused a null project");
        } else {
            load(Collections.singleton(project));
        }

        return project;
    }

    @Override
    public Collection<IKimProject> loadProjectFiles(Collection<File> projectRoots) {

        // TODO add logics to override a project if it has the same name of an existing
        // one but a different project root.
        List<IKimProject> projects = new ArrayList<>();
        for (File root : projectRoots) {

            IKimProject project = Kim.INSTANCE.getProject(MiscUtilities.getFileName(root));
            if (project != null && !project.getRoot().equals(root)) {
                // TODO override in same workspace
                KimWorkspace workspace = Kim.INSTANCE.getWorkspaceForProject(project.getName());
                project = workspace.overrideProject(project.getName(), root);
            }

            // if (!projectLocations.contains(root)) {
            project = Kim.INSTANCE.getProjectIn(root);
            if (project == null) {
                Kim.INSTANCE.userWorkspace.addProjectPath(root);
                Kim.INSTANCE.userWorkspace.readProjects();
                Kim.INSTANCE.registerWorkspace(Kim.INSTANCE.userWorkspace);
                project = Kim.INSTANCE.getProjectIn(root);
            }
            if (project != null) {
                projects.add(project);
            } else {
                System.err.println("internal: project path " + root + " caused a null project");
            }
        }
        // }
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
    public void revalidate(Object namespaceProxy) {
        revalidate(namespaceProxy, true);
    }

    public synchronized void revalidate(Object namespaceProxy, boolean recurseDependencies) {

        File file = getFile(namespaceProxy);
        IResourceValidator validator = getValidator();
        XtextResourceSet resourceSet = getResourceSet();
        URI uri = URI.createFileURI(file.toString());
        Resource resource = resourceSet.getResource(uri, true);
        if (resource != null) {
            resource.setModified(true);
            validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
        }

        if (recurseDependencies) {
            List<File> ret = new ArrayList<>();
            Set<File> visited = new HashSet<>();
            collectDependencies(file, ret, visited);
            for (File f : ret) {
                revalidate(f, false);
            }
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
                System.out.println("Unrecoverable parse errors in " + file);
            }
        }

        return loadResources(resources, fileMap, isReloading);
    }

    private XtextResourceSet getResourceSet() {
        // don't save this! It's an actual set and won't reload resources when called
        // again
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

        if (dependencyGraph == null) {
            dependencyGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        }

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

        if (namespaceResource instanceof KimProject) {
            loadProjectFiles(Collections.singletonList(((KimProject) namespaceResource).getRoot()));
            return null;
        }

        File file = getFile(namespaceResource);

        if (file.toString().endsWith(".kactor")) {
            System.out.println("HANDLE THIS NEW ACTOR FILE PLEASE");
            return null;
        }

        NamespaceLocation location = WorkspaceUtils.getNamespaceLocation(file);
        if (location == null) {
            throw new IllegalArgumentException(
                    "KimLoader: can't add " + namespaceResource + ": unable to determine role in project");
        }

        IKimProject project = Kim.INSTANCE.getProject(location.projectId);

        if (project == null) {
            throw new IllegalArgumentException(
                    "KimLoader: can't add " + namespaceResource + ": unable to find source project " + location.projectId);
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
        for (String name : namespaceFiles.keySet()) {
            ret.add(getNamespaceInfo(namespaceFiles.get(name)).namespace);
        }
        return ret;
    }

    private void loadResources(IKimProject project) {
        for (File file : project.getSourceFiles()) {
            if (Kim.INSTANCE.isKimFile(file)) {
                dependentResources.put(file, new NsInfo(project));
            } else if (KActors.INSTANCE.isKActorsFile(file)) {
                // these are loaded last after all the knowledge is in.
                behaviorFiles.add(file);
            }
        }
        for (String subdir : new String[]{IKimProject.SCRIPT_FOLDER, IKimProject.TESTS_FOLDER}) {
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
            } else if (f.toString().endsWith(".kactor")) {
                behaviorFiles.add(f);
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
                System.out.println("Unrecoverable parse errors in " + file);
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
                System.out.println("Unrecoverable parse errors in " + file);
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

    private List<IKimNamespace> loadResources(List<Resource> sortedResources, Map<URI, File> fileMap, boolean reloading) {

        List<IKimNamespace> ret = new ArrayList<>();
        IResourceValidator validator = getValidator();
        List<NamespaceDescriptor> namespaces = new ArrayList<>();

        for (Resource resource : sortedResources) {

            if (resource == null) {
                System.err.println("k.IM loader: null resource in load list: skipping");
                continue;
            }

            try {

                Kim.INSTANCE.removeNamespace(((Model) resource.getContents().get(0)).getNamespace());
                Kim.INSTANCE.setCurrentLoader(this);
                List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
                String name = Kim.getNamespaceId(((Model) resource.getContents().get(0)).getNamespace());

                final NsInfo info = getNamespaceInfo(fileMap.get(resource.getURI()));
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
                    throw new KlabInternalErrorException("namespace is not found after validation. This should never happen.");
                }

                ((KimNamespace) info.namespace).setLoader(this);
                ((KimNamespace) info.namespace).setFile(fileMap.get(resource.getURI()));
                ((KimProject) info.project).addNamespace(info.namespace);

                if (!reloading) {
                    this.namespaceFiles.put(name, fileMap.get(resource.getURI()));
                    this.sortedNames.add(name);
                }

                ret.add(info.namespace);
                namespaces.add(new NamespaceDescriptor(){

                    @Override
                    public Model getNamespaceModel() {
                        return (Model) ((KimNamespace) info.namespace).getEObject();
                    }

                    @Override
                    public String getProjectName() {
                        return info.project.getName();
                    }

                    @Override
                    public Role getNamespaceRole() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public List<ICompileNotification> getIssues() {
                        return info.issues;
                    }

                    @Override
                    public IKimNamespace getNamespace() {
                        return info.namespace;
                    }

                });

            } catch (Throwable e) {
                System.out.println("Unrecoverable parse errors in resource: " + resource);
                e.printStackTrace();
            }
        }

        if (!Kim.INSTANCE.getNotifiers().isEmpty()) {
            for (IKimNamespace namespace : ret) {
                // call notifiers for k.LAB model generation
                for (Notifier notifier : Kim.INSTANCE.getNotifiers()) {
                    notifier.synchronizeNamespaceWithRuntime(namespace);
                }
            }
        }

        /*
         * if we have a semantic consumer installed, build the dependency graph among the info files
         * and send out notifications and beans in order of dependency, along with project ID. All
         * info sent through the consumer should be serializable. The k.Actors and k.DL loaders
         * should do the same. We will eventually keep the IKim* beans only for validation.
         */
        if (reactor != null) {
            reactor.accept(namespaces);
        }

        computeDependencies();

        /**
         * Load all behavior files in order of dependency after all the knowledge is in.
         */
        KActors.INSTANCE.loadResources(behaviorFiles);

        return ret;
    }

    private ICompileNotification getNotification(IKimNamespace namespace, Issue issue) {

        Level level = null;
        ICompileNotification ret = null;

        switch(issue.getSeverity()) {
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
            ret = CompileNotification.create(level, issue.getMessage(), namespace.getName(), KimStatement.createDummy(issue));
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
                            // we just trust that no file in catalog means the dependency is on a
                            // core
                            // ontology.
                            this.dependencyGraph.addVertex(f);
                            this.dependencyGraph.addEdge(f, namespace.getFile(), new DefaultEdge(){

                                private static final long serialVersionUID = 1L;

                                @Override
                                public String toString() {
                                    return "";
                                }

                            });
                        }
                    }
                }
            }
        }
    }

    /**
     * Build a new dependency graph for display purposes.
     * 
     * @return
     */
    public DependencyGraph getDependencyGraph() {

        DependencyGraph ret = new DependencyGraph();

        for (IKimNamespace importing : getNamespaces()) {

            if (importing != null && importing.getFile() != null) {
                ret.addVertex(importing.getName());
                for (String imported : importing.getImportedNamespaceIds(true)) {
                    if (namespaceFiles.containsKey(imported)) {
                        File f = namespaceFiles.get(imported);
                        if (!f.equals(importing.getFile())) {
                            ret.addDependency(importing.getName(), imported);
                        }
                    }
                }
            }
        }
        return ret;
    }

}
