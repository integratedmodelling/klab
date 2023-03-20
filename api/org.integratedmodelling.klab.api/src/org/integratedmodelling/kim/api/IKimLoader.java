package org.integratedmodelling.kim.api;

import java.io.File;
import java.net.URI;
import java.util.Collection;

import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;

/**
 * A k.IM loader ingests projects and ensures they are loaded and up to date. It
 * keeps a catalog of all resource URIs and file names and a graph of internal
 * references, so that touching a k.IM resources will ensure that the entire
 * code base is refreshed.
 * <p>
 * Each {@link IKimWorkspace} uses one of these to manage loading.
 * Implementations should provide loaders that can be initialized with the
 * contents of another, in a nested fashion. During code generation and beyond,
 * the loader for a namespace can be retrieved using
 * {@link IKimNamespace#getLoader()}. Implementations using multiple workspaces
 * should endeavor to expose a top-level loader including the union of all those
 * created for each workspace.
 * <p>
 * k.IM projects are entirely file-based and depend strictly on their file
 * organization. Passing a non well-formed project can cause unpredictable
 * consequences.
 * 
 * @author ferdinando.villa
 *
 */
public interface IKimLoader {

	/**
	 * Load the passed projects, build a graph of dependencies, load each project in
	 * order of dependencies (twice if there are circular dependencies) and ensure
	 * that all correct namespaces are retrievable through the common register.
	 * <p>
	 * If a project redefines a previously loaded namespace, the previous is
	 * overridden by the new and discarded.
	 * 
	 * @param projects
	 */
	void load(Collection<IKimProject> projects);

	/**
	 * Same as {@link #load(IKimProject...)} but starting from project file
	 * locations. The passed project may override projects that are already loaded.
	 * 
	 * @param projectRoots
	 */
	Collection<IKimProject> loadProjectFiles(Collection<File> projectRoots);

	/**
	 * Pass either a {@link File}, {@link IKimNamespace} or {@link URI} to ensure
	 * that the correspondent namespace and all its dependents are reloaded and the
	 * graph of dependencies is rebuilt. The operation is atomic.
	 * 
	 * @param namespaceProxy
	 *            a file, namespace or URI that identifies an existing namespace.
	 * @return all the namespaces affected by the change and reloaded
	 * @throws IllegalArgumentException
	 *             if the passed object does not identify a namespace.
	 */
	Collection<IKimNamespace> touch(Object namespaceProxy);

	/**
	 * 
	 * @param namespaceProxy
	 */
	Collection<IKimNamespace> delete(Object namespaceProxy);

	/**
	 * 
	 * @param namespaceResource
	 *            a file or URL
	 */
	IKimNamespace add(Object namespaceResource);

	/**
	 * Retrieve the namespace pointed to by the passed object.
	 * 
	 * @param namespaceProxy
	 *            a file, namespace, string, URI (Java or EMF) or URL
	 * @return
	 */
	IKimNamespace getNamespace(Object namespaceProxy);

	/**
	 * An iterator of all the namespaces.
	 * 
	 * @return
	 */
	Iterable<IKimNamespace> getNamespaces();

	/**
	 * Rescan the projects to pick up changes; optionally perform a full clean
	 * reload of everything.
	 * 
	 * @param clean
	 *            if true, throw away any state and rebuild from scratch.
	 */
	void rescan(boolean clean);

	/**
	 * Get all the issues raised during compilation, validation and generation of
	 * the passed namespace.
	 * 
	 * @param namespaceProxy
	 * @return all issues in k.LAB format
	 */
	Collection<ICompileNotification> getIssues(Object namespaceProxy);

	/**
	 * Force revalidation of the passed namespace and its dependencies, using any
	 * object that can resolve to a namespace (name, EObject or KimNamespace).
	 * 
	 * @param namespaceProxy
	 * @return
	 */
	void revalidate(Object namespaceProxy);

}
