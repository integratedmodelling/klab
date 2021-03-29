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

import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;

public class ExportResource extends WizardPage {
	private Combo combo;
	private IKimProject targetProject;
	private EResourceReference resource;
	private Text text;
	private Map<String, String> formats;

	public ExportResource(IKimProject targetProject, EResourceReference resource) {
		super("wizardPage");
		this.targetProject = targetProject;
		this.resource = resource;
		this.formats = resource.getExportFormats();
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.png"));
		setTitle("Export k.LAB resource");
		setDescription("Export a k.LAB resource to an external file");
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
		lblNewLabel.setText("Export format");

		combo = new Combo(container, SWT.READ_ONLY);
		FormData fd_combo = new FormData();
		fd_combo.right = new FormAttachment(100, -84);
		fd_combo.left = new FormAttachment(lblNewLabel, 29);
		fd_combo.top = new FormAttachment(lblNewLabel, -3, SWT.TOP);
		combo.setLayoutData(fd_combo);

		Label lblOutputFile = new Label(container, SWT.NONE);
		FormData fd_lblOutputFile = new FormData();
		fd_lblOutputFile.top = new FormAttachment(lblNewLabel, 22);
		fd_lblOutputFile.left = new FormAttachment(0, 63);
		lblOutputFile.setLayoutData(fd_lblOutputFile);
		lblOutputFile.setText("Output file");

		text = new Text(container, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(lblOutputFile, -3, SWT.TOP);
		fd_text.right = new FormAttachment(lblOutputFile, 287, SWT.RIGHT);
		fd_text.left = new FormAttachment(combo, 0, SWT.LEFT);
		text.setLayoutData(fd_text);

		Button btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String format = getFormat();
				String extension = format;
				int pound = extension.indexOf('#');
				if (pound > 0) {
					extension = extension.substring(0, pound);
				}
				// open file chooser
				FileDialog fd = new FileDialog(Eclipse.INSTANCE.getShell(), SWT.SAVE);
				fd.setText("Choose file to save to");
				String[] filterExt = { "*." + extension };
				fd.setFilterExtensions(filterExt);
				text.setText(fd.open());
			}
		});
		FormData fd_btnBrowse = new FormData();
		fd_btnBrowse.left = new FormAttachment(lblOutputFile, 295);
		fd_btnBrowse.top = new FormAttachment(lblOutputFile, -5, SWT.TOP);
		btnBrowse.setLayoutData(fd_btnBrowse);
		btnBrowse.setText("Browse");

		for (String key : formats.keySet()) {
			combo.add(formats.get(key));
		}

		if (combo.getItemCount() > 0) {
			combo.select(0);
		}
	}

	public String getFormat() {
		for (String key : formats.keySet()) {
			if (formats.get(key).equals(combo.getText())) {
				return key;
			}
		}
		return null;
	}

	public String getOutputFile() {
		return text.getText() == null || text.getText().trim().isEmpty() ? null : text.getText().trim();
	}

	public EResourceReference getResource() {
		return resource;
	}
}
