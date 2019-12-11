package org.integratedmodelling.landcover.clue;

import java.util.Set;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.components.time.extents.Time;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.rasterdata.RasterData;
import nl.wur.iclue.parameter.SpatialDataset;

public class KLABSpatialDataset extends SpatialDataset {

	IState state;
	int currentTimeOffset = 0;
	
	KLABSpatialDataset(IState state) {
		this.state = state;
	}

	@Override
	public Set<Integer> getYears() {
		// TODO Auto-generated method stub
		return super.getYears();
	}

	@Override
	public RasterData getRasterData(int year) {
		return new KLABRasterData(state, state.getScale().getTime().at((long)year));
	}

	@Override
	public RasterData getLastRasterData() {
		// TODO Auto-generated method stub
		return super.getLastRasterData();
	}

	@Override
	public RasterData getMostRecentRasterData(int year) {
		// TODO Auto-generated method stub
		return super.getMostRecentRasterData(year);
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
	public int getFirstYear() {
		// TODO Auto-generated method stub
		return super.getFirstYear();
	}

	@Override
	public int getLastYear() {
		// TODO Auto-generated method stub
		return super.getLastYear();
	}

	@Override
	public boolean isYearKnown() {
		// TODO Auto-generated method stub
		return super.isYearKnown();
	}

	@Override
	public void removeByYear(long year) {
		// TODO Auto-generated method stub
		super.removeByYear(year);
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
