package org.integratedmodelling.random.services;

import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

public class RandomProcessResolver implements IResolver<IProcess> {

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess ret, IContextualizationScope scope) throws KlabException {

        /*
         * for each output mentioned in the function as a parameter, find the correspondent
         * observation and fill it in according to the function passed.
         */

        return ret;
    }

}
