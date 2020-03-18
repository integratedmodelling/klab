package org.integratedmodelling.klab.rest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The message used by the view actor to request view components. Any number of
 * these may be sent.
 * 
 * @author Ferd
 *
 */
public class ViewComponent {

	public static enum Type {
		Panel, Header, Footer, Alert, PushButton, CheckButton, RadioButton, TextInput, Combo, Group, Map, Tree, TreeItem,
		// etc
	}

	private String id;
	private Type type;
	private String name;
	private String style;
	private Map<String, String> data = new LinkedHashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
