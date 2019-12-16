package org.integratedmodelling.landcover.clue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;

import nl.alterra.shared.rasterdata.RasterData;

public class KLABRasterData extends RasterData {

	IState state;
	IStorage<?> storage;
	
	private ILocator time;
	
	public KLABRasterData(IState state, IExtent time) {
		this.state = state.at(this.time = time);
	}

	/**
	 * When using this form, we only keep one slice
	 * 
	 * @param storage
	 */
	public KLABRasterData(IStorage<?> storage) {
		this.storage = storage;
	}
	
	/**
	 * @return the time
	 */
	public ILocator getTime() {
		return time;
	}
	
	/**
	 * @param time the time to set
	 */
	public void setTime(ILocator time) {
		this.time = time;
	}
	
	public Map<Integer, Integer> createValueCountTable() {
		Map<Integer,Integer> map = new HashMap<>();
		if (state != null) {
			for (ILocator locator : state.getScale()) {
				Object o = state.get(locator);
				if (!Observations.INSTANCE.isNodata(o) && state.getDataKey() != null) {
					int value = state.getDataKey().reverseLookup(o);
					map.put(value, map.containsKey(value) ? map.get(value) + 1 : 1);
				}
			}
		}
		return map;
	}

	public Object getDataDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, Map<Integer, Integer>> tabulateCellCount(RasterData rasterData) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDataDefinitionValid() {
		// TODO Auto-generated method stub
		return true;
	}

	public RasterData cut(RasterData regionData, int regionValue) {
		// TODO Auto-generated method stub
		return regionData;
	}

	public Number getCellValue(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
