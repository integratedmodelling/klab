//package org.integratedmodelling.klab.engine.resources;
//
//import java.io.File;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import org.integratedmodelling.kim.model.KimWorkspace;
//import org.integratedmodelling.klab.Authentication;
//import org.integratedmodelling.klab.Configuration;
//import org.integratedmodelling.klab.Logging;
//import org.integratedmodelling.klab.api.auth.IUserIdentity;
//import org.integratedmodelling.klab.api.knowledge.IProject;
//import org.integratedmodelling.klab.api.services.IConfigurationService;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//import org.integratedmodelling.klab.utils.GitUtils;
//import org.integratedmodelling.klab.utils.MiscUtilities;
//
//public class MonitorableGitWorkspace extends MonitorableFileWorkspace {
//
//    Collection<String> gitUrls;
//    boolean synced;
//    boolean skipSync = false;
//    // to handle deletion
//    Map<String, String> gitUrlByName = new HashMap<>();
//
//    /**
//     * 
//     * @param root
//     * @param name
//     * @param gitUrls URL for each project -> set of groups that have access to it
//     * @param overridingProjects
//     */
//    public MonitorableGitWorkspace(File root, String name, Map<String, Set<String>> gitUrls,
//            File... overridingProjects) {
//
//        delegate = new KimWorkspace(root, name){
//
//            @Override
//            public void readProjects() {
//
//                IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
//
//                /*
//                 * catalog projects already existing in workspace; whatever is not allowed for user
//                 * gets removed.
//                 */
//
//                if (!synced && (!skipSync || !root.exists())) {
//                    synced = true;
//                    for (String url : gitUrls.keySet()) {
//
//                        /*
//                         * skip blacklisted projects
//                         */
//                        if (Configuration.INSTANCE
//                                .getProperty(IConfigurationService.KLAB_PROJECT_BLACKLIST_PROPERTY, "")
//                                .contains(MiscUtilities.getURLBaseName(url))) {
//                            continue;
//                        }
//                        // add branch if necessary
//                        String gitUrl;
//                        if (!url.contains("#")) {
//                            gitUrl =  url + "#" + Configuration.INSTANCE.getProductsBranch();
//                        } else {
//                            gitUrl = url;
//                        }
//                        try {
//                            GitUtils.requireUpdatedRepository(gitUrl, getRoot());
//                            Set<String> groups = gitUrls.get(url);
//                            if (!groups.isEmpty()) {
//                                /*
//                                 * merge group permissions with project properties for the reader
//                                 */
//                            }
//                            addProjectPath(
//                                    new File(root + File.separator + MiscUtilities.getURLBaseName(url)));
//                            gitUrlByName.put(MiscUtilities.getURLBaseName(url), url);
//                        } catch (KlabException e) {
//                            if (new File(
//                                    root + File.separator + MiscUtilities.getURLBaseName(url) + File.separator
//                                            + ".git")
//                                                    .exists()) {
//                                Logging.INSTANCE.error(
//                                        "cannot sync existing repository " + url + ": error is "
//                                                + e.getMessage());
//                            } else {
//                                throw new KlabIOException(e);
//                            }
//                        }
//                    }
//                }
//
//                super.readProjects();
//            }
//        };
//        this.gitUrls = gitUrls.keySet();
//
//    }
//
//    public void setSkipSync(boolean skipSync) {
//        this.skipSync = skipSync;
//    }
//
//    public void deleteProject(IProject project) {
//        gitUrlByName.remove(project.getName());
//        super.deleteProject(project);
//    }
//
//}
