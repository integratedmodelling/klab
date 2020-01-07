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
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.ResourceReference;

public class PublishResource extends WizardPage {

	private Text text;
	private Combo combo;
	private ResourceReference resource;
	private List<NodeReference> nodes;

	public PublishResource(ResourceReference resource, List<NodeReference> nodes) {
		super("wizardPage");
		this.resource = resource;
		this.nodes = nodes;
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.jpg"));
		setTitle("Publish resource");
		setDescription("Publish a local resource to a k.LAB node");
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
		lblNewLabel.setText("Host node");

		text = new Text(container, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(100, -84);
		text.setLayoutData(fd_text);

		combo = new Combo(container, SWT.READ_ONLY);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(lblNewLabel, -1, SWT.TOP);
		fd_combo.left = new FormAttachment(text, 0, SWT.LEFT);
		fd_combo.right = new FormAttachment(100, -67);
		combo.setLayoutData(fd_combo);

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		fd_text.left = new FormAttachment(lblNewLabel_1, 30);
		fd_text.top = new FormAttachment(lblNewLabel_1, -3, SWT.TOP);
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.top = new FormAttachment(lblNewLabel, 28);
		fd_lblNewLabel_1.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("Suggested ID");

		/*
		 * Add open projects in namespace, preselecting the one we started with, if any.
		 */
		this.text.setText(resource.getLocalName().toLowerCase());
		for (NodeReference node : nodes) {
			combo.add(node.getId());
		}
		combo.select(0);

	}

	public NodeReference getTargetNode() {
		return this.nodes.get(combo.getSelectionIndex());
	}
	
	public String getSuggestedName() {
		return this.text.getText();
	}
}
