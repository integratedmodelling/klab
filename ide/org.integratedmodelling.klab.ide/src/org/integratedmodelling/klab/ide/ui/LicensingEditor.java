package org.integratedmodelling.klab.ide.ui;

import org.eclipse.swt.widgets.Composite;
import org.integratedmodelling.klab.rest.ResourceReference;

/**
 * TODO put CC and other data licenses as text in resources (with versions)
 * 
 * @author Ferd
 *
 */
public class LicensingEditor extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LicensingEditor(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void setResource(ResourceReference resource) {
		
	}
	
	public void save() {
		
	}

}
