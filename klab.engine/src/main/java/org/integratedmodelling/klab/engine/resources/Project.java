package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.templates.KimTemplates;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.documentation.ProjectReferences;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class Project implements IProject {

	IKimProject delegate;
	Set<String> localResourceUrns = new HashSet<>();
	private Set<ProjectReferences> references = null;

	public Project(IKimProject project) {
		this.delegate = project;
		synchronizeResources();
	}

	@Override
	public Collection<String> getLocalResourceUrns() {
		return localResourceUrns;
	}

	public void synchronizeResources() {
		File resourceDir = new File(getRoot() + File.separator + "resources");
		if (resourceDir.exists() && resourceDir.isDirectory()) {
			for (File rdir : resourceDir.listFiles()) {
				if (rdir.isDirectory()) {
					ResourceReference rref = Resources.INSTANCE.synchronize(rdir);
					if (rref != null) {
						localResourceUrns.add(rref.getUrn());
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public File getRoot() {
		return delegate.getRoot();
	}

	@Override
	public List<INamespace> getNamespaces() {
		return null;
	}

	@Override
	public List<IProject> getPrerequisites() {
		List<IProject> ret = new ArrayList<>();
		// TODO
		return ret;
	}

	@Override
	public Version getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INamespace getUserKnowledge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCanonical() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRemote() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOriginatingNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a namespace file and return it (do not load it).
	 * 
	 * @param namespaceId
	 */
	public File createNamespace(String namespaceId, boolean createScenario, boolean isPrivate) {

		File ret = new File(getRoot() + File.separator + IKimProject.SOURCE_FOLDER + File.separator
				+ namespaceId.replace('.', File.separatorChar) + ".kim");
		new File(MiscUtilities.getFilePath(ret.toString())).mkdirs();
		try (PrintWriter out = new PrintWriter(ret)) {
			out.print((createScenario ? "scenario " : (isPrivate ? "private " : "") + "namespace ") + namespaceId
					+ ";\n\n");
		} catch (Exception e) {
			throw new KlabIOException(e);
		}

		return ret;

	}

	@Override
	public IKimProject getStatement() {
		return delegate;
	}

	public File createScript(String namespaceId, String scriptName, String scriptPath) {

		File ret = new File(getRoot() + File.separator + IKimProject.SCRIPT_FOLDER);
		ret.mkdirs();
		ret = new File(getRoot() + File.separator + IKimProject.SCRIPT_FOLDER + File.separator
				+ scriptPath.replaceAll("\\/", File.separator) + namespaceId.replace('.', File.separatorChar) + ".kim");
		ret.getParentFile().mkdirs();
		try (PrintWriter out = new PrintWriter(ret)) {
			out.print(KimTemplates.scriptTemplate.replaceAll("__NAME__", namespaceId)
					.replaceAll("__WORLDVIEW__", Resources.INSTANCE.getWorldview().getName())
					.replaceAll("__SCRIPT_NAME__", scriptName));
		} catch (Exception e) {
			throw new KlabIOException(e);
		}

		return ret;
	}

	public File createTestCase(String namespaceId, String scriptName, String scriptPath) {

		File ret = new File(getRoot() + File.separator + IKimProject.TESTS_FOLDER);
		ret.mkdirs();
		ret = new File(getRoot() + File.separator + IKimProject.TESTS_FOLDER + File.separator
				+ scriptPath.replaceAll("\\/", File.separator) + namespaceId.replace('.', File.separatorChar) + ".kim");
		ret.getParentFile().mkdirs();
		try (PrintWriter out = new PrintWriter(ret)) {
			out.print(KimTemplates.testCaseTemplate.replaceAll("__NAME__", namespaceId)
					.replaceAll("__WORLDVIEW__", Resources.INSTANCE.getWorldview().getName())
					.replaceAll("__TEST_NAME__", scriptName));
		} catch (Exception e) {
			throw new KlabIOException(e);
		}

		return ret;
	}

	public Collection<ProjectReferences> collectReferences() {
		if (this.references == null) {
			this.references = new HashSet<>();
			this.references.add(new ProjectReferences(this));
		}
		for (IProject dep : getPrerequisites()) {
			this.references.addAll(((Project) dep).collectReferences());
		}
		return this.references;
	}

	@Override
	public IWorkspace getWorkspace() {
		return Resources.INSTANCE.getWorkspaceFor(getRoot());
	}
}
