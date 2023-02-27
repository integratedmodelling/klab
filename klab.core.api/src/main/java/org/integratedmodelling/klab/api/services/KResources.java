package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.data.KKlabData;
import org.integratedmodelling.klab.api.knowledge.KResource;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;
import org.integratedmodelling.klab.api.knowledge.organization.KProject;
import org.integratedmodelling.klab.api.lang.kactors.KKActorsBehavior;
import org.integratedmodelling.klab.api.lang.kdl.KKdlDataflow;

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

}
