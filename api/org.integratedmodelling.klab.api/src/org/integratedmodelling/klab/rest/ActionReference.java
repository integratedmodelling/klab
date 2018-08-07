package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes a possible action to be performed on an observation. The engine
 * sends all possible actions with each new observation and handles
 * ActionRequest with action reference ID.
 * <p>
 * Actions may be regular ones (with the ID to communicate to the back end along
 * with the observation ID), separators (which do nothing except cause a
 * separator to be put in the menu) and downloads (which have a null actionId
 * but a non-null downloadUrl and downloadFileExtension, which should be handled
 * by the front end to produce a file download).
 * <p>
 * Actions may also have sub-actions, in which case they correspond to
 * sub-menus.
 * 
 * @author ferdinando.villa
 *
 */
public class ActionReference {

	private String actionLabel;
	private String actionId;
	private String downloadUrl;
	private String downloadFileExtension;
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

	public static ActionReference separator() {
		ActionReference ret = new ActionReference();
		ret.separator = true;
		return ret;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getDownloadFileExtension() {
		return downloadFileExtension;
	}

	public void setDownloadFileExtension(String downloadFileExtension) {
		this.downloadFileExtension = downloadFileExtension;
	}

}
