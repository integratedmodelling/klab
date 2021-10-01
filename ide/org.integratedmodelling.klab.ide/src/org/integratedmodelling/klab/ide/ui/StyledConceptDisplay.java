package org.integratedmodelling.klab.ide.ui;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.rest.StyledKimToken;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;

/**
 * Shows a set of k.IM concepts with appropriate coloring and spacing, with the option of hiding
 * namespaces and an appropriate icon that can be dragged.
 * 
 * @author Ferd
 *
 */
public class StyledConceptDisplay extends Composite {

    boolean showNamespaces = true;
    
    /**
     * Create the composite.
     * 
     * @param parent
     * @param style
     */
    public StyledConceptDisplay(Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout(3, false));
        
        Label lblNewLabel = new Label(this, SWT.NONE);
        GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_lblNewLabel.widthHint = 24;
        lblNewLabel.setLayoutData(gd_lblNewLabel);
        
        StyledText styledText = new StyledText(this, SWT.BORDER | SWT.READ_ONLY | SWT.SINGLE);
        GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_styledText.heightHint = 37;
        styledText.setLayoutData(gd_styledText);
        
        Button btnNewButton = new Button(this, SWT.TOGGLE);
        btnNewButton.setSelection(true);
        btnNewButton.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/namespace-checked.png"));
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

    public void setConcept(List<StyledKimToken> tokens, IKimConcept.Type type) {

    }

}
