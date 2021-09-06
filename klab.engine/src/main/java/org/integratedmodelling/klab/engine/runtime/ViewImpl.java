package org.integratedmodelling.klab.engine.runtime;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;

public class ViewImpl implements IActorIdentity.View {

	private Layout layout;
	private Map<String, ViewComponent> staticComponents;
	private static Set<ViewComponent.Type> containerTypes;
	
	static {
		containerTypes = EnumSet.noneOf(ViewComponent.Type.class);
		containerTypes.add(ViewComponent.Type.Container);
		containerTypes.add(ViewComponent.Type.Group);
		containerTypes.add(ViewComponent.Type.Panel);
		containerTypes.add(ViewComponent.Type.View);
	}
	
	public ViewImpl(Layout layout) {
		this.layout = layout;
	}

	@Override
	public Layout getLayout() {
		return layout;
	}

	@Override
	public Map<String, ViewComponent> getStaticComponents() {
		if (this.staticComponents == null) {
			this.staticComponents = new HashMap<>();
			visitLayout(this.layout);
		}
		return this.staticComponents;
	}

	private void visitLayout(Layout view) {
		if (view.getHeader() != null) {
			visitComponent(view.getHeader());
		}

		for (ViewPanel panel : view.getLeftPanels()) {
			visitComponent(panel);
		}

		for (ViewPanel panel : view.getPanels()) {
			visitComponent(panel);
		}

		for (ViewPanel panel : view.getRightPanels()) {
			visitComponent(panel);
		}

		if (view.getFooter() != null) {
			visitComponent(view.getHeader());
		}

	}

	private void visitComponent(ViewComponent component) {
		
		if (!containerTypes.contains(component.getType())) {
			this.staticComponents.put(component.getId(), component);
		}
		
		for (ViewComponent child : component.getComponents()) {
			visitComponent(child);
		}

	}

}
