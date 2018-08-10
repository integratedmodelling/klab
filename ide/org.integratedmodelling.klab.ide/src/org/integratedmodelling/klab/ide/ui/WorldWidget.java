package org.integratedmodelling.klab.ide.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.ide.Activator;

public class WorldWidget extends Composite {

	public WorldWidget(IGeometry geometry, Composite parent, int style) {
		
		super(parent, style);
		setLayout(new GridLayout(1, false));
		boolean drawn = false;
		Canvas canvas = new Canvas(this, SWT.NONE);
		GridData gd_canvas = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_canvas.heightHint = 181;
		gd_canvas.widthHint = 360;
		canvas.setLayoutData(gd_canvas);
		Image image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/worldscaled.png");
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
				drawn = true;
//			}
		}
		canvas.setBackgroundImage(image);
		if (!drawn) {
			canvas.setEnabled(false);
		}
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNewLabel.setText(geometry.toString());
	}
}
