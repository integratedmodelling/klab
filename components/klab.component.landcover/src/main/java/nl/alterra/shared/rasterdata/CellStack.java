package nl.alterra.shared.rasterdata;

/**
 * FV: I believe this is a set of cells in the same location. Should just
 * store a locator and be local to the raster stack.
 */
public class CellStack {

	public Number[] inputValues;

	private int rowIndex;
	private int columnIndex;
	
	public CellStack(int length) {
		this.inputValues = new Number[length];
	}
	
	public int getColumnIndex() {
		return rowIndex;
	}

	public int getRowIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public void setRowIndex(int columnIndex) {
		this.rowIndex = columnIndex;
	}

}
