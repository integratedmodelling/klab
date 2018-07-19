package org.integratedmodelling.klab.ide.navigator.e3;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.utils.ResourceManager;

public class ViewerLabelProvider extends LabelProvider implements IDescriptionProvider {

	public ViewerLabelProvider() {
	}

	WorkbenchLabelProvider delegate = new WorkbenchLabelProvider();

	public Image getImage(Object element) {
		if (element instanceof IProject) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/k-lab-icon-16.gif");
		}
		if (element instanceof IKimNamespace) {
			if (((IKimNamespace) element).isWorldviewBound()) {
				// return the icon for a script or semantic annotation
			}
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/namespace-checked.png");
		}
		if (element instanceof IKimConceptStatement) {
			IKimConceptStatement concept = (IKimConceptStatement) element;
			if (concept.getType().contains(Type.QUALITY)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/quality.png");
			}
			if (concept.getType().contains(Type.SUBJECT) || concept.getType().contains(Type.AGENT)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/subject.png");
			}
			if (concept.getType().contains(Type.PROCESS)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/process.png");
			}
			if (concept.getType().contains(Type.EVENT)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/event.png");
			}
			if (concept.getType().contains(Type.RELATIONSHIP)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/relationship.png");
			}
			if (concept.getType().contains(Type.IDENTITY)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/identity.png");
			}
			if (concept.getType().contains(Type.REALM)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/realm.png");
			}
			if (concept.getType().contains(Type.ATTRIBUTE)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/attribute.png");
			}
			if (concept.getType().contains(Type.ROLE)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/role.png");
			}
			if (concept.getType().contains(Type.CONFIGURATION)) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/configuration.png");
			}
		}
		if (element instanceof IKimModel) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/model.png");
		}
		if (element instanceof IKimObserver) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/realm.png");
		}
		return delegate.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof IKimNamespace) {
			if (((IKimNamespace) element).isWorldviewBound()) {
				// return the name of any test or run annotated or the base name of the resource
			}
			return ((IKimNamespace) element).getName();
		}
		if (element instanceof IKimConceptStatement) {
			return ((IKimConceptStatement) element).getName();
		}
		if (element instanceof IKimModel) {
			return ((IKimModel) element).getName();
		}
		if (element instanceof IKimObserver) {
			return ((IKimObserver) element).getName();
		}
		return delegate.getText(element);
	}

	@Override
	public String getDescription(Object element) {
		if (element instanceof IKimConceptStatement) {
			return ((IKimConceptStatement) element).getDocstring() == null ? ((IKimConceptStatement) element).getName()
					: ((IKimConceptStatement) element).getDocstring();
		}
		return delegate.getText(element);
	}
}