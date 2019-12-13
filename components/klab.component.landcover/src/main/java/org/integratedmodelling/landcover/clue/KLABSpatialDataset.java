package org.integratedmodelling.landcover.clue;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.scale.AbstractExtent;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.rasterdata.RasterData;
import nl.wur.iclue.parameter.SpatialDataset;

public class KLABSpatialDataset extends SpatialDataset {

	IState state;
	int currentTimeOffset = 0;
	
	KLABSpatialDataset(IState state) {
		this.state = state;
		for (int i = 0; i < state.getScale().getTime().size(); i++) {
			map.put(i, new KLABRasterData(state, ((AbstractExtent)state.getScale().getTime()).getExtent(i)));
		}
	}

	@Override
	public RasterData getRasterData() {
		return getRasterData(currentTimeOffset);
	}

	@Override
	public Integer getYear() {
		return currentTimeOffset;
	}

	@Override
	public SpatialDataset clone() {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public SpatialDataset cut(SpatialDataset regions, Category regionOfInterest) {
		// TODO Auto-generated method stub
		return super.cut(regions, regionOfInterest);
	}
	
	
	
}
