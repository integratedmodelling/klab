package org.integratedmodelling.klab.ide.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.custom.ScrolledComposite;

public class AuthorityEditor extends Composite {
	private Text text;

	public AuthorityEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.verticalSpacing = 1;
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		gl_composite.horizontalSpacing = 3;
		composite.setLayout(gl_composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setBounds(0, 0, 32, 32);
		
		Combo combo_1 = new Combo(composite, SWT.READ_ONLY);
		combo_1.setBounds(0, 0, 57, 20);
		
		Combo combo = new Combo(composite, SWT.READ_ONLY);
		combo.setEnabled(false);
		combo.setBounds(0, 0, 57, 20);
		
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setBounds(0, 0, 41, 19);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setToolTipText("Copy the currently selected identity code to the clipboard");
		btnNewButton.setImage(ResourceManager.getPluginImage("org.eclipse.ui", "/icons/full/etool16/copy_edit.png"));
		btnNewButton.setBounds(0, 0, 70, 21);
		
		ScrolledComposite composite_1 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		composite_1.setExpandVertical(true);
		composite_1.setExpandHorizontal(true);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_1.setBounds(0, 0, 32, 32);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNewLabel.setBounds(0, 238, 56, 16);
		lblNewLabel.setText("Choose an authority from the list");
	}
}
