package org.integratedmodelling.landcover.clue;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.scale.AbstractExtent;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.rasterdata.IMaskeable;
import nl.alterra.shared.rasterdata.RasterData;
import nl.wur.iclue.parameter.SpatialDataset;

public class KLABSpatialDataset extends SpatialDataset implements IMaskeable {

	IState state;
	int currentTimeOffset = 0;
	private SpatialDataset mask;
	private Category maskCategory;
	
	KLABSpatialDataset(IState state) {
		this.state = state;
		setCaption(Observations.INSTANCE.getDisplayLabel(state));
		// preload all states, then disable the check at creation. Each state is purely logical.
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
		// TODO Auto-generated method stub - check that it's not called
		System.out.println("CACABUFFO");
		return super.clone();
	}

	@Override
	public void setMask(SpatialDataset regions, Category category) {
		this.mask = regions;
		this.maskCategory = category;
	}
	
	
	
}
