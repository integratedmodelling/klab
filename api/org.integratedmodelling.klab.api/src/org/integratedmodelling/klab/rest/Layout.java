package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent from the back end to set up the interface. A Layout is a component with 0
 * or 1 header panel, 0 or 1 footer panel, 0 or more panels in the right, center
 * and left sections. The name of the view is the name of the behavior that
 * specifies it.
 * 
 * @author Ferd
 *
 */
public class Layout extends ViewComponent {

	public static final String DEFAULT_PANEL_NAME = "defaultpanel";

	private List<ViewPanel> panels = new ArrayList<>();
	private List<ViewPanel> leftPanels = new ArrayList<>();
	private List<ViewPanel> rightPanels = new ArrayList<>();
	private ViewPanel header;
	private ViewPanel footer;
	private String receivingIdentity;

	public Layout() {
		setType(Type.View);
	}

	public Layout(String behaviorName) {
		this();
		setName(behaviorName);
	}

	public List<ViewPanel> getPanels() {
		return panels;
	}

	public void setPanels(List<ViewPanel> panels) {
		this.panels = panels;
	}

	public ViewPanel getHeader() {
		return header;
	}

	public void setHeader(ViewPanel header) {
		this.header = header;
	}

	public ViewPanel getFooter() {
		return footer;
	}

	public void setFooter(ViewPanel footer) {
		this.footer = footer;
	}

	public List<ViewPanel> getLeftPanels() {
		return leftPanels;
	}

	public void setLeftPanels(List<ViewPanel> leftPanels) {
		this.leftPanels = leftPanels;
	}

	public List<ViewPanel> getRightPanels() {
		return rightPanels;
	}

	public void setRightPanels(List<ViewPanel> rightPanels) {
		this.rightPanels = rightPanels;
	}

	public boolean empty() {
		return panels.size() == 0 && footer == null && header == null && rightPanels.size() > 0
				&& leftPanels.size() > 0;
	}

	public String getReceivingIdentity() {
		return receivingIdentity;
	}

	public void setReceivingIdentity(String receivingIdentity) {
		this.receivingIdentity = receivingIdentity;
	}
}
