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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.ui.PermissionEditor;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.eclipse.swt.custom.CLabel;

public class PublishResource extends WizardPage {

	private Text text;
	private Combo combo;
	private ResourceReference resource;
	private List<NodeReference> nodes;
	private Combo catalogCombo;
	private Combo namespaceCombo;
	private PermissionEditor permissionsEditor;

	public PublishResource(ResourceReference resource, List<NodeReference> nodes) {
		super("wizardPage");
		this.resource = resource;
		this.nodes = nodes;
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.png"));
		setTitle("Publish resource");
		setDescription("Publish a local resource to a k.LAB node");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		GridLayout gl_container = new GridLayout(2, false);
		gl_container.marginTop = 12;
		container.setLayout(gl_container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setAlignment(SWT.RIGHT);
		lblNewLabel.setText("Host node");

		combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblNewLabel_1.setText("Suggested ID");

		/*
		 * Add open projects in namespace, preselecting the one we started with, if any.
		 */
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

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		this.text.setText(MiscUtilities.getFileBaseName(resource.getLocalName().toLowerCase()));

		Label lblCatalogoptional = new Label(container, SWT.NONE);
		lblCatalogoptional.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCatalogoptional.setAlignment(SWT.RIGHT);
		lblCatalogoptional.setText("Catalog (optional)");

		catalogCombo = new Combo(container, SWT.NONE);
		catalogCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		for (String catalog : nodes.get(0).getCatalogs()) {
			catalogCombo.add(catalog);
		}

		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Namespace (optional)");

		namespaceCombo = new Combo(container, SWT.NONE);
		namespaceCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		for (String namespace : nodes.get(0).getNamespaces()) {
			namespaceCombo.add(namespace);
		}

		combo.select(0);
		
		Group grpPermissions = new Group(container, SWT.NONE);
		grpPermissions.setText("Permissions");
		grpPermissions.setLayout(new GridLayout(1, false));
		grpPermissions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		permissionsEditor = new PermissionEditor(grpPermissions, SWT.NONE);
		permissionsEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		permissionsEditor.setPermissions(resource.getMetadata().get(IMetadata.IM_PERMISSIONS));
		
		CLabel lblNewLabel_3 = new CLabel(grpPermissions, SWT.NONE);
		lblNewLabel_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblNewLabel_3.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		lblNewLabel_3.setText("Resource ID, catalog and namespace define a permanent URN. Choose wisely,");
		
		CLabel lblNewLabel_3_1 = new CLabel(grpPermissions, SWT.NONE);
		lblNewLabel_3_1.setText("use sensible names and correct spelling.");
		lblNewLabel_3_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lblNewLabel_3_1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
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
	
	public String getPermissions() {
		return this.permissionsEditor.getPermissions();
	}
}
