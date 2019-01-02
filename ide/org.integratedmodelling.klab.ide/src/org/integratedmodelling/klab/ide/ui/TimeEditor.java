package org.integratedmodelling.klab.ide.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;

public class TimeEditor extends Composite {

	private Text time_resolution_multiplier;
	private Text time_start;
	private Text time_end;
	private Text time_step;

	public TimeEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		Composite grpTime = new Composite(this, SWT.NONE);
		grpTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTime.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(grpTime, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Type");
		
		Combo time_type = new Combo(grpTime, SWT.READ_ONLY);
		time_type.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		time_type.setItems(new String[] {"Generic", "Specific", "Grid", "Real time"});
		time_type.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		time_type.select(1);
		
		Label lblNewLabel_1 = new Label(grpTime, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Scope");
		
		Composite composite_7 = new Composite(grpTime, SWT.NONE);
		GridLayout gl_composite_7 = new GridLayout(2, false);
		gl_composite_7.horizontalSpacing = 2;
		gl_composite_7.marginWidth = 0;
		gl_composite_7.marginHeight = 0;
		composite_7.setLayout(gl_composite_7);
		composite_7.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		time_resolution_multiplier = new Text(composite_7, SWT.BORDER);
		GridData gd_time_resolution_multiplier = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_time_resolution_multiplier.widthHint = 24;
		time_resolution_multiplier.setLayoutData(gd_time_resolution_multiplier);
		time_resolution_multiplier.setText("1");
		
		Combo time_resolution = new Combo(composite_7, SWT.READ_ONLY);
		time_resolution.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		time_resolution.setItems(new String[] {"MIllennium", "Century", "Decade", "Year", "Month", "Week", "Day", "Hour", "Minute", "Second", "Millisecond", "Nanosecond"});
		time_resolution.select(3);
		
		Label lblNewLabel_2 = new Label(grpTime, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Start");
		
		Composite composite_4 = new Composite(grpTime, SWT.NONE);
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite_4 = new GridLayout(2, false);
		gl_composite_4.horizontalSpacing = 2;
		gl_composite_4.marginWidth = 0;
		gl_composite_4.marginHeight = 0;
		composite_4.setLayout(gl_composite_4);
		
		time_start = new Text(composite_4, SWT.BORDER);
		time_start.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button time_chooseStart = new Button(composite_4, SWT.NONE);
		GridData gd_time_chooseStart = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_time_chooseStart.widthHint = 48;
		time_chooseStart.setLayoutData(gd_time_chooseStart);
		time_chooseStart.setText("Choose");
		
		Label lblNewLabel_3 = new Label(grpTime, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("End");
		
		Composite composite_5 = new Composite(grpTime, SWT.NONE);
		composite_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite_5 = new GridLayout(2, false);
		gl_composite_5.marginWidth = 0;
		gl_composite_5.marginHeight = 0;
		gl_composite_5.horizontalSpacing = 2;
		composite_5.setLayout(gl_composite_5);
		
		time_end = new Text(composite_5, SWT.BORDER);
		time_end.setEnabled(false);
		time_end.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button time_chooseEnd = new Button(composite_5, SWT.NONE);
		GridData gd_time_chooseEnd = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_time_chooseEnd.widthHint = 48;
		time_chooseEnd.setLayoutData(gd_time_chooseEnd);
		time_chooseEnd.setEnabled(false);
		time_chooseEnd.setText("Choose");
		
		Label lblNewLabel_4 = new Label(grpTime, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("Step");
		
		Composite composite_6 = new Composite(grpTime, SWT.NONE);
		composite_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite_6 = new GridLayout(2, false);
		gl_composite_6.marginWidth = 0;
		gl_composite_6.marginHeight = 0;
		gl_composite_6.horizontalSpacing = 2;
		composite_6.setLayout(gl_composite_6);
		
		time_step = new Text(composite_6, SWT.BORDER);
		time_step.setEnabled(false);
		time_step.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button time_chooseStep = new Button(composite_6, SWT.NONE);
		GridData gd_time_chooseStep = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_time_chooseStep.widthHint = 48;
		time_chooseStep.setLayoutData(gd_time_chooseStep);
		time_chooseStep.setEnabled(false);
		time_chooseStep.setText("Choose");
	}

}
