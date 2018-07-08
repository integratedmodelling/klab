package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;

import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.DirectoryWatcher;
import org.integratedmodelling.klab.utils.FileUtils;

public class MonitorableFileWorkspace extends AbstractWorkspace implements IWorkspace {

	DirectoryWatcher watcher = null;

	static String[] projectDirectories = new String[] { "META-INF", "apps", "resources", "dataflows", "src" };

	static String projectTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<projectDescription>\r\n"
			+ "	<name>__PROJECT__</name>\r\n" + "	<comment></comment>\r\n" + "	<projects>\r\n" + "	</projects>\r\n"
			+ "	<buildSpec>\r\n" + "		<buildCommand>\r\n"
			+ "			<name>org.eclipse.xtext.ui.shared.xtextBuilder</name>\r\n" + "			<arguments>\r\n"
			+ "			</arguments>\r\n" + "		</buildCommand>\r\n" + "	</buildSpec>\r\n" + "	<natures>\r\n"
			+ "		<nature>org.eclipse.xtext.ui.shared.xtextNature</nature>\r\n" + "	</natures>\r\n"
			+ "</projectDescription>";

	static String resourceTemplate = "{\n}";
	static String propertiesTemplate = "";

	MonitorableFileWorkspace() {
	}

	public MonitorableFileWorkspace(File root, File... overridingProjects) {
		super(root, overridingProjects);
	}

	@Override
	public IProject createProject(String projectId) {

		if (getProjectNames().contains(projectId)) {
			throw new IllegalStateException(
					"cannot create project " + projectId + " because it already exists in the workspace");
		}

		File dir = new File(getRoot() + File.separator + projectId);
		dir.mkdir();

		for (String s : projectDirectories) {
			new File(dir + File.separator + s).mkdir();
		}

		try {
			FileUtils.writeStringToFile(new File(dir + File.separator + ".project"),
					projectTemplate.replaceAll("__PROJECT__", projectId), false);
			FileUtils.writeStringToFile(
					new File(dir + File.separator + "META-INF" + File.separator + "klab.properties"),
					propertiesTemplate, false);
			FileUtils.writeStringToFile(
					new File(dir + File.separator + "resources" + File.separator + "resources.json"),
					resourceTemplate, false);
		} catch (IOException e) {
			throw new KlabIOException(e);
		}

		delegate.loadProject(dir);

		return getProject(projectId);

	}

}
