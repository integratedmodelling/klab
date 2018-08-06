package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes a possible action to be performed on an observation. The engine
 * sends all possible actions with each new observation and handles
 * ActionRequest with action reference ID.
 * 
 * @author ferdinando.villa
 *
 */
public class ActionReference {

	private String actionLabel;
	private String actionId;
	private boolean enabled = true;
	private boolean separator = false;
	private List<ActionReference> submenu = new ArrayList<>();

	public ActionReference() {
	}

	public ActionReference(String label, String id) {
		actionLabel = label;
		actionId = id;
	}

	public String getActionLabel() {
		return actionLabel;
	}

	public void setActionLabel(String actionLabel) {
		this.actionLabel = actionLabel;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isSeparator() {
		return separator;
	}

	public void setSeparator(boolean separator) {
		this.separator = separator;
	}

	public List<ActionReference> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<ActionReference> submenu) {
		this.submenu = submenu;
	}

}
