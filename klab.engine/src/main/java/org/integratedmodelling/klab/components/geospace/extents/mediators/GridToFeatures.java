package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.components.geospace.api.ITessellation;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.utils.Pair;

public class GridToFeatures implements IScaleMediator {

    public GridToFeatures(Grid grid, ITessellation features) {
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
