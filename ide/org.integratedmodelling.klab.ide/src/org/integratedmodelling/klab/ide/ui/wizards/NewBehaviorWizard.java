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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.integratedmodelling.klab.rest.ProjectModificationRequest;
import org.integratedmodelling.klab.utils.StringUtil;

public class NewBehaviorWizard extends Wizard {

	private NewBehavior page;
	private IKimProject target = null;

	public NewBehaviorWizard(IKimProject target) {
		setWindowTitle("Create a new k.LAB actor behavior");
		this.target = target;
	}

	@Override
	public void addPages() {
		addPage(this.page = new NewBehavior(target));
	}

	@Override
	public boolean performFinish() {

		final String nspc = page.getNamespace().getText().trim();

		if (validate(nspc, target)) {

			ProjectModificationRequest request = new ProjectModificationRequest(page.getTargetProject().getText(),
					nspc);

			if (page.getIsLibrary().getSelection()) {
				Map<String, String> options = new HashMap<>();
				options.put(ProjectModificationRequest.LIBRARY_OPTION, "true");
				request.setParameters(options);
			}

			Activator.post((message) -> {
				File file = message.getPayload(ProjectModificationNotification.class).getFile();
				Activator.loader().add(file);
				Display.getDefault().asyncExec(() -> {
					IFile ifile = Eclipse.INSTANCE.getIFile(file);
					try {
						ifile.getParent().refreshLocal(IFolder.DEPTH_INFINITE, null);
						Eclipse.INSTANCE.openFile(ifile, 0);
						KlabNavigator.refresh();
					} catch (CoreException e) {
					}
				});
			}, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.CreateBehavior, request);
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
			for (IKActorsBehavior n : p.getBehaviors()) {
				if (n.getName().equals(nspc)) {
					this.page.setErrorMessage("Behavior " + nspc + " already exists in project " + p.getName() + ".");
					return false;
				}
			}
		}

		if (StringUtil.containsAny(nspc, StringUtil.UPPERCASE | StringUtil.WHITESPACE | StringUtil.NONLETTERS)) {
			page.setErrorMessage("Namespace identifiers must contain only lowercase letters with no whitespace.");
			return false;
		}

		for (String element : nspc.split("\\.")) {
			if (Kim.INSTANCE.getKimKeywords().contains(element)) {
				page.setErrorMessage(
						"'" + element + "' is a k.IM keyword and cannot be used as a namespace component.");
				return false;
			}
		}

		return true;
	}

	public IKimProject getProject() {
		return target;
	}

}
