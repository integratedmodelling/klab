package org.integratedmodelling.klab.ide.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

public class KlabProjectProperties extends PropertyPage {

	public static final String ID = "org.integratedmodelling.klab.ide.projectpropertypage";
	
	/**
	 * Create the property page.
	 */
	public KlabProjectProperties() {
	}

	/**
	 * Create contents of the property page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		return container;
	}

}
