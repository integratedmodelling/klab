/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.common.Geometry;
import org.joda.time.DateTimeConstants;

public class PopupTimeChooser extends Composite {

    /**
     * Style of the shell that will house the composite
     */
    private static final int SHELL_STYLE = SWT.MODELESS | SWT.NO_TRIM | SWT.ON_TOP | SWT.BORDER;

    /**
     * Shell that will house the composite
     */
    private final Shell shell;
    Listener listener;
    IGeometry geometry;

    // GUI coding, what a pain
    static HashMap<String, Long> toUnit = new HashMap<>();
    private Combo timeUnit;
    private Spinner timeStep;
    private Button btnStep;
    private Button btnEnd;
    private Button btnStart;
    private DateTime startTime;
    private DateTime endTime;
    
    private Long start;
    private Long end;
    private Long step;

    static {
        toUnit.put("Weeks", (long) DateTimeConstants.MILLIS_PER_WEEK);
        toUnit.put("Days", (long) DateTimeConstants.MILLIS_PER_DAY);
        toUnit.put("Hours", (long) DateTimeConstants.MILLIS_PER_HOUR);
    }

    public static interface Listener {

    }

    /**
     * Create a Pop-up composite. The default layout is a fill layout.
     * 
     * @param parent
     *          The parent shell.
     * @param style
     *          The composite style.
     * @param geometry 
     */
    public PopupTimeChooser(Shell parent, int style, IGeometry geometry) {

        super(new Shell(parent, SHELL_STYLE), style);
        getShell().setSize(320, 150);
        setSize(320, 150);
        setLayout(new FillLayout());

        this.geometry = Geometry.create(geometry.toString());

        Group grpSpatialRepresentationFor = new Group(this, SWT.NONE);
        grpSpatialRepresentationFor.setText("Temporal context");
        grpSpatialRepresentationFor.setBounds(10, 10, 295, 130);

        btnStart = new Button(grpSpatialRepresentationFor, SWT.CHECK);
        btnStart.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                startTime.setEnabled(btnStart.getSelection());
                set();
                // setStart(btnStart.getSelection());
            }
        });
        btnStart.setBounds(22, 29, 57, 16);
        btnStart.setText("Start");
        btnStart.setSelection(hasStart());

        btnEnd = new Button(grpSpatialRepresentationFor, SWT.CHECK);
        btnEnd.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                endTime.setEnabled(btnEnd.getSelection());
                set();
                // setEnd(btnEnd.getSelection());
            }
        });
        btnEnd.setBounds(22, 57, 57, 16);
        btnEnd.setText("End");
        btnEnd.setSelection(hasEnd());

        btnStep = new Button(grpSpatialRepresentationFor, SWT.CHECK);
        btnStep.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                timeStep.setEnabled(btnStep.getSelection());
                timeUnit.setEnabled(btnStep.getSelection());
                set();
                // setStep(btnStep.getSelection());
            }
        });
        btnStep.setBounds(22, 85, 57, 16);
        btnStep.setText("Step");
        btnStep.setSelection(hasStep());
        btnStep.setEnabled(hasStep() && hasStart());

        timeStep = new Spinner(grpSpatialRepresentationFor, SWT.BORDER);
        timeStep.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                set();
                // setStep(true);
            }
        });
        timeStep.setEnabled(hasStep() && hasEnd() && hasStart());
        timeStep.setMaximum(100000);
        timeStep.setMinimum(1);
        timeStep.setSelection(1);
        timeStep.setBounds(85, 83, 95, 22);

        timeUnit = new Combo(grpSpatialRepresentationFor, SWT.READ_ONLY);
        timeUnit.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                set();
                // setStep(true);
            }
        });
        timeUnit.setEnabled(hasStep());
        timeUnit.setItems(new String[] { "Weeks", "Days", "Hours" });
        timeUnit.setBounds(186, 82, 91, 23);
        timeUnit.select(1);

        endTime = new DateTime(grpSpatialRepresentationFor, SWT.BORDER);
        endTime.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                set();
                // setEnd(true);
            }
        });
        endTime.setEnabled(hasEnd());
        endTime.setBounds(84, 52, 193, 24);
        if (hasEnd()) {
            org.joda.time.DateTime dt = new org.joda.time.DateTime(this.end);
            endTime.setDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
        }

        startTime = new DateTime(grpSpatialRepresentationFor, SWT.BORDER);
        startTime.setEnabled(hasStart());
        startTime.setBounds(84, 24, 193, 24);
        if (hasStart()) {
            org.joda.time.DateTime dt = new org.joda.time.DateTime(this.start);
            startTime.setDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
        }
        startTime.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                set();
                // setStart(true);
            }
        });

        shell = getShell();
        shell.setLayout(new FillLayout());
        shell.addShellListener(new ActivationListener());
        shell.setMinimumSize(new Point(320, 136));
        setLayout(null);
    }

    protected void set() {

        if (startTime == null || endTime == null) {
            return;
        }

        boolean hasStart = btnStart.getSelection();
        boolean hasEnd = btnEnd.getSelection();
        boolean hasStep = btnStep.getSelection() && btnStep.isEnabled();

        org.joda.time.DateTime startDate = new org.joda.time.DateTime(startTime.getYear(), startTime.getMonth() + 1,
                startTime.getDay(), 0, 0);
        org.joda.time.DateTime endDate = new org.joda.time.DateTime(endTime.getYear(), endTime.getMonth() + 1,
                startTime.getDay(), 0, 0);

        long start = hasStart ? startDate.getMillis() : 0;
        long end = hasEnd ? endDate.getMillis() : 0;
        long step = hasStep ? timeStep.getSelection() * toUnit.get(timeUnit.getText()) : 0;

        btnStep.setEnabled(hasStart && hasEnd && (end - start) >= toUnit.get(timeUnit.getText()));
        if (!btnStep.isEnabled()) {
            btnStep.setSelection(false);
        }

        setForcing(start, end, step);
        notifyUpdate();
    }

    private void setForcing(Long start, Long end, Long step) {
        this.start = start;
        this.end = end;
        this.step = step;
        
    }

    private void notifyUpdate() {
        // TODO Auto-generated method stub
        
    }

    protected long getStepMillis() {
        return step == null ? 0 : step;
    }

    private boolean hasStep() {
        return step != null && step > 0;
    }

    private boolean hasStart() {
        return start != null && start > 0;
    }

    private boolean hasEnd() {
        return end != null && end > 0;
    }

    /**
     * Display the composite below the given tool item. The item will be sized
     * such that it's width is at least the width of the given tool item.
     * 
     * @param bar
     *          The tool bar.
     * @param item
     *          The tool item.
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
     *          The point where the pop-up should appear.
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
     *          The tool item.
     * @return The optimal size.
     */
    private Point computeSize(ToolItem item) {
        Point s2 = computeSize(item.getWidth(), SWT.DEFAULT);
        Point s1 = computeSize(SWT.DEFAULT, SWT.DEFAULT);
        return s1.x > s2.x ? s1 : s2;
    }

    /**
     * Class that handles shell appearance and disappearance appropriately.
     * Specifically, it hides the shell when it becomes de-activated (for example,
     * when the user clicks on the parent shell). Also, there is a minimum delay
     * which is enforced between showing and hiding the pop-up, to prevent
     * undesirable behavior such as hiding and immediately re-displaying the
     * pop-up when the user selects a button responsible for showing the tool
     * item.
     */
    private final class ActivationListener extends ShellAdapter {

        private static final int TIMEOUT = 500;
        private long time = -1;

        @Override
        public void shellDeactivated(ShellEvent e) {
            // Record time of event
            time = (e.time & 0xFFFFFFFFL);
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
