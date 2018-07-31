package org.integratedmodelling.kim.utils;

import java.io.File;

import org.integratedmodelling.kim.api.IKimNamespace.Role;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.kim.api.IKimProject;

public class WorkspaceUtils {

    public static class NamespaceLocation {

        public File workspaceRoot;
        public File projectRoot;
        public String projectId;
        public Role namespaceRole;
    }

    /**
     * Analyze the path of a k.IM source file and return a descriptor for its role and
     * location in a project structure. Return null if the file is not in a standard 
     * project structure. Does not look inside the file, just its file path is analyzed.
     * <p>
     * The namespace role in the returned descriptor will be {@link Role#KNOWLEDGE} even
     * if the namespace is a calibration or a scenario.
     * 
     * @return a descriptor or null. 
     */
    public static NamespaceLocation getNamespaceLocation(File file) {

        if (file == null) {
            return null;
        }
        
        for (String location : new String[] { IKimProject.SOURCE_FOLDER, IKimProject.SCRIPT_FOLDER,
                IKimProject.TESTS_FOLDER }) {
            String segment = File.separator + location + File.separator;
            if (file.toString().contains(segment)) {
                NamespaceLocation ret = new NamespaceLocation();
                ret.projectRoot = new File(file.toString().substring(0, file.toString().indexOf(segment)));
                ret.workspaceRoot = MiscUtilities.getPath(ret.projectRoot.toString());
                ret.projectId = MiscUtilities.getFileName(ret.projectRoot);
                switch (location) {
                case IKimProject.SOURCE_FOLDER:
                    ret.namespaceRole = Role.KNOWLEDGE;
                    break;
                case IKimProject.SCRIPT_FOLDER:
                    ret.namespaceRole = Role.SCRIPT;
                    break;
                case IKimProject.TESTS_FOLDER:
                    ret.namespaceRole = Role.TESTCASE;
                    break;
                }
                return ret;
            }
        }

        return null;
    }

}
