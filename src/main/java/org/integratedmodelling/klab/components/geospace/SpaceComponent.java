package org.integratedmodelling.klab.components.geospace;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;

@Component(id="geospace", version=Version.CURRENT)
public class SpaceComponent {

    public SpaceComponent() {
        // TODO Auto-generated constructor stub
    }

    @Initialize
    public void initialize() {
        // TODO set up defaults for projections etc.
    }
    
}
