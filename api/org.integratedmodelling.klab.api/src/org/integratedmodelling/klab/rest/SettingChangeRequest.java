package org.integratedmodelling.klab.rest;

public class SettingChangeRequest {

	public static enum Setting {
		InteractiveMode,
		LockSpace,
		LockTime
	}

	private Setting setting;
	private String previousValue;
	private String newValue;

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

}
