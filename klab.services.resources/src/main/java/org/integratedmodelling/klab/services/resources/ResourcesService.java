package org.integratedmodelling.klab.services.resources;

import org.integratedmodelling.klab.api.data.KKlabData;
import org.integratedmodelling.klab.api.knowledge.KResource;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;
import org.integratedmodelling.klab.api.knowledge.organization.KProject;
import org.integratedmodelling.klab.api.lang.kactors.KKActorsBehavior;
import org.integratedmodelling.klab.api.lang.kdl.KKdlDataflow;
import org.integratedmodelling.klab.api.services.KResources;
import org.springframework.stereotype.Service;

@Service
public class ResourcesService implements KResources {

    @Override
    public KProject resolveProject(String urn, KScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKActorsBehavior resolveBehavior(String urn, KScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KResource resolveResource(String urn, KScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KResource contextualizeResource(KResource originalResource, KContextScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKlabData contextualize(KResource contextualizedResource, KScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKdlDataflow resolveDataflow(String urn, KScope scope) {
        // TODO Auto-generated method stub
        return null;
    }
}
