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

import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.views.ResourcesView;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.ResourcePublishRequest;
import org.integratedmodelling.klab.rest.ResourceReference;

public class PublishResourceWizard extends Wizard {

	private PublishResource page;
	private ResourceReference target = null;
	private List<NodeReference> nodes;

	public PublishResourceWizard(ResourceReference target, List<NodeReference> nodes) {
		setWindowTitle("Create a new k.LAB Namespace");
		this.nodes = nodes;
		this.target = target;
	}

	@Override
	public void addPages() {
		addPage(this.page = new PublishResource(target, nodes));
	}

	@Override
	public boolean performFinish() {

		if (validate(target)) {

			ResourcePublishRequest request = new ResourcePublishRequest();

			request.setUrn(target.getUrn());
			request.setNode(page.getTargetNode());
			request.setSuggestedName(page.getSuggestedName());
			request.setSuggestedCatalog(page.getSuggestedCatalog());
			request.setSuggestedNamespace(page.getSuggestedNamespace());
			request.setPermissions(page.getPermissions());

			/*
			 * open a ticket
			 */
			Activator.session().getTicketManager().open(ITicket.Type.ResourceSubmission, "resource", target.getUrn());

			ResourcesView view = null;
			try {
				view = (ResourcesView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ResourcesView.ID);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				//what should we do in this case?
				e.printStackTrace();
			}
			//this also needs to do something more
			view.showPending();
			Activator.post(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.PublishLocalResource, request);
		}

		return false;
	}

	private boolean validate(ResourceReference resource) {

		// TODO basic validation of contents and metadata; engine will do the rest

		return true;
	}

}


