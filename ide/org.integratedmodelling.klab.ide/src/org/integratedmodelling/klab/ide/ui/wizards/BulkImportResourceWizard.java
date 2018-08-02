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
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.wizard.Wizard;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.rest.ResourceImportRequest;

public class BulkImportResourceWizard extends Wizard {

	private BulkImportResource page;
	private IKimProject targetProject = null;
	private EResourceFolder target = null;

	public BulkImportResourceWizard(EResourceFolder target) {
		setWindowTitle("Bulk import resources");
		this.targetProject = target.getEParent(EProject.class).getProject();
	}

	@Override
	public void addPages() {
		addPage(this.page = new BulkImportResource(target));
	}

	@Override
	public boolean performFinish() {

		final String adapter = page.getAdapter();
		String url = page.getChoice();

		if (url.trim().isEmpty()) {
			return false;
		}
		if (!url.startsWith("http")) {
			File file = new File(url);
			try {
				url = file.isDirectory() ? file.toURI().toURL().toString() : null;
			} catch (MalformedURLException e) {
				url = null;
			}
		}

		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
		}

		if (validate(u)) {

			ResourceImportRequest request = new ResourceImportRequest();
			request.setAdapter(adapter);
			request.setImportUrl(u);
			request.setBulkImport(true);
			request.setProjectName(targetProject.getName());

			Activator.post(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ImportResource, request);
			return true;
		}

		return false;
	}

	private boolean validate(URL u) {
		if (u == null) {
			page.setErrorMessage("invalid import URL or file: please check input");
			return false;
		}
		return true;
	}

	public IKimProject getProject() {
		return targetProject;
	}

}
