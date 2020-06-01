package org.integratedmodelling.klab.rest;

public class ViewPanel extends ViewComponent {
	
	public enum Location {
		Header,
		Footer,
		MainPanel,
		RightPanel,
		CenterPanel,
	}


	public ViewPanel() {}
	
	public ViewPanel(String name, String style) {
		setName(name);
		setStyle(style);
	}

}