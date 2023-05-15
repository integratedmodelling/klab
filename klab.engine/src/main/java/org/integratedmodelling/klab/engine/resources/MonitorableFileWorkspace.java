package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.templates.KimTemplates;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.DirectoryWatcher;
import org.integratedmodelling.klab.utils.FileUtils;

public class MonitorableFileWorkspace extends AbstractWorkspace implements IWorkspace {

    DirectoryWatcher watcher            = null;

    static String[]  projectDirectories = new String[] {
            "META-INF",
            IKimProject.SCRIPT_FOLDER,
            IKimProject.RESOURCE_FOLDER,
            IKimProject.TESTS_FOLDER,
            IKimProject.SOURCE_FOLDER,
            IKimProject.DOCUMENTATION_FOLDER };

    MonitorableFileWorkspace() {
    }

    public MonitorableFileWorkspace(String name, String worldview, File root) {
        super(name, worldview, root);
    }

    @Override
    public IProject createProject(String projectId, IMonitor monitor) {

        if (getProjectNames().contains(projectId)) {
            throw new IllegalStateException("cannot create project " + projectId
                    + " because it already exists in the workspace");
        }

        File dir = new File(getRoot() + File.separator + projectId);
        dir.mkdir();

        for (String s : projectDirectories) {
            new File(dir + File.separator + s).mkdir();
        }

        try {
            FileUtils.writeStringToFile(new File(dir + File.separator
                    + ".project"), KimTemplates.projectTemplate.replaceAll("__PROJECT__", projectId), false);
            FileUtils.writeStringToFile(new File(dir + File.separator + "META-INF" + File.separator
                    + "klab.properties"), KimTemplates.propertiesTemplate, false);
            FileUtils.writeStringToFile(new File(dir + File.separator + IKimProject.RESOURCE_FOLDER
                    + File.separator
                    + "resources.json"), KimTemplates.emptyJSONTemplate, false);
            FileUtils.writeStringToFile(new File(dir + File.separator + IKimProject.DOCUMENTATION_FOLDER
                    + File.separator
                    + "documentation.json"), KimTemplates.emptyJSONTemplate, false);
            FileUtils.writeStringToFile(new File(dir + File.separator + IKimProject.DOCUMENTATION_FOLDER
                    + File.separator
                    + "references.json"), KimTemplates.emptyJSONTemplate, false);
        } catch (IOException e) {
            throw new KlabIOException(e);
        }

        delegate.loadProject(dir);
        
        // needed to ensure the project is found. Cleaner but more wasteful, we could just reload the WS
        Kim.INSTANCE.registerProject(projectId, delegate);
        
        IProject ret = getProject(projectId);

        /*
         * Inform any UIs or other listeners that a new project was created.
         */
        monitor.send(IMessage.MessageClass.ProjectLifecycle, IMessage.Type.UserProjectOpened, Resources.INSTANCE
                .createProjectDescriptor(ret));

        return ret;

    }

}
