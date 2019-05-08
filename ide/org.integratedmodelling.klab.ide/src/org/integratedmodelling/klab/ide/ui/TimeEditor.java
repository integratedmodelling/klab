package org.integratedmodelling.klab.ide.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Type;

public class TimeEditor extends Composite {

	private Text time_resolution_multiplier;
	private Text time_start;
	private Text time_end;
	private Text time_step;
	private Resolution.Type resolution = Resolution.Type.YEAR;
	private ITime.Type timeType = null;
	private Combo time_resolution;
	private Listener listener = null;
	
	/**
	 * Pass one to the constructor to be notified of any changes that result in 
	 * a valid geometry.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public interface Listener {
		void onValidModification(String geometrySpecs);
	}
	
	public TimeEditor(Composite parent, int style) {
		this(parent, style, null);
	}
	
	public TimeEditor(Composite parent, int style, Listener listener) {
		super(parent, style);
		this.listener = listener;
		setLayout(new GridLayout(1, false));
		Composite grpTime = new Composite(this, SWT.NONE);
		grpTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTime.setLayout(new GridLayout(3, false));

		Label lblNewLabel = new Label(grpTime, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Type");

		Combo time_type = new Combo(grpTime, SWT.READ_ONLY);
		time_type.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setEnablements(time_type.getText());
				modified();
			}
		});
		time_type.setItems(new String[] { "None", "Generic", "Specific", "Grid", "Real time" });
		time_type.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		time_type.select(0);

		Label label = new Label(grpTime, SWT.NONE);
		label.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));

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
		time_resolution_multiplier.setEnabled(false);
		GridData gd_time_resolution_multiplier = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_time_resolution_multiplier.widthHint = 24;
		time_resolution_multiplier.setLayoutData(gd_time_resolution_multiplier);
		time_resolution_multiplier.setText("1");

		time_resolution = new Combo(composite_7, SWT.READ_ONLY);
		time_resolution.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setResolution(time_resolution.getText().toUpperCase());
				modified();
			}
		});
		time_resolution.setEnabled(false);
		time_resolution.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		time_resolution.setItems(new String[] { "Millennium", "Century", "Decade", "Year", "Month", "Week", "Day",
				"Hour", "Minute", "Second", "Millisecond", "Nanosecond" });
		time_resolution.select(3);

		Label label_1 = new Label(grpTime, SWT.NONE);
		label_1.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
			}
		});
		label_1.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));

		Label lblNewLabel_2 = new Label(grpTime, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Start");

		Composite composite_4 = new Composite(grpTime, SWT.NONE);
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite_4 = new GridLayout(1, false);
		gl_composite_4.horizontalSpacing = 2;
		gl_composite_4.marginWidth = 0;
		gl_composite_4.marginHeight = 0;
		composite_4.setLayout(gl_composite_4);

		time_start = new Text(composite_4, SWT.BORDER);
		time_start.setEnabled(false);
		time_start.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label time_chooseStart = new Label(grpTime, SWT.NONE);
		time_chooseStart.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));

		Label lblNewLabel_3 = new Label(grpTime, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("End");

		Composite composite_5 = new Composite(grpTime, SWT.NONE);
		composite_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite_5 = new GridLayout(1, false);
		gl_composite_5.marginWidth = 0;
		gl_composite_5.marginHeight = 0;
		gl_composite_5.horizontalSpacing = 2;
		composite_5.setLayout(gl_composite_5);

		time_end = new Text(composite_5, SWT.BORDER);
		time_end.setEnabled(false);
		time_end.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label time_chooseEnd = new Label(grpTime, SWT.NONE);
		time_chooseEnd.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));

		Label lblNewLabel_4 = new Label(grpTime, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("Step");

		Composite composite_6 = new Composite(grpTime, SWT.NONE);
		composite_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite_6 = new GridLayout(1, false);
		gl_composite_6.marginWidth = 0;
		gl_composite_6.marginHeight = 0;
		gl_composite_6.horizontalSpacing = 2;
		composite_6.setLayout(gl_composite_6);

		time_step = new Text(composite_6, SWT.BORDER);
		time_step.setEnabled(false);
		time_step.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label time_chooseStep = new Label(grpTime, SWT.NONE);
		time_chooseStep.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));
	}

	protected void modified() {
		if (listener != null) {
			String geometry = getGeometry();
			if (geometry != null) {
				listener.onValidModification(geometry);
			}
		}
	}

	protected void setResolution(String res) {

		resolution = Resolution.Type.valueOf(res.toUpperCase());
		
		// TODO constrain the unit for the step 
		
		switch (resolution) {
		case CENTURY:
			time_start.setMessage("III; 1000BC; 1990; ....");
			time_end.setMessage("IV; 2000AD; 1992; ...");
			break;
		case DAY:
			time_start.setMessage("29-1-2010; 29-1-2010 12:33; ...");
			time_end.setMessage("29-1-2012; 29-1-2012 12:33");
			break;
		case DECADE:
			time_start.setMessage("1990; 10-1990; 21-10-1990; 21-10-1990 12:23; ...");
			time_end.setMessage("1990; 10-1990; 21-10-1990; 21-10-1990 12:23; ...");
			break;
		case HOUR:
			time_start.setMessage("29-1-2010 12");
			time_end.setMessage("29-1-2012 12");
			break;
		case MILLENNIUM:
			time_start.setMessage("-1; 1000BC; 1990; ....");
			time_end.setMessage("1; 2000AD; 1992; ...");
			break;
		case MILLISECOND:
			time_start.setMessage("29-1-2010 12:33:25.000");
			time_end.setMessage("29-1-2012 12:33:25.010");
			break;
		case MINUTE:
			time_start.setMessage("e.g. 29-1-2010 12:33; ...");
			time_end.setMessage("e.g. 29-1-2012 12:33; ...");
			break;
		case MONTH:
			time_start.setMessage("10-1990; 21-10-1990; 21-10-1990 12:23; ...");
			time_end.setMessage("10-1992; 21-10-1992; 21-10-1992 12:23; ...");
			break;
		case NANOSECOND:
			time_start.setMessage("29-1-2010 12:33:25.859903");
			time_end.setMessage("29-1-2012 12:33:25.334432");
			break;
		case SECOND:
			time_start.setMessage("e.g. 29-1-2010 12:33:25; ...");
			time_end.setMessage("e.g. 29-1-2012 12:33:25; ...");
			break;
//		case WEEK:
//			break;
		case YEAR:
			time_start.setMessage("1990; 10-1990; 21-10-1990; 21-10-1990 12:23; ...");
			time_end.setMessage("1990; 10-1990; 21-10-1990; 21-10-1990 12:23; ...");
			break;
		default:
			break;
		}
	}

	protected void setEnablements(String text) {
		switch (text) {
		case "None":
			timeType = null;
			time_resolution.setEnabled(false);
			time_resolution_multiplier.setEnabled(false);
			time_start.setEnabled(false);
			time_end.setEnabled(false);
			time_step.setEnabled(false);
			break;
		case "Generic":
			timeType = Type.GENERIC;
			time_resolution.setEnabled(true);
			time_resolution_multiplier.setEnabled(true);
			time_start.setEnabled(false);
			time_end.setEnabled(false);
			time_step.setEnabled(false);
			break;
		case "Specific":
			timeType = Type.SPECIFIC;
			time_resolution.setEnabled(true);
			time_resolution_multiplier.setEnabled(true);
			time_start.setEnabled(true);
			time_end.setEnabled(true);
			time_step.setEnabled(false);
			break;
		case "Grid":
			timeType = Type.GRID;
		case "Real time":
			timeType = Type.REAL;
			time_resolution.setEnabled(true);
			time_resolution_multiplier.setEnabled(true);
			time_start.setEnabled(true);
			time_end.setEnabled(true);
			time_step.setEnabled(true);
			break;
		}
	}

	public String getGeometry() {

		String ret = null;
 
		// TODO
			switch (timeType) {
			case GENERIC:
				ret = "\u03C4";
				break;
			case GRID:
			case REAL:
				ret = "T";
				break;
			case SPECIFIC:
				ret = "t";
				break;
			default:
				break;
			}
			
			String resolution = "1";
			
			if (time_resolution.isEnabled()) {
				// compute step and n. of steps
			}

			return ret;
	}
	
	public void validate() {
	}

	public boolean isValid() {
		return getGeometry() != null;
	}
}
