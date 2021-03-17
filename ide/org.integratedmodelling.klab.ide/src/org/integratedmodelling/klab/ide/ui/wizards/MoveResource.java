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
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;

public class MoveResource extends WizardPage {
    private Combo   combo;
    private IKimProject targetProject;
    private EResourceReference resource;

    public MoveResource(IKimProject targetProject, EResourceReference resource) {
        super("wizardPage");
        this.targetProject = targetProject;
        this.resource = resource;
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.png"));
        setTitle("Move k.LAB resource");
        setDescription("Move a k.LAB resource from a project to another");
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

        combo = new Combo(container, SWT.READ_ONLY);
        FormData fd_combo = new FormData();
        fd_combo.right = new FormAttachment(100, -84);
        fd_combo.left = new FormAttachment(lblNewLabel, 29);
        fd_combo.top = new FormAttachment(lblNewLabel, -3, SWT.TOP);
        combo.setLayoutData(fd_combo);

        /*
         * Add open projects in namespace, preselecting the one we started with, if any.
         */
        for (IKimProject project : Kim.INSTANCE.getProjects()) {
            if (!project.getName().equals(targetProject.getName())) {
                combo.add(project.getName());
            }
        }
        if (combo.getItemCount() > 0) {
            combo.select(0);
        }
    }

    public String getTargetProject() {
        return combo.getText();
    }

    public EResourceReference getResource() {
        return resource;
    }

}
