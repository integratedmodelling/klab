/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.ide;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.common.Geometry;

/**
 * Class for displaying a pop-up control in its own shell. The control behaves similar to
 * a pop-up menu, but is a composite so can contain arbitrary controls.
 * 
 * <p>
 * <b>Sample Usage:</b>
 * 
 * <pre>
 * PopupComposite popup = new PopupComposite(getShell());
 * Text text = new Text(popup, SWT.BORDER);
 * popup.pack();
 * popup.show(shell.toDisplay(new Point(10, 10)));
 * </pre>
 * 
 * @author Kevin McGuinness
 */
public class PopupSpaceChooser extends Composite {

    /**
     * Style of the shell that will house the composite
     */
    private static final int SHELL_STYLE = SWT.MODELESS | SWT.NO_TRIM | SWT.ON_TOP | SWT.BORDER;

    /**
     * Shell that will house the composite
     */
    private final Shell shell;
    Spinner spinner;
    Combo combo;
    Listener listener;
    IGeometry geometry;

    // these track the state
    private double resolution = 1;
    private String unit = "km";

    static HashMap<String, String> toUnit = new HashMap<>();
    static HashMap<String, String> fromUnit = new HashMap<>();

    static {
        toUnit.put("kilometers", "km");
        toUnit.put("meters", "m");
        fromUnit.put("km", "kilometers");
        fromUnit.put("m", "meters");
    }

    public static interface Listener {

    }

    /**
     * Create a Pop-up composite. The default layout is a fill layout.
     * 
     * @param parent
     *            The parent shell.
     * @param style
     *            The composite style.
     * @param geometry the initial geometry
     */
    public PopupSpaceChooser(Shell parent, int style, IGeometry geometry) {

        super(new Shell(parent, SHELL_STYLE), style);
        getShell().setSize(320, 136);
        setSize(320, 136);
        this.geometry = Geometry.create(geometry.toString());

        setLayout(new FillLayout());

        Group grpSpatialRepresentationFor = new Group(this, SWT.NONE);
        grpSpatialRepresentationFor.setText("Spatial representation for shape objects");
        grpSpatialRepresentationFor.setBounds(10, 10, 295, 114);

        Button btnKeepSingleShape = new Button(grpSpatialRepresentationFor, SWT.RADIO);
        btnKeepSingleShape.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setForcing(0, toUnit.get(combo.getText()));
                spinner.setEnabled(false);
                combo.setEnabled(false);
                notifyUpdate();
            }
        });
        btnKeepSingleShape.setBounds(23, 23, 168, 16);
        btnKeepSingleShape.setText("As defined");

        Button btnRasterGrid = new Button(grpSpatialRepresentationFor, SWT.RADIO);
        btnRasterGrid.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setForcing(Double.parseDouble(spinner.getText()), toUnit.get(combo.getText()));
                spinner.setEnabled(true);
                combo.setEnabled(true);
                notifyUpdate();
            }
        });
        btnRasterGrid.setSelection(true);
        btnRasterGrid.setBounds(23, 45, 90, 16);
        btnRasterGrid.setText("Raster grid");

        spinner = new Spinner(grpSpatialRepresentationFor, SWT.BORDER);
        spinner.setBounds(45, 67, 140, 22);
        spinner.setMinimum(1);
        spinner.setValues((int) getForcingSize(), 1, 10000, 0, 1, 100);
        spinner.setSelection((int) getForcingSize());

        combo = new Combo(grpSpatialRepresentationFor, SWT.READ_ONLY);
        combo.setBounds(191, 67, 91, 23);
        combo.add("meters");
        combo.add("kilometers");
        combo.select(getForcingUnit().equals("m") ? 0 : 1);

        spinner.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                try {
                    setForcing(Double.parseDouble(spinner.getText()), toUnit.get(combo.getText()));
                    notifyUpdate();
                } catch (Throwable t) {
                    // Do nothing - this happens if user mistakenly types a letter
                    // or something else than a number.
                }
            }

        });

        combo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                setForcing(Double.parseDouble(spinner.getText()), toUnit.get(combo.getText()));
                notifyUpdate();
            }
        });

        shell = getShell();
        shell.setLayout(new FillLayout());
        shell.addShellListener(new ActivationListener());
        shell.setMinimumSize(new Point(320, 136));
        setLayout(null);
    }

    private void setForcing(double resolution, String unit) {
        this.resolution = resolution;
        this.unit = unit;
    }
    
    private Object getForcingUnit() {
        return unit;
    }

    private double getForcingSize() {
        return resolution;
    }

    protected void notifyUpdate() {
        // TODO Auto-generated method stub
        
    }

    /**
     * Display the composite below the given tool item. The item will be sized such that
     * it's width is at least the width of the given tool item.
     * 
     * @param bar
     *            The tool bar.
     * @param item
     *            The tool item.
     */
    public void showBelow(ToolBar bar, ToolItem item) {
        Rectangle r = item.getBounds();
        Point p = bar.toDisplay(new Point(r.x, r.y + r.height));
        setSize(computeSize(item));
        show(p);
    }

    /**
     * Display the composite in its own shell at the given point.
     * 
     * @param pt
     *            The point where the pop-up should appear.
     */
    public void show(Point pt) {
        // Match shell and component sizes
        shell.setSize(getSize());

        if (pt != null) {
            shell.setLocation(pt);
        }

        shell.open();
    }

    /**
     * Display the pop-up where it was last displayed.
     */
    public void show() {
        show(null);
    }

    /**
     * Hide the pop-up.
     */
    public void hide() {
        shell.setVisible(false);
    }

    /**
     * Returns <code>true</code> if the shell is currently activated.
     * 
     * @return <code>true</code> if the shell is visible.
     */
    public boolean isDisplayed() {
        return shell.isVisible();
    }

    /**
     * Computes the optimal size with respect to the given tool item.
     * 
     * @param item
     *            The tool item.
     * @return The optimal size.
     */
    private Point computeSize(ToolItem item) {
        Point s2 = computeSize(item.getWidth(), SWT.DEFAULT);
        Point s1 = computeSize(SWT.DEFAULT, SWT.DEFAULT);
        return s1.x > s2.x ? s1 : s2;
    }

    /**
     * Class that handles shell appearance and disappearance appropriately. Specifically,
     * it hides the shell when it becomes de-activated (for example, when the user clicks
     * on the parent shell). Also, there is a minimum delay which is enforced between
     * showing and hiding the pop-up, to prevent undesirable behavior such as hiding and
     * immediately re-displaying the pop-up when the user selects a button responsible for
     * showing the tool item.
     */
    private final class ActivationListener extends ShellAdapter {

        private static final int TIMEOUT = 500;
        private long time = -1;

        @Override
        public void shellDeactivated(ShellEvent e) {
            // Record time of event
            time = (e.time & 0xFFFFFFFFL);

            // Hide
            hide();
        }

        @Override
        public void shellActivated(ShellEvent e) {
            if (time > 0) {
                // Find elapsed time
                long elapsed = ((e.time & 0xFFFFFFFFL) - time);

                // If less than a timeout, don't activate
                if (elapsed < TIMEOUT) {
                    hide();

                    // Next activation event is fine
                    time = -1;
                }
            }
        }
    }
}
