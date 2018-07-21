package org.integratedmodelling.kim.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

public interface IKimWorkspace {

	/**
	 * Name of the workspace. May or may not be linked to the name of the root
	 * directory.
	 * 
	 * @return workspace name.
	 */
	String getName();

	/**
	 * Load or re-load all the projects we manage.
	 * 
	 * @param incremental
	 *            if true, load everything from scratch, otherwise just load what
	 *            changed and their dependencies.
	 * @return a list of the namespaces in the projects contained in this workspace.
	 * @throws IOException
	 */
	List<IKimNamespace> load(boolean incremental);

	/**
	 * Directories on the local filesystem where each project managed under this
	 * workspace. Each project has its own individual location - there is no
	 * requirement for a "root" workspace directory.
	 * 
	 * @return all registered project locations
	 */
	Collection<File> getProjectLocations();

	/**
	 * Names of all projects managed under this workspace.
	 * 
	 * @return all project names
	 */
	Collection<String> getProjectNames();

	/**
	 * The URL for the workspace.
	 * 
	 * @return the workspace URL.
	 */
	URL getURL();

	/**
	 * Root file location of the workspace.
	 * 
	 * @return root file location. Could be null for workspaces loaded from the
	 *         network or archive files, none of which are currently supported.
	 */
	File getRoot();

	/**
	 * Load a project from a root directory and add it to the locations managed by
	 * the workspace. It will override a project of the same name already loaded
	 * only if the file location is different from the existing one.
	 * 
	 * @param root
	 * @return
	 */
	IKimProject loadProject(File root);

	/**
	 * Find the named namespace in all the projects this workspace manages.
	 * @param id
	 * @return the namespace or null
	 */
    IKimNamespace findNamespace(String id);
}
