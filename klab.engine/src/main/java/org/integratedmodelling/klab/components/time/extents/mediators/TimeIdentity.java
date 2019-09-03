package org.integratedmodelling.klab.components.time.extents.mediators;

import java.util.Collection;
import java.util.Collections;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.utils.Pair;

public class TimeIdentity implements IScaleMediator {

	@Override
	public boolean isConformant() {
		return true;
	}

	@Override
	public long mapConformant(long originalOffset) {
		return originalOffset;
	}

	@Override
	public Collection<Pair<Long, Double>> map(long originalOffset) {
		return Collections.singleton(new Pair<Long, Double>(originalOffset, 1.0));
	}

}
