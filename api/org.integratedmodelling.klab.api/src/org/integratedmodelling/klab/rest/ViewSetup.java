package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.model.IAnnotation;

/**
 * Sent from the back end to set up the interface.
 * 
 * @author Ferd
 *
 */
public class ViewSetup {

	public static final String DEFAULT_PANEL_NAME = "defaultpanel";

	public static class Panel {
		private String name;
		private String style;

		public Panel() {}
		
		public Panel(String name, String style) {
			this.name = name;
			this.style = style;
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

	}

	private String style;
	private List<Panel> panels = new ArrayList<>();
	private Panel header;
	private Panel footer;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<Panel> getPanels() {
		return panels;
	}

	public void setPanels(List<Panel> panels) {
		this.panels = panels;
	}

	public Panel getHeader() {
		return header;
	}

	public void setHeader(Panel header) {
		this.header = header;
	}

	public Panel getFooter() {
		return footer;
	}

	public void setFooter(Panel footer) {
		this.footer = footer;
	}

}
