package org.integratedmodelling.kim.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
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
		Kim.INSTANCE.registerProject(this);
	}

	@Override
	public List<File> getSourceFiles() {
		List<File> ret = getSourceFiles(new File(root + File.separator + IKimProject.SOURCE_FOLDER));
		File kkim = new File(root + File.separator + "META-INF" + File.separator + "knowledge.kim");
		if (kkim.isFile()) {
			ret.add(kkim);
		}
		return ret;
	}

	@Override
	public KimNamespace getNamespace(String id) {
		return (KimNamespace) Kim.INSTANCE.getNamespace(id);
	}

	public String getNamespaceIdFor(EObject o) {

		final String PLATFORM_URI_PREFIX = "platform:/resource/";
		String ret = null;
		String sourceDir = SOURCE_FOLDER;
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
		File[] files = folder == null ? null : folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (isModelFile(f)) {
					result.add(f);
				} else if (f.isDirectory()) {
					result.addAll(getSourceFiles(f));
				}
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

	@Override
	public List<IKimNamespace> getNamespaces() {
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
}
