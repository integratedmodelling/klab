package org.integratedmodelling.landcover.clue;

import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.datakind.IDataKind.Type;
import nl.alterra.shared.rasterdata.RasterData;
import nl.wur.iclue.parameter.SpatialDataset;

/**
 * A dataset that simply provides one class for the context "administrative unit".
 * 
 * @author ferdinando.villa
 *
 */
public class KLABContextDataset extends SpatialDataset {

	int currentTimeOffset = 0;
	
	RasterData ctx;
	
	KLABContextDataset(IRuntimeScope scope) {
			
		/*
		 * one class with value 1
		 */
		DataKind dk = new DataKind();
		dk.setType(Type.QUANTITATIVE);
		Category ct = dk.addNew();
		ct.setCaption(scope.getRootSubject().getName());
		ct.setValue(1);

		setDatakind(dk);
		
		this.ctx = new RasterData() {
			// TODO
		};
		for (int i = 0; i < scope.getScale().getTime().size(); i++) {
			map.put(i, ctx);
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
	
}
