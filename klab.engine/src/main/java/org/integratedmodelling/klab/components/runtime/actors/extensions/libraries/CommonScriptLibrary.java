package org.integratedmodelling.klab.components.runtime.actors.extensions.libraries;

import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.extensions.actors.Call;
import org.integratedmodelling.klab.api.extensions.actors.Library;
import org.integratedmodelling.klab.common.mediation.Unit;

@Library(name="common", defaultFor = {Type.SCRIPT, Type.UNITTEST})
public class CommonScriptLibrary {

    @Call
    public IUnit unit(String definition) {
        return Unit.create(definition);
    }
    
}
