package nl.alterra.shared.rasterdata;

import java.util.Map;

import org.integratedmodelling.klab.Observations;

public class RasterData {

	/*
	 * should contain a state and a locator (scale in context). Same RD works across
	 * the temporal ranges as we only run one year in one CLUE call. Also because all
	 * ops here are done with integer codes, should either contain the key or operate on
	 * concepts.
	 */

	public Map<Integer, Integer> createValueCountTable() {
		// TODO Auto-generated method stub
		return null;
	}

	public static int getCellCount(Map<Integer, Integer> vat) {
		// TODO Auto-generated method stub
		return 0;
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
		// sure.
		return true;
	}

	public RasterData cut(RasterData regionData, int regionValue) {
		// TODO Auto-generated method stub
		return null;
	}

	public Number getCellValue(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNodata(Object cellValue) {
		return Observations.INSTANCE.isNodata(cellValue);
	}

}
