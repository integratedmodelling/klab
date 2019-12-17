package org.integratedmodelling.landcover.clue;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.utils.Utils;

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
		Map<Integer, Integer> map = new HashMap<>();
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
		return this.state == null ? "Raw storage" : Observations.INSTANCE.getDisplayLabel(this.state);
	}

	public Map<Integer, Map<Integer, Integer>> tabulateCellCount(RasterData rasterData) {
		Map<Integer, Map<Integer, Integer>> ret = new HashMap<>();	
		// TODO Auto-generated method stub
		return ret;
	}

	public boolean isDataDefinitionValid() {
		// of course.
		return true;
	}

	public RasterData cut(RasterData regionData, int regionValue) {
		// TODO Auto-generated method stub
		return regionData;
	}

	public Number getCellValue(int rowIndex, int columnIndex) {
		
		ISpace space = getScale().getSpace();
		if (space instanceof Space && ((Space) space).getGrid() != null) {

			Offset locator = getScale()
					.at(((Space) space).getGrid().getCell(((Space) space).getGrid().getOffset(columnIndex, rowIndex)))
					.as(Offset.class);

			if (state != null) {
				return state.get(locator, Number.class);
			} else if (storage != null) {
				return Utils.asType(storage.get(locator), Number.class);
			}
		}
		return null;
	}

	public IScale getScale() {
		return state == null ? (IScale) storage.getGeometry() : state.getScale();
	}

}
