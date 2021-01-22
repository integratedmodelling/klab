package org.integratedmodelling.klab.components.geospace.visualization;

import java.awt.Rectangle;
import java.awt.image.Raster;

import com.sun.media.jai.iterator.WrapperRI;

public class FloatRasterWrapper extends WrapperRI {

	private Raster ras;

	public FloatRasterWrapper(Raster ras) {
		super(ras);
		this.ras = ras;
	}

	public Raster getData() {
		return ras;
	}

	public Raster getData(Rectangle rect) {
		return ras.createChild(rect.x, rect.y, rect.width, rect.height, rect.x, rect.y, null);
	}
}
