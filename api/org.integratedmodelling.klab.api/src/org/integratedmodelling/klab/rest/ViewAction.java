package org.integratedmodelling.klab.rest;

import java.util.List;
import java.util.Map;

public class ViewAction {

	private ViewComponent component;
	
	private boolean booleanValue;
	private double doubleValue;
	private int intValue;
	private String stringValue;
	private List<String> listValue;
	private Map<String, String> mapValue;

	public ViewAction() {
	}

	public ViewAction(ViewComponent component) {
		this.component = component;
	}

	public ViewAction(ViewComponent component, String stringValue) {
		this.component = component;
		this.stringValue = stringValue;
	}

	
	public ViewAction(boolean response) {
		this.booleanValue = response;
	}

	public boolean isBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public List<String> getListValue() {
		return listValue;
	}

	public void setListValue(List<String> listValue) {
		this.listValue = listValue;
	}

	public Map<String, String> getMapValue() {
		return mapValue;
	}

	public void setMapValue(Map<String, String> mapValue) {
		this.mapValue = mapValue;
	}

	public ViewComponent getComponent() {
		return component;
	}

	public void setComponent(ViewComponent component) {
		this.component = component;
	}

}
