package org.integratedmodelling.klab.components.geospace.visualization;

import static sun.java2d.StateTrackable.State.STABLE;
import static sun.java2d.StateTrackable.State.UNTRACKABLE;

import java.awt.image.DataBuffer;
import java.util.function.Function;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.common.Offset;

public class ReadonlyStateFloatBuffer extends DataBuffer {
	public static final Object noDataValue = Float.NaN;

	private IState state;

	protected ReadonlyStateFloatBuffer(IState state, Function<Object, Object> transformation, int size) {
		super(DataBuffer.TYPE_FLOAT, size);
		this.state = state;
	}

	public int getElem(int i) {
		return (int) (data[i + offset]);
	}

	public int getElem(int bank, int i) {
		return (int) (bankdata[bank][i + offsets[bank]]);
	}

	public float getElemFloat(int i) {
		return (float) data[i + offset];
	}

	public float getElemFloat(int bank, int i) {
		return (float) bankdata[bank][i + offsets[bank]];
	}

	public double getElemDouble(int i) {
		return data[i + offset];
	}

	public double getElemDouble(int bank, int i) {
		return bankdata[bank][i + offsets[bank]];
	}

	public void setElem(int i, int val) {
		throwReadOnly();
	}

	public void setElem(int bank, int i, int val) {
		throwReadOnly();
	}

	public void setElemFloat(int i, float val) {
		throwReadOnly();
	}

	public void setElemFloat(int bank, int i, float val) {
		throwReadOnly();
	}

	public void setElemDouble(int i, double val) {
		throwReadOnly();
	}

	public void setElemDouble(int bank, int i, double val) {
		throwReadOnly();
	}

	private void throwReadOnly() {
		throw new RuntimeException("The buffer is readonly.");
	}

	public static Object getValue(int x, int y, IState state, Function<Object, Object> transformation) {
		Offset off = new Offset(state.getScale(), new long[] { x, y });
		Object o = state.get(off);
		if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
			return noDataValue;
		} else if (o instanceof Number) {
			if (transformation != null) {
				o = transformation.apply(o);
			}
			return ((Number) o).floatValue();
		} else if (o instanceof Boolean) {
			if (transformation != null) {
				o = transformation.apply(o);
			}
			return (float) (((Boolean) o) ? 1. : 0.);
		} else if (o instanceof IConcept) {
			if (transformation != null) {
				o = transformation.apply(o);
			}
			return (float) state.getDataKey().reverseLookup((IConcept) o);
		}
		return noDataValue;
	}
}
