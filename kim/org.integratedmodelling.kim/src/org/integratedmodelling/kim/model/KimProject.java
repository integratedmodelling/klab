package org.integratedmodelling.kim.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim.UriResolver;

public class KimProject implements IKimProject {

	private String name;
	private KimWorkspace workspace;
	private File root;
	private Properties properties;
	int errors = 0;
	int warnings = 0;

	/**
	 * Namespace IDs. The actual namespaces are held in Kim.INSTANCE.
	 */
	private Set<String> namespaces = new HashSet<>();
	/**
	 * Behavior IDs. The actual behaviors are in KActors.INSTANCE. (eventually)
	 */
	private Set<String> behaviors = new HashSet<>();

	public KimProject(KimWorkspace workspace, String name, File dir) {
		this.workspace = workspace;
		this.root = dir;
		this.name = name;
		this.properties = new Properties();
		File pfile = new File(this.root + File.separator + "META-INF" + File.separator + "klab.properties");
		if (pfile.exists()) {
			try (InputStream inp = new FileInputStream(pfile)) {
				properties.load(inp);
			} catch (IOException e) {
				// TODO log error
			}
		}
		// Kim.INSTANCE.registerProject(this);
	}

	@Override
	public List<File> getSourceFiles() {
		return getSourceFiles(new File(root + File.separator + IKimProject.SOURCE_FOLDER));
	}

	@Override
	public KimNamespace getNamespace(String id) {
		return (KimNamespace) Kim.INSTANCE.getNamespace(id);
	}

	public String getNamespaceIdFor(EObject o) {

		// FIXME won't work with projects in logical workspaces. Use
		// getURI().toFileString and just give up if not a file.

		final String PLATFORM_URI_PREFIX = "platform:/resource/";
		String ret = null;
		String sourceDir = SOURCE_FOLDER;

		String ouri = o.eResource().getURI().toString();
		String projectId = Kim.INSTANCE.getProjectName(o.eResource().getURI().toString());
		if (projectId != null) {
			/*
			 * find projectname/src in the URI
			 */
			String segment = projectId + "/" + SOURCE_FOLDER;
			int iseg = ouri.indexOf(segment);
			if (iseg > 0) {
				segment = ouri.substring(iseg + segment.length() + 1);
				if (segment.endsWith(".kim")) {
					segment = segment.substring(0, segment.length() - 4);
				}
				return segment.replaceAll("\\/", "\\.");
			}
		}

		// String kuri = workspace.getURL() + "/" + name + "/META-INF/knowledge.kim";
		String wuri = o.eResource().getURI().toString(); // THIS GETS platform for workspace files even
															// if they are the same.
		String furi = o.eResource().getURI().toFileString();

		if (wuri.startsWith(PLATFORM_URI_PREFIX) /* && kuri.startsWith("file:") */) {
			// substitute actual file location of workspace
			UriResolver resolver = Kim.INSTANCE.getUriResolver("platform");
			if (resolver != null) {
				String ws = resolver.resolveResourceUriToWorkspaceRootDirectory(o.eResource().getURI()).toURI()
						.toString();
				wuri = ws + (ws.endsWith("/") ? "" : "/") + wuri.substring(PLATFORM_URI_PREFIX.length());
			}
		}
		try {
			/*
			 * FIXME this is flawed - going backwards will return wrong results if the
			 * namespace parent has the same name as the project. After ensuring this works
			 * with tests and script, scrap this and remove the painful UriResolver for
			 * good.
			 */
			String kuri = root.toURI().toURL() + (sourceDir == null || sourceDir.isEmpty() ? "" : sourceDir);
			if (wuri.startsWith(kuri)) {
				ret = wuri.substring(kuri.length() + 1);
				if (ret.endsWith(".kim")) {
					ret = ret.substring(0, ret.length() - 4);
				}
				ret = ret.replaceAll("\\/", "\\.");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// no correspondence: resource is outside the beaten path
		return ret;
	}

	@Override
	public KimWorkspace getWorkspace() {
		return workspace;
	}

	private List<File> getSourceFiles(File folder) {
		List<File> result = new ArrayList<>();
		File[] files = folder == null ? null : folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (Kim.INSTANCE.isKimFile(f)) {
					result.add(f);
				} else if (KActors.INSTANCE.isKActorsFile(f)) {
					result.add(f);
				} else if (f.isDirectory()) {
					result.addAll(getSourceFiles(f));
				}
			}
		}
		return result;
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

	@Override
	public synchronized List<IKimNamespace> getNamespaces() {
		List<IKimNamespace> ret = new ArrayList<>();
		for (String namespace : namespaces) {
			ret.add(Kim.INSTANCE.getNamespace(namespace));
		}
		return ret;
	}

	public void removeNamespace(String name) {
		IKimNamespace ns = Kim.INSTANCE.getNamespace(name);
		if (ns != null) {
			if (ns.isErrors()) {
				errors--;
			}
			if (ns.isWarnings()) {
				warnings--;
			}
		}
		this.namespaces.remove(name);
	}

	public void addNamespace(IKimNamespace namespace) {
		namespaces.add(namespace.getName());
		if (namespace.isErrors()) {
			errors++;
		}
		if (namespace.isWarnings()) {
			warnings++;
		}
	}

	@Override
	public boolean isErrors() {
		return errors > 0;
	}

	@Override
	public boolean isWarnings() {
		return warnings > 0;
	}

	@Override
	public List<IKActorsBehavior> getBehaviors() {
		return KActors.INSTANCE.getBehaviors(this.name, IKActorsBehavior.Type.BEHAVIOR, IKActorsBehavior.Type.TRAITS);
	}

	@Override
	public List<IKActorsBehavior> getApps() {
		return KActors.INSTANCE.getBehaviors(this.name, IKActorsBehavior.Type.APP, IKActorsBehavior.Type.COMPONENT,
				IKActorsBehavior.Type.TRAITS);
	}

	@Override
	public List<IKActorsBehavior> getTests() {
		return KActors.INSTANCE.getBehaviors(this.name, IKActorsBehavior.Type.UNITTEST, IKActorsBehavior.Type.TRAITS);
	}
}
