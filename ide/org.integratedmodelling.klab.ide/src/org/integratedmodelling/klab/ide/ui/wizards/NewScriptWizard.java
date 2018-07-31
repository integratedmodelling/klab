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

import java.io.File;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimNamespace.Role;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.ide.utils.StringUtils;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.integratedmodelling.klab.rest.ProjectModificationRequest;

public class NewScriptWizard extends Wizard {

	private NewScript page;
	private IKimProject target = null;
	private Role role;
	
	public NewScriptWizard(IKimProject target, Role role) {
		setWindowTitle("Create a new k.LAB Namespace");
		this.target = target;
		this.role = role;
	}

	@Override
	public void addPages() {
		addPage(this.page = new NewScript(target, role));
	}

	@Override
	public boolean performFinish() {

		final String nspc = page.getNamespace().getText().trim();

		if (validate(nspc, target)) {
			Activator.post((message) -> {
				File file = message.getPayload(ProjectModificationNotification.class).getFile();
				Activator.loader().add(file);
				Display.getDefault().asyncExec(() -> {
				    Eclipse.INSTANCE.openFile(
						Eclipse.INSTANCE.getIFile(file),
						0);
				    KlabNavigator.refresh();
				});
			}, IMessage.MessageClass.ProjectLifecycle,
					role == Role.SCRIPT ? IMessage.Type.CreateScript : IMessage.Type.CreateTestCase,
					new ProjectModificationRequest(page.getTargetProject().getText(), nspc));
			return true;
		}

		return false;
	}

	private boolean validate(String nspc, IKimProject project) {

		if (project == null) {
			return false;
		}

		/*
		 * 1. check that namespace is not already there; set error on page if so 2.
		 * check that source folder is valid (later - we only list valid ones)
		 */

		for (IKimProject p : Kim.INSTANCE.getProjects()) {
			for (IKimNamespace n : p.getNamespaces()) {
				if (n.getName().equals(nspc)) {
					this.page.setErrorMessage("Namespace " + nspc + " already exists in project " + p.getName());
					return false;
				}
			}
		}

		if (StringUtils.containsAny(nspc, StringUtils.UPPERCASE | StringUtils.WHITESPACE | StringUtils.NONLETTERS)) {
			page.setErrorMessage("namespace identifiers must contain only lowercase letters with no whitespace");
			return false;
		}

		return true;
	}

	public IKimProject getProject() {
		return target;
	}

}
