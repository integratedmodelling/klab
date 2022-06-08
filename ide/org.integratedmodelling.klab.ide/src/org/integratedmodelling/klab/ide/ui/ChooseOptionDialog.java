package org.integratedmodelling.klab.ide.ui;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ChooseOptionDialog<T> extends Dialog {
    
    private Combo combo;
    private Label title;
    private String text;
    private List<T> alternatives;
    int choice = 0;

    public ChooseOptionDialog(Shell parentShell, String title, List<T> alternatives) {
        super(parentShell);
        this.text = title;
        this.alternatives = alternatives;
        this.setBlockOnOpen(true);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        new Label(container, SWT.NONE);
        
        title = new Label(container, SWT.NONE);
        title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        title.setText(text);
        
        combo = new Combo(container, SWT.READ_ONLY);
        combo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                choice = combo.getSelectionIndex();
            }
        });
        combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        for (T alternative : this.alternatives) {
            combo.add(alternative.toString());
        }
        combo.select(0);
        return container;
    }

    // overriding this methods allows you to set the
    // title of the custom dialog
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Make a selection");
    }

    @Override
    protected Point getInitialSize() {
        return new Point(450, 300);
    }

    public T run() {
        if (super.open() == Window.OK) {
            return this.alternatives.get(choice);
        } 
        return null;
    }
    
    

}