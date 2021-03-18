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
package org.integratedmodelling.klab.ide.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationPage;
import org.eclipse.swt.widgets.Combo;

public class NewDocumentationSection extends WizardPage {

	private Text text;
	private EDocumentationFolder folder;
	private EDocumentationPage item;
	private Combo triggerCombo;
	private Combo sectionCombo;
	

	/**
	 * Create the wizard.
	 * 
	 * @param item
	 * @param page
	 */
	public NewDocumentationSection(EDocumentationFolder folder, EDocumentationPage item) {
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.png"));
		setTitle("New documentation folder");
		setDescription("Create a new documentation page" + (item == null ? "" : " section"));
		this.folder = folder;
		this.item = item;
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		GridLayout gl_container = new GridLayout(3, false);
		gl_container.verticalSpacing = 10;
		gl_container.marginRight = 12;
		gl_container.marginLeft = 12;
		gl_container.marginTop = 32;
		container.setLayout(gl_container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("Page ID: ");

		Label lblProjectName = new Label(container, SWT.NONE);
		lblProjectName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblProjectName.setAlignment(SWT.RIGHT);
		lblProjectName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblProjectName.setText((item != null || folder.getPath().isEmpty()) ? "" : (folder.getPath() + "."));

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblTrigger = new Label(container, SWT.NONE);
		lblTrigger.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTrigger.setText("Trigger:");

		if (item != null) {
			text.setText(item.getPagePath());
			text.setEnabled(false);
		}

		triggerCombo = new Combo(container, SWT.READ_ONLY);
		triggerCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		triggerCombo.setItems(IDocumentation.triggers);
		// TODO remove existing and select Methods if enabled, first enabled otherwise
		triggerCombo.select(1);

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Section:");

		sectionCombo = new Combo(container, SWT.READ_ONLY);
		sectionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		// TODO disable or remove existing and select Methods if enabled, first enabled
		// otherwise
		sectionCombo.setItems(IDocumentation.sections);
		sectionCombo.select(0);
	}

	public Text getFolderName() {
		return text;
	}

	public String getTrigger() {
		return triggerCombo.getText();
	}

	public String getSection() {
		return sectionCombo.getText();
	}
}
