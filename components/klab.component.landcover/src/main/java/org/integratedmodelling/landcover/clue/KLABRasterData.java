package org.integratedmodelling.landcover.clue;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.scale.Scale;
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
		System.out.println("SUUUUUUKA CALLING TABULATECELLCOUNT");
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

	@SuppressWarnings("unchecked")
	public <T> T getCellValue(ILocator cell, Class<? extends T> cls) {

		Object value = null;
		if (cell instanceof IExtent) {

			if (state != null) {

				IExtent[] extents = new IExtent[2];
				extents[0] = (IExtent) time;
				extents[1] = (IExtent) cell;
				value = state.get(Scale.create(extents));

			} else if (storage != null && cell instanceof IGrid.Cell) {
				Offset offset = new Offset(getScale(),
						new long[] { getTimeOffset(), ((IGrid.Cell) cell).getOffsetInGrid() });
				value = storage.get(offset);
			}

		} else {
			// fooock
		}

		if (value == null) {
			// CLUE is allergic to nulls
			return Utils.asType(Double.NaN, cls);
		}
		
		if (value instanceof Number) {
			return Utils.asType(value, cls);
		} else if (state != null && state.getDataKey() != null) {
			value = state.getDataKey().reverseLookup(value);
			return Utils.asType(value, cls);
		}

		return (T) value;
	}

	private long getTimeOffset() {
		if (time == null || ((AbstractExtent)time).getLocatedOffset() <= 0) {
			return 0;
		}
		return ((AbstractExtent)time).getLocatedOffset();
	}
}
