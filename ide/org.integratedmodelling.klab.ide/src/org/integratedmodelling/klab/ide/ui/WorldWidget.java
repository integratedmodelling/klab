package org.integratedmodelling.klab.ide.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.rest.SpatialExtent;

public class WorldWidget extends Canvas {

    SpatialExtent geometry;

    public void setExtent(SpatialExtent geometry) {
        if (geometry != null) {
            this.geometry = geometry.normalize();
        }
        redraw();
    }

    public WorldWidget(Composite parent, int style) {
        super(parent, style);
        addPaintListener(new PaintListener(){
            public void paintControl(PaintEvent e) {
                Point size = getSize();
                int deltaX = (size.x - 360) / 2;

                Image pluginImage = ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/worldscaled.png");
                e.gc.drawImage(pluginImage, deltaX, 0);
                if (geometry != null) {
                    e.gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                    int x = (int) (geometry.getWest() + 180);
                    int y = (int) (geometry.getSouth() + 90);
                    int width = (int) (geometry.getEast() - geometry.getWest());
                    int height = (int) (geometry.getNorth() - geometry.getSouth());
                    if (width < 2 && height < 2) {
                        e.gc.drawImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/target_red.png"), x - 8,
                                180 - y - 8);
                    } else {
                        e.gc.drawRectangle(x + deltaX, 180 - y - height, width, height);
                    }
                }
            }
        });
    }
}
