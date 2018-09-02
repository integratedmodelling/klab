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

import java.util.Collections;

import org.eclipse.jface.wizard.Wizard;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigatorActions;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;

public class MoveResourceWizard extends Wizard {

    private MoveResource page;
    private EProject     target = null;
    private EResource    resource;

    public MoveResourceWizard(EResource resource) {
        setWindowTitle("Move resource");
        this.resource = resource;
    }

    @Override
    public void addPages() {
        addPage(this.page = new MoveResource(resource.getEParent(EProject.class).getProject(), resource
                .getResource()));
    }

    @Override
    public boolean performFinish() {

        final String nspc = page.getTargetProject();
        if (validate(nspc)) {
            KlabNavigatorActions
                    .copyResource(Collections.singleton(resource), target.getResourceFolder(), true);
            return true;
        }

        return false;
    }

    private boolean validate(String nspc) {

        target = new EProject(Kim.INSTANCE.getProject(nspc), null);
        if (target == null) {
            return false;
        }
        return true;
    }

}
