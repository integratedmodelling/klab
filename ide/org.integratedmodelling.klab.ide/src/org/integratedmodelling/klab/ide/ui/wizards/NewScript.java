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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.kim.api.IKimNamespace.Role;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.Activator;
import org.eclipse.swt.widgets.Button;

public class NewScript extends WizardPage {
    private Combo   combo;
    private IKimProject targetProject;
    private Text scriptName;
    private Button btnKimContext;
    private Button btnKactorsTest;
	private Role role;
    
    public NewScript(IKimProject targetProject, Role role) {
        super("wizardPage");
        this.targetProject = targetProject;
        this.role = role;
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.jpg"));
        setTitle("New k.LAB " + (role == Role.SCRIPT ? "script" : "test case"));
        setDescription("Create a new " + (role == Role.SCRIPT ? "script" : "test case"));
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);

        setControl(container);
        container.setLayout(new FormLayout());

        Label lblNewLabel = new Label(container, SWT.NONE);
        FormData fd_lblNewLabel = new FormData();
        fd_lblNewLabel.top = new FormAttachment(0, 38);
        lblNewLabel.setLayoutData(fd_lblNewLabel);
        lblNewLabel.setText("Target project");

        combo = new Combo(container, SWT.READ_ONLY);
        FormData fd_combo = new FormData();
        fd_combo.top = new FormAttachment(lblNewLabel, -3, SWT.TOP);
        fd_combo.left = new FormAttachment(lblNewLabel, 18);
        combo.setLayoutData(fd_combo);

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
        
        Label lblScriptName = new Label(container, SWT.NONE);
        fd_lblNewLabel.right = new FormAttachment(lblScriptName, 0, SWT.RIGHT);
        lblScriptName.setToolTipText("The script name is the name of the script or test as set in the correspondent annotation. It should be a hierarchical path name identifying the test suite and topic.");
        lblScriptName.setText("Script name");
        lblScriptName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
        FormData fd_lblScriptName = new FormData();
        fd_lblScriptName.top = new FormAttachment(lblNewLabel, 30);
        fd_lblScriptName.left = new FormAttachment(0, 57);
        lblScriptName.setLayoutData(fd_lblScriptName);
        
        scriptName = new Text(container, SWT.BORDER);
        fd_combo.right = new FormAttachment(scriptName, 0, SWT.RIGHT);
        FormData fd_scriptName = new FormData();
        fd_scriptName.right = new FormAttachment(100, -83);
        fd_scriptName.left = new FormAttachment(lblScriptName, 18);
        fd_scriptName.top = new FormAttachment(lblScriptName, -3, SWT.TOP);
        scriptName.setLayoutData(fd_scriptName);
        
        btnKimContext = new Button(container, SWT.RADIO);
        btnKimContext.setSelection(this.role == Role.TESTCASE);
        FormData fd_btnKimContext = new FormData();
        fd_btnKimContext.top = new FormAttachment(scriptName, 29);
        fd_btnKimContext.left = new FormAttachment(scriptName, 0, SWT.LEFT);
        btnKimContext.setLayoutData(fd_btnKimContext);
        btnKimContext.setText("k.IM context");
        
        btnKactorsTest = new Button(container, SWT.RADIO);
        btnKactorsTest.setSelection(this.role == Role.SCRIPT);
        FormData fd_btnKactorsTest = new FormData();
        fd_btnKactorsTest.top = new FormAttachment(btnKimContext, 0, SWT.TOP);
        fd_btnKactorsTest.left = new FormAttachment(btnKimContext, 23);
        btnKactorsTest.setLayoutData(fd_btnKactorsTest);
        btnKactorsTest.setText("k.Actors");

    }

    public Combo getTargetProject() {
        return combo;
    }

    public boolean isActorsFile() {
    	return btnKactorsTest.getSelection();
    }
    
    public Text getScriptName() {
        return scriptName;
    }
}
