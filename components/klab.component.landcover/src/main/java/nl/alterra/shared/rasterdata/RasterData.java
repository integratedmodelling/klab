package nl.alterra.shared.rasterdata;

import java.util.Map;
import java.util.Map.Entry;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;

public class RasterData {

	/*
	 * should contain a state and a locator (scale in context). Same RD works across
	 * the temporal ranges as we only run one year in one CLUE call. Also because all
	 * ops here are done with integer codes, should either contain the key or operate on
	 * concepts.
	 */

	public Map<Integer, Integer> createValueCountTable() {
		throw new KlabUnimplementedException("Quickscan code to be filled in - reimplement in derived classes");
	}

	/*
	 * Hope this is what they mean
	 */
	public static int getCellCount(Map<Integer, Integer> vat) {
		int ret = 0;
		for (Entry<Integer, Integer> e : vat.entrySet()) {
			ret += e.getValue();
		}
		return ret;
	}

	public Object getDataDefinition() {
		throw new KlabUnimplementedException("Quickscan code to be filled in - reimplement in derived classes");
	}

	public Map<Integer, Map<Integer, Integer>> tabulateCellCount(RasterData rasterData) {
		throw new KlabUnimplementedException("Quickscan code to be filled in - reimplement in derived classes");
	}

	public boolean isDataDefinitionValid() {
		// sure.
		return true;
	}

	public RasterData cut(RasterData regionData, int regionValue) {
		throw new KlabUnimplementedException("Quickscan code to be filled in - reimplement in derived classes");
	}

	public Number getCellValue(int rowIndex, int columnIndex) {
		throw new KlabUnimplementedException("Quickscan code to be filled in - reimplement in derived classes");
	}

	public boolean isNodata(Object cellValue) {
		return Observations.INSTANCE.isNodata(cellValue);
	}

}
