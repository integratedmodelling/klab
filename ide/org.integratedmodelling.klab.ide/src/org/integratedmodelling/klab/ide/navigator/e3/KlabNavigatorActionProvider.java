package org.integratedmodelling.klab.ide.navigator.e3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.api.resources.ResourceUtils;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EActorBehavior;
import org.integratedmodelling.klab.ide.navigator.model.EDefinition;
import org.integratedmodelling.klab.ide.navigator.model.EModel;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScript;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestCase;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationPage;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.utils.NameGenerator;

public class KlabNavigatorActionProvider extends CommonActionProvider {

	private static Map<String, KlabAction> actions = new HashMap<>();

	public KlabNavigatorActionProvider() {

		toolbar("New project...", "Create and load a new k.LAB project", "k-lab-icon-16.gif",
				() -> KlabNavigatorActions.createProject()).activate().saveAs("NewProject");

		action("Delete project", "Delete the selected project", "k-lab-icon-16.gif", EProject.class,
				(project) -> KlabNavigatorActions.deleteProject(project));
		action("New namespace...", "Create a new namespace", "namespace-checked.png", EProject.class,
				(project) -> KlabNavigatorActions.addNamespace(project));
		action("New behavior...", "Create a new behavior", "cog_add.png", EProject.class,
				(project) -> KlabNavigatorActions.addBehavior(project));
		action("Edit references", "Edit the bibliographic references and sections linked to the project",
				"documentation.gif", EProject.class, (project) -> KlabNavigatorActions.editReferences(project))
						.activate();
		action("Delete namespace", "Delete the selected namespace", "namespace-checked.png", ENamespace.class,
				(namespace) -> KlabNavigatorActions.deleteNamespace(namespace, wSite.getPage()));
		action("New application...", "Create a new application", "application_add.png", EScriptFolder.class,
				(folder) -> KlabNavigatorActions.addApplication(folder));
		action("New script...", "Create a new script file", "script.gif", EScriptFolder.class,
				(folder) -> KlabNavigatorActions.addScript(folder));
		action("New test case...", "Create a new test case", "test.gif", ETestFolder.class,
				(folder) -> KlabNavigatorActions.addTestCase(folder));
		action("Run test suite", "Run all the scripts in this folder as a test suite", "scripts.gif", ETestFolder.class,
				(folder) -> KlabNavigatorActions.runTestSuite(folder));
		action("View test reports", "View the test reports of all saved runs", "scripts.gif", ETestFolder.class,
				(folder) -> KlabNavigatorActions.viewTestReports(folder));
		action("Run script", "Delete the selected script", "script.gif", EScript.class,
				(namespace) -> KlabNavigatorActions.runScript(namespace));
		action("Run test case", "Delete the selected test case", "test.gif", ETestCase.class,
				(namespace) -> KlabNavigatorActions.runTestCase(namespace));
		action("Delete script", "Delete the selected script", "script.gif", EScript.class,
				(namespace) -> KlabNavigatorActions.deleteScript(namespace, wSite.getPage()));
		action("Delete behavior", "Delete the selected behavior", "cog_delete.png", EActorBehavior.class,
				(namespace) -> KlabNavigatorActions.deleteBehavior(namespace, wSite.getPage()));
        action("Localization...", "Edit localized strings", "cog_delete.png", EActorBehavior.class,
                (namespace) -> KlabNavigatorActions.editLocalization(namespace, wSite.getPage()));
		action("Delete script", "Delete the selected script", "script.gif", EScript.class,
				(namespace) -> KlabNavigatorActions.deleteScript(namespace, wSite.getPage()));
		action("Delete test case", "Delete the selected test case", "test.gif", ETestCase.class,
				(namespace) -> KlabNavigatorActions.deleteTestCase(namespace, wSite.getPage()));
		action("New resource...", "Create a new resource specifying adapter and parameters", "Database.png",
				EResourceFolder.class, (folder) -> KlabNavigatorActions.createResource(folder));
		action("Bulk import resources...", "Bulk import from a directory or a web service URL", "Database.png",
				EResourceFolder.class, (folder) -> KlabNavigatorActions.importResources(folder));
		action("Copy URN", "Copy the resource's URN to the clipboard", "copy.gif", EResource.class,
				(resource) -> Eclipse.INSTANCE.copyToClipboard(resource.getResource().getUrn())).activate();
        action("Copy URN", "Copy the model's URN to the clipboard", "copy.gif", EModel.class,
                (model) -> Eclipse.INSTANCE.copyToClipboard(model.getId())).activate();
        action("Copy shape", "Copy the resource's bounding box to the clipboard", "copy.gif", EResource.class,
				(resource) -> Eclipse.INSTANCE
						.copyToClipboard(ResourceUtils.extractShapeSpecification(resource.getResource()))).activate();
		action("Edit resource", "Edit the selected resource", "resource.gif", EResource.class,
				(resource) -> KlabNavigatorActions.editResource(resource.getResource()));
		action("Move resource...", "Move this resource to another project", "resource.gif", EResource.class,
				(resource) -> KlabNavigatorActions.moveResource(resource));
		action("Delete resource", "Delete the selected resource", "resource.gif", EResource.class,
				(resource) -> KlabNavigatorActions.deleteResource(resource));
		action("Export resource", "Export the selected resource", "resource.gif", EResource.class,
				(resource) -> KlabNavigatorActions.exportResource(resource)).activate((resource) -> {
					return !((EResource) resource).getExportFormats().isEmpty();
				});
		action("Edit documentation", "Edit the documentation for this model", "documentation.gif", EModel.class,
				(model) -> KlabNavigatorActions.editDocumentation(model)).activate((model) -> model.isDocumented());
		action("Edit documentation", "Edit the documentation for this item", "documentation.gif", EDefinition.class,
				(model) -> KlabNavigatorActions.editDocumentation(model)).activate((model) -> model.isDocumented());
		action("Add new documentation folder", "Add a documentation folder", "manual.gif", EDocumentationFolder.class,
				(resource) -> KlabNavigatorActions.addDocumentationSubsection(resource));
		action("Add new documentation page", "Add a new documentation page to reference with a model", "page.gif",
				EDocumentationFolder.class, (resource) -> KlabNavigatorActions.addDocumentationItem(resource))
						.onlyIf((resource) -> resource.getEParent() instanceof EDocumentationFolder);
		action("Add new target section", "Add a documentation section target for this item", "section.gif",
				EDocumentationPage.class, (resource) -> KlabNavigatorActions.addDocumentationSection(resource));
	}

	private ICommonViewerWorkbenchSite wSite;
	private boolean globalCreated;

	static class ActionDescriptor {

		String title;
		String tooltip;
		String icon;
		Function<ENavigatorItem, Boolean> checker;
		Function<ENavigatorItem, Boolean> onlyIf;
		Consumer<ENavigatorItem> action;
		Runnable voidAction;
		// 0 = with engine on; 1 = always active
		boolean activateUnconditionally;
		Function<ENavigatorItem, Boolean> activationCondition;
		String saveAs;
		ENavigatorItem item;

		ActionDescriptor activate() {
			this.activateUnconditionally = true;
			return this;
		}

		ActionDescriptor activate(Function<ENavigatorItem, Boolean> activationCondition) {
			this.activationCondition = activationCondition;
			// this avoids catching the second condition if the condition is true
			this.activateUnconditionally = true;
			return this;
		}

		ActionDescriptor onlyIf(Function<ENavigatorItem, Boolean> activationCondition) {
			this.onlyIf = activationCondition;
			return this;
		}

		ActionDescriptor saveAs(String id) {
			this.saveAs = id;
			return this;
		}

		ActionDescriptor withItem(ENavigatorItem item) {
			// ACHTUNG just set the item in the general descriptor instead of making a
			// copy and returning that. Should be OK as the UI is synchronous and the item
			// is set before each use.
			this.item = item;
			return this;
		}
	}

	@SuppressWarnings("unchecked")
	<T extends ENavigatorItem> ActionDescriptor action(String title, String tooltip, String icon, Class<T> applicable,
			Consumer<T> action) {

		ActionDescriptor ad = new ActionDescriptor();
		ad.title = title;
		ad.tooltip = tooltip;
		ad.icon = icon;
		ad.checker = (item) -> applicable.equals(item.getClass());
		ad.action = (Consumer<ENavigatorItem>) action;

		contextualDescriptors.add(ad);

		return ad;
	}

	<T extends ENavigatorItem> ActionDescriptor toolbar(String title, String tooltip, String icon, Runnable action) {

		ActionDescriptor ad = new ActionDescriptor();
		ad.title = title;
		ad.tooltip = tooltip;
		ad.icon = icon;
		ad.checker = (item) -> true;
		ad.voidAction = action;

		globalDescriptors.add(ad);

		return ad;
	}

	List<ActionDescriptor> contextualDescriptors = new ArrayList<>();
	List<ActionDescriptor> globalDescriptors = new ArrayList<>();

	@Override
	public void init(ICommonActionExtensionSite aSite) {
		super.init(aSite);
		ICommonViewerSite viewSite = aSite.getViewSite();
		if (viewSite instanceof ICommonViewerWorkbenchSite) {
			this.wSite = (ICommonViewerWorkbenchSite) viewSite;
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {

		super.fillContextMenu(menu);

		List<KlabAction> actions = new ArrayList<>();

		ISelection selection = wSite.getSelectionProvider().getSelection();
		if (!selection.isEmpty() && selection instanceof IStructuredSelection
				&& ((IStructuredSelection) selection).size() == 1
				&& ((IStructuredSelection) selection).getFirstElement() instanceof ENavigatorItem) {
			ENavigatorItem item = (ENavigatorItem) (((IStructuredSelection) selection).getFirstElement());
			for (ActionDescriptor descriptor : contextualDescriptors) {
				if (descriptor.checker.apply(item) && (descriptor.onlyIf == null || descriptor.onlyIf.apply(item))) {
					actions.add(new KlabAction(descriptor.withItem(item)));
				}
			}
		}

		if (actions.size() > 0) {
			String topOfMenu = menu.getItems()[0].getId();
			for (int i = actions.size() - 1; i >= 0; i--) {
				menu.insertBefore(menu.getItems()[0].getId(), actions.get(i));
			}
			menu.insertBefore(topOfMenu, new Separator());

		}
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {

		super.fillActionBars(actionBars);

		if (!globalCreated) {
			globalCreated = true;
			for (ActionDescriptor action : globalDescriptors) {
				actionBars.getToolBarManager().add(new KlabAction(action));
			}
		}
	}

	public class KlabAction extends Action {

		ISelectionProvider provider;
		IWorkbenchPage page;
		ENavigatorItem data;
		ActionDescriptor descriptor;

		public KlabAction(ActionDescriptor descriptor) {

			this.descriptor = descriptor;
			this.page = wSite.getPage();
			this.provider = wSite.getSelectionProvider();
			this.setText(descriptor.title);
			this.setToolTipText(descriptor.tooltip);
			this.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/" + descriptor.icon));

			if (descriptor.saveAs != null) {
				actions.put(descriptor.saveAs, this);
			}
		}

		@Override
		public boolean isEnabled() {

			if (descriptor.activationCondition != null
					&& (descriptor.item == null || !descriptor.activationCondition.apply(descriptor.item))) {
				return false;
			}

			if (!descriptor.activateUnconditionally && !Activator.engineMonitor().isRunning()) {
				return false;
			}

			if (descriptor.action != null) {
				ISelection selection = provider.getSelection();
				if (!selection.isEmpty()) {
					IStructuredSelection ssel = (IStructuredSelection) selection;
					if (ssel.size() == 1 && ssel.getFirstElement() instanceof ENavigatorItem) {
						data = (ENavigatorItem) (ssel.getFirstElement());
						return descriptor.checker.apply(data);
					}
				}
			}
			return descriptor.voidAction != null;
		}

		@Override
		public void run() {

			try {
				if (descriptor.action != null) {
					if (data == null) {
						return;
					}
					descriptor.action.accept(data);
				} else {
					descriptor.voidAction.run();
				}
			} catch (Throwable e) {
				Eclipse.INSTANCE.handleException(e);
			}
		}

		public void activate(boolean b) {
			this.descriptor.activateUnconditionally = b;
			setEnabled(b);
		}

		@Override
		public String getId() {
			return NameGenerator.shortUUID();
		}
	}

	public static KlabAction getAction(String id) {
		return actions.get(id);
	}

}
