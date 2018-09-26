package org.integratedmodelling.klab.rest;

import java.util.List;

/**
 * Simple wrapper for a list of strings encoding colors. Facilitates JSON handling.
 * 
 * @author ferdinando.villa
 *
 */
public class Colormap {

	public enum Type {
		VALUES,
		INTERVALS,
		RAMP
	}

	private List<String> colors;
	private List<String> labels;
	private Type type;

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
