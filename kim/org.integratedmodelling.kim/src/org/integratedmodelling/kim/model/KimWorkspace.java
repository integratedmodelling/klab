package org.integratedmodelling.kim.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.contrib.jgrapht.traverse.TopologicalOrderIterator;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimWorkspace;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class KimWorkspace implements IKimWorkspace {

	private File root;
	private URL url;
	private String name;
	private Set<File> projectLocations = new HashSet<>();
	private Set<String> projectNames = new HashSet<>();
	private Map<String, IKimProject> projects = new HashMap<>();
	private Set<String> namespaceIds = null;
	private Properties properties = new Properties();

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

	public Collection<IKimProject> getProjects() {
		return projects.values();
	}

	public KimProject getProject(String projectId) {
		KimProject ret = (KimProject) projects.get(projectId);
		if (ret == null) {
			// check if we have a location for the project
			for (File f : projectLocations) {
				if (MiscUtilities.getFileName(f).equals(projectId)) {
					ret = loadProject(f);
					break;
				}
			}
		}
		return ret;
	}

	@Override
	public KimProject loadProject(File root) {

		String pname = MiscUtilities.getFileName(root);
		if (projects.containsKey(pname)) {
			return (KimProject) projects.get(pname);
		}
		KimProject project = new KimProject(this, pname, root);
		if (project.isOpen()) {
			projects.put(pname, project);
			projectLocations.add(root);
			projectNames.add(pname);
			return project;
		}
		return null;
	}

	/**
	 * Register all project locations configured in, without loading the resources
	 * in them. Reentrant - won't read projects that are already registered.
	 */
	public void readProjects() {
		for (File dir : projectLocations) {
			if (Kim.INSTANCE.isKimProject(dir)) {
				loadProject(dir);
			}
		}
	}

	/**
	 * Constructor for a logical workspace with a default file location at the
	 * passed subspace which serves as a name and also creates or registers a
	 * directory in the k.LAB data dir. Projects are created manually and explicitly
	 * after creation, and may be in the file workspace or not.
	 * 
	 * @param workspaceSubdir
	 */
	public KimWorkspace(String workspaceSubdir) {
		this.name = workspaceSubdir;
		this.root = Configuration.INSTANCE.getDataPath(workspaceSubdir);
		try {
			this.url = normalize(root.toURI().toURL());
		} catch (MalformedURLException e) {
			// aaaargh
			throw new RuntimeException(e);
		}
	}

	/**
	 * Constructor for a file-based workspace. This one will use the root file
	 * location as an actual physical workspace and register any k.IM projects in
	 * it. Meant to be created by the API user (e.g. an engine) and not through the
	 * global Kim instance, so it will register itself into it.
	 * 
	 * The names decide whether Kim register these as the worldview or the r/w user
	 * workspace.
	 * 
	 * @param root
	 * @param overridingProjects
	 */
	public KimWorkspace(File root, String name) {
		this.root = root;
		this.name = name;
		try {
			this.url = normalize(root.toURI().toURL());
		} catch (MalformedURLException e) {
			// aaaargh
			throw new RuntimeException(e);
		}
		readProperties();
		for (File sub : root.listFiles()) {
			if (Kim.INSTANCE.isKimProject(sub)) {
				projectLocations.add(sub);
			}
		}
		readProjects();
		Kim.INSTANCE.registerWorkspace(this);
	}

	private void readProperties() {
		File propfile = new File(root + File.separator + "workspace.properties");
		if (!propfile.exists()) {
			try (FileOutputStream out = new FileOutputStream(propfile)) {
				this.properties.store(out, null);
			} catch (IOException e) {
				// fock
			}
		} else {
			try (FileInputStream inp = new FileInputStream(propfile)) {
				this.properties.load(inp);
			} catch (IOException e) {
				// fock
			}
		}
	}

	private void loadNamespaceIds(File file, String prefix) {
		if (!file.exists()) {
			return;
		}
		readProperties();
		if (file.isDirectory()) {
			for (File sub : file.listFiles()) {
				loadNamespaceIds(sub, prefix + (prefix.isEmpty() || prefix.endsWith(".") ? "" : ".")
						+ (sub.isDirectory() ? MiscUtilities.getFileBaseName(sub) : ""));
			}
		} else if (file.isFile() && file.toString().endsWith(".kim")) {
			namespaceIds.add(prefix + (prefix.isEmpty() || prefix.endsWith(".") ? "" : ".")
					+ MiscUtilities.getFileBaseName(file));
		}
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

	@Override
	public File getRoot() {
		return root;
	}

	@Override
	public URL getURL() {
		return url;
	}

	public IKimLoader load() {
		readProjects();
		IKimLoader ret = new KimLoader();
		ret.load(getSortedProjects());
		return ret;
	}

	/**
	 * Return the list of projects sorted by dependency. This is lenient - if a
	 * project doesn't have a dependency we just warn.
	 * 
	 * @return
	 */
	private List<IKimProject> getSortedProjects() {

		List<IKimProject> ret = new ArrayList<>();
		Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		for (IKimProject project : projects.values()) {
			graph.addVertex(project.getName());
			for (String dep : project.getRequiredProjectNames()) {
				graph.addVertex(dep);
				graph.addEdge(dep, project.getName());
			}
		}

		TopologicalOrderIterator<String, DefaultEdge> iterator = new TopologicalOrderIterator<>(graph);
		while (iterator.hasNext()) {
			IKimProject project = Kim.INSTANCE.getProject(iterator.next());
			if (project != null) {
				ret.add(project);
			}
		}

		return ret;
	}

	public IKimLoader load(IKimLoader loader) {
		readProjects();
		IKimLoader ret = new KimLoader(loader);
		ret.load(projects.values());
		return ret;
	}

	public KimNamespace findNamespace(String id) {
		for (IKimProject project : projects.values()) {
			KimNamespace ret = (KimNamespace) project.getNamespace(id);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	// public List<File> getAllKimResources() {
	// List<File> ret = new ArrayList<>();
	// for (IKimProject project : projects.values()) {
	// ret.addAll(project.getSourceFiles());
	// }
	// return ret;
	// }

	/**
	 * Return all the pre-loaded namespace IDs based on the presence of the
	 * corresponding files in the project space. Used to predefine correct imports
	 * and analyze dependencies during and not after resource loading.
	 * 
	 * @return
	 */
	public Set<String> getNamespaceIds() {
		if (namespaceIds == null) {
			namespaceIds = new HashSet<>();
			for (File sub : projectLocations) {
				if (sub.isDirectory()
						&& new File(sub + File.separator + "META-INF" + File.separator + "klab.properties").exists()) {
					loadNamespaceIds(new File(sub + File.separator + IKimProject.SOURCE_FOLDER), "");
				}
			}
		}
		return namespaceIds;
	}

	public void addProjectPath(File file) {
		projectLocations.add(file);
	}

	public String toString() {
		return "<W " + name + " (" + projectNames + ")>";
	}

	public IKimProject overrideProject(String name, File rootPath) {
		namespaceIds = null;
		IKimProject previous = projects.remove(name);
		if (previous != null) {
			projectLocations.remove(previous.getRoot());
		}
		projectLocations.add(rootPath);
		readProjects();
		return projects.get(name);
		// no changes in Kim.INSTANCE as all the project ops happen through workspaces
		// no reloading - that's the responsibility of the caller
	}

	public void deleteProject(IKimProject project) {
		namespaceIds = null;
		IKimProject previous = projects.remove(project.getName());
		projectNames.remove(project.getName());
		if (previous != null) {
			projectLocations.remove(previous.getRoot());
		}
		Kim.INSTANCE.unregisterProject(project);
		readProjects();
	}
	
	public boolean isOpen(String projectName) {
		return !Boolean.parseBoolean(properties.getProperty(projectName + ".closed", "false"));
	}

}
