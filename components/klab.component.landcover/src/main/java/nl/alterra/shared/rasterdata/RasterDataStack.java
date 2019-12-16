package nl.alterra.shared.rasterdata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.observations.scale.IScale;

public class RasterDataStack {

	List<RasterData> data = new ArrayList<>();

	class It implements Iterator<CellStack> {

		IScale iterable = null;
		
		It() {
			if (data.size() > 0) {
				// save the scale
			}
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return iterable != null && false;
		}

		@Override
		public CellStack next() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	class Itr implements Iterable<CellStack> {
		@Override
		public Iterator<CellStack> iterator() {
			return new It();
		}
	}

	public void addInput(RasterData landuseMap) {
		data.add(landuseMap);
	}

	public RasterData addOutput(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<CellStack> getCellCursor() {
		return new Itr();
	}

	public boolean containsInputWithNodata(CellStack cellStack) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setOutputValues(CellStack cellStack, Number[] outputCellValues) {
		// TODO Auto-generated method stub

	}

	public Number[] getOutputNodataValues() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clearOutputs() {
		// TODO Auto-generated method stub

	}

	public int getOutputCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Long getNumberOfCellsMatchingTheFilter(Number[] values, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getNumberOfCellsWithData() {
		// TODO Auto-generated method stub
		return null;
	}

}
