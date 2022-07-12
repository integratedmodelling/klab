package org.integratedmodelling.klab.ide.navigator.e3;

import java.util.logging.Level;

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
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.kim.api.IKimStatement.Scope;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EActorAction;
import org.integratedmodelling.klab.ide.navigator.model.EActorBehavior;
import org.integratedmodelling.klab.ide.navigator.model.EConcept;
import org.integratedmodelling.klab.ide.navigator.model.EDefinition;
import org.integratedmodelling.klab.ide.navigator.model.EDependency;
import org.integratedmodelling.klab.ide.navigator.model.EFolder;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.EModel;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EObservable;
import org.integratedmodelling.klab.ide.navigator.model.EAcknowledgement;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScript;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestCase;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationItem;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationPage;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationScope;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EReference;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EReferencesPage;
import org.integratedmodelling.klab.rest.NamespaceCompilationResult;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class ViewerLabelProvider extends LabelProvider
        implements
            IDescriptionProvider,
            IColorProvider,
            IFontProvider {

    public ViewerLabelProvider() {
    }

    Image getErrorMarker() {
        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "/icons/error_ovr.gif");
    }

    Image getWarningMarker() {
        return ResourceManager.getPluginImage("org.eclipse.ui.navigator.resources",
                "/icons/full/ovr16/warning_co.png");
    }

    Image getRunMarker() {
        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "/icons/run_co.gif");
    }

    Image getTestMarker() {
        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "/icons/template_co.gif");
    }

    Image getWorldviewMarker() {
        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "/icons/worldview_co.gif");
    }

    Image getAbstractMarker() {
        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "/icons/abstract_co.gif");
    }

    Image getPrivateMarker() {
        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "/icons/accessibility-private.gif");
    }

    Image getProjectPrivateMarker() {
        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "/icons/accessibility-project.gif");
    }

    WorkbenchLabelProvider delegate = new WorkbenchLabelProvider();

    public Image getImage(Object element) {

        Image ret = getBaseImage(element);

        if (ret == null) {
            // for now, only with domains etc. Should never happen at production.
            return ret;
        }

        boolean errors = false;
        boolean warnings = false;

        if (element instanceof ENavigatorItem) {
            errors = Activator.klab().hasNotifications((ENavigatorItem) element, Level.SEVERE);
            warnings = Activator.klab().hasNotifications((ENavigatorItem) element, Level.WARNING);
        }

        if (element instanceof EConcept && ((EConcept) element).isAbstract()) {
            ret = ResourceManager.decorateImage(ret, getAbstractMarker(), SWTResourceManager.TOP_RIGHT);
        }

        if (element instanceof EModel && ((EModel) element).getScope() == Scope.NAMESPACE
                || element instanceof ENamespace && ((ENamespace) element).getScope() == Scope.NAMESPACE) {
            ret = ResourceManager.decorateImage(ret, getPrivateMarker(), SWTResourceManager.TOP_RIGHT);
        }
        if (element instanceof EModel && ((EModel) element).getScope() == Scope.PROJECT
                || element instanceof ENamespace && ((ENamespace) element).getScope() == Scope.PROJECT) {
            ret = ResourceManager.decorateImage(ret, getProjectPrivateMarker(), SWTResourceManager.TOP_RIGHT);
        }

        if (errors) {
            ret = ResourceManager.decorateImage(ret, getErrorMarker(), SWTResourceManager.BOTTOM_LEFT);
        } else if (warnings) {
            ret = ResourceManager.decorateImage(ret, getWarningMarker(), SWTResourceManager.BOTTOM_LEFT);
        }

        return ret;
    }

    public Image getBaseImage(Object element) {

        if (element instanceof EProject) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                    ((EProject) element).isWorldview()
                            ? "icons/worldview_project.png"
                            : "icons/k-lab-icon-16.gif");
        }
        if (element instanceof ETestCase) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/test.gif");
        }
        if (element instanceof EScript) {
            return ResourceManager.decorateImage(
                    ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/script.gif"),
                    getRunMarker(), SWTResourceManager.TOP_LEFT);
        }
        if (element instanceof ENamespace) {
            NamespaceCompilationResult status = Activator.klab()
                    .getNamespaceStatus(((ENamespace) element).getName());
            boolean sonderklass = (status != null && status.isPublishable())
                    || ((ENamespace) element).getEParent(EProject.class).isWorldview();
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                    sonderklass
                            ? (((ENamespace) element).isScenario()
                                    ? "icons/globe.png"
                                    : "icons/namespace-checked.png")
                            : (((ENamespace) element).isScenario()
                                    ? "icons/globe.png"
                                    : "icons/namespace-unchecked.png"));
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
            Type observableType = ((EModel) element).getCoreObservableType();
            if (observableType != null) {
                switch(observableType) {
                /*
                 * TODO decoration
                 */
                case NOTHING:
                    // TODO SCREAM (at Javier)
                    break;
                case CONFIGURATION:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            "icons/configuration_resolver.png");
                case EVENT:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            ((EModel) element).isInstantiator()
                                    ? "icons/event_instantiator.png"
                                    : "icons/event_resolver.png");
                case PROCESS:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/process_resolver.png");
                case ATTRIBUTE:
                case TRAIT:
                case IDENTITY:
                case REALM:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            ((EModel) element).isInstantiator()
                                    ? "icons/attribute_instantiator.png"
                                    : "icons/attribute_resolver.png");
                case QUALITY:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/quality_resolver.png");
                case RELATIONSHIP:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            ((EModel) element).isInstantiator()
                                    ? "icons/relationship_instantiator.png"
                                    : "icons/relationship_resolver.png");
                case ROLE:
                    break;
                case SUBJECT:
                case AGENT:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            ((EModel) element).isInstantiator()
                                    ? "icons/subject_instantiator.png"
                                    : "icons/subject_resolver.png");
                default:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/model.png");
                }
            } else {
                return ResourceManager.decorateImage(
                        ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/model.png"),
                        getErrorMarker(), SWTResourceManager.BOTTOM_LEFT);
            }
        }
        if (element instanceof EObservable || element instanceof EDependency) {

            boolean isDependency = element instanceof EDependency;
            IKimObservable observable = (IKimObservable) ((EKimObject) element).getKimStatement();

            /*
             * TODO handle non-semantic first
             */
            Type observableType = observable.getMain() == null
                    ? null
                    : Kim.INSTANCE.getFundamentalType(observable.getMain().getType());

            if (observableType != null) {
                switch(observableType) {
                /*
                 * TODO decoration
                 */
                case NOTHING:
                    // TODO SCREAM (at Javier)
                    break;
                case CONFIGURATION:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            "icons/configuration_resolver.png");
                case EVENT:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            isDependency ? "icons/event_dependency.png" : "icons/event_observable.png");
                case PROCESS:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            isDependency ? "icons/process_dependency.png" : "icons/process_observable.png");
                case ATTRIBUTE:
                case TRAIT:
                case IDENTITY:
                case REALM:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            isDependency
                                    ? "icons/attribute_dependency.png"
                                    : "icons/attribute_observable.png");
                case QUALITY:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            isDependency ? "icons/quality_dependency.png" : "icons/quality_observable.png");
                case RELATIONSHIP:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            isDependency
                                    ? "icons/relationship_dependency.png"
                                    : "icons/relationship_observable.png");
                case ROLE:
                    break;
                case SUBJECT:
                case AGENT:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            isDependency ? "icons/subject_dependency.png" : "icons/subject_observable.png");
                default:
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                            isDependency
                                    ? "icons/nonsemantic_dependency.png"
                                    : "icons/nonsemantic_observable.png");
                }
            } else {
                // TODO use grey
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                        isDependency
                                ? "icons/nonsemantic_dependency.png"
                                : "icons/nonsemantic_observable.png");
            }
        }
        if (element instanceof IKimAcknowledgement)

        {
            if (Type.EVENT == ((EAcknowledgement) element).getCoreObservableType()) {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/eventObservation.gif");
            } else {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/subjectObservation.gif");
            }
        }
        if (element instanceof EResourceFolder) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/Database.png");
        }
        if (element instanceof EDocumentationFolder) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/manual.gif");
        }
        if (element instanceof EDocumentationPage) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/page.gif");
        }
        if (element instanceof EReferencesPage) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/book.gif");
        }
        if (element instanceof EDocumentationScope) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/trigger.gif");
        }
        if (element instanceof EDocumentationItem) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/section.gif");
        }
        if (element instanceof EFolder) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/folder_open.gif");
        }
        if (element instanceof EReference) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/bookmark.gif");
        }
        if (element instanceof EActorBehavior) {
            // TODO make it an app if app, add the component/puzzle thingy if component and a user
            // if
            // user
            if (((EActorBehavior) element).getType() == IKActorsBehavior.Type.COMPONENT
                    || ((EActorBehavior) element).getType() == IKActorsBehavior.Type.TRAITS) {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/plugin.gif");
            } else if (((EActorBehavior) element).getType() == IKActorsBehavior.Type.SCRIPT) {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/script.gif");
            } else if (((EActorBehavior) element).getType() == IKActorsBehavior.Type.APP) {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/start_application.png");
            } else if (((EActorBehavior) element).getType() == IKActorsBehavior.Type.UNITTEST) {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/ss9.png");
            } else {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/behavior.png");
            }
        }
        if (element instanceof EActorAction) {
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/action.gif");
        }
        if (element instanceof EScriptFolder) {
            return ResourceManager.decorateImage(
                    ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/application_double.png"),
                    getRunMarker(),
                    SWTResourceManager.TOP_LEFT);
        }
        if (element instanceof ETestFolder) {
            return ResourceManager.decorateImage(
                    ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/scripts.gif"),
                    getTestMarker(), SWTResourceManager.TOP_LEFT);
        }
        if (element instanceof EResource) {
            if (((EResource) element).getResource().isError()) {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/resource.gif");
            }
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                    ((EResource) element).getResource().getGeometry().startsWith("#")
                            ? "icons/resources.gif"
                            : "icons/resource.gif");
        }
        if (element instanceof EDefinition) {
            if (((EDefinition) element).getDefineClass() != null) {
                switch(((EDefinition) element).getDefineClass()) {
                case "table":
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/table.gif");
                case "chart":
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/chart_bar.png");
                }
            }
            return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/define.gif");
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
        if (element instanceof EAcknowledgement) {
            return ((EAcknowledgement) element).getName();
        }
        if (element instanceof EProject) {
            return ((EProject) element).getName();
        }
        if (element instanceof EActorBehavior) {
            return ((EActorBehavior) element).getName();
        }
        if (element instanceof EActorAction) {
            return ((EActorAction) element).getName();
        }
        if (element instanceof EDefinition) {
            return ((EDefinition) element).getName();
        }
        if (element instanceof EResourceFolder) {
            return "Resources";
        }
        if (element instanceof EFolder) {
            return MiscUtilities.getFileBaseName(((EFolder) element).getFolder());
        }
        if (element instanceof EDocumentationFolder) {
            return ((EDocumentationFolder) element).getName();
        }
        if (element instanceof EDocumentationPage) {
            return ((EDocumentationPage) element).getPagePath();
        }
        if (element instanceof EDocumentationItem) {
            return ((EDocumentationItem) element).getName();
        }
        if (element instanceof EDocumentationScope) {
            return ((EDocumentationScope) element).getName();
        }
        if (element instanceof EObservable) {
            return ((EObservable) element).getName();
        }
        if (element instanceof EDependency) {
            return ((EDependency) element).getName();
        }
        if (element instanceof EReferencesPage) {
            return "References";
        }
        if (element instanceof EReference) {
            return ((EReference) element).getName();
        }
        if (element instanceof EScriptFolder) {
            return "Apps";
        }
        if (element instanceof ETestFolder) {
            return "Test cases";
        }
        if (element instanceof EResource) {
            return ((EResource) element).getResource().getLocalName();
        }
        return delegate.getText(element);
    }

    @Override
    public String getDescription(Object element) {
        if (element instanceof EConcept) {
            return ((EConcept) element).getDocstring() == null
                    ? ((EConcept) element).getName()
                    : ((EConcept) element).getDocstring();
        }
        return delegate.getText(element);
    }

    @Override
    public Font getFont(Object element) {
        if (element instanceof EResource) {
            EResourceReference resource = ((EResource) element).getResource();
            if (resource.isOnline()) {
                return SWTResourceManager.getBoldFont(KlabNavigator.getViewerFont());
            }
        }
        if (element instanceof EDependency || element instanceof EObservable) {
            IKimObservable observable = (IKimObservable) ((EKimObject) element).getKimStatement();
            if (observable.getMain() != null && observable.getMain().is(Type.ABSTRACT)) {
                return SWTResourceManager.getItalicFont(KlabNavigator.getViewerFont());
            }
        }
        return null;
    }

    @Override
    public Color getForeground(Object element) {
        if (element instanceof EResource) {
            EResourceReference resource = ((EResource) element).getResource();
            return /* TODO errors in red */ SWTResourceManager
                    .getColor(resource.isOnline() ? SWT.COLOR_DARK_GREEN : SWT.COLOR_DARK_GRAY);
        }
        if (element instanceof EDependency || element instanceof EObservable) {
            IKimObservable observable = (IKimObservable) ((EKimObject) element).getKimStatement();
            if (observable.getMain() == null) {
                return SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY);
            }
        }
        return null;
    }

    @Override
    public Color getBackground(Object element) {
        // TODO Auto-generated method stub
        return null;
    }
}
