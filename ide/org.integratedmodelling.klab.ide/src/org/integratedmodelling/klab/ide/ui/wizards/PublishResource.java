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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	private Combo catalogCombo;
	private Combo namespaceCombo;

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
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Host node");

		text = new Text(container, SWT.BORDER);
		FormData fd_text = new FormData();
		text.setLayoutData(fd_text);

		combo = new Combo(container, SWT.READ_ONLY);
		fd_text.right = new FormAttachment(combo, 0, SWT.RIGHT);
		fd_text.top = new FormAttachment(combo, 22);
		fd_lblNewLabel.top = new FormAttachment(combo, 1, SWT.TOP);
		FormData fd_combo = new FormData();
		fd_combo.right = new FormAttachment(100, -67);
		fd_combo.left = new FormAttachment(lblNewLabel, 12);
		fd_combo.top = new FormAttachment(0, 37);
		combo.setLayoutData(fd_combo);

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		fd_lblNewLabel.right = new FormAttachment(lblNewLabel_1, 0, SWT.RIGHT);
		fd_text.left = new FormAttachment(0, 159);
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.top = new FormAttachment(lblNewLabel, 27);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("Suggested ID");

		/*
		 * Add open projects in namespace, preselecting the one we started with, if any.
		 */
		this.text.setText(resource.getLocalName().toLowerCase());
		for (NodeReference node : nodes) {
			combo.add(node.getId());
		}
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				NodeReference node = nodes.get(combo.getSelectionIndex());
				namespaceCombo.removeAll();
				for (String namespace : node.getNamespaces()) {
					namespaceCombo.add(namespace);
				}
				catalogCombo.removeAll();
				for (String catalog : node.getCatalogs()) {
					catalogCombo.add(catalog);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		combo.select(0);
		
		catalogCombo = new Combo(container, SWT.NONE);
		FormData fd_catalogCombo = new FormData();
		fd_catalogCombo.top = new FormAttachment(text, 30);
		fd_catalogCombo.right = new FormAttachment(100, -66);
		catalogCombo.setLayoutData(fd_catalogCombo);
		
		Label lblCatalogoptional = new Label(container, SWT.NONE);
		fd_lblNewLabel_1.right = new FormAttachment(lblCatalogoptional, 0, SWT.RIGHT);
		fd_catalogCombo.left = new FormAttachment(lblCatalogoptional, 12);
		lblCatalogoptional.setAlignment(SWT.RIGHT);
		FormData fd_lblCatalogoptional = new FormData();
		fd_lblCatalogoptional.top = new FormAttachment(catalogCombo, 1, SWT.TOP);
		lblCatalogoptional.setLayoutData(fd_lblCatalogoptional);
		lblCatalogoptional.setText("Catalog (optional)");
		
		namespaceCombo = new Combo(container, SWT.NONE);
		FormData fd_namespaceCombo = new FormData();
		fd_namespaceCombo.right = new FormAttachment(100, -67);
		fd_namespaceCombo.top = new FormAttachment(catalogCombo, 27);
		fd_namespaceCombo.left = new FormAttachment(0, 159);
		namespaceCombo.setLayoutData(fd_namespaceCombo);
		
		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		fd_lblCatalogoptional.left = new FormAttachment(0, 39);
		fd_lblCatalogoptional.right = new FormAttachment(lblNewLabel_2, 0, SWT.RIGHT);
		FormData fd_lblNewLabel_2 = new FormData();
		fd_lblNewLabel_2.left = new FormAttachment(0, 28);
		fd_lblNewLabel_2.right = new FormAttachment(100, -430);
		fd_lblNewLabel_2.top = new FormAttachment(namespaceCombo, 1, SWT.TOP);
		lblNewLabel_2.setLayoutData(fd_lblNewLabel_2);
		lblNewLabel_2.setText("Namespace (optional)");

	}

	public NodeReference getTargetNode() {
		return this.nodes.get(combo.getSelectionIndex());
	}
	
	public String getSuggestedName() {
		return this.text.getText();
	}
	
	public String getSuggestedNamespace() {
		return this.namespaceCombo.getText();
	}

	public String getSuggestedCatalog() {
		return this.catalogCombo.getText();
	}


}
