package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.components.geospace.api.ITessellation;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.utils.Pair;

public class FeaturesToShape implements IScaleMediator {

    public FeaturesToShape(ITessellation features, Shape shape) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean isConformant() {
        // TODO Auto-generated method stub
        return false;
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
