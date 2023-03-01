package org.integratedmodelling.klab.api.services;

import java.util.List;

import org.integratedmodelling.klab.api.data.KKlabData;
import org.integratedmodelling.klab.api.knowledge.KResource;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;
import org.integratedmodelling.klab.api.knowledge.organization.KProject;
import org.integratedmodelling.klab.api.knowledge.organization.KWorkspace;
import org.integratedmodelling.klab.api.lang.kactors.KKActorsBehavior;
import org.integratedmodelling.klab.api.lang.kdl.KKdlDataflow;
import org.integratedmodelling.klab.api.lang.kim.KKimNamespace;

public interface KResources {

    /**
     * 
     * @param urn
     * @param scope
     * @return
     */
    KProject resolveProject(String urn, KScope scope);

    /**
     * 
     * @param urn
     * @param scope
     * @return
     */
    KKActorsBehavior resolveBehavior(String urn, KScope scope);

    /**
     * 
     * @param urn
     * @param scope
     * @return
     */
    KResource resolveResource(String urn, KScope scope);

    /**
     * 
     * @param originalResource
     * @param scope
     * @return
     */
    KResource contextualizeResource(KResource originalResource, KContextScope scope);

    /**
     * 
     * @param contextualizedResource
     * @param scope
     * @return
     */
    KKlabData contextualize(KResource contextualizedResource, KScope scope);

    /**
     * 
     * @param urn
     * @param scope
     * @return
     */
    KKdlDataflow resolveDataflow(String urn, KScope scope);

    /**
     * Return all the namespaces that depend on the passed namespace.
     * 
     * @param namespaceId
     * @return
     */
    List<KKimNamespace> dependents(String namespaceId);

    /**
     * Return all the namespaces that the passed namespace depends on. These must be available to
     * the resolver prior to loading any namespace. The closure of the namespace must be complete,
     * no matter if they come from this service or others: a service cannot serve a namespace unless
     * it's prepared to serve its entire closure under the same scope.
     * 
     * @param namespaceId
     * @return
     */
    List<KKimNamespace> precursors(String namespaceId);

    /**
     * Admin interface to submit/remove projects and configure the service.
     * 
     * @author Ferd
     *
     */
    interface Admin {

        /**
         * Add or update a project from an external source to the local repository.
         * 
         * @param workspaceName
         * @param projectUrl can be a file (zip or existing folder), a git URL (with a potential
         *        branch name after a # sign) or a http URL from another resource manager.
         * @return true if operation succeeded
         */
        boolean addProjectToLocalWorkspace(String workspaceName, String projectUrl);

        /**
         * 
         * @param workspaceName
         * @param projectName
         * @return true if operation was carried out
         */
        void removeProjectFromLocalWorkspace(String workspaceName, String projectName);

        /**
         * Remove an entire workspace and all the projects and resources in it.
         * 
         * @param workspaceName
         */
        void removeWorkspace(String workspaceName);

        /**
         * Return a list of all the workspaces available with their contents.
         * 
         * @return
         */
        List<KWorkspace> getWorkspaces();

    }

}
