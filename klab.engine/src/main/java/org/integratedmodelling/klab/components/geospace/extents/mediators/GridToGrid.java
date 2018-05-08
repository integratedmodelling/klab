package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.utils.Pair;

public class GridToGrid implements IScaleMediator {

    boolean conformant = false;
    
    @Override
    public boolean isConformant() {
        return conformant;
    }

    @Override
    public long mapConformant(long originalOffset) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Collection<Pair<Long, Double>> map(long originalOffset) {
        // TODO Auto-generated method stub
        return null;
    }

}
