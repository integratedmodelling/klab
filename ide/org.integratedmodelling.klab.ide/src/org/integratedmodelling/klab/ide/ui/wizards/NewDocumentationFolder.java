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
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;

public class NewDocumentationFolder extends WizardPage {
	
	private Text text;
	private EDocumentationFolder folder;
	/**
	 * Create the wizard.
	 * @param page 
	 */
	public NewDocumentationFolder(EDocumentationFolder folder) {
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.png"));
		setTitle("New documentation folder");
		setDescription("Create a new documentation folder");
		this.folder = folder;
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	@Override
    public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		GridLayout gl_container = new GridLayout(3, false);
		gl_container.marginTop = 48;
		container.setLayout(gl_container);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("Folder ID: ");
		
		Label lblProjectName = new Label(container, SWT.NONE);
		lblProjectName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblProjectName.setAlignment(SWT.RIGHT);
		lblProjectName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblProjectName.setText(folder.getPath().isEmpty() ? "" : (folder.getPath() + "."));
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	public Text getFolderName() {
		return text;
	}
}
