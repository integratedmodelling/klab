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
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.ide.Activator;

public class NewProject extends WizardPage {
	private Text text;

	/**
	 * Create the wizard.
	 */
	public NewProject() {
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.png"));
		setTitle("New k.LAB project");
		setDescription("Create a new local k.LAB project");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	@Override
    public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new FormLayout());
		
		text = new Text(container, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0, 37);
		fd_text.right = new FormAttachment(100, -83);
		text.setLayoutData(fd_text);
		
		Label lblProjectName = new Label(container, SWT.NONE);
		fd_text.left = new FormAttachment(lblProjectName, 6);
		lblProjectName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		FormData fd_lblProjectName = new FormData();
		fd_lblProjectName.bottom = new FormAttachment(100, -44);
		fd_lblProjectName.top = new FormAttachment(0, 36);
		fd_lblProjectName.left = new FormAttachment(0, 61);
		fd_lblProjectName.right = new FormAttachment(100, -323);
		lblProjectName.setLayoutData(fd_lblProjectName);
		lblProjectName.setText("Project name");
	}
	public Text getProjectName() {
		return text;
	}

}
