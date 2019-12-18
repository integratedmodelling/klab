package nl.alterra.shared.rasterdata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.landcover.clue.KLABRasterData;

import nl.wur.iclue.model.CLUEModel;

public class RasterDataStack {

	protected final List<RasterData> inputs = new ArrayList<>();
	protected final List<RasterData> outputs = new ArrayList<>();

	private CLUEModel model;

	RasterDataStack(CLUEModel model) {
		this.model = model;
	}

	public void addInput(RasterData rasterData) {
		this.inputs.add(rasterData);
	}

	public RasterData getInput(int t) {
		return (RasterData) this.inputs.get(t);
	}

	public void addOutput(RasterData rasterData) {
		this.outputs.add(rasterData);
	}

	public RasterData getOutput(int t) {
		return this.outputs.get(t);
	}

	class It implements Iterator<CellStack> {

		Iterator<ILocator> iterator = null;

		It() {
			if (inputs.size() > 0 && inputs.get(0) instanceof KLABRasterData) {
				iterator = ((KLABRasterData)inputs.get(0)).getScale().iterator();
			}
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public CellStack next() {
			
			ILocator locator = iterator.next();
			CellStack ret = new CellStack(inputs.size());

			ILocator cell = ((IScale)locator).getSpace();
			
			int i = 0;
			for (RasterData input : inputs) {
				ret.inputValues[i++] = input.getCellValue(cell, Number.class);
			}
			return ret;
		}

	}

	class Itr implements Iterable<CellStack> {
		@Override
		public Iterator<CellStack> iterator() {
			return new It();
		}
	}

	public RasterData addOutput(boolean isInteger) {
		RasterData data = new KLABRasterData(Klab.INSTANCE.getStorageProvider().createStorage(Type.NUMBER,
				model.getKlabScope().getScale().initialization(), model.getKlabScope()));
		addOutput(data);
		return data;
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
		// TODO set an entire stack of values to nodata
		return null;
	}

	public void clearOutputs() {
		outputs.clear();
	}

	public int getInputCount() {
		return inputs.size();
	}

	public int getOutputCount() {
		return outputs.size();
	}

	public Long getNumberOfCellsWithData() {

		Long count = 0L;
		Iterable<CellStack> cursor = this.getCellCursor();
		Iterator<CellStack> iterator = cursor.iterator();

		while (iterator.hasNext()) {
			CellStack cs = (CellStack) iterator.next();
			if (!this.containsInputWithNodata(cs)) {
				count = count + 1L;
			}
		}

		return count;
	}

	public Long getNumberOfCellsMatchingTheFilter(Number[] cellStackValuesAsFilter, boolean ignoreCellsWithNoData) {

		Long count = 0L;
		Iterable<CellStack> cursor = this.getCellCursor();
		Iterator<CellStack> iterator = cursor.iterator();

		while (true) {
			CellStack cs;
			do {
				if (!iterator.hasNext()) {
					return count;
				}

				cs = (CellStack) iterator.next();
			} while (ignoreCellsWithNoData && this.containsInputWithNodata(cs));

			boolean valuesMatch = true;

			for (int i = 0; i < cellStackValuesAsFilter.length; ++i) {
				if (!cs.inputValues[i].equals(cellStackValuesAsFilter[i])) {
					valuesMatch = false;
					break;
				}
			}

			if (valuesMatch) {
				count = count + 1L;
			}
		}
	}

}
