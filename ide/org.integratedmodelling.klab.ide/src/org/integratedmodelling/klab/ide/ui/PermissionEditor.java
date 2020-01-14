package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.integratedmodelling.klab.api.auth.KlabPermissions;
import org.integratedmodelling.klab.utils.CollectionUtils;

public class PermissionEditor extends Composite {

	private Text allowedGroups;
	private Text allowedUsers;
	private Text excludedGroups;
	private Text excludedUsers;
	private Button publicButton;
	private Label permissionsLabel;
	private String permissions;

	public PermissionEditor(Composite parent, int style) {

		super(parent, style);
		setLayout(new GridLayout(1, false));

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		publicButton = new Button(composite, SWT.CHECK);
		publicButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});
		publicButton.setText("Public");

		permissionsLabel = new Label(composite, SWT.RIGHT);
		permissionsLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		permissionsLabel.setText("Only owner has access");

		Group grpAllowed = new Group(this, SWT.NONE);
		grpAllowed.setLayout(new GridLayout(2, false));
		grpAllowed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpAllowed.setText("Allowed");

		Label lblGroups = new Label(grpAllowed, SWT.NONE);
		lblGroups.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGroups.setText("Groups");

		allowedGroups = new Text(grpAllowed, SWT.BORDER | SWT.WRAP);
		allowedGroups.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				refresh();
			}
		});
		allowedGroups.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblUsers = new Label(grpAllowed, SWT.NONE);
		lblUsers.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsers.setText("Users");

		allowedUsers = new Text(grpAllowed, SWT.BORDER | SWT.WRAP);
		allowedUsers.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				refresh();
			}
		});
		allowedUsers.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group grpExcluded = new Group(this, SWT.NONE);
		grpExcluded.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpExcluded.setText("Excluded");
		grpExcluded.setLayout(new GridLayout(2, false));

		Label label = new Label(grpExcluded, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("Groups");

		excludedGroups = new Text(grpExcluded, SWT.BORDER | SWT.WRAP);
		excludedGroups.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				refresh();
			}
		});
		excludedGroups.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label label_1 = new Label(grpExcluded, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("Users");

		excludedUsers = new Text(grpExcluded, SWT.BORDER | SWT.WRAP);
		excludedUsers.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				refresh();
			}
		});
		excludedUsers.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	protected void refresh() {
		KlabPermissions perm = KlabPermissions.empty();
		if (publicButton.getSelection()) {
			perm.setPublic(true);
		} else {
			for (String g : tokenize(allowedGroups.getText().trim())) {
				perm.getAllowedGroups().add(g);
			}
			for (String g : tokenize(allowedUsers.getText().trim())) {
				perm.getAllowedUsers().add(g);
			}
		}
		for (String g : tokenize(excludedGroups.getText().trim())) {
			perm.getExcludedGroups().add(g);
		}
		for (String g : tokenize(excludedUsers.getText().trim())) {
			perm.getExcludedUsers().add(g);
		}
		this.permissions = perm.toString();
		this.permissionsLabel.setText(this.permissions.isEmpty() ? "Only owner has access" : "");
	}

	private List<String> tokenize(String string) {
		List<String> ret = new ArrayList<>();
		string = string.replace(',', ' ');
		for (String s : string.split("\\s+")) {
			if (!s.isEmpty()) {
				ret.add(s);
			}
		}
		return ret;
	}

	public void setPermissions(String permissions) {
		KlabPermissions perm = KlabPermissions.create(permissions);
		Display.getDefault().asyncExec(() -> {
			publicButton.setSelection(perm.isPublic());
			allowedGroups.setText(join(perm.getAllowedGroups()));
			allowedUsers.setText(join(perm.getAllowedUsers()));
			excludedGroups.setText(join(perm.getExcludedGroups()));
			excludedGroups.setText(join(perm.getExcludedGroups()));
			this.permissions = perm.toString();
			this.permissionsLabel.setText(this.permissions.isEmpty() ? "Only owner has access" : "");
		});
	}

	private String join(Set<String> strings) {
		if (strings.size() == 0) {
			return "";
		}
		String ret = "";
		for (String s : strings) {
			ret += (ret.isEmpty() ? "" : ", ") + s;
		}
		return ret;
	}

	public String getPermissions() {
		return this.permissions;
	}
}
