package org.integratedmodelling.klab.components.runtime.contextualizers.debug;

import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;

/**
 * Does little but exposing targets with flexible API to test mediations and transitions.
 * 
 * @author Ferd
 *
 */
public class ProcessTestResolver extends AbstractContextualizer implements IResolver<IProcess> {

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IProcess resolve(IProcess ret, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

}
