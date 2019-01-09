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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;

public class NewResourceWizard extends Wizard implements INewWizard {

	NewResource page;
	EResourceFolder folder;

	public NewResourceWizard(EResourceFolder folder) {
		setWindowTitle("New k.LAB resource");
		this.folder = folder;
	}

	@Override
	public void addPages() {
		addPage(page = new NewResource());
	}

	@Override
	public boolean performFinish() {

		if (validate()) {
			return true;
		}

		return false;
	}

	private boolean validate() {
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

}
