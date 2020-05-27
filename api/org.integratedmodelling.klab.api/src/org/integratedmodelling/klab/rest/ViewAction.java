package org.integratedmodelling.klab.rest;

import java.util.List;
import java.util.Map;

public class ViewAction {

	private boolean booleanValue;
	private double doubleValue;
	private int intValue;
	private String stringValue;
	private List<String> listValue;
	private Map<String, String> mapValue;

	public ViewAction() {
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

	public List getListValue() {
		return listValue;
	}

	public void setListValue(List listValue) {
		this.listValue = listValue;
	}

	public Map<String, String> getMapValue() {
		return mapValue;
	}

	public void setMapValue(Map<String, String> mapValue) {
		this.mapValue = mapValue;
	}

}
