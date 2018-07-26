package org.integratedmodelling.klab.ide.navigator.e3;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EConcept;
import org.integratedmodelling.klab.ide.navigator.model.EModel;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.EObserver;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScript;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestCase;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;

public class ViewerLabelProvider extends LabelProvider implements IDescriptionProvider, IColorProvider, IFontProvider {

	public ViewerLabelProvider() {
	}

	WorkbenchLabelProvider delegate = new WorkbenchLabelProvider();

	public Image getImage(Object element) {
		if (element instanceof EProject) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/k-lab-icon-16.gif");
		}
		if (element instanceof ETestCase) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/script.gif");
		}
		if (element instanceof EScript) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/script.gif");
		}
		if (element instanceof ENamespace) {
			if (((ENamespace) element).isWorldviewBound()) {
				// return the icon for a script or semantic annotation
			}
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/namespace-checked.png");
		}
		if (element instanceof EConcept) {
			/*
			 * TODO decorations!
			 */
			EConcept concept = (EConcept) element;
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
		if (element instanceof EModel) {

			/*
			 * TODO handle non-semantic first
			 */

			switch (((EModel) element).getCoreObservableType()) {
			/*
			 * TODO decoration
			 */
			case CONFIGURATION:
				break;
			case EVENT:
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
						((EModel) element).isInstantiator() ? "icons/event_instantiator.png"
								: "icons/event_resolver.png");
			case IDENTITY:
				break;
			case NOTHING:
				break;
			case PROCESS:
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/process_resolver.png");
			case QUALITY:
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/quality_resolver.png");
			case REALM:
				break;
			case RELATIONSHIP:
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
						((EModel) element).isInstantiator() ? "icons/relationship_instantiator.png"
								: "icons/relationship_resolver.png");
			case ROLE:
				break;
			case SUBJECT:
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
						((EModel) element).isInstantiator() ? "icons/subject_instantiator.png"
								: "icons/subject_resolver.png");
			default:
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/model.png");
			}
		}
		if (element instanceof IKimObserver) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/realm.png");
		}
		if (element instanceof EResourceFolder) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/Database.png");
		}
		if (element instanceof EScriptFolder) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/scripts.gif");
		}
		if (element instanceof ETestFolder) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/scripts.gif");
		}
		if (element instanceof EResource) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/scripts.gif");
		}
		return delegate.getImage(element);
	}

	public String getText(Object element) {

		if (element instanceof ETestCase) {
			return ((ETestCase) element).getId();
		}
		if (element instanceof EScript) {
			return ((EScript) element).getId();
		}
		if (element instanceof ENamespace) {
			if (((ENamespace) element).isWorldviewBound()) {
				// return the name of any test or run annotated or the base name of the resource
			}
			return ((ENamespace) element).getName();
		}
		if (element instanceof EConcept) {
			return ((EConcept) element).getName();
		}
		if (element instanceof EModel) {
			return ((EModel) element).getName();
		}
		if (element instanceof EObserver) {
			return ((EObserver) element).getName();
		}
		if (element instanceof EProject) {
			return ((EProject) element).getName();
		}
		if (element instanceof EResourceFolder) {
			return "Resources";
		}
		if (element instanceof EScriptFolder) {
			return "Scripts";
		}
		if (element instanceof ETestFolder) {
			return "Test cases";
		}
        if (element instanceof EResource) {
            return ((EResource)element).getId();
        }
        return delegate.getText(element);
	}

	@Override
	public String getDescription(Object element) {
		if (element instanceof EConcept) {
			return ((EConcept) element).getDocstring() == null ? ((EConcept) element).getName()
					: ((EConcept) element).getDocstring();
		}
		return delegate.getText(element);
	}

	@Override
	public Font getFont(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof EResource) {
			EResourceReference resource = ((EResource) element).getResource();
			return /* TODO errors in red */ SWTResourceManager
					.getColor(resource.isOnline() ? SWT.COLOR_DARK_GREEN : SWT.COLOR_DARK_GRAY);
		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}
}
