package org.integratedmodelling.kim.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kim.KimStandaloneSetup;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.kim.Model;
import org.integratedmodelling.kim.utils.ResourceSorter;
import org.integratedmodelling.klab.utils.CollectionUtils;

import com.google.inject.Injector;

/**
 * TODO loaders must subscribe with Kim, so that reloads changes initiated by
 * external code (e.g. Eclipse builders) will pick up the changes and recompute
 * the dependency graph.
 * 
 * @author Ferd
 *
 */
public class KimLoader implements IKimLoader {

	private Injector injector;

	class NsInfo {

		IKimNamespace namespace;
		List<Issue> issues = new ArrayList<>();
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

	public KimLoader() {
	}

	public KimLoader(Injector injector) {
		setInjector(injector);
	}

	/**
	 * Call this to use an appropriate injector if calling from a non-standalone
	 * setup.
	 */
	public void setInjector(Injector injector) {
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
		}
		for (File file : ((KimLoader) loader).nonDependentResources.keySet()) {
			NsInfo info = ((KimLoader) loader).nonDependentResources.get(file);
			this.nonDependentResources.put(file, info.copyAsExternal());
		}
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
	public void loadProjectFiles(Collection<File> projectRoots) {
		List<IKimProject> projects = new ArrayList<>();
		for (File root : projectRoots) {
			IKimProject project = Kim.INSTANCE.getProjectIn(root, true);
			projects.add(project);
		}
		load(projects);
	}

	@Override
	public void reload() {
		// TODO remove anyth NsInfo that's not external AND whose file has time of
		// update > the namespace's.
		// TODO reload all projects loaded so far
	}

	@Override
	public void load(Collection<IKimProject> projects) {
		for (IKimProject project : projects) {
			loadResources(project);
		}
		doLoad();
	}

	@Override
	public List<IKimNamespace> touch(Object namespaceProxy) {
		return touch(namespaceProxy, true);
	}

	public List<IKimNamespace> touch(Object namespaceProxy, boolean recurseDependencies) {

		File resource = getFile(namespaceProxy);
		List<IKimNamespace> ret = new ArrayList<>();

		boolean loaded = false;
		IKimNamespace ours = getNamespace(resource);
		if (ours != null) {
			IKimNamespace publ = Kim.INSTANCE.getNamespace(ours.getName());
			if (publ != null && publ.getTimestamp() > resource.lastModified()) {
				setNamespace(resource, publ);
				System.out.println(publ.getImportedNamespaceIds(true));
				loaded = true;
			}
		}

		// TODO save current dependencies

		if (!loaded) {
			// TODO reload
		}

		if (recurseDependencies) {
			// TODO reload all the formerly dependent

			// TODO rebuild deps

			// TODO add each reloaded to returned result
		}
		return ret;
	}

	@Override
	public List<IKimNamespace> delete(Object namespaceProxy) {
		// TODO Auto-generated method stub
		// TODO if project, remove all files
		// TODO if file or URL or namespace, remove that and reload all
		// dependents
		return null;
	}

	@Override
	public IKimNamespace add(Object namespaceResource) {
		// TODO Auto-generated method stub
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
		// TODO turn the proxy to a file
		if (!(namespaceProxy instanceof File)) {
			throw new IllegalArgumentException("cannot infer a namespace file from " + namespaceProxy);
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
				for (File f : pdir.listFiles()) {
					if (f.toString().endsWith(".kim")) {
						nonDependentResources.put(f, new NsInfo(project));
					}
				}
			}
		}
	}

	private synchronized void doLoad() {

		if (injector == null) {
			injector = new KimStandaloneSetup().createInjectorAndDoEMFRegistration();
		}

		this.sortedNames.clear();

		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		ResourceSorter sorter = new ResourceSorter();
		Map<URI, File> fileMap = new HashMap<>();

		for (File file : dependentResources.keySet()) {
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

		loadResources(sortedResources, fileMap);
	}

	private void loadResources(List<Resource> sortedResources, Map<URI, File> fileMap) {

		IResourceValidator validator = injector.getInstance(IResourceValidator.class);
		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();

		for (Resource resource : sortedResources) {
			try {
				/*
				 * TODO don't add if it's there and was loaded after the resource modification, unless
				 * a force flag was set
				 */
				Kim.INSTANCE.removeNamespace(((Model) resource.getContents().get(0)).getNamespace());
				List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
				String name = Kim.getNamespaceId(((Model) resource.getContents().get(0)).getNamespace());
				NsInfo info = getNamespaceInfo(fileMap.get(resource.getURI()));
				info.issues.addAll(issues);
				info.name = name;
				info.namespace = Kim.INSTANCE.getNamespace(name);
				((KimNamespace) info.namespace).setFile(fileMap.get(resource.getURI()));
				((KimProject) info.project).addNamespace(info.namespace);
				this.namespaceFiles.put(name, fileMap.get(resource.getURI()));
				this.sortedNames.add(name);
			} catch (Throwable e) {
				System.out.println("PORCATE: " + resource);
			}
		}

		GeneratorDelegate generator = injector.getInstance(GeneratorDelegate.class);
		for (Resource resource : sortedResources) {
			generator.doGenerate(resource, fsa);
		}
	}

	private NsInfo getNamespaceInfo(File file) {
		if (dependentResources.containsKey(file)) {
			return dependentResources.get(file);
		}
		return nonDependentResources.get(file);
	}

}
