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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.Activator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class NewBehavior extends WizardPage {

	private Text    text;
    private Combo   combo;
    private Button  btnCreateALibrary;
    private IKimProject targetProject;

    public NewBehavior(IKimProject targetProject) {
        super("wizardPage");
        this.targetProject = targetProject;
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.jpg"));
        setTitle("New k.LAB behavior");
        setDescription("Create a new behavior for observed actors");
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);

        setControl(container);
        container.setLayout(new FormLayout());

        Label lblNewLabel = new Label(container, SWT.NONE);
        FormData fd_lblNewLabel = new FormData();
        fd_lblNewLabel.left = new FormAttachment(0, 57);
        fd_lblNewLabel.top = new FormAttachment(0, 38);
        lblNewLabel.setLayoutData(fd_lblNewLabel);
        lblNewLabel.setText("Target project");

        text = new Text(container, SWT.BORDER);
        FormData fd_text = new FormData();
        fd_text.left = new FormAttachment(0, 159);
        text.setLayoutData(fd_text);

        combo = new Combo(container, SWT.READ_ONLY);
        fd_text.right = new FormAttachment(combo, 0, SWT.RIGHT);
        FormData fd_combo = new FormData();
        fd_combo.right = new FormAttachment(100, -84);
        fd_combo.left = new FormAttachment(lblNewLabel, 29);
        fd_combo.top = new FormAttachment(lblNewLabel, -3, SWT.TOP);
        combo.setLayoutData(fd_combo);

        Label lblNewLabel_1 = new Label(container, SWT.NONE);
        fd_text.top = new FormAttachment(lblNewLabel_1, -3, SWT.TOP);
        lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
        FormData fd_lblNewLabel_1 = new FormData();
        fd_lblNewLabel_1.top = new FormAttachment(lblNewLabel, 28);
        fd_lblNewLabel_1.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
        lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
        lblNewLabel_1.setText("Behavior ID");

        /*
         * Add open projects in namespace, preselecting the one we started with, if any.
         */
        int selection = 0, i = 0;
        for (IKimProject project : Kim.INSTANCE.getProjects()) {
            if (/* project.isOpen() && !project.isRemote() */ true) {
                combo.add(project.getName());
                if (targetProject != null && project.getName().equals(targetProject.getName())) {
                    selection = i;
                }
                i++;
            }
        }
        combo.select(selection);

        btnCreateALibrary = new Button(container, SWT.CHECK);
        btnCreateALibrary.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        	}
        });
        FormData fd_btnCreateALibrary = new FormData();
        fd_btnCreateALibrary.top = new FormAttachment(text, 24);
        fd_btnCreateALibrary.left = new FormAttachment(text, 0, SWT.LEFT);
        btnCreateALibrary.setLayoutData(fd_btnCreateALibrary);
        btnCreateALibrary.setText("Create a trait library");

    }

    public Combo getTargetProject() {
        return combo;
    }

    public Text getNamespace() {
        return text;
    }

    public Button getCreateLibrary() {
        return btnCreateALibrary;
    }
    
    public Button getIsLibrary() {
    	return btnCreateALibrary;
    }
}
