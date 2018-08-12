package org.integratedmodelling.klab.ide.ui;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.ide.Activator;

public class WorldWidget extends Canvas {

	public WorldWidget(IGeometry geometry, Composite parent, int style) {
		
		super(parent, style);
        this.setSize(360, 181);
        this.setBackgroundImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/worldscaled.png"));
		if (geometry.getDimension(Type.SPACE) != null) {
//			GC gc = new GC(image);
//			gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
//			double[] bbox = geometry.getDimension(Type.SPACE).getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);
//			if (bbox != null) {
//				int x = (int)(bbox[0] + 180);
//				int y = (int)(bbox[2] + 90);
//				int width = (int)(bbox[1] - bbox[0]);
//				int height = (int)(bbox[3] - bbox[2]);
//				gc.drawRectangle(
//						x, 
//						y, 
//						width, 
//						height);
//			}
		}
	}
}
