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
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.utils.StringUtils;
import org.integratedmodelling.klab.rest.ProjectModificationRequest;

public class NewProjectWizard extends Wizard implements INewWizard {

	private NewProject page;

	public NewProjectWizard() {
		setWindowTitle("New Thinklab project");
	}

	@Override
	public void addPages() {
		addPage(page = new NewProject());
	}

	@Override
	public boolean performFinish() {

		final String name = page.getProjectName().getText();
		final String nspc = page.getNamespace().getText();

		if (validate(name, nspc)) {
			Activator.post(IMessage.MessageClass.ProjectLifecycle, IMessage.Type.CreateProject,
					new ProjectModificationRequest(name, nspc));
			return true;
		}

		return false;
	}

	private boolean validate(String src, String nspc) {

		// TODO do something sensible
		if (src.isEmpty())
			return false;
		if (nspc.isEmpty())
			return false;

		if (StringUtils.containsAny(src, StringUtils.UPPERCASE | StringUtils.WHITESPACE | StringUtils.NONLETTERS)) {
			page.setErrorMessage("project names must contain only lowercase letters with no whitespace");
			return false;
		}

		if (StringUtils.containsAny(src, StringUtils.UPPERCASE | StringUtils.WHITESPACE | StringUtils.NONLETTERS)) {
			page.setErrorMessage("namespace identifiers must contain only lowercase letters with no whitespace");
			return false;
		}

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

}
