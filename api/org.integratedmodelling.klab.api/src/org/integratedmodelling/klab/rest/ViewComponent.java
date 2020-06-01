package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.rest.ObservationReference.ValueType;

/**
 * The message used by the view actor to request view components. Any number of
 * these may be sent.
 * 
 * @author Ferd
 *
 */
public class ViewComponent {

	public static enum Type {
		Panel, Header, Footer, Alert, PushButton, CheckButton, RadioButton, TextInput, Combo, Group, Map, Tree,
		TreeItem, Confirm, View
		// etc
	}

	private String id;
	private String parentId;
	private Type type;
	private String name;
	private String style;
	private String title;
	private ValueType contentType;
	private String content;
	private List<String> possibleContent;
	// for groups
	private List<ViewComponent> components;
	private Map<String, String> data = new HashMap<>();

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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ValueType getContentType() {
		return contentType;
	}

	public void setContentType(ValueType contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getPossibleContent() {
		return possibleContent;
	}

	public void setPossibleContent(List<String> possibleContent) {
		this.possibleContent = possibleContent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ViewComponent> getComponents() {
		return components;
	}

	public void setComponents(List<ViewComponent> components) {
		this.components = components;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ViewComponent [parentId=" + parentId + ", type=" + type + ", name=" + name + ", title=" + title
				+ ", content=" + content + ", data=" + data + "]";
	}

	
	
}
