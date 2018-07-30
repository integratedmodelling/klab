package org.integratedmodelling.klab.ide.navigator.e3;

import java.util.ArrayList;
import java.util.List;
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
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScript;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestCase;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.utils.NameGenerator;

public class KlabNavigatorActionProvider extends CommonActionProvider {

	static {

		contextualDescriptors = new ArrayList<>();
		globalDescriptors = new ArrayList<>();

		toolbar("New project...", "Create and load a new k.LAB project", "k-lab-icon-16.gif",
				() -> KlabNavigatorActions.createProject());

		action("Delete project", "Delete the selected project", "k-lab-icon-16.gif", EProject.class,
				(project) -> KlabNavigatorActions.deleteProject(project));
		action("New namespace...", "Create a new namespace", "namespace-checked.png", EProject.class,
				(project) -> KlabNavigatorActions.addNamespace(project));
		action("Delete namespace", "Delete the selected namespace", "namespace-checked.png", ENamespace.class,
				(namespace) -> KlabNavigatorActions.deleteNamespace(namespace));
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
				(namespace) -> KlabNavigatorActions.deleteScript(namespace));
		action("Delete test case", "Delete the selected test case", "test.gif", ETestCase.class,
				(namespace) -> KlabNavigatorActions.deleteTestCase(namespace));
		action("Bulk import resources...", "Bulk import from a directory or a web service URL", "Database.png",
				EResourceFolder.class, (folder) -> KlabNavigatorActions.importResources(folder));
		action("Edit resource", "Edit the selected resource", "resource.gif", EResource.class,
				(resource) -> KlabNavigatorActions.editResource(resource));
		action("Delete resource", "Delete the selected resource", "resource.gif", EResource.class,
				(resource) -> KlabNavigatorActions.deleteResource(resource));
	}

	private ICommonViewerWorkbenchSite wSite;
	private boolean globalCreated;

	static class ActionDescriptor {

		String title;
		String tooltip;
		String icon;
		Function<ENavigatorItem, Boolean> checker;
		Consumer<ENavigatorItem> action;
		Runnable voidAction;
	}

	@SuppressWarnings("unchecked")
	static <T extends ENavigatorItem> void action(String title, String tooltip, String icon, Class<T> applicable,
			Consumer<T> action) {

		ActionDescriptor ad = new ActionDescriptor();
		ad.title = title;
		ad.tooltip = tooltip;
		ad.icon = icon;
		ad.checker = (item) -> applicable.equals(item.getClass());
		ad.action = (Consumer<ENavigatorItem>) action;

		contextualDescriptors.add(ad);
	}

	static <T extends ENavigatorItem> void toolbar(String title, String tooltip, String icon, Runnable action) {

		ActionDescriptor ad = new ActionDescriptor();
		ad.title = title;
		ad.tooltip = tooltip;
		ad.icon = icon;
		ad.checker = (item) -> true;
		ad.voidAction = action;

		globalDescriptors.add(ad);
	}

	static List<ActionDescriptor> contextualDescriptors;
	static List<ActionDescriptor> globalDescriptors;

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
				if (descriptor.checker.apply(item)) {
					actions.add(new KlabAction(descriptor));
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
		}

		@Override
		public boolean isEnabled() {
			
			if (!Activator.engineMonitor().isRunning()) {
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

		@Override
		public String getId() {
			return NameGenerator.shortUUID();
		}
	}

}
