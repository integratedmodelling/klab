package org.integratedmodelling.klab.ide.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Type;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeEditor extends Composite {
    
    private static Logger logger = LoggerFactory.getLogger(TimeEditor.class);

	private Text time_scope_multiplier;
	private Text time_start;
	private Text time_end;
	private Text time_step_multiplier;
	private Resolution.Type scopeResolution = Resolution.Type.YEAR;
	private Resolution.Type stepResolution = Resolution.Type.YEAR;
	private Resolution.Type coverageResolution = Resolution.Type.YEAR;
	private ITime.Type timeType = null;
	private Combo time_scope;
	private Listener listener = null;
	private String error;
	private Composite scopeWidget;
	private Composite stepWidget;
	private Combo time_step;
	private CLabel message;
	private Combo time_type;
	private Text coverageEnd;
	private Text coverageStart;
	private Combo coverageUnit;

	// used for converting the longs in geometries to reparseable dates
	DateFormat dateFormat;

	/**
	 * Pass one to the constructor to be notified of any changes that result in a
	 * valid geometry.
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
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.listener = listener;
		setLayout(new GridLayout(1, false));
		Composite grpTime = new Composite(this, SWT.NONE);
		grpTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		grpTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_grpTime = new GridLayout(3, false);
		gl_grpTime.marginTop = 5;
		grpTime.setLayout(gl_grpTime);

		Label lblNewLabel = new Label(grpTime, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Type");

		time_type = new Combo(grpTime, SWT.READ_ONLY);
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

		scopeWidget = new Composite(grpTime, SWT.NONE);
		GridLayout gl_scopeWidget = new GridLayout(2, false);
		gl_scopeWidget.horizontalSpacing = 2;
		gl_scopeWidget.marginWidth = 0;
		gl_scopeWidget.marginHeight = 0;
		scopeWidget.setLayout(gl_scopeWidget);
		scopeWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		time_scope_multiplier = new Text(scopeWidget, SWT.BORDER);
		time_scope_multiplier.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				modified();
			}
		});
		time_scope_multiplier.setEnabled(false);
		GridData gd_time_scope_multiplier = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_time_scope_multiplier.widthHint = 36;
		time_scope_multiplier.setLayoutData(gd_time_scope_multiplier);
		time_scope_multiplier.setText("1");

		time_scope = new Combo(scopeWidget, SWT.READ_ONLY);
		time_scope.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scopeResolution = setResolution(time_scope.getText().toUpperCase());
				logger.debug("scopeResolution is " + scopeResolution);
				modified();
			}
		});
		time_scope.setEnabled(false);
		time_scope.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		time_scope.setItems(new String[] { "Millennium", "Century", "Decade", "Year", "Month", "Week", "Day", "Hour",
				"Minute", "Second", "Millisecond"/* , "Nanosecond" */ });
		time_scope.select(3);

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
		time_start.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				modified();
			}
		});
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
		time_end.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				modified();
			}
		});
		time_end.setEnabled(false);
		time_end.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label time_chooseEnd = new Label(grpTime, SWT.NONE);
		time_chooseEnd.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));

		Label lblCovers = new Label(grpTime, SWT.NONE);
		lblCovers.setText("Covers");

		Composite coverageWidget = new Composite(grpTime, SWT.NONE);
		coverageWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_coverageWidget = new GridLayout(4, false);
		gl_coverageWidget.marginWidth = 0;
		gl_coverageWidget.marginHeight = 0;
		gl_coverageWidget.horizontalSpacing = 2;
		coverageWidget.setLayout(gl_coverageWidget);

		coverageUnit = new Combo(coverageWidget, SWT.READ_ONLY);
		coverageUnit.setItems(new String[] { "Century", "Decade", "Year", "Month", "Week", "Day", "Hour", "Minute",
				"Second", "Millisecond", "Nanosecond" });
		coverageUnit.setEnabled(false);
		coverageUnit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		coverageUnit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				coverageResolution = Resolution.Type.valueOf(coverageUnit.getText().toUpperCase());
				modified();
			}
		});
		coverageUnit.select(4);

		coverageStart = new Text(coverageWidget, SWT.BORDER);
		coverageStart.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		coverageStart.setEnabled(false);
		coverageStart.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				modified();
			}
		});

		Label lblTo = new Label(coverageWidget, SWT.NONE);
		lblTo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTo.setText("To");

		coverageEnd = new Text(coverageWidget, SWT.BORDER);
		coverageEnd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		coverageEnd.setEnabled(false);
		coverageEnd.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				modified();
			}
		});

		Label time_chooseStep = new Label(grpTime, SWT.NONE);
		time_chooseStep.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));

		Label lblNewLabel_4 = new Label(grpTime, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("Step");

		stepWidget = new Composite(grpTime, SWT.NONE);
		stepWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_stepWidget = new GridLayout(2, false);
		gl_stepWidget.marginWidth = 0;
		gl_stepWidget.marginHeight = 0;
		gl_stepWidget.horizontalSpacing = 2;
		stepWidget.setLayout(gl_stepWidget);

		time_step_multiplier = new Text(stepWidget, SWT.BORDER);
		time_step_multiplier.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				modified();
			}
		});
		time_step_multiplier.setEnabled(false);
		GridData gd_time_step_multiplier = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_time_step_multiplier.widthHint = 36;
		time_step_multiplier.setLayoutData(gd_time_step_multiplier);

		time_step = new Combo(stepWidget, SWT.READ_ONLY);
		time_step.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stepResolution = setResolution(time_step.getText());
				modified();
			}
		});
		time_step.setItems(new String[] { "Millennium", "Century", "Decade", "Year", "Month", "Week", "Day", "Hour",
				"Minute", "Second", "Millisecond", "Nanosecond" });
		time_step.setEnabled(false);
		time_step.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		time_step.select(3);

		Label label_2 = new Label(grpTime, SWT.NONE);
		label_2.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));
		new Label(grpTime, SWT.NONE);

		message = new CLabel(grpTime, SWT.WRAP);
		message.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		message.setTopMargin(5);
		message.setText("");
		message.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		new Label(grpTime, SWT.NONE);
	}

	public void setTo(Dimension time) {
		if (time == null) {
			timeType = null;
			scopeResolution = stepResolution = null;
			time_end.setText("");
			time_start.setText("");
			time_step_multiplier.setText("");
			time_step.select(3);
			time_scope.select(3);
			time_type.select(0);
			coverageEnd.setText("");
			coverageStart.setText("");
			coverageUnit.select(4);
		} else {
			if (time.getParameters().contains(Geometry.PARAMETER_TIME_REPRESENTATION)) {
				switch (time.getParameters().get(Geometry.PARAMETER_TIME_REPRESENTATION, String.class)) {
				case "logical":
					time_type.select(1);
					break;
				case "physical":
					time_type.select(2);
					break;
				case "grid":
					time_type.select(3);
					break;
				case "real":
					time_type.select(4);
					break;
				default:
					time_type.select(0);
				}
			}
			if (time.getParameters().contains(Geometry.PARAMETER_TIME_SCOPE_UNIT)) {
				time_scope.select(Arrays.asList(time_scope.getItems()).indexOf(StringUtil.capitalize(
						time.getParameters().get(Geometry.PARAMETER_TIME_SCOPE_UNIT, String.class).toLowerCase())));
			}
//			if (time.getParameters().contains(Geometry.PARAMETER_TIME_)) {
//				time_scope.select(Arrays.asList(coverageUnit.getItems()).indexOf(
//						time.getParameters().get(Geometry.PARAMETER_TIME_SCOPE_UNIT, String.class).toUpperCase()));
//			}
			if (time.getParameters().contains(Geometry.PARAMETER_TIME_SCOPE)) {
				time_scope_multiplier.setText("" + time.getParameters().get(Geometry.PARAMETER_TIME_SCOPE));
			}
			if (time.getParameters().contains(Geometry.PARAMETER_TIME_START)) {
				Date date = new Date(time.getParameters().get(Geometry.PARAMETER_TIME_START, Long.class));
				time_start.setText(dateFormat.format(date));
			}
			if (time.getParameters().contains(Geometry.PARAMETER_TIME_END)) {
				Date date = new Date(time.getParameters().get(Geometry.PARAMETER_TIME_END, Long.class));
				time_end.setText(dateFormat.format(date));
			}
			if (time.getParameters().contains(Geometry.PARAMETER_TIME_COVERAGE_START)) {
				coverageStart.setText("" + time.getParameters().get(Geometry.PARAMETER_TIME_COVERAGE_START));
			}
			if (time.getParameters().contains(Geometry.PARAMETER_TIME_COVERAGE_END)) {
				coverageEnd.setText("" + time.getParameters().get(Geometry.PARAMETER_TIME_COVERAGE_END));
			}
			if (time.getParameters().contains(Geometry.PARAMETER_TIME_COVERAGE_UNIT)) {
				coverageUnit.select(Arrays.asList(coverageUnit.getItems()).indexOf(StringUtil.capitalize(
						time.getParameters().get(Geometry.PARAMETER_TIME_COVERAGE_UNIT, String.class).toLowerCase())));
			}

			// TODO grid - needs resolution and unit in geometry, not fixed MS interval.

			setEnablements(time_type.getText());
		}
	}

	protected void modified() {
		String geometry = getGeometry();
		if (listener != null) {
//			if (geometry != null) {
			listener.onValidModification(geometry);
//			}
		}
		/*
		 * TODO set colors for text and fields based on validity
		 */
	}

	protected Resolution.Type setResolution(String res) {

		Resolution.Type ret = Resolution.Type.valueOf(res.toUpperCase());

		// TODO constrain the unit for the step

		switch (ret) {
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

		return ret;
	}

	protected void setEnablements(String text) {
		switch (text) {
		case "None":
			timeType = null;
			time_scope.setEnabled(false);
			time_scope_multiplier.setEnabled(false);
			time_start.setEnabled(false);
			time_end.setEnabled(false);
			time_step_multiplier.setEnabled(false);
			time_step.setEnabled(false);
			coverageUnit.setEnabled(false);
			coverageEnd.setEnabled(false);
			coverageStart.setEnabled(false);
//			stepResolution = null;
			break;
		case "Generic":
			timeType = Type.LOGICAL;
			time_scope.setEnabled(true);
			time_scope_multiplier.setEnabled(true);
			time_start.setEnabled(true);
			time_end.setEnabled(true);
			time_step_multiplier.setEnabled(false);
			time_step.setEnabled(false);
			coverageUnit.setEnabled(true);
			coverageEnd.setEnabled(true);
			coverageStart.setEnabled(true);
//			stepResolution = null;
			break;
		case "Specific":
			timeType = Type.PHYSICAL;
			time_scope.setEnabled(true);
			time_scope_multiplier.setEnabled(true);
			time_start.setEnabled(true);
			time_end.setEnabled(true);
			time_step_multiplier.setEnabled(false);
			time_step.setEnabled(false);
			coverageUnit.setEnabled(false);
			coverageEnd.setEnabled(false);
			coverageStart.setEnabled(false);
//			stepResolution = null;
			break;
		case "Grid":
			timeType = Type.GRID;
			time_scope.setEnabled(false);
			time_scope_multiplier.setEnabled(false);
			time_step_multiplier.setEnabled(true);
			time_step.setEnabled(true);
			time_start.setEnabled(true);
			time_end.setEnabled(true);
			coverageUnit.setEnabled(false);
			coverageEnd.setEnabled(false);
			coverageStart.setEnabled(false);
			break;
		case "Real time":
			timeType = Type.REAL;
			time_scope.setEnabled(false);
			time_scope_multiplier.setEnabled(false);
			time_step_multiplier.setEnabled(true);
			time_step.setEnabled(true);
			time_start.setEnabled(true);
			time_end.setEnabled(true);
			coverageUnit.setEnabled(false);
			coverageEnd.setEnabled(false);
			coverageStart.setEnabled(false);
			break;
		}
	}

	public String getGeometry() {

		// happens at init
		if (this.message == null) {
			return null;
		}

		this.message.setText("");

		String ret = null;
		this.error = null;

		if (timeType == null) {
			return null;
		}
		switch (timeType) {
		case LOGICAL:
			ret = "\u03C4" + "1";
			break;
		case GRID:
		case REAL:
			ret = "T1";
			if (timeType == Type.REAL && time_end.getText().trim().isEmpty()) {
				ret += "(\u221E)";
			}
			break;
		case PHYSICAL:
			ret = "t1";
			break;
		default:
			break;
		}

		Date start = null;
		Date end = null;
		String startDesc = null;
		String endDesc = null;
		String covDesc = null;

		if (time_start.isEnabled() && !time_start.getText().trim().isEmpty()) {
			try {
				startDesc = time_start.getText().trim();
				DateFormat df = getFormat(startDesc);
				start = df == null ? null : df.parse(startDesc);
				if (df == null) {
					error = "Invalid format for start date";
				}
			} catch (ParseException e) {
				error = "Invalid format for start date";
			}
		}

		if (time_end.isEnabled() && !time_end.getText().trim().isEmpty()) {
			try {
				endDesc = time_end.getText().trim();
				DateFormat df = getFormat(endDesc);
				end = df == null ? null : df.parse(endDesc);
				if (df == null) {
					error = "Invalid format for end date";
				}
			} catch (ParseException e) {
				error = "Invalid format for end date";
			}
		}

		if (coverageUnit.isEnabled() && !coverageStart.getText().trim().isEmpty()) {
			String eend = "";
			if (!coverageEnd.getText().trim().isEmpty()) {
				eend = coverageEnd.getText().trim();
			} else {
				eend = "" + (Long.parseLong(coverageStart.getText().trim()) + 1);
			}
			covDesc = "coverageunit=" + coverageResolution.name().toLowerCase() + ",coveragestart="
					+ coverageStart.getText().trim() + ",coverageend=" + eend;
		}

		long step = -1;
		long divs = -1;
		Resolution.Type resolution = null;
		double resolutionMultiplier = 1;

		if (start != null && end != null) {
			long diff = end.getTime() - start.getTime();
			if (diff <= 0) {
				error = "End date not after the start date";
			}
		}

		String stepDesc = null;
		boolean haveStep = start != null && end != null && time_step.isEnabled()
				&& !time_step_multiplier.getText().trim().isEmpty();
		if (error == null && stepResolution != null) {
			resolution = haveStep ? stepResolution : scopeResolution;
			long len = -1;
			try {
				resolutionMultiplier = Double.parseDouble(time_scope_multiplier.getText().trim());
				len = (long) (resolutionMultiplier * stepResolution.getMilliseconds());
			} catch (Throwable t) {
				error = "Time scope multiplier is not a number";
			}

			if (error == null && start != null && end != null && time_step.isEnabled()
					&& !time_step_multiplier.getText().trim().isEmpty()) {
				// must be divisible
				long diff = end.getTime() - start.getTime();
				if (diff <= len) {
					error = "Time step larger than the interval";
				} else if ((diff % len) != 0) {
					error = "Time step does not divide the interval";
				} else {
					divs = diff / len;
					step = len;
					stepDesc = time_step_multiplier.getText() + " " + stepResolution;
					ret += ("(" + divs + ")");
				}
			}
		} else if (error != null && time_scope.isEnabled()) {
			resolution = scopeResolution;
		}

		if (error != null) {

			this.message.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			this.message.setText(error);

		} else {

			ret += "{";
			ret += "ttype=" + timeType.name().toLowerCase();
			if (resolution != null) {
				ret += ",tunit=" + resolution.name().toLowerCase();
				ret += ",tscope=" + resolutionMultiplier;
			}

			if (start != null) {
				ret += ",tstart=" + start.getTime();
			}
			if (end != null) {
				ret += ",tend=" + end.getTime();
			}
			if (step > 0) {
				ret += ",tstep=" + step;
			}
			if (covDesc != null) {
				ret += "," + covDesc;
			}

			ret += "}";
		}

		if (listener != null && error == null) {
			listener.onValidModification(ret);
		}

		return error != null ? null : ret;
	}

	private DateFormat getFormat(String text) {

		/*
		 * Return the date format most appropriate to the input and the current mode.
		 */
		String[] tokens = text.split("\\ ");

		// actual formats in passed tokens
		int nTokens = tokens.length;
		int nDashes = tokens.length >= 1 ? StringUtil.countMatches(tokens[0], "-") : -1;
		int nColons = tokens.length > 1 ? StringUtil.countMatches(tokens[1], ":") : -1;
		boolean mss = tokens.length > 1 && tokens[1].contains(".");

		/*
		 * Ensure we have the min tokens required
		 */
		if (nTokens > 2 || nDashes > 2
				|| nColons > 2 /* || nTokens < rTokens || nDashes < rDashes || nColons < rColons */) {
			return null;
		}

		/*
		 * Build the format based on the user input
		 */
		String format = null;

		if (nTokens >= 1) {
			switch (nDashes) {
			case 0:
				format = "yyyy";
				break;
			case 1:
				format = "MM-yyyy";
				break;
			case 2:
				format = "yyyy-MM-dd";
				break;
			}
		}

		if (nTokens > 1) {
			switch (nColons) {
			case 0:
				format += " HH";
				break;
			case 1:
				format += " HH:mm";
				break;
			case 2:
				format += " HH:mm:ss";
				break;
			}
		}

		if (mss) {
			format += ".SSSSSSS";
		}

		SimpleDateFormat ret = format == null ? null : new SimpleDateFormat(format);
		ret.setTimeZone(TimeZone.getTimeZone("UTC"));

		return ret;

	}

	public void validate() {
		isValid();
	}

	public boolean isValid() {
		return getGeometry() != null;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	
}
